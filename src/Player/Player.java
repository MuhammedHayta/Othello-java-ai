package Player;

public interface Player {

    int[] getMove(int[] board, int player);

    // Called when the user clicks a tile.
    default void handleClick(int x, int y) {
        // By default, do nothing (for AI or unimplemented).
    }
}
