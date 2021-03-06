# Chess Game

by Charles Li & Max Sun. Original repo hosted on [BitBucket](https://bitbucket.org/ms2814/55.git) and pushed to GitHub on 5/10/22

Implementaion of Chess written in Java. Play against a friend locally in a terminal :D

<img src="https://user-images.githubusercontent.com/50348516/167724150-4729a892-c23d-4cb5-b4de-46c0e1881317.png" width=800>

### How to run via Eclipse IDE
1. Open the */Chess55* folder as a project in Eclipse
2. Run *Chess.java*


### How to run via Command Line
1. Open the */Chess55/Chess55/src* directory in terminal

2. Compile **piece.java** & **Chess.java** found in */chess* directory using `javac chess/*.java`

3. Run `java chess.Chess` to start playing

### How to Play

Moves are specified by entering the coordinate of the piece you want to move followed by the destination you wish to move it to.

`White's move:`  `e2 e3` at the start of a new game moves white pawn at E2 one square forward to E3.

If a draw occurs, the game will notify the player and the opposing player may accept the draw with `draw`

At any point a player may resign with `resign`


### How to view the javadocs documentaion
1. Open */Chess55/docs/chess* folder

2. Open *Chess.html* & *piece.html* using a web browser

