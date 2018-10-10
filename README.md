# MediMineR

This is a project to automatically detect fields for extraction by comparing similar phrases across documents.

The initial output is a list of common terms which should be used as boundaries for extraction

The outputted file needs to be manually manipulated by the user to ensure that replacement is meaningful and also because the find and replace dictionary may need domain specific knowledge which is beyond the scope of the programme.

Once the find and replace dictionary is satisfactory the replacement can then be done and then automatically the values associated with the key will be extracted and cleaned for the user to use.

It also extracts from tables

The end result is a large HashMap with all values from each document which can then be used for any further analysis
