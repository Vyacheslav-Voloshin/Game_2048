package game;
/*
 Клас міститиме ігрову логіку та зберігатиме
ігрове поле.
Клас буде відповідальний за всі маніпуляції, що проводяться
 з ігровим полем.
 */
public class Model {
    private static final int FIELD_WIDTH = 4; //константа відповідаюча за ширину поля
    private Tile [][] gameTiles; //

    public Model() {
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTiles[i][j] = new Tile();
            }
        }
    }
}
