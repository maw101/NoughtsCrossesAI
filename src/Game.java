import java.util.Scanner;

class Game {

    private char[][] grid;
    private int boardSize;
    private int[] moveSums;
    private boolean gameWon;

    public Game() {
        this(3);
    }

    public Game(int boardSize) {
        this.boardSize = boardSize;
        grid = getNewGrid();
        moveSums = new int[(boardSize * 2) + 2]; // n positions for rows, n for columns then 2 for the diagonals
        gameWon = false;
    }

    private char[][] getNewGrid() {
        return new char[boardSize][boardSize];
    }

    public void play() {
        char currentPlayer = 'O';

        renderGrid();
        do {
            if (currentPlayer == 'X') {
                currentPlayer = 'O';
            } else {
                currentPlayer = 'X';
            }

            makeMove(currentPlayer);
            renderGrid();

            if (isGridFull()) {
                System.out.println("Draw!");
                gameWon = true;
            }
        } while (!gameWon);
    }

    private void renderGrid() {
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
        } else if ((grid[move.getX()][move.getY()] != 0)) { // if == 0 means empty square
            System.err.println("Square " + move + " is already occupied");
            return false;
        }
        return true;

    }

    private void makeMove(char player) {
        Coordinate move;
        do {
            move = getCoordinateInput();
        } while (!isValidMove(move));

        grid[move.getX()][move.getY()] = player;
        checkIfMoveWonGame(move, player);
    }

    private void checkIfMoveWonGame(Coordinate move, char player) {
        int amountToAdd;
        if (player == 'X')
            amountToAdd = 1;
        else
            amountToAdd = -1;

        // add to row sum
        moveSums[move.getY()] += amountToAdd;
        // add to column sum
        moveSums[boardSize + move.getX()] += amountToAdd;

        // if on leading diagonal, add to this sum
        if (move.getX() == move.getY())
            moveSums[2 * boardSize] += amountToAdd;
        // if on anti diagonal, add to this sum
        if ((move.getX() + move.getY() + 1) == boardSize)
            moveSums[(2 * boardSize) + 1] += amountToAdd;

        // check these positions
        if  (Math.abs(moveSums[move.getY()]) == boardSize || Math.abs(moveSums[boardSize + move.getX()]) == boardSize ||
                Math.abs(moveSums[2 * boardSize]) == boardSize || Math.abs(moveSums[(2 * boardSize) + 1]) == boardSize) {
            System.out.println("Game Won! Player " + player + " has won the game with " + boardSize + " in a row.");
            gameWon = true;
        }
    }

    private boolean isGridFull() {
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++)
                if (grid[col][row] == 0) // if == 0 means empty square
                    return false;
        return true;
    }

}