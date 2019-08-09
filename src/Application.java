class Application {

    public static void main(String[] args) throws Exception {
        Game g = new Game();
        g.runBattles(500000, "RandomAI", "FindWinningBlockLosingAI");
        //g.play(args[0], args[1]);
    }

}