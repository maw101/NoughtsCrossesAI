/**
 * The type Random AI.
 *
 * Randomly chooses a valid move on the grid.
 */
public class RandomAI implements MovingPlayer {

    /**
     * Generates a random valid move on the grid.
     */
    @Override
    public Coordinate getMove(char[][] grid, int gridSize, int[] moveSums, Player[] players, int currentPlayerIndex) throws Exception {
        Coordinate position = new Coordinate();

        do {
            position.setX(rand.nextInt(gridSize));
            position.setY(rand.nextInt(gridSize));
        } while (grid[position.getX()][position.getY()] != 0); // if != 0 means occupied square

        return position;
    }

}