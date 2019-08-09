public class FindWinningBlockLosingAI implements MovingPlayer {

    @Override
    public Coordinate getMove(char[][] grid, int gridSize, int[] moveSums, char player) {
        Coordinate position;

        // get winning move for the AI player
        position = getWinningMoveForGivenPlayer(grid, gridSize, moveSums, player);

        // if unable to find winning move, check if winning move for rival so can block
        if (position == null) {
            if (player == 'X')
                position = getWinningMoveForGivenPlayer(grid, gridSize, moveSums, 'O');
            else
                position = getWinningMoveForGivenPlayer(grid, gridSize, moveSums, 'X');
        }

        // worst case, place random move
        if (position == null) // unable to find a winning move so choose a position randomly
            position = getRandomMove(grid, gridSize);

        return position;
    }

    private Coordinate getWinningMoveForGivenPlayer(char[][] grid, int gridSize,  int[] moveSums, char player) {
        Coordinate position = null;
        int countLookingFor = gridSize - 1; // looking for a count where we are 1 off n in a row

        if (player == 'O')
            countLookingFor *= -1;

        // try to find a line where we are 1 off n in a row
        // we utilise moveSums which is used to store the count for the player occupying
        //  the most positions on the current winning line. rows are followed by columns
        //  which are then followed by the diagonals - this is detailed in Game.java.
        //  'X' gives a positive value in moveSums and 'O' gives a negative value in it.
        for (int moveNum = 0; moveNum < gridSize; moveNum++)
            if (moveSums[moveNum] == countLookingFor) { // found a line where we are one off winning
                if (moveNum < gridSize) { // is row
                    for (int column = 0; column < gridSize; column++)
                        if (grid[column][moveNum] == 0) // ie if is blank
                            position = new Coordinate(column, moveNum); // place in blank square
                } else if (moveNum < gridSize * 2) { // is a column
                    for (int row = 0; row < gridSize; row++)
                        if (grid[(moveNum - gridSize)][row] == 0) // ie if is blank
                            position = new Coordinate((moveNum - gridSize), row); // place in blank square
                } else if (moveNum == (gridSize * 2)) { // is the leading diagonal
                    for (int i = 0; i < gridSize; i++)
                        if (grid[i][i] == 0) // ie if is blank
                            position = new Coordinate(i, i); // place in blank square
                } else if (moveNum == ((gridSize * 2) + 1)) { // is the anti diagonal
                    for (int row = 0, column = (gridSize - 1); row < gridSize; row++, column--)
                        if (grid[column][row] == 0) // ie if is blank
                            position = new Coordinate(column, row); // place in blank square
                }
            }

        return position;
    }

    private Coordinate getRandomMove(char[][] grid, int gridSize) {
        Coordinate position = new Coordinate();

        do {
            position.setX(rand.nextInt(gridSize));
            position.setY(rand.nextInt(gridSize));
        } while (grid[position.getX()][position.getY()] != 0); // if != 0 means occupied square

        return position;
    }

}