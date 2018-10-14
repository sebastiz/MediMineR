package Input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.prefs.Preferences;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;



//So using the methodology above.....Both files should be organised by line alphabetically
//1. Iterate through the folder. in main
//2. Get the file text and organise all the lines alphabetically. FUNCTION 1
				//a. Pass the text to this function
				//b. Identify and remove things like tables for separate processing.
				//c. Split the lines of remaining text by newline and then into length chunk eg every 20 characters.
				//d. Organise lines alphabetically- perhaps a function already exists for this

//3. Then read the first line of file "Comp" and compare it to all the lines in file "Ref" FUNCTION 2
				//a. Store line as a String variable
				//b. Iterate through the lines in Ref and compare with LevenshteinDistance
				//c. If line < certain distance then add to file called Terms
				//d. Iterate to the next line in Comp and repeat until Comp file ends
				//e. Then move to the next Comp file
			//f. Once this is complete then go to the next Comp file
//4. Once all the iterations have been done then alphabetically organise "Terms" and compare line 1 to line 2 and line 2 to line 3 etc. FUNCTION 3
//5. Remove line above if < a certain similarity distance from current line FUNCTION 3
//6. Repeat this until all lines sufficiently different FUNCTION 3
//7. Then use this final file as a key-value locator in the files to create a hash-map. FUNCTION 4

//Each line in the incoming file is then compared line by line with the reference file.
//If less than a certain distance then line is stored in another file which will become the terms to lookup
//All the similars are then ordered in this file and then each term is compared to the one below iteratively
//until the match between each term is > a certain distance
//Then all the terms in all the files that have string distance < a certain amount are converted to this new term
//And the terms are also used as the lookup fields.

