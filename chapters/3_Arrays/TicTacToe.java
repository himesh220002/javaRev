
import java.util.Scanner;

public class TicTacToe {

    private static final int SIZE = 3;

    public static void main(String[] args) {
        String[][] board = initBoard();
        printBoard(board);
        playGame(board);
    }

    // Setup board with numbers 1–9
    static String[][] initBoard() {
        String[][] board = new String[SIZE][SIZE];
        int count = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = String.valueOf(count++);
            }
        }
        return board;
    }

    // Main game loop
    static void playGame(String[][] board) {
        boolean isFirstTurn = true;
        int totalAttempts = SIZE * SIZE;

        try (Scanner sc = new Scanner(System.in)) {
            while (totalAttempts > 0) {
                boolean validMove = false;
                String symbol = isFirstTurn ? "X" : "O";
                String player = isFirstTurn ? "Player One" : "Player Two";

                while (!validMove) {
                    System.out.print(player + "'s turn (" + symbol + "): ");
                    int move = sc.nextInt();
                    validMove = makeMove(board, move, symbol);
                    if (!validMove) {
                        System.out.println("Invalid move. Try again.");
                    }
                }

                printBoard(board);
                totalAttempts--;
                isFirstTurn = !isFirstTurn;

                String winner = checkWinner(board);
                if (winner != null) {
                    if (winner.equals("Tie")) {
                        System.out.println("Game ended in a tie!");
                    } else {
                        System.out.println("Player " + winner + " wins!");
                    }
                    break;
                }
            }
        }
    }

    // Validate and update move
    static boolean makeMove(String[][] board, int move, String symbol) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j].equals(String.valueOf(move))) {
                    board[i][j] = symbol;
                    return true;
                }
            }
        }
        return false;
    }

    // Check winner or tie
    static String checkWinner(String[][] board) {
        String[][] wins = {
            {board[0][0], board[0][1], board[0][2]},
            {board[1][0], board[1][1], board[1][2]},
            {board[2][0], board[2][1], board[2][2]},
            {board[0][0], board[1][0], board[2][0]},
            {board[0][1], board[1][1], board[2][1]},
            {board[0][2], board[1][2], board[2][2]},
            {board[0][0], board[1][1], board[2][2]},
            {board[0][2], board[1][1], board[2][0]}
        };

        for (String[] w : wins) {
            if (w[0].equals(w[1]) && w[1].equals(w[2])) {
                return w[0];
            }
        }

        boolean full = true;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!board[i][j].equals("X") && !board[i][j].equals("O")) {
                    full = false;
                }
            }
        }
        return full ? "Tie" : null;
    }

    // Display board
    static void printBoard(String[][] board) {
        for (int i = 0; i < SIZE; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < SIZE; j++) {
                if (j == 0) {
                    row.append(" ").append(board[i][j]);
                } else {
                    row.append(" | ").append(board[i][j]);
                }
            }
            System.out.println(row);
            if (i < SIZE - 1) {
                System.out.println("---+---+---");
            }
        }
    }
}
