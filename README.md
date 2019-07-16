# NoughtsCrossesAI
 
A 3x3 Noughts and Crosses engine allowing:
 * player vs player games;
 * player vs computer games;
 * computer vs computer games.
 
Several computer players have been implemented, namely: 
  * a very dumb player that just makes valid random moves
  * a more intelligent player that:
      * makes winning moves for itself if it is able to win on the current turn;
      * otherwise a random move will be chosen.
  * an even more intelligent player that: 
      * firstly makes winning moves for itself if it is able to win on the current turn;
      * blocks the opponents winning move if the opponent is able to win on their next turn;
      * otherwise a random move will be chosen.
      
The next computer player will utilise the minimax algorithm which makes use of recursion - this will allow the computer to look at possible game states in the future and allows it to choose the move with the highest chance of it winning in the future. This will be implemented in the next few weeks once I get a more powerful computer.
 