public class FileInputter {
	static HashSet<String> myFiles = new HashSet<String>();
	public static Preferences prefs;
	//static String filename="/Users/sebastianzeki/Desktop/ADETOSOYE%2cO_4566185S_final.rtf";
	//static String filename2="/Users/sebastianzeki/Documents/PhysJava/BugFolder - Copy/BugFolder - Copy/HRM_test/MEDAIYESE Esther_1392879Q_28042016_HRM.pdf";
	static File child;
	static Path p;
	public FileInputter() {
	}


public static void main(String[] args) throws IOException, SAXException, TikaException, SQLException, ParseException, URISyntaxException{
//Tester line for github
    //Iterate through the folder structure:
	//File file = new File("/Users/sebastianzeki/Documents/PhysJava/BugFolder - Copy/BugFolder - Copy/HRM_test");
	File file = new File("/Users/sebastianzeki/Documents/PhysJava/BugFolder - Copy/BugFolder - Copy/Imp_Test/rtf");
	//File file = new File("/Users/sebastianzeki/Documents/PhysJava/BugFolder - Copy/BugFolder - Copy/Imp_Test/pdf/Test");
	//File file = new File("/Users/sebastianzeki/Desktop/TestMediMineR");

	//Declare the folder of interest
	Collection<File> dir = FileUtils.listFiles(file, null, true);


	        //Add everything to a final Set so duplicates are removed.
			Set<String> AllStrings =new HashSet<String>();

			//ArrayList to put the final set in so can then order all the strings
			ArrayList<String> FinalArray=new ArrayList<String>();


	//Iterate through the folder:
	for(File file2 : dir){
		//This is the reference file all the others will compare to:
		String File1=FileIn(file2.getAbsolutePath());
        //Clean the reference file:
		ArrayList<String> RefFile=stringCutter(File1);
		//Now we iterate through all the other files so we can make a comparison

		for(File file3 : dir){
			String File2=FileIn(file3.getAbsolutePath());

			//Iterate through folder here and declare compFile
			if(file3.getAbsolutePath()!=file2.getAbsolutePath()){
			ArrayList<String> CompareFile=stringCutter(File2);

			//Compare the files and add the result to a Set to prevent duplicates
		    AllStrings.addAll(compareStrings(RefFile,CompareFile));

			}
			 System.gc();
	  }
	}
	 FinalArray.addAll(AllStrings);
     Collections.sort(FinalArray);

     //Test to examine the arrayList
     for (String theResult:FinalArray){

      	// System.out.println("THE FINALARRAY: "+theResult);
        }
     fileOut(FinalArray);
		}


//Method to get parse the original document and get the raw text from the input file .
public static String FileIn(String filename) throws IOException, SAXException, TikaException, SQLException, ParseException, URISyntaxException{

	//Convert everything to a pdf here.
	//Perhaps extract the tables here using pdfBox - not sure if tika needs to parse first to allow this to happen.


    AutoDetectParser parser = new AutoDetectParser();
	BodyContentHandler handler = new BodyContentHandler(-1);
	Metadata metadata = new Metadata();
	FileInputStream inputstream = new FileInputStream(new File(filename));
	ParseContext context = new ParseContext();
	parser.parse(inputstream, handler, metadata, context);
	String s=null;
    s =handler.toString();
    handler=null;
    context=null;
    inputstream.close();
    parser=null;
    System.gc();
    System.out.println(s);


    //Need to do a find and replace here for whitespace
    s=s.replaceAll("\\s+([^a-zA-Z]+)", "$1").replaceAll("([^a-zA-Z]+)\\s+", "$1");

    return s;
}


//Method to extract the tables from the original text so that the rest of the extraction is on a table -less form
//How do I identify tables? Do I need to do differently per document type??
public static ArrayList<String> tableCutterMain(String s) throws FileNotFoundException {


	//How to identify tables
		//1. May have to separate by file type
			//a. pdf use pdfbox
			//b. rtf these are usually tabulated
			//doc ?
			//docx ??
		//1. Line of words closest to the lines
	return null;

}

public static ArrayList<String> tableCutterpdf(String s) throws FileNotFoundException {
	//use traprange
	return null;
}
public static ArrayList<String> tableCutterdoc(String s) throws FileNotFoundException {
	//Use the XWPF or HPPF
	return null;
}
public static ArrayList<String> tableCutterdocx(String s) throws FileNotFoundException {
	//Use the XWPF or HPPF
	return null;
}
public static ArrayList<String> tableCutterrtf(String s) throws FileNotFoundException {
	//Tabs usually define this type of document
	return null;
}

//Method to cut each line up into chunks so that compareString method can compare chunk by chunk
public static ArrayList<String> stringCutter(String stee) throws FileNotFoundException {

  //Split the text up by newline before you chunk each line- ie split each line by a certain number of characters: (split in a nested loop):
  //Get rid of trailing whitespace so all characters start from the same starting point:

  String [] nd=stee.split("\n");

  ArrayList<String> strings1 = new ArrayList<String>();
  for (int ig=0;ig<nd.length;ig++){
  	 int index = 0;
  	 //Now split each line by a certain number of characters and get rid of leading and final whitespace
		 while (index < nd[ig].length()) {
			 	String p=null;
				 //Is this actually taking the whole line in 15 character chunks or just the first 15 characters?
				 p=cleanUp(nd[ig].substring(index, Math.min(index + 40,nd[ig].length())));

		        strings1.add(p);
		        index += 3;
		    }
	}
//return an Array list that is a list of cleaned chunked parts of all the lines.
  return(strings1);
}


//Method to calculate the Levenshtein distance
public static int distance(String a, String b) {
  a = a.toLowerCase();
  b = b.toLowerCase();
  // i == 0
  int [] costs = new int [b.length() + 1];
  for (int j = 0; j < costs.length; j++)
      costs[j] = j;
  for (int i = 1; i <= a.length(); i++) {
      // j == 0; nw = lev(i - 1, j)
      costs[0] = i;
      int nw = i - 1;
      for (int j = 1; j <= b.length(); j++) {
          int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
          nw = costs[j];
          costs[j] = cj;
      }
  }
  return costs[b.length()];
}


//Method to compare all strings from consecutive files using Levenstein distance
public static Set<String> compareStrings(ArrayList<String> strings, ArrayList<String> compArray) throws FileNotFoundException {

//the idea is to return a set with all the strings that are similar between two text files but without duplicates (hence the return of a Set).
	  Set<String> Srr =new HashSet<String>();

	  //We will have to avoid comparing files that are the same as they will return all of the same variables
    for (String temp : strings) {
  	 //Compare the reference Array string (from the outer Array called temp to each of the comparison Array strings (called inner)
  	  for (String compareString : compArray) {

  		  //Do the comparison string by string
  		  //Write down in result arrayList which were sufficiently similar
  		  LevenshteinDistance ld = LevenshteinDistance.getDefaultInstance();

            //Now match similar strings from the two files so get the matches only
  		  //First put them in a Set so duplicates are removed, then put the set in an ArrayList to order them:
  		        Integer distance = ld.apply(compareString, temp);
  		      //Add to the set if sufficiently similar
  		        if (distance < 2 )
  		        	Srr.add(compareString.trim());
  		        //Perhaps if a certain number of positions are the same then keep those positions that are the same
  	  }
    }



	return Srr;
}


//Method to cleanUp the terms that are common between files.
public static String cleanUp(String stringIn) throws FileNotFoundException {


    //This method cleans the string coming out of the compareStrings method
	//1. Digits digitalised
	stringIn=stringIn.replaceAll("\\d+\\.*\\d+", "");
	//2. Digits on their own at the start
	stringIn=stringIn.replaceAll("\\^\\d*\\s*\\d*", "");
	//3. Digits on their own at the end
	stringIn=stringIn.replaceAll("\\s*\\d+$", "");
	//Only allow strings with a capital at the start through.
    //4. All trailing and leading punctuation
	//stringIn=stringIn.replaceAll("^[^a-zA-Z]*", "");
	//stringIn=stringIn.replaceAll("[^a-zA-Z]*$", "");
	//5. Remove all gaps between letter and a bracket
	//stringIn=stringIn.replaceAll(" [(]", "(");

	//Any trailing punctuation apart from closed brackets
	//Get rid of anything that starts with a lower case letter
	  stringIn=stringIn.replaceAll("^[^A-Z].*", "");
      stringIn=stringIn.replaceAll("[^0-9A-Za-z\\)]+\\s*$", "");
      stringIn=stringIn.replaceAll("^[^A-Z].*", "");
	//Get rid of lines that have no words
	//System.out.println("HEREPre "+stringIn);
	//Remove any line with lower case to start as unlikely to be.
	//stringIn=stringIn.replaceAll("^[a-z].*", "");
	//stringIn=stringIn.replaceAll("^[a-z0-9]*.*", "");
	return stringIn;
   }

//Method to create the find and replace dictionary
public static void fileOut(ArrayList<String> arrayIn) throws FileNotFoundException {

PrintStream out = new PrintStream(new FileOutputStream("FindAndReplace.txt"));
    for (int i = 0; i < arrayIn.size(); i++)
      out.println(arrayIn.get(i).toString() + ": ");
    out.close();

}


//Method to use the find and replace from the find and replace dictionary
public static String findNReplace(String n) throws IOException, URISyntaxException{

	BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.class.getResourceAsStream("/Files/FindAndReplace.txt")),2048);

	String [] split=null;
	ArrayList<String> orig= new ArrayList<String>();
	String [] orig_arr=null;
	ArrayList<String> newDoc= new ArrayList<String>();
	String [] newDoc_arr=null;


    try {
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();

        while (line != null) {
        	split=line.split(":");
        	//System.out.println("SPLIT"+split);
        		orig.add(split[1]);
        		newDoc.add(split[0]);
            sb.append(line);
            sb.append("\n");

            line = reader.readLine();
        }

    } finally {
    	reader.close();
    }

    orig_arr = new String[orig.size()];
    orig_arr = orig.toArray(orig_arr);
    newDoc_arr = new String[newDoc.size()];
    newDoc_arr = newDoc.toArray(newDoc_arr);

    String replacer=StringUtils.replaceEach(n, orig_arr, newDoc_arr);
    newDoc_arr=null;
    orig_arr=null;
    n=null;

	return replacer;

	}


}
