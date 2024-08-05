package game;

import java.awt.event.KeyAdapter;

/*
���� ��������� �� ����������� ����� �
 ��� ���.
 */
public class Controller extends KeyAdapter {

    Model model;

    //����� ����������� ������ ���� � ���������
    public Tile[][] getGameTiles(){
        return model.getGameTiles();
    }

    //����� ����������� ������� ������� � ����������
    public int getScore(){
        return model.score;
    }

}
