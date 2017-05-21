package koliber.library;

import java.awt.*;
import java.util.*;

/**
 * Created by koliber on 5/4/17.
 */
public class Game {

    int game_array[][] = new int[50][50];
    int solve_array[][] = new int[50][50];
    boolean game_seen[][] = new boolean[50][50];
    Point end = null,man = null,start = null;
    public int WALL = 0;
    public int CLEAR = 1;
    public int MAN = 2;
    public int END = 3;
    public int SOLVE_WAY = 4;
    private final int MAP_GENERATE_DELAY = 1;
    private final int SOLVE_DELAY = 30;
    ArrayList<MPoint> block_ways = new ArrayList<>();
    ArrayList<MPoint> seenable_list;
    Random randomizer = new Random();
    private Panel panel;
    private Tools tools;
    private Thread init_thread;

    void setPanel(Panel panel){
        this.panel = panel;
    }

    void setTools(Tools tools){
        this.tools = tools;
    }

    Game(){
        seenable_list = new ArrayList<>();
        init_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int a = 0 ; a < game_array.length ; a++){
                    Arrays.fill(game_seen[a],false);
                    Arrays.fill(game_array[a],0);
                }
                shuffle(new MPoint(5,5,4));
            }
        });
        init_thread.start();
    }

    private void shuffle(MPoint focus_point){
        ArrayList<MPoint> neighbours;
        do{
            game_seen[focus_point.y][focus_point.x] = true;
            if(isClearableBlock(focus_point)){
                game_array[focus_point.y][focus_point.x] = CLEAR;
                neighbours = getNeighbours(focus_point);
                seenable_list.addAll(neighbours);

            }else{
                game_array[focus_point.y][focus_point.x] = WALL;
            }
            if(seenable_list.size() > 0){
                int rand = randomizer.nextInt(seenable_list.size());
                focus_point = seenable_list.get(rand);
                seenable_list.remove(rand);
            }
            try{
                Thread.sleep(MAP_GENERATE_DELAY);
                panel.repaint();
            }catch (Exception e){}
        } while(seenable_list.size() > 0);
        pointWay();
    }

    private ArrayList<MPoint> getNeighbours(MPoint point){
        ArrayList<MPoint> result_array = new ArrayList<>();
        try {
            if(point.x + 1 < game_array.length && ! game_seen[point.y][point.x+1] && game_array[point.y][point.x+1] != CLEAR){
                result_array.add(new MPoint(point.x+1 , point.y , 2));
            }
        }catch (Exception e){}
        try {
            if(point.y + 1 < game_array.length && ! game_seen[point.y+1][point.x] && game_array[point.y+1][point.x] != CLEAR){
                result_array.add(new MPoint(point.x , point.y+1 , 3));
            }
        }catch (Exception e){}
        try {
            if(point.x - 1 >= 0 && ! game_seen[point.y][point.x-1] && game_array[point.y][point.x-1] != CLEAR){
                result_array.add(new MPoint(point.x-1 , point.y , 0));
            }
        }catch (Exception e){}
        try {
            if(point.y - 1 >= 0 && ! game_seen[point.y-1][point.x] && game_array[point.y-1][point.x] != CLEAR){
                result_array.add(new MPoint(point.x , point.y-1 , 1));
            }
        }catch (Exception e){}
        System.out.println("X="+point.x+"\tY="+point.y+"\tPARENT="+point.parent+"\tCOUNT="+result_array.size());
        return result_array;
    }

    private boolean isClearableBlock(MPoint point){
        // point.parent === check_side
        boolean is_clearable = true;
        switch (point.parent){
            case 0 :{
                for(int y = point.y - 1 ; y <= point.y + 1; y++){
                    for(int x = point.x - 1; x <= point.x ; x++){
                        try {
                            if(game_array[y][x] == CLEAR || point.y == 0 || point.x == 0 || point.y == game_array.length - 1 || point.x == game_array.length - 1){
                                is_clearable = false;
                                break;
                            }
                        }catch (Exception e){}
                    }
                }
                break;
            }
            case 1 :{
                for(int y = point.y - 1 ; y <= point.y ; y++){
                    for(int x = point.x - 1 ; x <= point.x + 1 ; x++){
                        try {
                            if(game_array[y][x] == CLEAR || point.y == 0 || point.x == 0 || point.y == game_array.length - 1 || point.x == game_array.length - 1){
                                is_clearable = false;
                                break;
                            }
                        }catch (Exception e){}
                    }
                }
                break;
            }
            case 2 :{
                for(int y = point.y - 1 ; y <= point.y + 1; y++){
                    for(int x = point.x ; x <= point.x + 1; x++){
                        try {
                            if(game_array[y][x] == CLEAR || point.y == 0 || point.x == 0 || point.y == game_array.length - 1 || point.x == game_array.length - 1){
                                is_clearable = false;
                                break;
                            }
                        }catch (Exception e){}
                    }
                }
                break;
            }
            case 3 :{
                for(int y = point.y ; y <= point.y + 1; y++){
                    for(int x = point.x - 1 ; x <= point.x + 1 ; x++){
                        try {
                            if(game_array[y][x] == CLEAR || point.y == 0 || point.x == 0 || point.y == game_array.length - 1 || point.x == game_array.length - 1){
                                is_clearable = false;
                                break;
                            }
                        }catch (Exception e){}
                    }
                }
                break;
            }
            default: {
                for(int y = point.y - 1; y <= point.y + 1; y++){
                    for(int x = point.x - 1 ; x <= point.x + 1 ; x++){
                        try {
                            if(game_array[y][x] == CLEAR || point.y == 0 || point.x == 0 || point.y == game_array.length - 1 || point.x == game_array.length - 1){
                                is_clearable = false;
                                break;
                            }
                        }catch (Exception e){}
                    }
                }
                break;
            }
        }

        return is_clearable;
    }

    private void pointWay(){
        man = null;
        end = null;
        for(int b = 0 ; b < game_array.length ; b++) {
            for(int a = 0 ; a < game_array.length ; a++){
                if (game_array[b][a] == CLEAR) {
                    game_array[b][a] = MAN;
                    man = new Point(a, b);
                    start = man;
                    break;
                }
            }
            if(man != null) break;
        }
        for(int b = game_array.length-1 ; b >= 0 ; b--){
            for(int a = game_array.length-1 ; a >= 0 ; a--){
                if(game_array[b][a] == CLEAR){
                    game_array[b][a] = END;
                    end = new Point(a,b);
                    break;
                }
            }
            if(end != null) break;
        }
        tools.startGame();
    }

    public void restartGame(){
        seenable_list = new ArrayList<>();
        init_thread.interrupt();
        init_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int a = 0 ; a < game_array.length ; a++){
                    Arrays.fill(game_seen[a],false);
                    Arrays.fill(game_array[a],0);
                }
                shuffle(new MPoint(5,5,4));
            }
        });
        init_thread.start();
    }

    public void solveGame(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                solve();
            }
        }).start();
    }

    private void copyArray(){
        for(int a = 0 ; a < game_array.length ; a++){
            for(int b = 0 ; b < game_array[a].length ; b++ ){
                solve_array[a][b] = game_array[a][b];
            }
        }
    }

    private void solve(){
        copyArray();
        ArrayList<MPoint> next_blocks;
        Stack<MPoint> the_way = new Stack<>();
        MPoint this_point = new MPoint(man.x,man.y,2);
        do{
            next_blocks = getNextBlocks(this_point);
            if(next_blocks.size() == 0){
                // block way
                solve_array[this_point.y][this_point.x] = WALL;
                game_array[this_point.y][this_point.x] = CLEAR;
                this_point = the_way.pop();
            }else{
                the_way.push(this_point);
                this_point = next_blocks.get(0);
                // have way
            }
            game_array[this_point.y][this_point.x] = SOLVE_WAY;
            try {
                Thread.sleep(SOLVE_DELAY);
                panel.repaint();
            }catch (Exception e){}

        }while (this_point.x != end.x || this_point.y != end.y);
    }

    private ArrayList<MPoint> getNextBlocks(MPoint block){
        ArrayList<MPoint> next_blocks = new ArrayList<>();
        if((solve_array[block.y][block.x+1] == CLEAR || solve_array[block.y][block.x+1] == SOLVE_WAY  || solve_array[block.y][block.x+1] == END) && block.parent != 0){
            next_blocks.add(new MPoint(block.x+1,block.y,2));
        }
        if((solve_array[block.y+1][block.x] == CLEAR || solve_array[block.y+1][block.x] == SOLVE_WAY || solve_array[block.y+1][block.x] == END) && block.parent != 1){
            next_blocks.add(new MPoint(block.x,block.y+1,3));
        }
        if((solve_array[block.y][block.x-1] == CLEAR || solve_array[block.y][block.x-1] == SOLVE_WAY || solve_array[block.y][block.x-1] == END) && block.parent != 2){
            next_blocks.add(new MPoint(block.x-1,block.y,0));
        }
        if((solve_array[block.y-1][block.x] == CLEAR || solve_array[block.y-1][block.x] == SOLVE_WAY || solve_array[block.y-1][block.x] == END) && block.parent != 3){
            next_blocks.add(new MPoint(block.x,block.y-1,1));
        }
        return next_blocks;
    }

    private class MPoint extends Point{
        int x,y,parent;
        MPoint(int x ,int y ,int parent){
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

}
