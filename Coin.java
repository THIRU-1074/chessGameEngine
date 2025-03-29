package chessGameEngine;

abstract class Coin {

    boolean isBlack;//coin color

    Coin(boolean color) {
        this.isBlack = color;
    }

    abstract boolean move(int fromRow, int fromCol, int toRow, int toCol, Square[][] board);

    boolean isValidRookMove(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        if (fromRow == toRow) {
            int j = 1;
            if (fromCol > toCol) {
                j = -1;
            }
            for (int c = fromCol; c < toCol; c += j) {
                if (board[toRow][c] != null) {
                    return false;
                }
            }
        } else if (fromCol == fromRow) {
            int i = 1;
            if (fromCol > toCol) {
                i = -1;
            }
            for (int r = fromRow; r < toRow; r += i) {
                if (board[r][toCol] != null) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    boolean isValidBishopMove(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        if (Math.abs(fromRow - toRow) != Math.abs(fromCol - toCol)) {
            return false;
        }
        int i = 1, j = 1;
        if (fromRow > toCol) {
            i = -1;
        }
        if (fromCol > toCol) {
            j = -1;
        }
        for (int r = fromRow; r < toRow; r += i) {
            for (int c = fromCol; c < toCol; c += j) {
                if (board[r][c].coin != null) {
                    return false;
                }
            }
        }
        return true;
    }
}

class King extends Coin {

    King(boolean color) {
        super(color);
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        return (Math.abs(fromRow - toRow) == 1 ^ Math.abs(fromCol - toCol) == 1);
    }
}

class Queen extends Coin {

    Queen(boolean color) {
        super(color);
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        return (isValidBishopMove(fromRow, fromCol, toRow, toCol, board) || isValidRookMove(fromRow, fromCol, toRow, toCol, board));
    }
}

class Bishop extends Coin {

    Bishop(boolean color) {
        super(color);
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        return isValidBishopMove(fromRow, fromCol, toRow, toCol, board);
    }
}

class Rook extends Coin {

    Rook(boolean color) {
        super(color);
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        return isValidRookMove(fromRow, fromCol, toRow, toCol, board);
    }
}

class Knight extends Coin {

    Knight(boolean color) {
        super(color);
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 1) {
            return true;
        }
        if (Math.abs(fromRow - toRow) == 1 && Math.abs(fromCol - toCol) == 2) {
            return true;
        }
        return false;
    }
}

class Pawn extends Coin {

    Pawn(boolean color) {
        super(color);
    }

    boolean move(int fromRow, int fromCol, int toRow, int toCol, Square[][] board) {
        if (Math.abs(toCol - fromCol) > 1) {
            return false;
        }
        if (isBlack && toRow >= fromRow) {
            return false;
        }
        if (!isBlack && toRow <= fromRow) {
            return false;
        }
        if (Math.abs(toCol - fromCol) == 1 && board[toRow][toCol].coin == null) {
            return false;
        }
        return true;
    }
}
