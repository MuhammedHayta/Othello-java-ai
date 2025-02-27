package Player;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import Game.Othello;

public abstract class AI implements Player {
    private int myId;

    @Override
    public int[] getMove(int[] board, int player, int depth) {
        this.myId = player;
        List<int[]> validMoves = Othello.getValidMoves(board, player);
        if (validMoves.isEmpty()) {
            return null; // No valid moves
        }

        AtomicInteger bestScore = new AtomicInteger(Integer.MIN_VALUE);
        AtomicReference<int[]> bestMove = new AtomicReference<>(new int[2]);
        Object lock = new Object();
        List<Thread> threads = new ArrayList<>();

        for (int[] move : validMoves) {
            Thread t = new Thread(() -> {
                int[] newBoard = Othello.getUpdatedBoard(board, move[0], move[1], player);
                int score = minmax(newBoard, 3 - player, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, move[0], move[1])[0];
                synchronized (lock) {
                    if (score > bestScore.get()) {
                        bestScore.set(score);
                        bestMove.set(move);
                    }
                }
            });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // Handle exception
            }
        }

        return bestMove.get();
    }

    public abstract int CalculateEvaluationScore(int[] board, int x, int y, int player);

    public int[] minmax(int[] board, int currPlayer, int depth, int alpha, int beta, int x, int y) {
        if (depth == 0 || Othello.isGameOver(board)) {
            int score = CalculateEvaluationScore(board, x, y, myId);
            return new int[] { score, -1, -1 };
        }

        List<int[]> validMoves = Othello.getValidMoves(board, currPlayer);
        if (validMoves.isEmpty()) {
            return minmax(board, 3 - currPlayer, depth - 1, alpha, beta, -1, -1);
        }

        // Use isMaximizing to decide whether to maximize or minimize
        boolean isMaximizing = (currPlayer == myId);
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = new int[2];

        for (int[] move : validMoves) {
            int[] newBoard = Othello.getUpdatedBoard(board, move[0], move[1], currPlayer);
            int score = minmax(newBoard, 3 - currPlayer, depth - 1, alpha, beta, move[0], move[1])[0];

            if (isMaximizing) {
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