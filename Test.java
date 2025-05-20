package chessGameEngine;

import java.util.*;

class Test {

    public static void printCoin(Coin c) {
        if (c == null) {
            System.out.print(" . ");
            return;
        }
        String res = "";
        if (c.isBlack) {
            res += "b";
        } else {
            res += "w";
        }
        if (c instanceof King) {
            res += "K";
        } else if (c instanceof Queen) {
            res += "Q";
        } else if (c instanceof Knight) {
            res += "N";
        } else if (c instanceof Bishop) {
            res += "B";
        } else if (c instanceof Rook) {
            res += "R";
        } else {
            res += "P";
        }
        System.out.print(res + " ");
    }

    public static void display(Board b) {
        for (int row = 7; row >= 0; --row) {
            for (int col = 0; col < 8; ++col) {
                printCoin(b.board[row][col].coin);
            }
            System.out.println();
        }
    }

    public static void moveParser(String move, Board b) {
        int fromRow = (int) (move.charAt(1) - 49);
        int fromCol = (int) (move.charAt(0) - 97);
        int toRow = (int) (move.charAt(3) - 49);
        int toCol = (int) (move.charAt(2) - 97);
        if (b.move(fromRow, fromCol, toRow, toCol, true)) {
            System.out.println("Legal Move ...");
        }
        display(b);
    }

    public static void main(String[] args) {
        Board b = new Board();
        Scanner in = new Scanner(System.in);
        while (!b.isMate) {
            String move = in.nextLine();
            moveParser(move, b);
            b.checkMate(b.isBlackTurn);
        }
        if (!b.check(b.isBlackTurn)) {
            System.out.println("Stale Mate (Draw)");
        } else if (b.isBlackTurn) {
            System.out.println("White won");
        } else {
            System.out.println("Black won");
        }
        in.close();
    }
}
