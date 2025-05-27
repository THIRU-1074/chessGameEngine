package chessGameEngine;

class Square {

    boolean isColorBlack;
    Coin coin;

    Square(boolean color) {
        this.coin = null;
        this.isColorBlack = color;
    }
}
