package chessGameEngine;

public class Square {

    boolean isColorBlack;
    Coin coin;

    Square(boolean color) {
        this.coin = null;
        this.isColorBlack = color;
    }
}
