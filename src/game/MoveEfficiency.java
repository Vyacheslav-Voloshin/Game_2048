package game;


//����, ���� ����� ������������ ����
public class MoveEfficiency implements Comparable<MoveEfficiency>{

    private int numberOfEmptyTiles; //������� ������ �������
    private int score; // ������� ���
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency o) {
        if (this.numberOfEmptyTiles>o.numberOfEmptyTiles){
            return 1;
        } else if (this.numberOfEmptyTiles==o.numberOfEmptyTiles){
            if(this.score>o.score){
                return 1;
            } else if(this.score==o.score){
                return 0;
            }
        }
        return -1;
    }
}
