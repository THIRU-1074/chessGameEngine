package chessGameEngine;

public class Square {

    boolean isColorBlack;
    Coin c;

    Square(boolean color) {
        this.c = null;
        this.isColorBlack = color;
    }
}
