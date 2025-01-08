package Player;

import java.util.List;

import Game.Othello;

public abstract class AI implements Player {
    
    @Override
    public int[] getMove(int[] board, int player, int depth){
        List<int[]> validMoves = Othello.getValidMoves(board, player);
        if(validMoves.size() == 0){
            return null;
        }

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for(int[] move : validMoves){
            int[] newBoard = Othello.getUpdatedBoard(board, move[0], move[1], player);
            int score = minmax(newBoard, 3 - player, depth, Integer.MIN_VALUE, Integer.MAX_VALUE)[0];
            if(score > bestScore){
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    // calculate the evaluation score of the board
    public abstract int CalculateEvaluationScore(int[] board, int x, int y, int player);
    
    
    // minmax with alpha beta pruning algorithm
    public int[] minmax(int[] board, int player, int depth, int alpha, int beta){
        int[] bestMove = new int[2];
        int bestScore = player == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        if(depth == 0){
            bestScore = CalculateEvaluationScore(board, 0, 0, player);
        }else{
            for(int i = 0; i < board.length; i++){
                if(board[i] == 0){
                    int x = i / 8;
                    int y = i % 8;
                    int[] newBoard = Othello.getUpdatedBoard(board, x, y, player);
                    int score = minmax(newBoard, 3 - player, depth - 1, alpha, beta)[0];
                    if(player == 1){
                        if(score > bestScore){
                            bestScore = score;
                            bestMove = new int[]{x, y};
                        }
                        alpha = Math.max(alpha, bestScore);
                    }else{
                        if(score < bestScore){
                            bestScore = score;
                            bestMove = new int[]{x, y};
                        }
                        beta = Math.min(beta, bestScore);
                    }
                    if(alpha >= beta){
                        break;
                    }
                }
            }
        }
        return new int[]{bestScore, bestMove[0], bestMove[1]};
    }
    
}
