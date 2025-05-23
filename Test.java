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
    }

    public static void main(String[] args) {
        Board b = Board.getInstance();
        Scanner in = new Scanner(System.in);
        ChessBoardGUI gui = null;
        boolean isControlMode = true;
        while (isControlMode) {
            System.out.println("Press One to Start the Game...!");
            if (in.nextInt() == 1) {
                gui = ChessBoardGUI.getInstance();
                isControlMode = false;
            }
        }
        if (gui == null) {
            return;
        }
        gui.displayBoard(b);
        in.nextLine();
        while (!b.isMate) {
            String move = in.nextLine();
            if (move.equals("ctrl")) {
                isControlMode = true;
            }
            while (isControlMode) {
                System.out.println("Enter :\n 'u' for Undo...\n 'rst' for Board reset...\n 'wpt' for white Points...\n 'bpt' for black Points...\n 'chk' to see if check...\n 'game' to enter the game mode...");
                switch (in.nextLine()) {
                    case "u" -> {
                        b.undo();
                        gui.displayBoard(b);
                    }
                    case "rst" -> {
                        b.resetBoard();
                        gui.displayBoard(b);
                    }
                    case "bpt" ->
                        System.out.println("Black Point:" + b.blackPoints);
                    case "wpt" ->
                        System.out.println("White Points:" + b.whitePoints);
                    case "chk" ->
                        System.out.println((b.isCheck) ? ("Your on Check") : ("Safe no check"));
                    case "game" -> {
                        isControlMode = false;
                        System.out.println("Entering back to game...!");
                    }
                    default ->
                        System.out.println("Invalid ctrl cmd....!");
                }
            }
            if (move.equals("ctrl")) {
                continue;
            }
            moveParser(move, b);
            gui.displayBoard(b);
        }
        if (!b.isCheck) {
            System.out.println("Stale Mate (Draw)");
        } else if (b.isBlackTurn) {
            System.out.println("White won");
        } else {
            System.out.println("Black won");
        }
        in.close();
    }
}
