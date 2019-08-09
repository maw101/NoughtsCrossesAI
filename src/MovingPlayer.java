import java.util.Random;

public interface MovingPlayer {

    Random rand = new Random();

    Coordinate getMove(char[][] grid, int[] moveSums, char playerSymbol);

}