import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The type Game.
 *
 * @author maw101
 */
class Game {

    /**
     * Instantiates a new Game.
     */
    public Game() {}

    /**
     * Plays the game.
     *
     * @param playerOneAlgorithmName the player one algorithm name
     * @param playerTwoAlgorithmName the player two algorithm name
     *
     * @throws RuntimeException if invalid player algorithm name
     */
    public void play(String playerOneAlgorithmName, String playerTwoAlgorithmName) throws RuntimeException {
        // setup grid
        final int gridSize = 3;
        char[][] grid = getNewGrid(gridSize); // setup a new blank grid

        // setup moveSums which allows us to determine a winner efficiently
        int[] moveSums = new int[(gridSize * 2) + 2]; // n positions for rows, n for columns then 2 for the diagonals

        // define gameWon boolean
        boolean gameWon;

        // define variables for a move
        Coordinate move;
        Player currentPlayer;
        int currentPlayerIndex;

        // define each player
        Player[] players = {
                new Player('X', playerOneAlgorithmName),
                new Player('O', playerTwoAlgorithmName)
        };

        // define and initialise a turn counter, will allow us to determine the player to move
        int turnCount = 0;

        // output player information - symbol and algorithm name for each player
        for (Player player : players) {
            System.out.println(player);
        }
        System.out.println("############################################################");

        do {
            // set current player
            currentPlayerIndex = turnCount % 2;
            currentPlayer = players[currentPlayerIndex];

            // print current players symbol
            System.out.println("\n" + currentPlayer.getSymbol() + " - it is your go. The current board state is shown below:\n");

            // render grid for the move
            renderGrid(grid, gridSize);

            // make the move
            move = makeMove(players, currentPlayerIndex, grid, gridSize, moveSums);

            // add to move sums
            addMoveToMoveSums(move, currentPlayer.getSymbol(), gridSize, moveSums);

            // check if the previous move won the game
            gameWon = checkIfMoveWonGame(move, gridSize, moveSums);

            if (gameWon) {
                renderGrid(grid, gridSize);
                // print details of player who won
                System.out.println("GAME WON! Player " + currentPlayer.getSymbol() +
                        " has won the game with " + gridSize + " in a row.\n");
            } else if (isGridFull(grid, gridSize)) {
                renderGrid(grid, gridSize);
                System.out.println("Draw!");
                gameWon = true;
            }

            // increment the turn counter
            turnCount++;
        } while (!gameWon);
    }

    private static char[][] getNewGrid(int boardSize) {
        return new char[boardSize][boardSize];
    }

    // ######## BATTLING AI METHODS ########

    /**
     * Run battles.
     *
     * @param numberOfBattles        the number of battles
     * @param playerOneAlgorithmName the player one algorithm name
     * @param playerTwoAlgorithmName the player two algorithm name
     */
    public void runBattles(int numberOfBattles, String playerOneAlgorithmName, String playerTwoAlgorithmName) {
        int pOneWinCount = 0;
        int pTwoWinCount = 0;
        int drawCount = 0;
        System.out.println(playerOneAlgorithmName + " vs. " + playerTwoAlgorithmName);
        for (int i = 0; i < numberOfBattles; i++) {
            if (i % 50 == 0) {
                System.out.print("Battle #" + i + " Running... ");
            }
            switch (runSingleBattle(playerOneAlgorithmName, playerTwoAlgorithmName)) {
                case 0: // x won
                    pOneWinCount++;
                    break;
                case 1: // o won
                    pTwoWinCount++;
                    break;
                case -1:
                    drawCount++;
                    break;
            }
        }
        System.out.println();
        System.out.println(numberOfBattles + " Battles Completed");
        System.out.println("Player One (" + playerOneAlgorithmName + ") Wins: " + pOneWinCount + " (~" + (int) (((double) pOneWinCount / numberOfBattles) * 100) + "%)");
        System.out.println("Player Two (" + playerTwoAlgorithmName + ") Wins: " + pTwoWinCount + " (~" + (int) (((double) pTwoWinCount / numberOfBattles) * 100) + "%)");
        System.out.println("Draw Count: " + drawCount + " (~" + (int) (((double) drawCount / numberOfBattles) * 100) + "%)");
    }

