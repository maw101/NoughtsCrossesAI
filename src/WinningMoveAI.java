/**
 * The type Winning Move AI.
 *
 * Chooses a winning move if one exists, otherwise randomly
 * chooses a valid move on the grid.
 */
public class WinningMoveAI implements MovingPlayer {

    @Override
    public Coordinate getMove(char[][] grid, int[] moveSums, char player) {
        Coordinate position = null;

        position = getWinningMoveForGivenPlayer(grid, moveSums, player);

        if (position == null) // unable to find a winning move so choose a position randomly
            position = getRandomMove(grid);

        return position;
    }

    private Coordinate getWinningMoveForGivenPlayer(char[][] grid, int[] moveSums, char player) {
        Coordinate position = null;
        int boardSize = grid.length;
        int countLookingFor = grid.length - 1; // looking for a count where we are 1 off n in a row

        if (player == 'O')
            countLookingFor *= -1;

        // try to find a line where we are 1 off n in a row
        // we utilise moveSums which is used to store the count for the player occupying
        //  the most positions on the current winning line. rows are followed by columns
        //  which are then followed by the diagonals - this is detailed in Game.java.
        //  'X' gives a positive value in moveSums and 'O' gives a negative value in it.
        for (int moveNum = 0; moveNum < moveSums.length; moveNum++)
            if (moveSums[moveNum] == countLookingFor) { // found a line where we are one off winning
                if (moveNum < boardSize) { // is row
                    for (int column = 0; column < boardSize; column++)
                        if (grid[column][moveNum] == 0) // ie if is blank
                            position = new Coordinate(column, moveNum); // place in blank square
                } else if (moveNum < boardSize * 2) { // is a column
                    for (int row = 0; row < boardSize; row++)
                        if (grid[(moveNum - boardSize)][row] == 0) // ie if is blank
                            position = new Coordinate((moveNum - boardSize), row); // place in blank square
                } else if (moveNum == (boardSize * 2)) { // is the leading diagonal
                    for (int i = 0; i < boardSize; i++)
                        if (grid[i][i] == 0) // ie if is blank
                            position = new Coordinate(i, i); // place in blank square
                } else if (moveNum == ((boardSize * 2) + 1)) { // is the anti diagonal
                    for (int row = 0, column = (boardSize - 1); row < boardSize; row++, column--)
                        if (grid[column][row] == 0) // ie if is blank
                            position = new Coordinate(column, row); // place in blank square
                }
            }

        return position;
    }

    private Coordinate getRandomMove(char[][] grid) {
        Coordinate position = new Coordinate();
        do {
            position.setX(rand.nextInt(grid.length));
            position.setY(rand.nextInt(grid.length));
        } while (grid[position.getX()][position.getY()] != 0); // if != 0 means occupied square
        return position;
    }

}