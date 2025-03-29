package chessGameEngine;

public class Board {

    Square[][] board;
    boolean isMate;
    boolean isCheck;
    int blackPoints, whitePoints;

    void check() {

    }

    void checkMate() {

    }

    void capture(int atRow, int atCol) {
        int point;
        Coin captureCoin = board[atRow][atCol].coin;
        if (captureCoin instanceof Rook) {
            point = 5; 
        }else if (captureCoin instanceof Queen) {
            point = 9; 
        }else if (captureCoin instanceof Bishop || captureCoin instanceof Knight) {
            point = 3; 
        }else {
            point = 1;
        }
        if (board[atRow][atCol].coin.isBlack) {
            blackPoints += point; 
        }else {
            whitePoints += point;
        }
        board[atRow][atCol].coin = null;
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 7) {
            return false;
        }
        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            return false;
        }
        if (board[fromRow][fromCol] == null) {
            return false;
        }
        if (!board[fromRow][fromCol].coin.move(fromRow, fromCol, toRow, toCol, board)) {
            return false;
        }
        if (board[toRow][toCol].coin != null) {
            if (board[toRow][toCol].coin.isBlack == board[fromRow][fromCol].coin.isBlack) {
                return false;
            }
            capture(toRow, toCol);
        }
        board[toRow][toCol].coin = board[fromRow][fromCol].coin;
        board[fromRow][fromCol] = null;
        return true;
    }

    boolean resetBoard() {
        blackPoints = 0;
        whitePoints = 0;
        isMate = false;
        isCheck = false;
        boolean colorInd = false;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j].coin = null;
            }
        }
        for (int i = 0; i < 8; ++i) {
            board[1][i].coin = new Pawn(true);
            board[6][i].coin = new Pawn(false);
            colorInd = !colorInd;
            if (i == 0 || i == 7) {
                board[0][i].coin = new Rook(true);
                board[7][i].coin = new Rook(false);
            } else if (i == 1 || i == 6) {
                board[0][i].coin = new Knight(true);
                board[7][i].coin = new Knight(false);
            } else if (i == 2 || i == 5) {
                board[0][i].coin = new Bishop(true);
                board[7][i].coin = new Bishop(false);
            } else if (i == 3) {
                board[0][i].coin = new Queen(true);
                board[7][i].coin = new Queen(false);
            } else {
                board[0][i].coin = new King(true);
                board[7][i].coin = new King(false);
            }
        }
        return true;
    }

    Board() {
        board = new Square[8][8];
        boolean colorStart = false;
        blackPoints = 0;
        whitePoints = 0;
        for (int i = 0; i < 8; ++i) {
            boolean colorCurrent = colorStart;
            for (int j = 0; j < 8; ++j) {
                board[i][j] = new Square(colorStart);
                colorCurrent = !colorCurrent;
            }
            colorStart = !colorStart;
        }
        resetBoard();
    }
}
