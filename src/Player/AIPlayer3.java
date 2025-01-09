package Player;

import Game.Othello;
import java.util.List;

public class AIPlayer3 extends AI {

    @Override
    public int CalculateEvaluationScore(int[] board, int x, int y, int player) {
        int opponent = 3 - player;
        int[] weights = { 30, 25, 25, 5 }; // Weights: Corners, Stability, Coin Parity, Mobility

        // Corners Heuristic
        int cornerScore = evaluateCorners(board, player, opponent);

        // Stability Heuristic
        int stabilityScore = evaluateStability(board, player, opponent);

        // Coin Parity Heuristic
        int coinParityScore = evaluateCoinParity(board, player, opponent);

        // Mobility Heuristic
        int mobilityScore = evaluateMobility(board, player, opponent);

        return weights[0] * cornerScore +
                weights[1] * stabilityScore +
                weights[2] * coinParityScore +
                weights[3] * mobilityScore;
    }

    private int evaluateCorners(int[] board, int player, int opponent) {
        int[] corners = { 0, 7, 56, 63 };
        int playerCorners = 0;
        int opponentCorners = 0;

        for (int corner : corners) {
            if (board[corner] == player)
                playerCorners++;
            else if (board[corner] == opponent)
                opponentCorners++;
        }

        if (playerCorners + opponentCorners == 0)
            return 0;
        return 100 * (playerCorners - opponentCorners) / (playerCorners + opponentCorners);
    }

    private int evaluateStability(int[] board, int player, int opponent) {
        int playerStability = 0;
        int opponentStability = 0;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == player) {
                playerStability += isStable(board, i, player) ? 1 : 0;
            } else if (board[i] == opponent) {
                opponentStability += isStable(board, i, opponent) ? 1 : 0;
            }
        }

        if (playerStability + opponentStability == 0)
            return 0;
        return 100 * (playerStability - opponentStability) / (playerStability + opponentStability);
    }

    private boolean isStable(int[] board, int position, int player) {
        int[][] directions = Othello.getDirections();
        for (int[] dir : directions) {
            int x = position / 8;
            int y = position % 8;
            boolean stable = true;
            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (Othello.getPlaceOnBoard(board, x, y) != player) {
                    stable = false;
                    break;
                }
                x += dir[0];
                y += dir[1];
            }
            if (stable)
                return true;
        }
        return false;
    }

    private int evaluateCoinParity(int[] board, int player, int opponent) {
        int playerCoins = 0;
        int opponentCoins = 0;

        for (int cell : board) {
            if (cell == player)
                playerCoins++;
            else if (cell == opponent)
                opponentCoins++;
        }

        if (playerCoins + opponentCoins == 0)
            return 0;
        return 100 * (playerCoins - opponentCoins) / (playerCoins + opponentCoins);
    }

    private int evaluateMobility(int[] board, int player, int opponent) {
        List<int[]> playerMoves = Othello.getValidMoves(board, player);
        List<int[]> opponentMoves = Othello.getValidMoves(board, opponent);

        int playerMobility = playerMoves.size();
        int opponentMobility = opponentMoves.size();

        if (playerMobility + opponentMobility == 0)
            return 0;
        return 100 * (playerMobility - opponentMobility) / (playerMobility + opponentMobility);
    }
}