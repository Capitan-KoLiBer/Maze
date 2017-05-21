package koliber.library;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        Panel panel = new Panel();
        game.setPanel(panel);
        panel.setGame(game);
        new UserInteface(game,panel);
    }
}
