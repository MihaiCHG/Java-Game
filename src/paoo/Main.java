package paoo;

import paoo.Game.*;

public class Main {

    public static void main(String[] args) throws Exception{
        Game game = Game.getInstance();
        ImageLoader imgLoader = ImageLoader.getInstance();
        DataBase.init();
        //game.start();
    }
}
