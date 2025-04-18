package chessGameEngine;

import java.util.*;

final public class Board {

    Square[][] board;
    boolean isMate;
    boolean isCheck;
    int blackPoints, whitePoints;
    boolean isBlackTurn;
    Stack<Coin> captured;
    Stack<Integer> bPointHistory;
    Stack<Integer> wPointHistory;
    int kingRowBlack, kingColBlack;
    int kingRowWhite, kingColWhite;

    boolean check(boolean checkFor) {
        for (int r = 0; r < 8; ++r) {
            for (int c = 0; c < 8; ++c) {
                if (board[r][c].coin.isBlack ^ !checkFor) {
                    continue;
                }
                if (board[r][c].coin.isBlack) {
                    if (board[r][c].coin.move(r, c, kingRowWhite, kingColWhite, board)) {
                        return true;
                    }
                } else {
                    if (board[r][c].coin.move(r, c, kingRowBlack, kingColBlack, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void checkMate() {

    }

    void undo(int fromRow, int fromCol, int toRow, int toCol) {
        board[fromRow][fromCol].coin = board[toRow][toCol].coin;
        board[toRow][toCol].coin = captured.pop();
        if (board[fromRow][fromCol].coin instanceof King) {
            if (board[fromRow][fromCol].coin.isBlack) {
                kingColBlack = fromCol;
                kingRowBlack = fromRow;
            } else {
                kingColWhite = fromCol;
                kingRowWhite = fromRow;
            }
        }
        whitePoints = bPointHistory.pop();
        blackPoints = wPointHistory.pop();
    }

    void capture(int atRow, int atCol) {
        int point;
        Coin captureCoin = board[atRow][atCol].coin;

        if (captureCoin instanceof Rook) {
            point = 5;
        } else if (captureCoin instanceof Queen) {
            point = 9;
        } else if (captureCoin instanceof Bishop || captureCoin instanceof Knight) {
            point = 3;
        } else {
            point = 1;
        }
        if (board[atRow][atCol].coin.isBlack) {
            blackPoints += point;
        } else {
            whitePoints += point;
        }
        captured.push(captureCoin);

        board[atRow][atCol].coin = null;
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 7) {
            return false;
        }
        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            return false;
        }
        if (board[fromRow][fromCol].coin == null) {
            return false;
        }
        if (!board[fromRow][fromCol].coin.move(fromRow, fromCol, toRow, toCol, board)) {
            return false;
        }
        boolean wasCheck = isCheck;
        if (board[toRow][toCol].coin != null) {
            if (board[toRow][toCol].coin.isBlack == board[fromRow][fromCol].coin.isBlack) {
                return false;
            }
            capture(toRow, toCol);
        } else {
            board[toRow][toCol].coin = board[fromRow][fromCol].coin;
            board[fromRow][fromCol] = null;
            captured.push(null);
        }
        bPointHistory.push(blackPoints);
        wPointHistory.push(whitePoints);
        if (board[fromRow][fromCol].coin instanceof King) {
            if (board[fromRow][fromCol].coin.isBlack) {
                kingColBlack = toCol;
                kingRowBlack = toRow;
            } else {
                kingColWhite = toCol;
                kingRowWhite = toRow;
            }
        }
        check(isBlackTurn);
        if (wasCheck && isCheck) {
            undo(fromRow, fromCol, toRow, toCol);
            return false;
        }
        isBlackTurn = !isBlackTurn;
        check(isBlackTurn);
        return true;
    }

    void resetBoard() {
        blackPoints = 0;
        whitePoints = 0;
        isMate = false;
        isCheck = false;
        isBlackTurn = false;
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
    }

    Board() {
        board = new Square[8][8];
        captured = new Stack<Coin>();
        bPointHistory = new Stack<Integer>();
        wPointHistory = new Stack<Integer>();
        boolean colorStart = false;
        for (int i = 0; i < 8; ++i) {
            boolean colorCurrent = colorStart;
            for (int j = 0; j < 8; ++j) {
                board[i][j] = new Square(colorCurrent);
                colorCurrent = !colorCurrent;
            }
            colorStart = !colorStart;
        }
        resetBoard();
    }
}
