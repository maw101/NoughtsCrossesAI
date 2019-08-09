import java.util.ArrayList;
import java.util.List;

class Game {

    private char[][] grid;
    private final int boardSize;
    private final int[] moveSums;
    private boolean gameWon;

    public Game() {
        this(3);
    }

    public Game(int boardSize) {
        this.boardSize = boardSize;
        moveSums = new int[(boardSize * 2) + 2]; // n positions for rows, n for columns then 2 for the diagonals
        gameWon = false;
    }

    private char[][] getNewGrid() {
        return new char[boardSize][boardSize];
    }

    public void play(String playerOneAlgorithmName, String playerTwoAlgorithmName) throws Exception {
        Coordinate move;
        Player currentPlayer;
        // define each player
        Player[] players = {
                new Player('X', playerOneAlgorithmName),
                new Player('O', playerTwoAlgorithmName)
        };
        // define and initialise a turn counter, will allow us to determine the player to move
        int turnCount = 0;

        grid = getNewGrid(); // setup a new blank grid

        // output player information
        for (Player player : players)
            System.out.println(player);
        System.out.println("############################################################");

        do {
            // set current player
            currentPlayer = players[turnCount % 2];

            // print current players symbol
            System.out.println("\n" + currentPlayer.getSymbol() + " - it is your go. The current board state is shown below:\n");

            // render grid for the move
            renderGrid();

            // make the move
            move = makeMove(currentPlayer);

            // check if the previous move won the game
            gameWon = checkIfMoveWonGame(move, currentPlayer.getSymbol());

            if (gameWon) {
                renderGrid();
                // print details of player who won
                System.out.println("GAME WON! Player " + currentPlayer.getSymbol() +
                        " has won the game with " + boardSize + " in a row.\n");
            } else if (isGridFull()) {
                renderGrid();
                System.out.println("Draw!");
                gameWon = true;
            }

            // increment the turn counter
            turnCount++;
        } while (!gameWon);
    }

    public Coordinate[] getAllValidMoveCoordinates() {
        List<Coordinate> validMoves = new ArrayList<>();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
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

    public char getWinner() {
        // loop through the moveSums array which allows us to determine lines that have won
        for (int lineSum : moveSums) {
            if (lineSum == boardSize) // if lines sum is equal to the board size, x has won
                return 'X';
            else if (lineSum == -boardSize) // otherwise if the lines sum is equal to -(board size), o has won
                return 'O';
        }
        return Character.MIN_VALUE; // no winner as yet - return null char literal
    }

    public boolean isGridFull() {
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++)
                if (grid[col][row] == 0) // if == 0 means empty square
                    return false;
        return true;
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
        System.out.println();
    }

    private boolean isLegalMove(Coordinate move) {
        // check if move is null
        if (move == null) // means a class implementing the 'MovingPlayer' interface has been incorrectly implemented
            throw new NullPointerException("Move is null - the current players algorithm has been incorrectly " +
                    "implemented as it has failed to return a move");
        // check if move is inside the grid
        if ((move.getX() < 0 || move.getX() >= boardSize) ||
                (move.getY() < 0 || move.getY() >= boardSize)) {
            System.err.println("Invalid Coordinate - " + move + " - outside the grid");
            return false;
        } else if (grid[move.getX()][move.getY()] != 0) { // check if position occupied (if == 0 means empty position)
            System.err.println("Square " + move + " is already occupied");
            return false;
        }
        return true;
    }

    private Coordinate getMove(Player currentPlayer) throws Exception {
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

    private Coordinate makeMove(Player currentPlayer) throws Exception {
        Coordinate move;

        // get move coordinates from current player, do this until a legal move is chosen
        do {
            move = getMove(currentPlayer);
        } while (!isLegalMove(move));

        // place the move on the grid
        grid[move.getX()][move.getY()] = currentPlayer.getSymbol();

        // return coordinates of the move made
        return move;
    }

    private boolean checkIfMoveWonGame(Coordinate move, char playerSymbol) {
        int amountToAdd;
        // determine the value to add later on through the players symbol
        if (playerSymbol == 'X')
            amountToAdd = 1;
        else
            amountToAdd = -1;

        // add value to the moves row sum
        moveSums[move.getY()] += amountToAdd;
        // add value to the moves column sum
        moveSums[boardSize + move.getX()] += amountToAdd;

        // if move is on leading diagonal, add value to this sum
        if (move.getX() == move.getY())
            moveSums[2 * boardSize] += amountToAdd;
        // if move on anti diagonal, add value to this sum
        if ((move.getX() + move.getY() + 1) == boardSize)
            moveSums[(2 * boardSize) + 1] += amountToAdd;

        // check each of these lines to see if the absolute sum value has reached the board size
        // if it has, then we have a winning line from the current player meaning they have got n-in a row (where n is
        //  the board size)
        return (Math.abs(moveSums[move.getY()]) == boardSize || Math.abs(moveSums[boardSize + move.getX()]) == boardSize ||
                Math.abs(moveSums[2 * boardSize]) == boardSize || Math.abs(moveSums[(2 * boardSize) + 1]) == boardSize);
    }

}