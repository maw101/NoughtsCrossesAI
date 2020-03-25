import java.util.Scanner;

/**
 * The type Human player.
 *
 * @author maw101
 */
public class HumanPlayer implements MovingPlayer {

    @Override
    public Coordinate getMove(char[][] grid, int gridSize, int[] moveSums, Player[] players, int currentPlayerIndex) throws RuntimeException {
        // no checks required to see if it is valid as this is checked in Game class' makeMove method
        return getCoordinateInput();
    }

    // todo: need better input process to prevent exceptions from incorrect data types etc
    private Coordinate getCoordinateInput() {
        Scanner in = new Scanner(System.in);
        Coordinate position = new Coordinate();

        // get X position
        System.out.println("Enter X Coordinate: ");
        position.setX(in.nextInt());

        // get Y position
        System.out.println("Enter Y Coordinate: ");
        position.setY(in.nextInt());

        return position;
    }

}