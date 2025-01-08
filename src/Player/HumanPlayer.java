package Player;

import java.util.concurrent.atomic.AtomicReference;

public class HumanPlayer implements Player {
    private final AtomicReference<int[]> clickedMove = new AtomicReference<>(null);

    @Override
    public void handleClick(int x, int y) {
        clickedMove.set(new int[]{x, y});
    }

    @Override
    public int[] getMove(int[] board, int player, int depth) {
        // Return the last clicked move immediately (or null if none)
        return clickedMove.getAndSet(null);
    }
}
