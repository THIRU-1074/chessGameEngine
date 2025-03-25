package chessGameEngine;

public class Board{
    Square[][] board;
    boolean isMate;
    boolean isCheck;
    int blackPoints,whitePoints;
    void reSetBoard(){

    }
    Board(){
        board=new Square[8][8];
        blackPoints=0;
        whitePoints=0;
        boolean colorInd=false;
        for(int i=0;i<8;++i){
            board[1][i]=new Square(colorInd);
            board[1][i].c=new Pawn(true);
            colorInd=!colorInd;
        }

    }
}