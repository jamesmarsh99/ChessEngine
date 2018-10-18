import java.util.HashMap;
import java.util.HashSet;

public class Rook implements Piece {
    int color;
    public Piece[][] board;
    String coordinates;
    boolean hasMoved;

    public Rook(int color, String coordinates) {
        this.color = color;
        this.coordinates = coordinates;
        hasMoved = false;
    }

    public void hasMoved() {
        hasMoved = true;
    }

    @Override
    public void changeCoordinates(String newCoordinates) {
        coordinates = newCoordinates;
    }

    @Override
    public boolean validMove(String coordinates1, String coordinates2) {
        int number1 = Board.number(coordinates1);
        int letter1 = Board.letter(coordinates1);
        int number2 = Board.number(coordinates2);
        int letter2 = Board.letter(coordinates2);
        if (letter1 == letter2) {
            if (number1 > number2) {
                int holder = number2;
                number2 = number1;
                number1 = holder;
            }
            //number1 is less than number2
            for (int number = number1 + 1; number < number2; number++) {
                if (board[number][letter1] != null) {
                    return false;
                }
            }
            return true;
        } else if (number1 == number2) {
            if (letter1 > letter2) {
                int holder = letter2;
                letter2 = letter1;
                letter1 = holder;
            }
            for (int letter = letter1 + 1; letter < letter2; letter++) {
                if (board[number1][letter] != null) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
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
        for (int num = 0; num < 8; num++) {
            String destination = Board.indicesToCoordinates(num, letter);
            if (Board.coordinatesToPiece(destination) == null ||
                    Board.coordinatesToPiece(destination).color() == -1 * color) {
                if (validMove(coordinates, destination)) {
                    possibleMoves.add(destination);
                }
            }
        }
        for (int let = 0; let < 8; let++) {
            String destination = Board.indicesToCoordinates(number, let);
            if (Board.coordinatesToPiece(destination) == null ||
                    Board.coordinatesToPiece(destination).color() == -1 * color) {
                if (validMove(coordinates, destination)) {
                    possibleMoves.add(destination);
                }
            }
        }
        moves.put(coordinates, possibleMoves);
        return moves;
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
