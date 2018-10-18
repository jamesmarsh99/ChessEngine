import java.util.HashMap;
import java.util.HashSet;

public class Pawn implements Piece {
    int color;
    boolean enPassant;
    int direction;
    int counter;
    String coordinates;

    public Piece[][] board;

    public Pawn(int color, String coordinates) {
        this.color = color;
        this.coordinates = coordinates;
        direction = 10;
    }

    @Override
    public HashMap<String, HashSet<String>> moves() {
        //check if an enPassant move is possible
        HashMap<String, HashSet<String>> moves = new HashMap<>();
        HashSet<String> possibleMoves = new HashSet<>();
        int number = Board.number(coordinates);
        int letter = Board.letter(coordinates);
        String pawnPush = Board.indicesToCoordinates(number - color, letter);
        String captureLeft = Board.indicesToCoordinates(number - color, letter - 1);
        String captureRight = Board.indicesToCoordinates(number - color, letter + 1);
        String twoPawnPush = Board.indicesToCoordinates(number - 2*color, letter);
        String enPassantMove;
        if (direction != 10) {
            enPassantMove = Board.indicesToCoordinates(number - color, letter + direction);
        } else {
            enPassantMove = null;
        }
        if (Board.coordinatesToPiece(pawnPush) == null && validMove(coordinates, pawnPush)) {
            possibleMoves.add(pawnPush);
        }
        if (captureLeft!= null && Board.coordinatesToPiece(captureLeft) != null
        && Board.coordinatesToPiece(captureLeft).color() == -1 * color
                && validMove(coordinates, captureLeft)) {
            possibleMoves.add(captureLeft);
        }
        if (captureRight != null && Board.coordinatesToPiece(captureRight) != null
                && Board.coordinatesToPiece(captureRight).color() == -1 * color
                && validMove(coordinates, captureRight)) {
            possibleMoves.add(captureRight);
        }
        if (color == 1 && number == 6) {
            if (validMove(coordinates, twoPawnPush)) {
                possibleMoves.add(twoPawnPush);
            }
        }
        if (color == -1 && number == 1) {
            if (validMove(coordinates, twoPawnPush)) {
                possibleMoves.add(twoPawnPush);
            }
        }
        if (enPassant && enPassantMove!= null
        && validMove(coordinates, enPassantMove)) {
            possibleMoves.add(enPassantMove);
        }
        moves.put(coordinates, possibleMoves);
        return moves;
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
        Piece squareTwo = Board.coordinatesToPiece(coordinates2);
        if (color == 1) {
            //advancing 2 spaces
            if (enPassant) {
                if (Board.number(coordinates2) == 2 &&
                        Board.letter(coordinates2) == Board.letter(coordinates1) + direction) {
                    board[Board.number(coordinates2) + 1][Board.letter(coordinates2)] = null;
                    Board.removeBlackPiece(Board.number(coordinates2) + 1, Board.letter(coordinates2));
                    return true;
                }
            }
            if (letter1 == letter2 && number1 - number2 == 2 && number1 == 6) {
                return board[number1 - 1][letter1] == null && squareTwo == null;
            }
            //taking a piece
            if (Math.abs(letter1 - letter2) == 1 && number1 - number2 == 1) {
                return squareTwo != null && squareTwo.color() == -1;
            }
            //advancing 1 space
            return number1 - number2 == 1 && Board.coordinatesToPiece(coordinates2) == null;
        } else {
            if (enPassant) {
                if (Board.number(coordinates2) == 5 &&
                        Board.letter(coordinates2) == Board.letter(coordinates1) + direction) {
                    board[Board.number(coordinates2) - 1][Board.letter(coordinates2)] = null;
                    Board.removeWhitePiece(Board.number(coordinates2) - 1, Board.letter(coordinates2));
                    return true;
                }
            }
            if (letter1 == letter2 && number1 - number2 == -2 && number1 == 1) {
                return board[number1 + 1][letter1] == null && Board.coordinatesToPiece(coordinates2) == null;
            }
            if (Math.abs(letter1 - letter2) == 1 && number1 - number2 == -1) {
                return squareTwo != null && squareTwo.color() == 1;
            }
            return number1 - number2 == -1 && Board.coordinatesToPiece(coordinates2) == null;
        }
    }

    public void setEnPassant(int direction) {
        enPassant = true;
        this.direction = direction;
        counter = 1;
    }

    public void setEnPassantFalse() {
        counter -= 1;
        if (counter == -1) {
            enPassant = false;
        }
    }

    @Override
    public void setBoard(Piece[][] board) {
        this.board = board;
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
