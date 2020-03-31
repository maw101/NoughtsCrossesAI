/**
 * The type Application.
 *
 * @author maw101
 */
public class Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws RuntimeException if invalid player algorithm name
     */
    public static void main(String[] args) throws Exception {
        Game g = new Game();
        //g.runBattles(500, "FindWinningBlockLosingAI", "OptimisedAI");

        g.play("Human", "RandomAI");

        g.runBattles(10, "RandomAI", "OptimisedAI");
    }

}