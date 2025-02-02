package game;

import javax.swing.*;
import java.util.*;

/*
 ���� �������� ������ ����� �� ����������
������ ����.
���� ���� ������������ �� �� �����������, �� �����������
 � ������� �����.
 */
public class Model {
    private static final int FIELD_WIDTH = 4; //��������� ���������� �� ������ ����
    private Tile [][] gameTiles;

     int score = 0; //����, ��� ������ �������� �������

     int maxTile = 0; // ����, ��� �� ����������� ���� ������ �� �������� ���

    private Stack<Tile[][]> previousStates; // ���� � ����� �� �������� ���������� ���� �������� ����

    private Stack<Integer> previousScores; // ���� � ����� �� �������� ���������� �������

    boolean  isSaveNeeded = true;

    public Model() {
        resetGameTiles();
        previousScores = new Stack<>();
        previousStates = new Stack<>();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }


    /*
        ����� addTile, ���� �������� �� ������ ������� �, ���� ���
         �,����� ���� ������ � ���, ������� ���������� �����,
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
����� getEmptyTiles ������� ��������� ������ �������� ������ � ����� gameTiles.
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

    public int getEmptyTilesCount(){
        return getEmptyTiles().size();
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
 �����, �� ��������� ����� ���������� ��������� ������, ����� �����, ��� �� �������
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
�������� �����, �� ��� {4, 4, 4, 4} ������������� {8, 8, 0, 0}, � {4, 4, 4, 0} {8, 4, 0, 0}.
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

    //�����, ���� ������� �� 90 ������� �� ������ ������ ��� �����
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
    ����� left, ���� ���� ��� ����� ����� ������ gameTiles ��������� ������ compressTiles
 �� mergeTiles � �������� ���� ������ �� ���������  ������ addTile � ���, ���� �� ���������.
     */
    public void left (){
        if (isSaveNeeded) saveState(gameTiles);
        boolean res = false;
        for (Tile[] t:gameTiles) {
            if (compressTiles(t)|mergeTiles(t)) res = true;
        }
        if (res) addTile();
        isSaveNeeded=true;
    }

    /*
     ����� up, ���� ���� ��������� ���� �������� ������, ��������� ��� ����� ����� ������ gameTiles ��������� ������ compressTiles
 �� mergeTiles � �������� ���� ������ �� ���������  ������ addTile � ���, ���� �� ���������.
     */
    public void   down(){
        saveState(gameTiles);
       gameTiles = ninetyDegreeTurn(gameTiles);
       left();
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
    }


    /*
     ����� right, ���� ���� ��������� �������� �������� ������, ��������� ��� ����� ����� ������ gameTiles ��������� ������ compressTiles
 �� mergeTiles � �������� ���� ������ �� ���������  ������ addTile � ���, ���� �� ���������.
     */
    public void   right(){
        saveState(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        left();
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);

    }

    /*
     ����� up, ���� ���� ��������� ����� �������� ������, ��������� ��� ����� ����� ������ gameTiles ��������� ������ compressTiles
 �� mergeTiles � �������� ���� ������ �� ���������  ������ addTile � ���, ���� �� ���������.
     */
    public void   up(){
        saveState(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        gameTiles = ninetyDegreeTurn(gameTiles);
        left();
        gameTiles = ninetyDegreeTurn(gameTiles);
    }

    /*
    ����� canMove ����������� true � �������, ���� � �������� ������� ����� �������
    ��� �� ��� ���� �������� ���� ��������. ������ � false.
    ³������� �� ������ ��� ��� ��������, ���� �� �������� ��� � ���� � ���� ����� ������,
    � ���� � ������, �� ���������� ���� � ����� � ��� ����� ����� ������� ��������.
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

   /*����� ����������  � ����� ���������� ���� Tile[][] ���������� �������
     ������� ���� �� ������� � ����� �� ��������� ������� push �
    ������������� ������ isSaveNeeded ����� false
    */
    private void saveState (Tile[][] gameTiles){
        Tile[][] tiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tiles[i][j] =  new Tile(gameTiles[i][j].value);
            }
        }
        previousStates.push(tiles);
        previousScores.push(this.score);
        isSaveNeeded=false;
    }

    /*
    ����� rollback ���� ������������� ������� ������� ���� ����� ����������, �� �������� � ������
    �� ��������� ������ pop.
     */
    public void rollback(){
        if (!previousScores.isEmpty() && !previousStates.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    /*
    ����� randomMove � ���� Model, ���� ���� ��������� ���� �� ������
    ���� ���������� �����
     */
    public void randomMove(){
        int rundomNumber = ((int) (Math.random()*100))%4;
        switch (rundomNumber) {
            case (1) -> up();
            case (2) -> down();
            case (3) -> right();
            case (4) -> left();
        }
    }

    /*
    �����  boolean hasBoardChanged - ����������� true,
    � �������, ���� ���� ������ � ����� gameTiles �����������
    ³� ���� ������ � ��������� ����� ����� previousStates.
    �������� ����� �� ��, �� �� �� ������� �������� � �����
    ������� ������� �� ��������� ������ peek.
     */
    public boolean hasBoardChanged(){
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if(gameTiles[i][j].value !=previousStates.peek()[i][j].value){
                    return true;
                }
            }
        }
        return false;
    }

    /*
    ����� ��������� ������������ ���������� ����
     */
    public MoveEfficiency getMoveEfficiency(Move move){
       MoveEfficiency moveEfficiency = new MoveEfficiency(-1,0,move);
       move.move();
       if (hasBoardChanged()){
           moveEfficiency = new MoveEfficiency(getEmptyTilesCount(),score,move);
       }
       rollback();
       return moveEfficiency;
    }

    /*
    ����� autoMove � ���� Model, ���� ���� �������� ��������� � �������� ���� ��
    ���������� ����.
     */
    public void autoMove(){
        PriorityQueue<MoveEfficiency> queue = new PriorityQueue<>(4,Collections.reverseOrder());
        queue.offer(getMoveEfficiency(this::left));
        queue.offer(getMoveEfficiency(this::right));
        queue.offer(getMoveEfficiency(this::up));
        queue.offer(getMoveEfficiency(this::down));
        queue.peek().getMove().move();


    }

}
