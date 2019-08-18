# Noughts and Crosses with AI Players
 
A 3x3 Noughts and Crosses engine allowing:
 * player vs player games;
 * player vs computer games;
 * computer vs computer games.
 
Several computer players have been implemented, namely: 
  * RandomAI - a very dumb player that just makes valid random moves
  * WinningMoveAI - a more intelligent player that:
      * makes winning moves for itself if it is able to win on the current turn;
      * otherwise a random move will be chosen.
  * FindWinningBlockLosingAI - an even more intelligent player that: 
      * firstly makes winning moves for itself if it is able to win on the current turn;
      * blocks the opponents winning move if the opponent is able to win on their next turn;
      * otherwise a random move will be chosen.
  * OptimisedAI - a computer player which utilises the minimax algorithm - this allows the computer to look at possible future game states by simulating future outcomes if it plays in a given free position.

## Battle Simulations

Battle simulations between each of the AI players (and even the human player) can be run through the `runBattles` method. This simulates `n` battles given the names of two algorithms and sends a formatted description of the simulation to the standard output stating the win and draw statistics.

## Optimisations

### Determining Winner in Constant Time
By utilising an array of size (2n + 2) we are able to keep track of all the n-in-a-row lines possible for an n x n board.
Once one of the positions reaches a value of either n or -n, we know we have a winner.

For each 'X' present on a line, we add 1 to the line in the winners array. For each 'O' present on a line, we add -1.

Once a move is made we add the value to:
* the moves row sum;
* the moves column sum;
* and if the move is on leading diagonal, we add the value to this sum.

The array is organised as follows:
* Index `0` to `n-1` - stores each of the row lines
* Index `n` to `2n - 1` - stores each of the column lines
* Index `2n` and `2n + 1` - stores the two diagonals.

After a move is made we can pass in the moves coordinates to a function which checks the row sum, the column sum, and both of the diagonal sums.
