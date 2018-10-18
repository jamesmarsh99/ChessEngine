import java.util.HashMap;
import java.util.HashSet;

public interface Piece {
    public HashMap<String, HashSet<String>> moves();

    //A white piece will be represented as 1
    //A black piece will be represented as -1
    public int color();

    public void setBoard(Piece[][] b);

    public String getCoordinates();

    public boolean validMove(String coordinates1, String coordinates2);

    public void changeCoordinates(String newCoordinates);
}
