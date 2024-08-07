package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
Клас стежитиме за натисканням клавіш у
 час гри.
 */
public class Controller extends KeyAdapter {

    private Model model;
    private View view;

    private static final int WINNING_TILE = 2048; //Змінна визначатиме вагу плитки при досягненні якоїгра буде вважатися виграною.

    public Controller(Model model) {
        this.model = model;
        view = new View(this);
    }

    //метод повертаючий игрове поле в контролер
    public Tile[][] getGameTiles(){
        return model.getGameTiles();
    }

    //метод повертаючий текучий рахунок у контроллер
    public int getScore(){
        return model.score;
    }

    public View getView() {
        return view;
    }

    //метод resetGame, який дозволить повернути ігрове поле у початковий стан.
    public void resetGame(){
        model.score=0;
        model.resetGameTiles();
        view.isGameLost=false;
        view.isGameWon=false;
    }


    // метод, який надає можливість обробляти ход на клавіатурі користувача
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE) resetGame();
        if (!model.canMove()) view.isGameLost=true;
        if (!view.isGameLost && !view.isGameWon) {
            switch (e.getKeyCode()) {
                case (KeyEvent.VK_LEFT):
                    model.left();
                    break;
                case (KeyEvent.VK_RIGHT):
                    model.right();
                    break;
                case (KeyEvent.VK_UP):
                    model.up();
                    break;
                case (KeyEvent.VK_DOWN):
                    model.down();
                    break;
                case (KeyEvent.VK_Z):
                    model.rollback();
                    break;
                case(KeyEvent.VK_R):
                    model.randomMove();
                    break;
                case(KeyEvent.VK_A):
                    model.autoMove();
                    break;
            }
        }
        if (model.maxTile==WINNING_TILE){
            view.isGameWon=true;
        }
        view.repaint();

    }
}
