# NoughtsCrossesAI
 
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
 
Battle simulations between each of the AI players can be run through the `runBattles` method. This simulates `n` battles given the names of two algorithms and sends a formatted description of the simulation to the standard output stating the win and draw statistics.
