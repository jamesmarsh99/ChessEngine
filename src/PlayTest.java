import edu.princeton.cs.introcs.StdDraw;

import java.util.HashSet;
import java.util.Random;

public class PlayTest {
    static String one = null;
    static String two = null;

    public static void play(char key) {
        if (one == null || one.length() < 2) {
            if (one == null) {
                one = "";
            }
            one += key;
        } else if (two == null || two.length() < 2) {
            if (two == null) {
                two = "";
            }
            two += key;
        }
        if (one != null && two != null && one.length() == 2 && two.length() == 2) {
            System.out.println("Moving from " + one + " to " + two);
            one = null;
            two = null;
        }
        System.out.print(one + " ");
        System.out.println(two);
    }

    public static void main(String[] args) {
        //play('e');
        //play('2');
        //play('e');
        //play('4');
        Board b = new Board();
        b.movePiece("e2", "e4");
        b.printBoard();
        HashSet<String> blacksPossibleMoves = Board.blackMoves();
        String moveToPlay = null;
        while (moveToPlay == null || !b.movePiece(moveToPlay.substring(0, 2), moveToPlay.substring(2, 4))) {
            int size = blacksPossibleMoves.size();
            int item = new Random().nextInt(size);
            int i = 0;
            for(String move : blacksPossibleMoves) {
                if (i == item) {
                    moveToPlay = move;
                }
                i++;
            }
        }
    }
}
