import java.util.ArrayList;
import java.util.List;

public class Othello {
    

    private int boardSize = 8;

    public Othello(int boardSize){
        this.boardSize = boardSize;
    }


    // Returns the initial board state
    public int[] initializeBoard(){
        // initialize the board
        // 1 represents black, 2 represents white, 0 represents empty
        int[] board = new int[boardSize*boardSize];
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                board[i*boardSize + j] = 0;
            }
        }
        // x = 3, y = 3, set 2
        board[3*boardSize + 3] = 2;
        // x = 3, y = 4, set 1
        board[3*boardSize + 4] = 1;
        // x = 4, y = 3, set 1
        board[4*boardSize + 3] = 1;
        // x = 4, y = 4, set 2
        board[4*boardSize + 4] = 2;

        return board;

    }

    public int getPlaceOnBoard(int[] board, int x, int y){
        // get the value of the board at position (x, y)
        return board[x*boardSize + y];
    }


    // TODO: implement a non brute force solution in the future
    public List<int[]> getValidMoves(int[] board, int player){
        // get all valid moves for the player
        List<int[]> validMoves = new ArrayList<int[]>();
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(getPlaceOnBoard(board, i, j) == 0 && isValidMove(board, i, j, player)){
                    validMoves.add(new int[]{i, j});
                }
            }
        }
        return validMoves;
    }


    public boolean isValidMove(int[] board, int x, int y, int player){
        if(getPlaceOnBoard(board, x, y) != 0) return false;
        // check if the move is valid
        int[][] directions = getDirections();
        for(int i = 0; i < directions.length; i++){
            int dx = directions[i][0];
            int dy = directions[i][1];
            int nx = x + dx;
            int ny = y + dy;
            if(nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && getPlaceOnBoard(board, nx, ny) == 3-player){
                while(nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && getPlaceOnBoard(board, nx, ny) == 3-player){
                    nx += dx;
                    ny += dy;
                }
                if(nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && getPlaceOnBoard(board, nx, ny) == player){
                    return true;
                }
            }
        }
        return false;
    }


    public int[][] getDirections(){
        // return directions such as (1,0), (0,1), (1,1), etc.
        return new int[][]{
            {1,0},
            {0,1},
            {1,1},
            {1,-1},
            {-1,0},
            {0,-1},
            {-1,-1},
            {-1,1}
        };
    }

    // Returns the scores of the game,
    // First element is the score of player 1 (black)
    // Second element is the score of player 2 (white)
    public int[] getScores(int[] board){
        // get the scores of the game
        int[] scores = new int[2];
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if(getPlaceOnBoard(board, i, j) == 1){
                    scores[0]++;
                }else if(getPlaceOnBoard(board, i, j) == 2){
                    scores[1]++;
                }
            }
        }
        return scores;
    }


    public int[] getUpdatedBoard(int[] board, int x, int y, int player){
        // update the board with the move
        int[][] directions = getDirections();
        int[] newBoard = new int[boardSize*boardSize];
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                newBoard[i*boardSize + j] = getPlaceOnBoard(board, i, j);
            }
        }
        newBoard[x*boardSize + y] = player;
        for(int i = 0; i < directions.length; i++){
            int dx = directions[i][0];
            int dy = directions[i][1];
            int nx = x + dx;
            int ny = y + dy; // 
            if(nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && getPlaceOnBoard(board, nx, ny) == 3-player){
                while(nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && getPlaceOnBoard(board, nx, ny) == 3-player){
                    nx += dx;
                    ny += dy;
                }
                if(nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize && getPlaceOnBoard(board, nx, ny) == player){
                    while(nx != x || ny != y){
                        newBoard[nx*boardSize + ny] = player;
                        nx -= dx;
                        ny -= dy;
                    }
                }
            }
        }
        return newBoard;

    }
}
