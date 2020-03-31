package noughtsandcrosses.players.ai;

import noughtsandcrosses.game.Coordinate;
import noughtsandcrosses.game.Game;
import noughtsandcrosses.players.MovingPlayer;
import noughtsandcrosses.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The type Optimised AI.
 *
 * @author maw101
 */
public class OptimisedAI implements MovingPlayer {

    @Override
    public Coordinate getMove(char[][] grid, int gridSize, int[] moveSums, Player[] players, int currentPlayerIndex) throws RuntimeException {
        // copy current game to run simulations on it separately
        char[][] gridCopy;
        int[] moveSumsCopy;

        // store players
        Player currentPlayer = players[currentPlayerIndex];
        int opponentIndex = getOpponentIndex(currentPlayerIndex);

        // setup variables for storing best move and its corresponding score
        Coordinate currentBestMove = null;
        int currentBestScore = Integer.MIN_VALUE;
        int currentMovesScore;

        // check that the grid is 3x3, if not then will require too much computing power so throw exception
        if (gridSize != 3) {
            throw new IllegalStateException("Board must be 3x3 for this AI to be able to compute a move - not enough " +
                    "computing power to compute grids larger than this");
        }

        // for each valid move, run move and subsequent games scoring each of the outcomes
        for (Coordinate move : Game.getAllValidMoveCoordinates(grid, gridSize)) {
            // copy the grid as need to simulate moves - cannot do on real grid
            gridCopy = deepCopyGrid(grid);

            // copy moveSums as need to reflect state of game on the copied grid
            moveSumsCopy = Arrays.copyOf(moveSums, moveSums.length);

            // make the current move on the copied grid
            Game.placeMove(move, players, currentPlayerIndex, gridCopy, gridSize);

            // add to move sums
            Game.addMoveToMoveSums(move, currentPlayer.getSymbol(), gridSize, moveSumsCopy);

            currentMovesScore = minimaxScoring(players, gridCopy, gridSize, moveSumsCopy, currentPlayerIndex, opponentIndex);
            if (currentMovesScore > currentBestScore) {
                currentBestScore = currentMovesScore;
                currentBestMove = move;
            }
        }

        return currentBestMove;
    }

    private int minimaxScoring(Player[] players, char[][] grid, int gridSize, int[] moveSums, int playerToOptimiseIndex, int playerToMoveIndex) {
        // setup players
        Player playerToOptimise = players[playerToOptimiseIndex];
        Player playerToMove = players[playerToMoveIndex];
        int indexOfOpponentOfPlayerToMove;

        // copy current game to run simulations on it separately
        char[][] gridCopy;
        int[] moveSumsCopy;

        // setup variables for storing best move and its corresponding score
        List<Integer> responseScores = new ArrayList<>();

        // determine if there is a winner for the game
        char winningPlayerSymbol = Game.getWinner(moveSums, grid, gridSize);
        if (winningPlayerSymbol != Character.MIN_VALUE) { // game must be complete
            if (winningPlayerSymbol == Character.MAX_VALUE) { // game drawn
                return 0; // game drawn as grid is full
            } else if (winningPlayerSymbol == playerToOptimise.getSymbol()) {
                return 1;
            } else { // opponent has won - not what we wanted
                return -1;
            }
        }

        // for each valid move, run subsequent games scoring each of the outcomes
        for (Coordinate move : Game.getAllValidMoveCoordinates(grid, gridSize)) {
            // copy the grid as need to simulate moves - cannot do on real grid
            gridCopy = deepCopyGrid(grid);
            // copy moveSums as need to reflect state of game on the copied grid
            moveSumsCopy = Arrays.copyOf(moveSums, moveSums.length);

            // make the current move on the copied grid
            Game.placeMove(move, players, playerToMoveIndex, gridCopy, gridSize);

            // add to move sums
            Game.addMoveToMoveSums(move, playerToMove.getSymbol(), gridSize, moveSumsCopy);

            // get opponent
            indexOfOpponentOfPlayerToMove = getOpponentIndex(playerToMoveIndex);

            // recursively run subsequent games and return best score from these
            responseScores.add(minimaxScoring(players, gridCopy, gridSize, moveSumsCopy, playerToOptimiseIndex, indexOfOpponentOfPlayerToMove));
        }

        if (playerToMove.equals(playerToOptimise)) { // trying to maximise score, return max of all scores
            return Collections.max(responseScores);
        } else { // trying to minimise score, return min of all scores
            return Collections.min(responseScores);
        }
    }

    private static char[][] deepCopyGrid(char[][] originalGrid) {
        if (originalGrid == null) {
            return null;
        }

        final char[][] copy = new char[originalGrid.length][originalGrid.length];
        for (int i = 0; i < originalGrid.length; i++) {
            copy[i] = Arrays.copyOf(originalGrid[i], originalGrid[i].length);
        }

        return copy;
    }

    private int getOpponentIndex(int currentPlayerIndex) {
        if ((currentPlayerIndex == 0) || (currentPlayerIndex == 1)) {
            return (currentPlayerIndex + 1) % 2;
        } else {
            throw new IllegalArgumentException("Invalid current player index specified. '" + currentPlayerIndex + "' given, should be 0 or 1.");
        }
    }

}