public class RandomAI implements AI {

    @Override
    public Coordinate makeMove(char[][] grid, char player) {
        Coordinate position = new Coordinate();

        do {
            position.setX(rand.nextInt(grid.length));
            position.setY(rand.nextInt(grid.length));
        } while (grid[position.getX()][position.getY()] != 0); // if != 0 means occupied square

        return position;
    }

}