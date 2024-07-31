package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 ���� �������� ������ ����� �� ����������
������ ����.
���� ���� ������������ �� �� ����������, �� �����������
 � ������� �����.
 */
public class Model {
    private static final int FIELD_WIDTH = 4; //��������� ���������� �� ������ ����
    private Tile [][] gameTiles;

     int score = 0; //����, ��� ������ �������� �������

     int maxTile = 0; // ����, ��� �� ����������� ���� ������ �� �������� ���



    public Model() {
        resetGameTiles();
    }


/*
����� addTile, ���� �������� �� ������ ������ �, ���� ���
 �,����� ���� ���� � ���, ������� ���������� �����,
�� 2 ��� 4 (�� 9 ����� ������� ��������� 1 �������).
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
����� getEmptyTiles ������� ��������� ������ ������� ������ � ����� gameTiles.
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
    ����� resetGameTiles ������� ����������� ����� gameTiles ������
�������� �� ����� �������� ���� � ��� �� ��������� ���� ������� ������ addTile.
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
 �����, �� ��������� ����� ���������� ��������� ������, ����� �����, ��� �� ������
������ ��������, �����. ��� {4, 2, 0, 4} ��� ����� {4, 2, 4, 0}
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
�����, �� ��������� ����� ���������� ������ ������ ������ �������, �����.
��� {4, 4, 2, 0} ��� ����� {8, 2, 0, 0}.
������� �����, �� ��� {4, 4, 4, 4} ������������� {8, 8, 0, 0}, � {4, 4, 4, 0} {8, 4, 0, 0}.
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

    /*
    ����� left, ���� ���� ��� ����� ����� ������ gameTiles ��������� ������ compressTiles
 �� mergeTiles � �������� ���� ������ �� ���������  ������ addTile � ���, ���� �� ���������.
     */
    public void left (){
        boolean res = false;
        for (Tile[] t:gameTiles) {
            if (compressTiles(t)|mergeTiles(t)) res = true;
        }
        if (res) addTile();
    }

    public static void main(String[] args) {
        Model model = new Model();
// ��� compress
//        Tile[][] tiles = new Tile[][]{{new Tile(8), new Tile(0), new Tile(0), new Tile(0)},
//                {new Tile(4), new Tile(0), new Tile(0), new Tile(4)},
//                {new Tile(0), new Tile(4), new Tile(4), new Tile(0)},
//                {new Tile(0), new Tile(2), new Tile(0), new Tile(2)}};
        // ��� merge
           Tile[][] tiles = new Tile[][]{{new Tile(8), new Tile(0), new Tile(0), new Tile(0)},
                   {new Tile(4), new Tile(2), new Tile(2), new Tile(4)},
                   {new Tile(4), new Tile(4), new Tile(4), new Tile(0)},
                  {new Tile(4), new Tile(4), new Tile(4), new Tile(4)}};
        //
        // ��
        for (int i = 0; i < tiles.length; i++) {
            System.out.println(Arrays.toString(tiles[i]));
        }
        System.out.println();
        //
        for (int i = 0; i < tiles.length; i++) {
            System.out.println(model.mergeTiles(tiles[i]));
            // System.out.println(model.mergeTiles(tiles[i]));
        }
        System.out.println();
        //�����
        for (int i = 0; i < tiles.length; i++) {
            System.out.println(Arrays.toString(tiles[i]));
        }
    }
}
