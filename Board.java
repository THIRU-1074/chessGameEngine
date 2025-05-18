package chessGameEngine;

import java.util.*;

final public class Board {

    Square[][] board;
    boolean isMate;
    boolean isCheck;
    int blackPoints, whitePoints;
    boolean isBlackTurn;
    Stack<Coin> captured;
    Stack<String> prevMoves;
    int kingRowBlack, kingColBlack;
    int kingRowWhite, kingColWhite;
    boolean canBCastle, canWCastle;

    boolean check(boolean checkForBlack) {
        for (int r = 0; r < 8; ++r) {
            for (int c = 0; c < 8; ++c) {
                if (board[r][c].coin == null) {
                    continue;
                }
                if (board[r][c].coin.isBlack == checkForBlack) {
                    continue;
                }
                if (board[r][c].coin.isBlack) {
                    if (board[r][c].coin.move(r, c, kingRowWhite, kingColWhite, board)) {
                        System.out.println(r + " " + c);
                        return true;
                    }
                } else {
                    if (board[r][c].coin.move(r, c, kingRowBlack, kingColBlack, board)) {
                        System.out.println(r + "   " + c);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean anyLegalMoveForCoin(int row, int col) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (move(row, col, i, j, false)) {
                    // System.out.println("From " + row + " " + col + " To " + i + " " + j);
                    undo();
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkMate(boolean checkMateForBlack) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j].coin == null
                        || board[i][j].coin.isBlack != checkMateForBlack) {
                    continue;
                }
                if (anyLegalMoveForCoin(i, j)) {
                    return false;
                }
            }
        }
        isMate = true;
        return true;
    }

    void undo() {
        if (prevMoves.isEmpty()) {
            return;
        }
        isBlackTurn = !isBlackTurn;
        String prevMove = prevMoves.pop();
        canBCastle = (prevMove.charAt(0) == '1') ? (true) : (false);
        canWCastle = (prevMove.charAt(1) == '1') ? (true) : (false);
        int fromRow = prevMove.charAt(2) - 48, fromCol = prevMove.charAt(3) - 48;
        int toRow = prevMove.charAt(4) - 48, toCol = prevMove.charAt(5) - 48;
        board[fromRow][fromCol].coin = board[toRow][toCol].coin;
        board[toRow][toCol].coin = captured.pop();
        if (board[fromRow][fromCol].coin instanceof King) {
            if (board[fromRow][fromCol].coin.isBlack) {
                kingRowBlack = fromRow;
                kingColBlack = fromCol;
            } else {
                kingRowWhite = fromRow;
                kingColBlack = fromCol;
            }
        }
        if (board[toRow][toCol].coin == null) {
            return;
        }
        if (board[toRow][toCol].coin.isBlack) {
            whitePoints -= pointOf(board[toRow][toCol].coin);
        } else {
            blackPoints -= pointOf(board[toRow][toCol].coin);
        }
    }

    int pointOf(Coin captureCoin) {
        int point;
        if (captureCoin == null) {
            point = 0;
        } else if (captureCoin instanceof Rook) {
            point = 5;
        } else if (captureCoin instanceof Queen) {
            point = 9;
        } else if (captureCoin instanceof Bishop || captureCoin instanceof Knight) {
            point = 3;
        } else {
            point = 1;
        }
        return point;
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol, boolean verbose) {
        if (fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 7) {
            if (verbose) {
                System.out.println("Move out of Bound...");
            }
            return false;
        }
        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            if (verbose) {
                System.out.println("Move out of Bound...");
            }
            return false;
        }
        if (board[fromRow][fromCol].coin == null) {
            if (verbose) {
                System.out.println("No Coin is there...");
            }
            return false;
        }
        if (board[fromRow][fromCol].coin.isBlack != isBlackTurn) {
            if (verbose) {
                System.out.println("Its not your Turn...");
            }
            return false;
        }
        if (!board[fromRow][fromCol].coin.move(fromRow, fromCol, toRow, toCol, board)) {
            if (verbose) {
                System.out.println("The coin move is not feasible...");
            }
            return false;
        }
        if (board[toRow][toCol].coin != null) {
            if (board[toRow][toCol].coin.isBlack == board[fromRow][fromCol].coin.isBlack) {
                if (verbose) {
                    System.out.println("Don't Capture of your army.....");
                }
                return false;
            }
            captured.push(board[toRow][toCol].coin);
            int point = pointOf(board[toRow][toCol].coin);
            if (board[toRow][toCol].coin.isBlack) {
                whitePoints += point;
            } else {
                blackPoints += point;
            }
        } else {
            captured.push(null);
        }

        if (board[fromRow][fromCol].coin instanceof King) {
            if (board[fromRow][fromCol].coin.isBlack) {
                kingColBlack = toCol;
                kingRowBlack = toRow;
            } else {
                kingColWhite = toCol;
                kingRowWhite = toRow;
            }
        }
        String str = "";
        str += (canBCastle) ? ('1') : ('0');
        str += (canWCastle) ? ('1') : ('0');
        str += (char) (fromRow + 48);
        str += (char) (fromCol + 48);
        str += (char) (toRow + 48);
        str += (char) (toCol + 48);
        prevMoves.push(str);
        if (board[toRow][toCol].coin instanceof King) {
            if (isBlackTurn) {
                canBCastle = false;
            } else {
                canWCastle = false;
            }
        }
        board[toRow][toCol].coin = board[fromRow][fromCol].coin;
        board[fromRow][fromCol].coin = null;
        boolean flag = check(isBlackTurn);
        isBlackTurn = !isBlackTurn;
        if (flag) {
            undo();
            if (verbose) {
                System.out.println("There is Check on move...");
            }
            return false;
        }
        return true;
    }

    void resetBoard() {
        captured = new Stack<>();
        prevMoves = new Stack<>();
        canBCastle = true;
        canWCastle = true;
        blackPoints = 0;
        whitePoints = 0;
        isMate = false;
        isCheck = false;
        isBlackTurn = false;
        kingColBlack = 'e' - 97;
        kingColWhite = 'e' - 97;
        kingRowBlack = 7;
        kingRowWhite = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                board[i][j].coin = null;
            }
        }
        for (int i = 0; i < 8; ++i) {
            board[6][i].coin = new Pawn(true);
            board[1][i].coin = new Pawn(false);
            switch (i) {
                case 0, 7 -> {
                    board[7][i].coin = new Rook(true);
                    board[0][i].coin = new Rook(false);
                }
                case 1, 6 -> {
                    board[7][i].coin = new Knight(true);
                    board[0][i].coin = new Knight(false);
                }
                case 2, 5 -> {
                    board[7][i].coin = new Bishop(true);
                    board[0][i].coin = new Bishop(false);
                }
                case 3 -> {
                    board[7][i].coin = new Queen(true);
                    board[0][i].coin = new Queen(false);
                }
                default -> {
                    board[7][i].coin = new King(true);
                    board[0][i].coin = new King(false);
                }
            }
        }
    }

    Board() {
        board = new Square[8][8];

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
