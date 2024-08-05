package game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 Клас міститиме ігрову логіку та зберігатиме
ігрове поле.
Клас буде відповідальний за всі маніпуляції, що проводяться
 з ігровим полем.
 */
public class Model {
    private static final int FIELD_WIDTH = 4; //константа відповідаюча за ширину поля
    private Tile [][] gameTiles;

     int score = 0; //поле, яке зберігає поточний рахунок

     int maxTile = 0; // поле, яке має максимальна вагу плитки на ігровому полі



    public Model() {
        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }


    /*
        метод addTile, який дивитися які плитки порожні і, якщо такі
         є,міняти вагу однієї з них, обраною випадковим чином,
        на 2 або 4 (на 9 двійок повинна припадати 1 четвірка).
         */
    private void  addTile(){
        List<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int index = (int) (Math.random() * emptyTiles.size()) % emptyTiles.size();
            Tile emptyTile = emptyTiles.get(index);
            emptyTile.value = Math.random() < 0.9 ? 2 : 4;
        }
    }

/*
Метод getEmptyTiles повинен повертати список порожніх плиток у масиві gameTiles.
 */
    private List<Tile> getEmptyTiles(){
        final List<Tile> tileList = new ArrayList<>();
        for (Tile[] tile:gameTiles) {
            for (Tile t:tile) {
                if (t.isEmpty()){
                    tileList.add(t);
                }
            }
        }
        return tileList;
    }


    /*
    Метод resetGameTiles повинен заповнювати масив gameTiles новими
плитками та міняти значення двох з них за допомогою двох викликів методу addTile.
     */
     void resetGameTiles(){
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

/*
 Метод, за допомогою якого відбувається стиснення плиток, таким чином, щоб всі порожні
плитки праворуч, тобто. ряд {4, 2, 0, 4} стає поряд {4, 2, 4, 0}
 */

private boolean compressTiles(Tile[] tiles) {
    boolean value = false;
    int insertPosition = 0;
    for (int i = 0; i < FIELD_WIDTH; i++) {
        if (!tiles[i].isEmpty()) {
            if (i != insertPosition) {
                tiles[insertPosition] = tiles[i];
                tiles[i] = new Tile();
                value = true;
            }
            insertPosition++;
        }
    }
    return value;
}

/*
Метод, за допомогою якого відбувається злиття плиток одного номіналу, тобто.
ряд {4, 4, 2, 0} стає поряд {8, 2, 0, 0}.
Зверніть увагу, що ряд {4, 4, 4, 4} перетвориться {8, 8, 0, 0}, а {4, 4, 4, 0} {8, 4, 0, 0}.
 */
    private boolean mergeTiles(Tile[] tiles){
        boolean value = false;
        for (int i = 0; i < FIELD_WIDTH-1; i++) {
            if (tiles[i].value == tiles[i+1].value){
                tiles[i].value = tiles[i].value + tiles[i+1].value;
                tiles[i+1].value = 0;
                if (tiles[i].value>maxTile) maxTile=tiles[i].value;
                score+=tiles[i].value;
                if (tiles[i].value != 0 )value = true;
            }
        }
        compressTiles(tiles);
        return value;
    }

    //метод, який повертає на 90 градусів по часовій стрілці наш масив
    private Tile[][] ninetyDegreeTurn (Tile[][] tiles){
        int n = tiles.length;
        Tile[][] res = new Tile[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[j][n-1-i]=tiles[i][j];

            }
        }
        return res;
    }

    /*
    метод left, який буде для кожної рядка масиву gameTiles викликати методи compressTiles
 та mergeTiles і додавати одну плитку за допомогою  методу addTile у разі, якщо це необхідно.
     */
    public void left (){
        boolean res = false;
        for (Tile[] t:gameTiles) {
            if (compressTiles(t)|mergeTiles(t)) res = true;
        }
        if (res) addTile();
    }

    /*
     метод up, який буде переміщати вниз елементи масиву, викликати для кожної рядка масиву gameTiles викликати методи compressTiles
 та mergeTiles і додавати одну плитку за допомогою  методу addTile у разі, якщо це необхідно.
     */
    public void   down(){
       gameTiles = ninetyDegreeTurn(gameTiles);
       left();
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
    }


    /*
     метод right, який буде переміщати праворуч елементи масиву, викликати для кожної рядка масиву gameTiles викликати методи compressTiles
 та mergeTiles і додавати одну плитку за допомогою  методу addTile у разі, якщо це необхідно.
     */
    public void   right(){
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        left();
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);

    }

    /*
     метод up, який буде переміщати вгору елементи масиву, викликати для кожної рядка масиву gameTiles викликати методи compressTiles
 та mergeTiles і додавати одну плитку за допомогою  методу addTile у разі, якщо це необхідно.
     */
    public void   up(){
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        left();
        gameTiles = ninetyDegreeTurn(gameTiles);
    }

    /*
    метод canMove повертаючий true у випадку, якщо в поточній позиції можна зробити
    хід те щоб стан ігрового поля змінилося. Інакше – false.
    Відповідно до правил гри хід можливий, якщо на ігровому полі є хоча б одна вільна клітина,
    і якщо є плитки, що стикаються один з одним і при цьому мають однакові значення.
     */
    public boolean canMove (){
        if (getEmptyTiles().size()!=0) return true;
        for (int x = 0; x < FIELD_WIDTH; x++) {
            for (int y = 0; y < FIELD_WIDTH; y++) {
                Tile t = gameTiles[x][y];
                if ((x < FIELD_WIDTH - 1 && t.value == gameTiles[x + 1][y].value)
                        || ((y < FIELD_WIDTH - 1) && t.value == gameTiles[x][y + 1].value)) {
                    return true;
                }
            }
        }
        return false;
    }

}
