package chessGameEngine;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Test {

    public static void moveParser(String move, Board b) {
        int fromRow = (int) (move.charAt(1) - 49);
        int fromCol = (int) (move.charAt(0) - 97);
        int toRow = (int) (move.charAt(3) - 49);
        int toCol = (int) (move.charAt(2) - 97);
        b.move(fromRow, fromCol, toRow, toCol, true);
    }

    public static boolean controlMode(Board b, boolean isControlMode, Scanner in, ChessBoardGUI gui) {
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
        return false;
    }

    public static void main(String[] args) {
        Board b = Board.getInstance();
        Scanner in = new Scanner(System.in);
        ChessBoardGUI gui = null;
        boolean isControlMode = true;
        boolean isBotActive = false;
        System.out.println("===================\n\nLets Play Chess...!\n\n===================");
        System.out.println("Enter :\n 'b' for playing with Bot...\n 'h' for two player mode...");
        StockfishEngine bot = null;
        if (in.next().charAt(0) == 'b') {
            isBotActive = true;
            bot = StockfishEngine.getInstance();
            boolean started = bot.startEngine("chessGameEngine/stockfish-windows-x86-64-avx2.exe");
            if (!started) {
                System.out.println("Failed to start Stockfish");
                return;
            }
        }
        in.nextLine();
        while (isControlMode) {
            System.out.println("Press One to Start the Game...!");
            if (in.nextInt() == 1) {
                System.out.println("Enter 'ctrl' at any point in game to enter control mode...");
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
            String move = "XXXX";
            if (isBotActive && b.isBlackTurn) {
                if (bot == null) {
                    return;
                }
                try {
                    move = bot.getBestMove(BoardState.stateString(b));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Enter your move/('ctrl'): ");
                move = in.nextLine();
            }
            if (move.equals("ctrl")) {
                isControlMode = true;
            }
            isControlMode = controlMode(b, isControlMode, in, gui);
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
        if (bot != null) {
            try {
                bot.stopEngine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        in.close();
    }
}
