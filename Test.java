package chessGameEngine;

import java.util.*;

public class Test {

    public static void moveParser(String move, Board b) {
        int fromRow = (int) move.charAt(1) - 48;
        int fromCol = (int) move.charAt(0) - 97;
        int toRow = (int) move.charAt(3) - 48;
        int toCol = (int) move.charAt(2) - 97;
        b.move(fromRow, fromCol, toRow, toCol);
    }

    public static void main(String[] args) {
        Board b = new Board();
        Scanner in = new Scanner(System.in);
        while (!b.isMate) {
            String move = in.nextLine();
            moveParser(move, b);
            b.checkMate();
        }
        if (b.isBlackTurn) {
            System.out.println("White won");
        } else {
            System.out.println("Black won");
        }
        in.close();
    }
}
