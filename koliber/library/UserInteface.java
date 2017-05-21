package koliber.library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by koliber on 5/4/17.
 */
public class UserInteface extends JFrame{


    private Game game;
    private Panel panel;
    private Tools tools;

    UserInteface(Game game, Panel panel){
        this.game = game;
        this.panel = panel;
        initMenu();
        JPanel game_panel = new JPanel();
        Tools tools = new Tools(game,panel);
        this.tools = tools;
        game.setTools(tools);
        tools.setPreferredSize(new Dimension(getWidth(),50));
        game_panel.setLayout(new BorderLayout());
        game_panel.add(tools,BorderLayout.NORTH);
        game_panel.add(panel,BorderLayout.CENTER);
        setPreferredSize(new Dimension(700,700));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(tools);
        setContentPane(game_panel);
        setVisible(true);
        pack();
        setFocusable(true);
        requestFocus();
    }

    private void initMenu(){
        MenuBar menu_bar = new MenuBar();
        Menu game_menu = new Menu("Game");
        MenuItem restart_item = new MenuItem("Restart");
        MenuItem solve_item = new MenuItem("Solve");
        restart_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tools.restartGame();
            }
        });
        solve_item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tools.solveGame();
            }
        });
        game_menu.add(restart_item);
        game_menu.add(solve_item);
        menu_bar.add(game_menu);
        setMenuBar(menu_bar);
    }
}
