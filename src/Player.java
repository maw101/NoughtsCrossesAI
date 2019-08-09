import java.util.Objects;

public class Player {

    private char symbol;
    private String algorithmName;

    public Player(char symbol, String algorithmName) {
        this.symbol = symbol;
        this.algorithmName = algorithmName;
    }

    public char getSymbol() {
        return symbol;
    }

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