    private int runSingleBattle(String playerOneAlgorithmName, String playerTwoAlgorithmName) {
        Random rand = new Random();
        // setup grid
        final int gridSize = 3;
        char[][] grid = getNewGrid(gridSize); // setup a new blank grid

        // setup moveSums which allows us to determine a winner efficiently
        int[] moveSums = new int[(gridSize * 2) + 2]; // n positions for rows, n for columns then 2 for the diagonals

        // define gameWon boolean
        boolean gameWon;

        // define variables for a move
        Coordinate move;
        Player currentPlayer;
        int currentPlayerIndex;

        // define each player
        Player[] players = {
                new Player('X', playerOneAlgorithmName),
                new Player('O', playerTwoAlgorithmName)
        };

        // define and initialise a turn counter, will allow us to determine the player to move
        int turnCount = rand.nextInt(2);

        do {
            // set current player
            currentPlayerIndex = turnCount % 2;
            currentPlayer = players[currentPlayerIndex];

            // make the move
            move = makeMove(players, currentPlayerIndex, grid, gridSize, moveSums);

            // add to move sums
            addMoveToMoveSums(move, currentPlayer.getSymbol(), gridSize, moveSums);

            // check if the previous move won the game
            gameWon = checkIfMoveWonGame(move, gridSize, moveSums);

            if (gameWon) {
                return turnCount % 2; // X=0, O=1
            } else if (isGridFull(grid, gridSize)) {
                return -1; // draw=-1
            }

            // increment the turn counter
            turnCount++;
        } while (true);
    }

    // ######## /END BATTLING AI METHODS ########

