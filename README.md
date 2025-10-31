# java-jeuEchec

 ## Présentation
Ce projet consiste à modéliser et implémenter en Java une partie d’échecs simplifiée entre deux joueurs humains.  
Le programme arbitre la partie, vérifie la validité des coups et affiche l’état de l’échiquier en mode texte.

## Fonctionnalités
- Saisie et enregistrement des noms des joueurs  
- Affichage textuel de l’échiquier et de la liste des coups joués  
- Saisie et validation des coups des joueurs  
- Détection de la fin de partie : victoire ou partie nulle  
- (Optionnel) Gestion d’une horloge pour chaque joueur  

## Règles simplifiées
- Pas de roque, promotion, ni prise en passant  
- Les déplacements sont validés selon le type de pièce (Tour, Fou, Cavalier, Dame, Roi, Pion)  
- Le programme vérifie que le roi n’est jamais en prise  

## Organisation du projet
Le projet a été réalisé en binôme :  
- Chaque membre prend en charge certaines classes (`Joueur`, `Partie`, `Echiquier`, `Piece`, etc.)  
- Le code respecte la modélisation UML établie avant l’implémentation  

### Classes principales
- `Partie` : arbitre la partie, gère le tour des joueurs et la fin de partie  
- `Echiquier` : représente l’échiquier et ses cases  
- `Case` : représente une case de l’échiquier  
- `Piece` (abstraite) et ses sous-classes : `Tour`, `Fou`, `Cavalier`, `Dame`, `Roi`, `Pion`  
- `Joueur` : stocke le nom et les informations du joueur  

## Lancer le programme
Depuis un terminal, dans le dossier contenant le projet Java :  

```bash
# Compiler toutes les classes
javac *.java

# Lancer la partie
java Partie
Ce projet a été réalisé en binôme en utilisant Java, Visual Studio Code, Git pour le versioning, et des tests pour vérifier le bon fonctionnement des classes.
