# 📝TP2 Text editor

## Command and memento patter project

This git repository contains the java text editor project. It has been realized in the context of 
of the course tools and methods for development.

Development configuration : Windows 11 (updated) + IntelliJ 2022.2.2 + openJDK 18.0.2

📄 Design and implementation reports are at the root of the repository

## 1️⃣V1

### 📁 Files description

The V1 folder of this project contains a design folder in which are stored all the plantUML codes 
plantUML codes that were used to design the project as well as the src folder that contains any 
contains any implementation.

The design folder is separated between the UML scripts and the produced images, this allows to find the images more easily if they are
easier to find the images if they are too small on the report (especially the 
class diagram).

The src folder is a Java module that contains a source folder (src), separated in
two parts. First we find the implementation package with all
the class files. There are the command files (Copy, Cut, Delete, Insert, Paste
and Replace), the invoker file (DummyInvoker), the implementation of the reciever (Engine) 
as well as the GUI (MyGUI), the buffer (SimpleBuffer) and the clipboard (SimpleClipboard).

Then we find the interfaces folder which, as its name indicates, contains all the interfaces 
interfaces useful to the project and which are referenced in the class diagram.

### 🛠️ How to use

When the program is run, the interface opens. It is composed of a banner with :

* The indicator of the position of the first cursor and its modifiable label
* The indicator of the position of the second cursor and its modifiable label
* The three buttons for copying, cutting and pasting


![V1 interface](/dat/V1/interface.jpg)

The labels that indicate the position of the cursors can be modified, they govern the position of the
of the cursors. If the indicated value is greater than the length of the text, the position is 
automatically set to the maximum position. It is not possible to insert non-numeric characters
characters, the cursor will move to the end of the text. 

⚠️⚠️ To change the position of a cursor, do not forget to press enter

For the copy/cut buttons, they use the position of the two cursors, regardless of the relative position of 
relative to each other. To paste on a selection, the second cursor must be on a different 
To paste on a selection, the second cursor must be on a different position than the first cursor.

⚠️⚠️ It is possible to delete text. However, since the basic component of the GUI 
component is a JTextArea whose setEditable(false) function has been called, windows interprets the deletion request as an 
request as an error and will make a characteristic sound. Cell does not affect the deletion 
Cell does not affect the deletion functionality, because it is internal to the application.

## 2️⃣  V2

### 📁 Files description

The V2 folder was created by duplicating the V1 folder. The architecture is more or less the same. 
the same. The main difference is in the srcV2 folder. The arrangement of the files 
implementation is organized according to the different design patterns and the concrete commands 
are grouped together in a Command sub-folder. A script folder has been added to 
user-created scripts and a dat folder to store the icons of the application. 
icons of the application.

![](/V2/srcV2/src/dat/copy.png)
![](/V2/srcV2/src/dat/cut.png)
![](/V2/srcV2/src/dat/paste.png)
![](/V2/srcV2/src/dat/undo.png)
![](/V2/srcV2/src/dat/redo.png)
![](/V2/srcV2/src/dat/script.png)
![](/V2/srcV2/src/dat/save.png)
![](/V2/srcV2/src/dat/execute.png)
![](/V2/srcV2/src/dat/file.png)
![](/V2/srcV2/src/dat/exit.png)

### 🛠️How to use

When you launch the interface of V2, you notice that there have been changes:
    
* The buttons have been replaced by a menu banner
* the color theme has changed
* the main banner has become a small information banner at the bottom

![Interface V2](/dat/V2/Interface.png)

All the features of V1 are still available, the copy, cut and paste buttons are all in the menu
all in menu>edit. This section also includes the two new features undo and redo
to undo an action or cancel an undo.


Scripts are now available! These scripts will save all commands until the 
save. To play a script, you have to go to menu>script>load and choose the script to be executed 
from the list of available scripts.

⚠️⚠️ Be sure to specify a script name before saving. To name a script, you just have to enter the
name in the "Script name" field.

Other new features:

* The two cursors are now synchronous. To make a selection, you just have to move the second
the second cursor.


* It is possible to quit the application from the menu>system>exit


* The carriage return has been added to the list of available characters


* It is possible to move the main cursor with the left and right arrows and the second cursor with the up 
second cursor with the up and down arrows.

⚠️⚠️ It is not possible for the second cursor to be in front of the first cursor.

* Keyboard shortcuts are available! 
  * copy => ctrl + F1
  * cut => ctrl + F2
  * paste => ctrl + F3
  * undo => ctrl + F4
  * redo => ctrl + F5
