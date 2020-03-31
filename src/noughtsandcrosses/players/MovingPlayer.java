package noughtsandcrosses.players;

import noughtsandcrosses.game.Coordinate;

import java.util.Random;

/**
 * The interface Moving Player.
 *
 * @author maw101
 */
public interface MovingPlayer {

    /**
     * The constant rand.
     */
    Random rand = new Random();

    /**
     * Gets a players move coordinate.
     *
     * @param grid               the grid
     * @param gridSize           the grid size
     * @param moveSums           the move sums
     * @param players            the players
     * @param currentPlayerIndex the current player index
     *
     * @return a coordinate for the players move
     *
     * @throws RuntimeException if invalid player algorithm name
     */
    Coordinate getMove(char[][] grid, int gridSize, int[] moveSums, Player[] players, int currentPlayerIndex) throws RuntimeException;

}