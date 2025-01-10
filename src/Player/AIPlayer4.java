package Player;

import Game.Othello;


public class AIPlayer3 extends AI{
    

    @Override
    public int CalculateEvaluationScore(int[] board, int x, int y, int player){
        int score = 0;
        int[] weights = {
            5, -1, 4, 4, 4, 4, -1, 5,
            -1, -7, 0, 0, 0, 0, -7, -1,
            4, 0, 0, 0, 0, 0, 0, 4,
            4, 0, 0, -1, -1, 0, 0, 4,
            4, 0, 0, -1, -1, 0, 0, 4,
            4, 0, 0, 0, 0, 0, 0, 4,
            -1, -7, 0, 0, 0, 0, -7, -1,
            5, -1, 4, 4, 4, 4, -1, 5
        };
        int playerMoveNumber = Othello.getValidMoves(board, player).size();
        int opponentMoveNumber = Othello.getValidMoves(board, 3 - player).size();
        score += weights[x * 8 + y] -  opponentMoveNumber;
        System.out.println("Player: " + player + " PlayerMoveNumber: " + playerMoveNumber + " OpponentMoveNumber: " + opponentMoveNumber + " Score: " + score);
        return score;
    }

    
}
