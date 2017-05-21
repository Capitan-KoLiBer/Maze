package koliber.library;

import javax.swing.*;
import java.awt.*;

/**
 * Created by koliber on 5/4/17.
 */
public class Panel extends JPanel{


    private Game game;
    void setGame(Game game){
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics graphics){
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,getWidth(),getHeight());
        for(int a = 0 ; a < game.game_array.length ; a++){
            for(int b = 0 ; b < game.game_array[a].length ; b++){
                if(game.game_array[b][a] == game.CLEAR){
                    graphics.setColor(Color.WHITE);
                    graphics.fillRect(a*getWidth()/game.game_array.length , b*getHeight()/game.game_array[0].length , getWidth()/game.game_array.length , getHeight()/game.game_array[0].length);
                }else if(game.game_array[b][a] == game.WALL){
                    graphics.drawImage(new ImageIcon(getClass().getResource("/Brick_Block.png")).getImage(),a*getWidth()/game.game_array.length , b*getHeight()/game.game_array[0].length,getWidth()/game.game_array.length , getHeight()/game.game_array[0].length,this);
                }else if(game.game_array[b][a] == game.MAN){
                    graphics.setColor(Color.BLUE);
                    graphics.fillRect(a*getWidth()/game.game_array.length , b*getHeight()/game.game_array[0].length , getWidth()/game.game_array.length , getHeight()/game.game_array[0].length);
                }else if(game.game_array[b][a] == game.END){
                    graphics.setColor(Color.GREEN);
                    graphics.fillRect(a*getWidth()/game.game_array.length , b*getHeight()/game.game_array[0].length , getWidth()/game.game_array.length , getHeight()/game.game_array[0].length);
                }else if(game.game_array[b][a] == game.SOLVE_WAY){
                    graphics.setColor(Color.YELLOW);
                    graphics.fillRect(a*getWidth()/game.game_array.length , b*getHeight()/game.game_array[0].length , getWidth()/game.game_array.length , getHeight()/game.game_array[0].length);
                }
            }
        }

    }
}
