package Player;

import Game.Othello;

public class AIPlayer1 extends AI {

    @Override
    public int CalculateEvaluationScore(int[] board, int x, int y, int player){
        int score = 0;
        int[] colorScores = Othello.getScores(board);
        score += colorScores[player - 1] - colorScores[3 - player - 1]; // score = current player's score - opponent's score
        return score;
    }
    
}
