import java.util.Scanner;

public class Game {

    private char[][] grid;

    public Game() {
        grid = new char[3][3];
    }

    public char[][] getNewGrid() {
        return new char[3][3];
    }

    public void renderGrid() {
        System.out.println("  0 1 2");
        System.out.println("  -----");
        for (int row = 0; row < 3; row++) {
            System.out.print(row + "|");
            for (int col = 0; col < 3; col++) {
                if (grid[col][row] == 0)
                    System.out.print("  ");
                else
                    System.out.print(grid[col][row] + " ");
            }
            System.out.println();
        }
    }

    // todo: better input method to prevent exceptions etc
    public Coordinate getPlayerMove() {
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

    public void makeMove() {

    }

}