# CHESS_ENGINE

**Targets**

1.Initial Design Based on the string based move triggers [Accomplished]

-> Clone the Repository to your local Machine <br>
-> cd to the Directory of the Package folder <br>

> javac chessGameEngine/\*.java <br>
> java chessGameEngine.Test <br>
> just Type **from** and **to** Positions<br> [ fromCol[a-h] fromRow[1-8] toCol[a-h] toRow[1-8] ]<br>

    (eg) e2e4,a7a6....

2.UI plugin <br>
-> Initally Stage to just Display the board,moves and Turn<br>
![GUI](Game_Visual_Stage2.png)
3.Training the DL model to develop the Bot [Reinforcement Learning]<br>
->Stockfish Engine is used for BestMoves ,the custom model would be implemented though the perfermance couldn't be as much still an exciting to try implement one.<br>
->The custom model would follow the UCI guidelines for communication with GameEngine.
4.Connect the UI and model for a complete Engine
