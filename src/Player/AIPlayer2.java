package Player;

public class AIPlayer2 extends AI {

    final int[] weights = {
            100, -20, 10, 5, 5, 10, -20, 100,
            -20, -50, -5, -5, -5, -5, -50, -20,
            10, -5, 5, 0, 0, 5, -5, 10,
            5, -5, 0, 0, 0, 0, -5, 5,
            5, -5, 0, 0, 0, 0, -5, 5,
            10, -5, 5, 0, 0, 5, -5, 10,
            -20, -50, -5, -5, -5, -5, -50, -20,
            100, -20, 10, 5, 5, 10, -20, 100
    };

    @Override
    public int CalculateEvaluationScore(int[] board, int x, int y, int player) {
        int score = 0;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == player) {
                score += weights[i];
            } else if (board[i] == 3 - player) {
                score -= weights[i];
            }
        }

        return score;
    }

}
