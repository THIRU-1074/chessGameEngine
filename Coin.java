package chessGameEngine;

abstract class Coin {

    boolean isBlack;//coin color

    Coin(boolean color) {
        this.isBlack = color;
    }

    abstract boolean move(int toRow, int toCol);

    boolean capture(int atRow, int atCol) {

    }
}

class King extends Coin {

    King(boolean color) {
        super(color);
    }

    boolean move(int toRow, int toCol) {

    }
}

class Queen extends Coin {

    Queen(boolean color) {
        super(color);
    }

    boolean move(int toRow, int toCol) {

    }
}

class Bishop extends Coin {

    Bishop(boolean color) {
        super(color);
    }

    boolean move(int toRow, int toCol) {

    }
}

class Rook extends Coin {

    Rook(boolean color) {
        super(color);
    }

    boolean move(int toRow, int toCol) {

    }
}

class Knight extends Coin {

    Knight(boolean color) {
        super(color);
    }

    boolean move(int toRow, int toCol) {

    }
}

class Pawn extends Coin {

    Pawn(boolean color) {
        super(color);
    }

    boolean move(int toRow, int toCol) {

    }
}
