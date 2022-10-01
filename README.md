# 📝TP2 Editeur de texte 

Ce dépot git contient le projet d'éditeur de texte en java. Il a été réalisé dans le cardre 
du cours outils et méthodes pour le développement.

configuration de développement : Windows 11 (à jour) + IntelliJ 2022.2.2 + openJDK 18.0.2
## 1️⃣V1

### 📁 Descirption des fichiers 

Le dossier V1 de ce projet contient un dossier de conception dans lequel sont rangés tous 
les codes plantUML qui ont servis à la conception du projet ainsi que le dossier src qui 
contient toute l'implementation.

Le dossier src est un module Java qui contient un dossier source (src), séparé en
deux parties. Tout d'abord on trouve le package d'implémentation avec tous
les fichiers de classes. Il y a les fichiers de commandes (Copy, Cut, Delete, Insert, Paste
et Replace), le fichier de l'invoker (DummyInvoker), l'implémentation du reciever (Engine) 
ainsi que la GUI (MyGUI), le buffer (SimpleBuffer) et le presse-papier(SimpleClipboard).

On trouve ensuite le dossier interfaces qui, comme son nom l'indique, contient toutes les 
interfaces utiles au projet et qui sont référencées dans le diagramme de classes.

### 🛠️Mode d'emploi

Lors de l'éxécution du programme, l'interface s'ouvre. Elle est composée d'un bandeau avec :

* L'indicateur de la position du premier curseur et son label modifiable
* L'indicateur de la position du second curseur et son label modifiable
* Les trois boutons pour copier, couper et coller


![Interface v1](/dat/V1/interface.jpg)

Les labels qui indiquent la position des curseurs sont modifiables, ils régissent la positions
des curseurs. Si la veleur indiquées sont supérieur à la longueur du texte, la position se 
regle automatiquement à la position maximale. Il n'est pas possible de mettre des caractères
non numeriques, le curseur se mettra à la fin du texte. 

⚠️⚠️ Pour chagner la position d'un curseur, ne pas oublier d'appuyer sur entrer

Pour les boutons copier/couper, ils utilisent la position des deux curseurs, peu importe la 
position relative de l'un par rapport à l'autre. Pour coller sur une selection, il faut que le 
second curseur soit sur une position différente du premier curseur.

⚠️⚠️ Il est possible de supprimer du texte. Toutefois, comme le composante de base de la GUI est un 
JTextArea dont on a appelé la fonction setEditable(false), windows interprete la demande de 
supression comme un erreur et fera un petit son caractéristique. Celà n'affecte en rien la 
fonctionalité de supression car elle est interne à l'application.

## V2
