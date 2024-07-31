package game;

import java.util.ArrayList;
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

private void compressTiles(Tile[] tiles) {
    int insertPosition = 0;
    for (int i = 0; i < FIELD_WIDTH; i++) {
        if (!tiles[i].isEmpty()) {
            if (i != insertPosition) {
                tiles[insertPosition] = tiles[i];
                tiles[i] = new Tile();
            }
            insertPosition++;
        }
    }
}

/*
�����, �� ��������� ����� ���������� ������ ������ ������ �������, �����.
��� {4, 4, 2, 0} ��� ����� {8, 2, 0, 0}.
������� �����, �� ��� {4, 4, 4, 4} ������������� {8, 8, 0, 0}, � {4, 4, 4, 0} {8, 4, 0, 0}.
 */
    private void mergeTiles(Tile[] tiles){
        for (int i = 0; i < FIELD_WIDTH-1; i++) {
            if (tiles[i].value == tiles[i+1].value){
                tiles[i].value = tiles[i].value + tiles[i+1].value;
                tiles[i+1].value = 0;
                if (tiles[i].value>maxTile) maxTile=tiles[i].value;
                score+=tiles[i].value;
            }
        }
        compressTiles(tiles);
    }

    public static void main(String[] args) {
        Model m = new Model();
        m.gameTiles = new Tile[][]{{new Tile(4), new Tile(4), new Tile(2), new Tile(0)},
                {new Tile(4), new Tile(2), new Tile(0), new Tile(4)},
                {new Tile(4), new Tile(4), new Tile(4), new Tile(0)},
                {new Tile(4), new Tile(4), new Tile(4), new Tile(0)}};
        for (Tile[] t: m.gameTiles) {
            for (int i = 0; i < t.length; i++) {
                System.out.print(t[i].value + " ");
            }
            //m.compressTiles(t);
        }
        System.out.println();
        for (Tile[] t: m.gameTiles) {
            m.mergeTiles(t);
        }
        for (Tile[] t: m.gameTiles) {
            for (int i = 0; i < t.length; i++) {
                System.out.print(t[i].value + " ");
            }
            //m.compressTiles(t);
        }
        System.out.println();
        System.out.println(m.score);
        System.out.println(m.maxTile);
    }
}
