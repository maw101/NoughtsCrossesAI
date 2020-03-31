package noughtsandcrosses.players.ai;

import noughtsandcrosses.game.Coordinate;
import noughtsandcrosses.players.MovingPlayer;
import noughtsandcrosses.players.Player;

/**
 * The type Random AI.
 * <p>
 * Randomly chooses a valid move on the grid.
 *
 * @author maw101
 */
public class RandomAI implements MovingPlayer {

    @Override
    public Coordinate getMove(char[][] grid, int gridSize, int[] moveSums, Player[] players, int currentPlayerIndex) throws RuntimeException {
        Coordinate position = new Coordinate();

        do {
            position.setX(rand.nextInt(gridSize));
            position.setY(rand.nextInt(gridSize));
        } while (grid[position.getX()][position.getY()] != 0); // if != 0 means occupied square

        return position;
    }

}