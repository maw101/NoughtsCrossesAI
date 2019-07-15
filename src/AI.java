import java.util.Random;

public interface AI {

    Random rand = new Random();

    Coordinate makeMove(char[][] grid, int[] moveSums, char player);

}