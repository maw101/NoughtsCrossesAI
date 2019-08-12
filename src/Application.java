class Application {

    public static void main(String[] args) throws Exception {
        Game g = new Game();
        //g.runBattles(1000, "FindWinningBlockLosingAI", "OptimisedAI");
        g.play(args[0], args[1]);
    }

}