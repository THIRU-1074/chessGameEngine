package chessGameEngine;

import java.util.*;

public class Test {

    public static void moveParser(String move, Board b, boolean turn) {

    }

    public static void main(String[] args) {
        Board b = new Board();
        Scanner in = new Scanner(System.in);
        boolean turn = true;
        while (b.isMate) {
            String move = in.nextLine();
            moveParser(move, b, turn);
            turn = !turn;
        }
        if (turn) {
            System.out.println("Black wins");
        } else {
            System.out.println("White wins");
        }
        in.close();
    }
}
