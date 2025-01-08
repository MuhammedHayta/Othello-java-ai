import java.util.List;

import Game.Othello;
//import Player.HumanPlayer;
import Player.Player;

public class Visual {

    private static int boardSize = 8;
    private static int depth = 3;

    public static void main(String[] args){
        // Othello othello = new Othello(8);
        // Player player1 = new HumanPlayer();
        // Player player2 = new HumanPlayer();
        // runGame(othello, player1, player2);
        
        // RUN SWING INSTEAD:
        new SwingOthelloUI().setVisible(true);
    }

    public static void runGame(Othello othello, Player player1, Player player2){
        int[] board = othello.initializeBoard();
        int player = 1;
        while(true){
            List<int[]> validMoves = Othello.getValidMoves(board, player);
            if(validMoves.size() == 0){
                player = 3 - player;
                validMoves = Othello.getValidMoves(board, player);
                if(validMoves.size() == 0){
                    break;
                }
            }
            printBoard(board);
            printScores(Othello.getScores(board));
            System.out.println("Player " + player + "'s turn");
            System.out.println("Enter your move: ");
            int[] move = player == 1 ? player1.getMove(board, player, depth) : player2.getMove(board, player, depth);
            int x = move[0];
            int y = move[1];
            if(Othello.isValidMove(board, x, y, player)){
                board = Othello.getUpdatedBoard(board, x, y, player);
                player = 3 - player;
            } else {
                System.out.println("Invalid move");
            }
        }
        printBoard(board);
        printScores(Othello.getScores(board));
        
    }

    private static void printBoard( int[] board){
        // print the board
        for(int y = boardSize - 1; 0 <= y ; y--){
            for(int x = 0; x < boardSize; x++){
                System.out.print(getPlaceOnBoard(board, x, y) + " ");
            }
            System.out.println();
        }
    }

    private static void printScores(int[] scores){
        // print the scores
        System.out.println("Black: " + scores[0] + " White: " + scores[1]);
    }

    public static int getPlaceOnBoard(int[] board, int x, int y){
        // get the value of the board at position (x, y)
        return board[x*boardSize + y];
    }
    
}
