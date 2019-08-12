import java.util.Random;

interface MovingPlayer {

    Random rand = new Random();

    Coordinate getMove(char[][] grid, int gridSize, int[] moveSums, Player[] players, int currentPlayerIndex) throws Exception;

}