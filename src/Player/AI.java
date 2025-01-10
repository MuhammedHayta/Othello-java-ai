package Player;

import java.util.List;
import Game.Othello;

public abstract class AI implements Player {

    @Override
    public int[] getMove(int[] board, int player, int depth) {
        List<int[]> validMoves = Othello.getValidMoves(board, player);
        if (validMoves.isEmpty()) {
            return null; // No valid moves
        }

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int[] move : validMoves) {
            int[] newBoard = Othello.getUpdatedBoard(board, move[0], move[1], player);
            int score = minmax(newBoard, 3 - player, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, move[0],
                    move[1])[0];
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }

    public abstract int CalculateEvaluationScore(int[] board, int x, int y, int player);

    public int[] minmax(int[] board, int player, int depth, int alpha, int beta, int x, int y) {
        if (depth == 0 || Othello.isGameOver(board)) {
            int score = CalculateEvaluationScore(board, x, y, 3 - player); // Evaluate for the opponent
            return new int[] { score, -1, -1 }; // No specific move
        }

        List<int[]> validMoves = Othello.getValidMoves(board, player);
        if (validMoves.isEmpty()) {
            return minmax(board, 3 - player, depth - 1, alpha, beta, -1, -1); // Pass turn
        }

        int[] bestMove = new int[2];
        int bestScore = player == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int[] move : validMoves) {
            int[] newBoard = Othello.getUpdatedBoard(board, move[0], move[1], player);
            int score = minmax(newBoard, 3 - player, depth - 1, alpha, beta, move[0], move[1])[0];

            if (player == 1) {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, bestScore);
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                beta = Math.min(beta, bestScore);
            }

            if (alpha >= beta) {
                break; // Prune
            }
        }

        return new int[] { bestScore, bestMove[0], bestMove[1] };
    }
}