package Player;

public class AIPlayer2 extends AI{


    @Override
    public int CalculateEvaluationScore(int[] board, int x, int y, int player){
        int score = 0;
        int[] weights = {
            20, -3, 11, 8, 8, 11, -3, 20,
            -3, -7, -4, 1, 1, -4, -7, -3,
            11, -4, 2, 2, 2, 2, -4, 11,
            8, 1, 2, -3, -3, 2, 1, 8,
            8, 1, 2, -3, -3, 2, 1, 8,
            11, -4, 2, 2, 2, 2, -4, 11,
            -3, -7, -4, 1, 1, -4, -7, -3,
            20, -3, 11, 8, 8, 11, -3, 20
        };
        score += weights[x * 8 + y];
        return score;
    }
    
}
