import java.util.HashMap;
import java.util.HashSet;

public class Bishop implements Piece {
    int color;
    public Piece[][] board;
    String coordinates;

    public Bishop(int color, String coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }

    @Override
    public void changeCoordinates(String newCoordinates) {
        coordinates = newCoordinates;
    }

    @Override
    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    @Override
    public HashMap<String, HashSet<String>> moves() {
        HashMap<String, HashSet<String>> moves = new HashMap<>();
        HashSet<String> possibleMoves = new HashSet<>();
        int number = Board.number(coordinates);
        int letter = Board.letter(coordinates);
        int num1 = number;
        int let1 = letter;
        while (num1 > 0 && let1 > 0) {
            num1--;
            let1--;
        }//now num1 and let1 should be the upper left hand corner of the negatively sloped diagonal of
        // the bishop
        int num2 = number;
        int let2 = letter;
        while (num2 > 0 && let2 < 7) {
            num2--;
            let2++;
        } //now num2 and let2 should be the upper right hand corner of the positively sloped diagonal of
        // the bishop
        for(int i = 0; i < 8; i++) {
            String destination1 = Board.indicesToCoordinates(num1 + i, let1 + i);
            String destination2 = Board.indicesToCoordinates(num2 + i, let2 - i);
            if (destination1 != null && (Board.coordinatesToPiece(destination1) == null ||
                    Board.coordinatesToPiece(destination1).color() == -1 * color)) {
                if (validMove(coordinates, destination1)) {
                    possibleMoves.add(destination1);
                }
            }
            if (destination2 != null && (Board.coordinatesToPiece(destination2) == null ||
                    Board.coordinatesToPiece(destination2).color() == -1 * color)) {
                if (validMove(coordinates, destination2)) {
                    possibleMoves.add(destination2);
                }
            }
        }
        moves.put(coordinates, possibleMoves);
        return moves;
    }

    @Override
    public boolean validMove(String coordinates1, String coordinates2) {
        int number1 = Board.number(coordinates1);
        int letter1 = Board.letter(coordinates1);
        int number2 = Board.number(coordinates2);
        int letter2 = Board.letter(coordinates2);
        if (letter1 > letter2) {
            return validMove(coordinates2, coordinates1);
        } //now the bishop is always moving from left to right
        if (number1 > number2) {
            int number = number1 - 1;
            int letter = letter1 + 1;
            while (letter < letter2 && number > number2) {
                if (board[number][letter] != null) {
                    return false;
                }
                number--;
                letter++;
            }
            return letter == letter2 && number == number2;
        } else {
            int number = number1 + 1;
            int letter = letter1 + 1;
            while (letter < letter2 && number < number2) {
                if (board[number][letter] != null) {
                    return false;
                }
                number++;
                letter++;
            }
            return letter == letter2 && number == number2;
        }
    }

    @Override
    public String getCoordinates() {
        return coordinates;
    }


    @Override
    public int color() {
        return color;
    }
}
