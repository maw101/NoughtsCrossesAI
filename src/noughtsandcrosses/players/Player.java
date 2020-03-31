package noughtsandcrosses.players;

import java.util.Objects;

/**
 * The type Player.
 *
 * @author maw101
 */
public class Player {

    private final char symbol;
    private final String algorithmName;

    /**
     * Instantiates a new Player.
     *
     * @param symbol        the symbol
     * @param algorithmName the algorithm name
     */
    public Player(char symbol, String algorithmName) {
        this.symbol = symbol;
        this.algorithmName = algorithmName;
    }

    /**
     * Gets players symbol.
     *
     * @return the players symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Gets algorithm name.
     *
     * @return the algorithm name
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    @Override
    public String toString() {
        return "Player with symbol '" + symbol +
                "' using algorithm '" + algorithmName + "'.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return symbol == player.symbol &&
                Objects.equals(algorithmName, player.algorithmName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, algorithmName);
    }

}