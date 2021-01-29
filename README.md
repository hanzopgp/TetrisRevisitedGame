# Tetris revisité :

## Table des matières

1. [Presentation](#presentation-)
2. [Utilisation](#utilisation-)
3. [Fonctionnement du jeu](#fonctionnement-du-jeu-)
4. [Touches](#touches-)
5. [Autres details](#autres-details-)
6. [Liens utiles](#liens-utiles-)

## Présentation :

Jeu ressemblant au tetris, seulement le but est de former le rectangle avec la plus grande aire avec un nombre de coups max. <br>
L3 Informatique, note : ??/20.

## Utilisation :

### Compiler le projet :
  
>On utilise **ant** pour créer le dossier dist contenant l'executable grace au fichier `build.xml`
   
Ouvrez un terminal a la racine du projet et tapez : `ant`

### Executer le project :
    
Toujours a la racine du projet tapez : `java -jar dist/Tetris-0.1.jar`

## Fonctionnement du jeu :

>L'idée s'inspire du fameux Tetris, mais les modalités sont différentes :
  >- toutes les pièces sont exposées sur l'aire de jeu dès le début de la partie,
  >- à tout moment, le joueur peut choisir une pièce en cliquant dessus, et a alors la
  >possibilité de la faire tourner ou de la déplacer,
  >- son but est de minimiser l'espace occupé par l'ensemble des pièces. Plus précisément, la fonction d'évaluation sera l'aire du plus petit rectangle (parallèle aux
  >axes) recouvrant l'ensemble des pièces,
  >- lorsque le joueur considère avoir terminé (ou lorsque le nombre maximum d'actions autorisées est atteint), il clique sur un bouton est son score est alors calculé,
  >- à chaque partie, il est possible soit de demander à obtenir une nouvelle configuration de départ, soit charger une configuration déjà créée et sauvegardée. Dans ce
  >dernier cas, le meilleur score ainsi que le nom du joueur correspondant seront
  >sauvegardés.
  >- Une option permettra de faire jouer l'ordinateur. Réaliser un bon joueur robot nécessiterait des connaissances solides en IA, mais vous essaierez de faire mieux
  >que le hasard.
  
## Touches :

- En terminal il suffit de suivre les consignes pour jouer.
- En mode graphique il faut appuyer sur la touche que l'on veut manipuler, les touches pour manipuler sont :
  - Z,Q,S,D pour les mouvements
  - A,E pour les rotations
  - T pour que le solver joue un coup

## Autres details :

- Le solver n'étant pas optimisé un niveau de difficulté "SolverTest" a été créé pour pouvoir le tester et voir son fonctionnement.
- Dans l'interface graphique le solveur joue coup par coup, alors que dans le terminal il joue coup par coup jusqu'a la fin de la partie.
- Le solver n'utilise pas les rotations de base car cela ajoute beaucoup de temps d'attente pour la resolution.
- Problème de fuite mémoire résolu après analyse avec VisualJM, amenant la suppresion de la classe PointWithScore et de l'affichage du type d'aire.

## Liens utiles :

- https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller
- https://www.geeksforgeeks.org/maximum-size-rectangle-binary-sub-matrix-1s/
- https://visualvm.github.io/
