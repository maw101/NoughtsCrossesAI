import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Game {

    public Game() {}

    public void play(String playerOneAlgorithmName, String playerTwoAlgorithmName) throws Exception {
        final int gridSize = 3;
        char[][] grid = getNewGrid(gridSize); // setup a new blank grid
        int[] moveSums = new int[(gridSize * 2) + 2]; // n positions for rows, n for columns then 2 for the diagonals
        boolean gameWon = false;
        Coordinate move;
        Player currentPlayer;

        // define each player
        Player[] players = {
                new Player('X', playerOneAlgorithmName),
                new Player('O', playerTwoAlgorithmName)
        };

        // define and initialise a turn counter, will allow us to determine the player to move
        int turnCount = 0;

        // output player information - symbol and algorithm name for each player
        for (Player player : players)
            System.out.println(player);
        System.out.println("############################################################");

        do {
            // set current player
            currentPlayer = players[turnCount % 2];

            // print current players symbol
            System.out.println("\n" + currentPlayer.getSymbol() + " - it is your go. The current board state is shown below:\n");

            // render grid for the move
            renderGrid(grid, gridSize);

            // make the move
            move = makeMove(currentPlayer, grid, gridSize, moveSums);

            // check if the previous move won the game
            gameWon = checkIfMoveWonGame(move, currentPlayer.getSymbol(), gridSize, moveSums);

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

    private char[][] getNewGrid(int boardSize) {
        return new char[boardSize][boardSize];
    }

    // ######## BATTLING AI METHODS ########

    public void runBattles(int numberOfBattles, String playerOneAlgorithmName, String playerTwoAlgorithmName) throws Exception {
        int pOneWinCount = 0;
        int pTwoWinCount = 0;
        int drawCount = 0;
        for (int i = 0; i < numberOfBattles; i++) {
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
        System.out.println(numberOfBattles + " Battles");
        System.out.println("Player One (" + playerOneAlgorithmName + ") Wins: " + pOneWinCount + " (~" + (int) (((double) pOneWinCount / numberOfBattles) * 100) + "%)");
        System.out.println("Player Two (" + playerTwoAlgorithmName + ") Wins: " + pTwoWinCount + " (~" + (int) (((double) pTwoWinCount / numberOfBattles) * 100) + "%)");
        System.out.println("Draw Count: " + drawCount + " (~" + (int) (((double) drawCount / numberOfBattles) * 100) + "%)");
    }

    private int runSingleBattle(String playerOneAlgorithmName, String playerTwoAlgorithmName) throws Exception {
        Random rand = new Random();
        final int gridSize = 3;
        char[][] grid = getNewGrid(gridSize); // setup a new blank grid
        int[] moveSums = new int[(gridSize * 2) + 2]; // n positions for rows, n for columns then 2 for the diagonals
        boolean gameWon = false;
        Coordinate move;
        Player currentPlayer;

        // define each player
        Player[] players = {
                new Player('X', playerOneAlgorithmName),
                new Player('O', playerTwoAlgorithmName)
        };

        // define and initialise a turn counter, will allow us to determine the player to move
        int turnCount = rand.nextInt(2);

        do {
            // set current player
            currentPlayer = players[turnCount % 2];

            // make the move
            move = makeMove(currentPlayer, grid, gridSize, moveSums);

            // check if the previous move won the game
            gameWon = checkIfMoveWonGame(move, currentPlayer.getSymbol(), gridSize, moveSums);

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

    public Coordinate[] getAllValidMoveCoordinates(char[][] grid, int gridSize) {
        List<Coordinate> validMoves = new ArrayList<>();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (grid[col][row] == 0) // check if position empty
                    validMoves.add(new Coordinate(col, row));
            }
        }
        return validMoves.toArray(new Coordinate[validMoves.size()]); // convert ArrayList to an Array of Coordinates
    }

    public char getOpponentsSymbol(char requestingPlayersSymbol) throws Exception {
        if (requestingPlayersSymbol == 'X')
            return 'O';
        else if (requestingPlayersSymbol == 'O')
            return 'X';
        throw new Exception("Unknown Player Symbol: '" + requestingPlayersSymbol + "'");
    }

    public char getWinner(int[] moveSums, int gridSize) {
        // loop through the moveSums array which allows us to determine lines that have won
        for (int lineSum : moveSums) {
            if (lineSum == gridSize) // if lines sum is equal to the board size, x has won
                return 'X';
            else if (lineSum == -gridSize) // otherwise if the lines sum is equal to -(board size), o has won
                return 'O';
        }
        return Character.MIN_VALUE; // no winner as yet - return null char literal
    }

    public boolean isGridFull(char[][] grid, int gridSize) {
        for (int row = 0; row < gridSize; row++)
            for (int col = 0; col < gridSize; col++)
                if (grid[col][row] == 0) // if == 0 means empty square
                    return false;
        return true;
    }

    private void renderGrid(char[][] grid, int gridSize) {
        System.out.println("  0 1 2");
        System.out.println("  -----");
        for (int row = 0; row < gridSize; row++) {
            System.out.print(row + "|");
            for (int col = 0; col < gridSize; col++) {
                if (grid[col][row] == 0)
                    System.out.print("  ");
                else
                    System.out.print(grid[col][row] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isLegalMove(Coordinate move, char[][] grid, int gridSize) {
        // check if move is null
        if (move == null) // means a class implementing the 'MovingPlayer' interface has been incorrectly implemented
            throw new NullPointerException("Move is null - the current players algorithm has been incorrectly " +
                    "implemented as it has failed to return a move");
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

    private Coordinate getMove(Player currentPlayer, char[][] grid, int[] moveSums) throws Exception {
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
                throw new Exception("Unknown Algorithm: '" + currentPlayer.getAlgorithmName() + "'");
        }
        // make the move with the correct algorithm and return the chosen coordinate
        return movingPlayer.getMove(grid, moveSums, currentPlayer.getSymbol());
    }

    private Coordinate makeMove(Player currentPlayer, char[][] grid, int gridSize, int[] moveSums) throws Exception {
        Coordinate move;

        // get move coordinates from current player, do this until a legal move is chosen
        do {
            move = getMove(currentPlayer, grid, moveSums);
        } while (!isLegalMove(move, grid, gridSize));

        // place the move on the grid
        grid[move.getX()][move.getY()] = currentPlayer.getSymbol();

        // return coordinates of the move made
        return move;
    }

    private boolean checkIfMoveWonGame(Coordinate move, char playerSymbol, int gridSize, int[] moveSums) {
        int amountToAdd;
        // determine the value to add later on through the players symbol
        if (playerSymbol == 'X')
            amountToAdd = 1;
        else
            amountToAdd = -1;

        // add value to the moves row sum
        moveSums[move.getY()] += amountToAdd;
        // add value to the moves column sum
        moveSums[gridSize + move.getX()] += amountToAdd;

        // if move is on leading diagonal, add value to this sum
        if (move.getX() == move.getY())
            moveSums[2 * gridSize] += amountToAdd;
        // if move on anti diagonal, add value to this sum
        if ((move.getX() + move.getY() + 1) == gridSize)
            moveSums[(2 * gridSize) + 1] += amountToAdd;

        // check each of these lines to see if the absolute sum value has reached the board size
        // if it has, then we have a winning line from the current player meaning they have got n-in a row (where n is
        //  the board size)
        return (Math.abs(moveSums[move.getY()]) == gridSize || Math.abs(moveSums[gridSize + move.getX()]) == gridSize ||
                Math.abs(moveSums[2 * gridSize]) == gridSize || Math.abs(moveSums[(2 * gridSize) + 1]) == gridSize);
    }

}