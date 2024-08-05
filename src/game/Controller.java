package game;

import java.awt.event.KeyAdapter;

/*
Клас стежитиме за натисканням клавіш у
 час гри.
 */
public class Controller extends KeyAdapter {

    Model model;

    //метод повертаючий игрове поле в контролер
    public Tile[][] getGameTiles(){
        return model.getGameTiles();
    }

    //метод повертаючий текучий рахунок у контроллер
    public int getScore(){
        return model.score;
    }

}