    /**
     * Get all valid move coordinates coordinate [ ].
     *
     * @param grid     the grid
     * @param gridSize the grid size
     * @return the coordinate [ ]
     */
    static Coordinate[] getAllValidMoveCoordinates(char[][] grid, int gridSize) {
        List<Coordinate> validMoves = new ArrayList<>();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[col][row] == '\0') { // check if position empty
                    validMoves.add(new Coordinate(col, row));
                }
            }
        }

        return validMoves.toArray(new Coordinate[0]); // convert ArrayList to an Array of Coordinates
    }

    /**
     * Gets winner.
     *
     * @param moveSums the move sums
     * @param grid     the grid
     * @param gridSize the grid size
     * @return the winner
     */
    static char getWinner(int[] moveSums, char[][] grid, int gridSize) {
        // loop through the moveSums array which allows us to determine lines that have won
        for (int lineSum : moveSums) {
            if (lineSum == gridSize) { // if lines sum is equal to the board size, x has won
                return 'X';
            } else if (lineSum == -gridSize) { // otherwise if the lines sum is equal to -(board size), o has won
                return 'O';
            }
        }

        if (isGridFull(grid, gridSize)) {
            return Character.MAX_VALUE; // game drawn
        }

        return Character.MIN_VALUE; // no winner as yet - return null char literal
    }

    /**
     * Is grid full boolean.
     *
     * @param grid     the grid
     * @param gridSize the grid size
     * @return the boolean
     */
    static boolean isGridFull(char[][] grid, int gridSize) {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[col][row] == '\0') { // if == 0 means empty square
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Render grid.
     *
     * @param grid     the grid
     * @param gridSize the grid size
     */
    static void renderGrid(char[][] grid, int gridSize) { // todo: make private again
        System.out.println("  0 1 2");
        System.out.println("  -----");
        for (int row = 0; row < gridSize; row++) {
            System.out.print(row + "|");
            for (int col = 0; col < gridSize; col++) {
                if (grid[col][row] == '\0') {
                    System.out.print("  ");
                } else {
                    System.out.print(grid[col][row] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Is legal move boolean.
     *
     * @param move     the move
     * @param grid     the grid
     * @param gridSize the grid size
     * @return the boolean
     */
    static boolean isLegalMove(Coordinate move, char[][] grid, int gridSize) {
        // check if move is null
        if (move == null) { // means a class implementing the 'MovingPlayer' interface has been incorrectly implemented
            throw new NullPointerException("Move is null - the current players algorithm has been incorrectly " +
                    "implemented as it has failed to return a move");
        }
        // check if move is inside the grid
        if ((move.getX() < 0 || move.getX() >= gridSize) ||
                (move.getY() < 0 || move.getY() >= gridSize)) {
            System.err.println("Invalid Coordinate - " + move + " - outside the grid");
            return false;
        } else if (grid[move.getX()][move.getY()] != 0) { // check if position occupied (if == 0 means empty position)
            System.err.println("Square " + move + " is already occupied");
            return false;
        }
        return true;
    }

    /**
     * Gets move.
     *
     * @param players            the players
     * @param currentPlayerIndex the current player index
     * @param grid               the grid
     * @param gridSize           the grid size
     * @param moveSums           the move sums
     * @return the move
     *
     * @throws RuntimeException if invalid player algorithm name
     */
    static Coordinate getMove(Player[] players, int currentPlayerIndex, char[][] grid, int gridSize, int[] moveSums) throws RuntimeException {
        Player currentPlayer = players[currentPlayerIndex];
        MovingPlayer movingPlayer;

        // setup correct algorithm ready to then make move
        switch (currentPlayer.getAlgorithmName()) {
            case "Human":
                movingPlayer = new HumanPlayer();
                break;
            case "RandomAI":
                movingPlayer = new RandomAI();
                break;
            case "WinningMoveAI":
                movingPlayer = new WinningMoveAI();
                break;
            case "FindWinningBlockLosingAI":
                movingPlayer = new FindWinningBlockLosingAI();
                break;
            case "OptimisedAI":
                movingPlayer = new OptimisedAI();
                break;
            default:
                throw new RuntimeException("Unknown Algorithm: '" + currentPlayer.getAlgorithmName() + "'");
        }
        // make the move with the correct algorithm and return the chosen coordinate
        return movingPlayer.getMove(grid, gridSize, moveSums, players, currentPlayerIndex);
    }

    /**
     * Make move coordinate.
     *
     * @param players            the players
     * @param currentPlayerIndex the current player index
     * @param grid               the grid
     * @param gridSize           the grid size
     * @param moveSums           the move sums
     * @return the coordinate
     *
     * @throws RuntimeException if invalid player algorithm name
     */
    static Coordinate makeMove(Player[] players, int currentPlayerIndex, char[][] grid, int gridSize, int[] moveSums) throws RuntimeException {
        Coordinate move;

        // get move coordinates from current player, do this until a legal move is chosen
        do {
            move = getMove(players, currentPlayerIndex, grid, gridSize, moveSums);
        } while (!isLegalMove(move, grid, gridSize));

        // place the move on the grid
        placeMove(move, players, currentPlayerIndex, grid, gridSize);

        // return coordinates of the move made
        return move;
    }

    /**
     * Place move.
     *
     * @param move               the move
     * @param players            the players
     * @param currentPlayerIndex the current player index
     * @param grid               the grid
     * @param gridSize           the grid size
     */
    static void placeMove(Coordinate move, Player[] players, int currentPlayerIndex, char[][] grid, int gridSize) {
        Player currentPlayer = players[currentPlayerIndex];

        if (isLegalMove(move, grid, gridSize)) {
            // place the move on the grid
            grid[move.getX()][move.getY()] = currentPlayer.getSymbol();
        } else {
            throw new IllegalArgumentException("Illegal Move!");
        }
    }

    private static boolean checkIfMoveWonGame(Coordinate move, int gridSize, int[] moveSums) {
        // check each of these lines to see if the absolute sum value has reached the board size
        // if it has, then we have a winning line from the current player meaning they have got n-in a row (where n is
        //  the board size)
        return (Math.abs(moveSums[move.getY()]) == gridSize || Math.abs(moveSums[gridSize + move.getX()]) == gridSize ||
                Math.abs(moveSums[2 * gridSize]) == gridSize || Math.abs(moveSums[(2 * gridSize) + 1]) == gridSize);
    }

    /**
     * Add move to move sums.
     *
     * @param move         the move
     * @param playerSymbol the player symbol
     * @param gridSize     the grid size
     * @param moveSums     the move sums
     */
    static void addMoveToMoveSums(Coordinate move, char playerSymbol, int gridSize, int[] moveSums) {
        int amountToAdd;
        // determine the value to add later on through the players symbol
        if (playerSymbol == 'X') {
            amountToAdd = 1;
        } else {
            amountToAdd = -1;
        }

        // add value to the moves row sum
        moveSums[move.getY()] += amountToAdd;
        // add value to the moves column sum
        moveSums[gridSize + move.getX()] += amountToAdd;

        // if move is on leading diagonal, add value to this sum
        if (move.getX() == move.getY()) {
            moveSums[2 * gridSize] += amountToAdd;
        }
        // if move on anti diagonal, add value to this sum
        if ((move.getX() + move.getY() + 1) == gridSize) {
            moveSums[(2 * gridSize) + 1] += amountToAdd;
        }
    }

}