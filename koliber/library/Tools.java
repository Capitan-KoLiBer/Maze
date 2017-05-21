package koliber.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tools extends JPanel implements KeyListener{

    private Game game;
    private Panel panel;
    private int moves = 0;
    private int second = 0;
    private JLabel moves_label,time_label;
    private Thread timer_thread;

    Tools(Game game, Panel panel){
        this.game = game;
        this.panel = panel;
        setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        moves_label = new JLabel("Moves : 0");
        time_label = new JLabel("Time : 00:00:00");
        add(moves_label);
        add(time_label);
        timer_thread = new Thread(() -> {
            while(true){
                try {
                    Thread.sleep(1000);
                    second ++;
                    refreshLabels();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timer_thread.start();
    }

    public void restartGame(){
        game.restartGame();
    }

    public void startGame(){
        second = 0;
        moves = 0;
        refreshLabels();
    }

    public void solveGame(){
        game.solveGame();
    }

    private void refreshLabels(){
        moves_label.setText("Moves : "+moves);
        time_label.setText("Time : "+getTime());
    }

    private String getTime(){
        int hour = second%(60*60*24) / (60*60);
        int min = second%(60*60) / (60);
        int sec = second%(60);
        String h,m,s;
        if(hour < 10){
            h = "0"+hour;
        }else{
            h = hour +"";
        }
        if(min < 10){
            m = "0"+min;
        }else{
            m = min +"";
        }
        if(sec < 10){
            s = "0"+sec;
        }else{
            s = sec +"";
        }
        return h+":"+m+":"+s;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case 37 :{
                // left
                if(game.game_array[game.man.y][game.man.x-1] == game.CLEAR || game.game_array[game.man.y][game.man.x-1] == game.END || game.game_array[game.man.y][game.man.x-1] == game.SOLVE_WAY){
                    game.game_array[game.man.y][game.man.x] = game.CLEAR;
                    game.game_array[game.man.y][game.man.x-1] = game.MAN;
                    game.man = new Point(game.man.x-1,game.man.y);
                    moves++;
                }
                break;
            }
            case 38 :{
                // top
                if(game.game_array[game.man.y-1][game.man.x] == game.CLEAR || game.game_array[game.man.y-1][game.man.x] == game.END || game.game_array[game.man.y-1][game.man.x] == game.SOLVE_WAY){
                    game.game_array[game.man.y][game.man.x] = game.CLEAR;
                    game.game_array[game.man.y-1][game.man.x] = game.MAN;
                    game.man = new Point(game.man.x,game.man.y-1);
                    moves++;
                }
                break;
            }
            case 39 :{
                // right
                if(game.game_array[game.man.y][game.man.x+1] == game.CLEAR || game.game_array[game.man.y][game.man.x+1] == game.END || game.game_array[game.man.y][game.man.x+1] == game.SOLVE_WAY){
                    game.game_array[game.man.y][game.man.x] = game.CLEAR;
                    game.game_array[game.man.y][game.man.x+1] = game.MAN;
                    game.man = new Point(game.man.x+1,game.man.y);
                    moves++;
                }
                break;
            }
            case 40 :{
                // bottom
                if(game.game_array[game.man.y+1][game.man.x] == game.CLEAR || game.game_array[game.man.y+1][game.man.x] == game.END || game.game_array[game.man.y+1][game.man.x] == game.SOLVE_WAY){
                    game.game_array[game.man.y][game.man.x] = game.CLEAR;
                    game.game_array[game.man.y+1][game.man.x] = game.MAN;
                    game.man = new Point(game.man.x,game.man.y+1);
                    moves++;
                }
                break;
            }
        }
        refreshLabels();
        panel.repaint();
        if(game.man.y == game.end.y && game.man.x == game.end.x){
            JOptionPane.showMessageDialog(null,"Game END !\n Moves : "+moves+"\n Time : "+getTime());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
