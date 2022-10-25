# 📝TP2 Editeur de texte 

Ce depot git contient le projet d'éditeur de texte en java. Il a été réalisé dans le cadre 
du cours outils et méthodes pour le développement.

Configuration de développement : Windows 11 (à jour) + IntelliJ 2022.2.2 + openJDK 18.0.2

📄 Les rapports de conception et d'implémentation sont à la racine du repository

## 1️⃣V1

---

### 📁 Description des fichiers 

Le dossier V1 de ce projet contient un dossier de conception dans lequel sont rangés tous 
les codes plantUML qui ont servis à la conception du projet ainsi que le dossier src qui 
contient toute implementation.

Le dossier de conception est séparé entre les scripts UMLs et les images produites, cela permet de
retrouver plus facilement les images si elles sont trop petites sur le rapport (notement le 
diagramme de classes).

Le dossier src est un module Java qui contient un dossier source (src), séparé en
deux parties. Tout d'abord on trouve le package d'implémentation avec tous
les fichiers de classes. Il y a les fichiers de commandes (Copy, Cut, Delete, Insert, Paste
et Replace), le fichier de l'invoker (DummyInvoker), l'implémentation du reciever (Engine) 
ainsi que la GUI (MyGUI), le buffer (SimpleBuffer) et le presse-papier(SimpleClipboard).

On trouve ensuite le dossier interfaces qui, comme son nom l'indique, contient toutes les 
interfaces utiles au projet et qui sont référencées dans le diagramme de classes.

### 🛠️Mode d'emploi

Lors de l'exécution du programme, l'interface s'ouvre. Elle est composée d'un bandeau avec :

* L'indicateur de la position du premier curseur et son label modifiable
* L'indicateur de la position du second curseur et son label modifiable
* Les trois boutons pour copier, couper et coller


![Interface v1](/dat/V1/interface.jpg)

Les labels qui indiquent la position des curseurs sont modifiables, ils régissent la position
des curseurs. Si la valeur indiquée sont supérieures à la longueur du texte, la position se 
règle automatiquement à la position maximale. Il n'est pas possible de mettre des caractères
non numériques, le curseur se mettra à la fin du texte. 

⚠️⚠️ Pour changer la position d'un curseur, ne pas oublier d'appuyer sur entrer

Pour les boutons copier/couper, ils utilisent la position des deux curseurs, peu importe la 
position relative de l'un par rapport à l'autre. Pour coller sur une selection, il faut que le 
second curseur soit sur une position différente du premier curseur.

⚠️⚠️ Il est possible de supprimer du texte. Toutefois, comme la composante de base de la GUI 
est un JTextArea dont on a appelé la fonction setEditable(false), windows interpret la demande de 
suppression comme une erreur et fera un petit son caractéristique. Cell n'affecte en rien la 
fonctionnalité de suppression, car elle est interne à l'application.

## 2️⃣  V2

---

### 📁 Description des fichiers

Le dossier V2 a été créé en dupliquant le dossier V1. L'architecture est à peu de choses près la 
même. Le principal différence réside dans le dossier srcV2. Le rangement des fichiers 
implementation est organisé selon les différents patrons de conception et les commandes concrètes 
sont regroupées ensemble dans un sous dossier de Command. Un dossier de script à été ajouté pour 
stocker les scripts créés par l'utilisateur et un dossier dat pour stocker les icons de 
l'application.

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
![](/V2/srcV2/src/dat/redo.png)

### 🛠️Mode d'emploi

Lorsque l'on lance l'interface de la V2, on remarque qu'il y a eu du changement :
    
* Les boutons ont étés remplacés par un bandeau de menu
* le theme de couleur a changé
* le bandeau principal est devenu un petit bandeau d'information en bas

![Interface V2](/dat/V2/Interface.png)

Toutes les fonctionnalités de la V1 sont toujours disponibles, les boutons copy, cut et paste sont
tous dans menu>edit. Cette section comporte aussi les deux nouvelles fonctionnalités undo et redo
pour annuler une action ou annuler une annulation.


Les scripts sont maintenant disponibles ! Ces scripts enregistreront toutes les commandes jusqu'à la 
sauvegarde. Pour jouer un script, il faut aller dans menu>script>load et choisir le script à 
exécuter parmi la liste des scripts disponibles.

⚠️⚠️ Veillez à bien indiquer un nom de script avant d'enregistrer. Pour nommer un script, il
suffit d'entrer le nom voulu dans le champ "Script name".

Autres nouveautés :

* Les deux curseurs sont maintenant synchrones. Pour faire une selection, il suffit de déplacer le
deuxième curseur.


* Il est possible de quitter l'application depuis le menu>system>exit


* Le retour chariot a été ajouté dans la liste des caractères disponibles


* il est possible de déplacer le curseur principal avec les flèches de droite et de gauche et le 
second curseur avec les fleches haut et bas.

⚠️⚠️ Il n'est pas possible pur le second curseur d'être devant le premier curseur.

* Des raccourcis claviers sont disponibles ! 
  * copier => ctrl + F1
  * couper => ctrl + F2
  * coller => ctrl + F3
  * undo   => ctrl + F4
  * redo   => ctrl + F5