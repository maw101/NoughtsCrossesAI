import java.util.Scanner;

public class Game {

    private char[][] grid;
    private int boardSize;

    public Game() {
        boardSize = 3;
        grid = new char[boardSize][boardSize];
    }

    public char[][] getNewGrid() {
        return new char[boardSize][boardSize];
    }

    public void renderGrid() {
        System.out.println("  0 1 2");
        System.out.println("  -----");
        for (int row = 0; row < boardSize; row++) {
            System.out.print(row + "|");
            for (int col = 0; col < boardSize; col++) {
                if (grid[col][row] == 0)
                    System.out.print("  ");
                else
                    System.out.print(grid[col][row] + " ");
            }
            System.out.println();
        }
    }

    // todo: better input method to prevent exceptions etc
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

    private boolean isValidMove(Coordinate move) {
        if ((move.getX() < 0 || move.getX() >= boardSize) ||
                (move.getY() < 0 || move.getY() >= boardSize)) {
            System.err.println("Invalid Coordinate - " + move + " - outside the grid");
            return false;
        } else if ((grid[move.getY()][move.getX()] == 0)) { // if == 0 means empty square
            System.err.println("Square " + move + " is already occupied ");
            return false;
        }
        return true;

    }

    public void makeMove(char player) {
        Coordinate move;
        do {
            move = getCoordinateInput();
        } while (!isValidMove(move));

        grid[move.getY()][move.getX()] = player;
    }

}