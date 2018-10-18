import java.util.HashMap;
import java.util.HashSet;

public class Knight implements Piece {
    int color;
    public Piece[][] board;
    String coordinates;

    public Knight(int color, String coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }

    @Override
    public void changeCoordinates(String newCoordinates) {
        coordinates = newCoordinates;
    }

    @Override
    public boolean validMove(String coordinates1, String coordinates2) {
        int letter1 = Board.letter(coordinates1);
        int number1 = Board.number(coordinates1);
        int letter2 = Board.letter(coordinates2);
        int number2 = Board.number(coordinates2);
        return Math.pow(letter1 - letter2, 2) + Math.pow(number1 - number2, 2) == 5;
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
        for(int i = -2; i <= 2; i++) {
            for(int j = -2; j<= 2; j++) {
                if (Math.pow(i, 2) + Math.pow(j, 2) == 5) {
                    String destination = Board.indicesToCoordinates(number + i, letter + j);
                    if (destination != null && (Board.coordinatesToPiece(destination) == null ||
                            Board.coordinatesToPiece(destination).color() == -1 * color)) {
                        possibleMoves.add(destination);
                    }
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
