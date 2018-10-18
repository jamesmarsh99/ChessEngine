import java.util.HashMap;
import java.util.HashSet;

public class King implements Piece {
    int color;
    public Piece[][] board;
    String coordinates;
    boolean hasMoved;
    boolean isCastlingShort;
    boolean isCastlingLong;

    public King(int color, String coordinates) {
        this.color = color;
        this.coordinates = coordinates;
        hasMoved = false;
        isCastlingShort = false;
        isCastlingLong = false;
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
        if (color == 1) {
            if (coordinates1.equals("e1")) {
                if (coordinates2.equals("g1")) {
                    //white short castle
                    if (!hasMoved && board[7][7] instanceof Rook) { //king hasnt moved and rook is on h1
                        Rook shortCastle = (Rook) board[7][7];
                        if (!shortCastle.hasMoved && Board.coordinatesToPiece("f1") == null &&
                                Board.coordinatesToPiece("g1") == null) { //rook hasnt moved and no pieces are in kings way
                            //now have to check for if king would be in check on f1
                            board[Board.number("f1")][Board.letter("f1")] = this;
                            board[Board.number(coordinates1)][Board.letter(coordinates1)] = null;
                            changeCoordinates("f1");
                            Board.blackMoves();
                            board[Board.number("e1")][Board.letter("e1")] = this;
                            board[Board.number("f1")][Board.letter("f1")] = null;
                            changeCoordinates(coordinates1);
                            if (Board.whiteInCheck) {
                                System.out.println("Trying to castle puts yourself in check!");
                                Board.whiteInCheck = false;
                                return false;
                            } else {
                                isCastlingShort = true;
                                return true;
                            }
                        }
                    }
                }
                if (coordinates2.equals("c1")) {
                    //white long castle
                    if (!hasMoved && board[7][0] instanceof Rook) { //king hasnt moved and rook is on a1
                        Rook longCastle = (Rook) board[7][0];
                        if (!longCastle.hasMoved && Board.coordinatesToPiece("d1") == null &&
                                Board.coordinatesToPiece("c1") == null && Board.coordinatesToPiece("b1") == null) { //rook hasnt moved and no pieces are in kings way
                            //now have to check for if king would be in check on f1
                            board[Board.number("d1")][Board.letter("d1")] = this;
                            board[Board.number(coordinates1)][Board.letter(coordinates1)] = null;
                            changeCoordinates("d1");
                            Board.blackMoves();
                            board[Board.number("e1")][Board.letter("e1")] = this;
                            board[Board.number("d1")][Board.letter("d1")] = null;
                            changeCoordinates(coordinates1);
                            if (Board.whiteInCheck) {
                                System.out.println("Trying to castle puts yourself in check!");
                                Board.whiteInCheck = false;
                                return false;
                            } else {
                                isCastlingLong = true;
                                return true;
                            }
                        }
                    }
                }
            }
        } else {
            if (coordinates1.equals("e8")) {
                if (coordinates2.equals("g8")) {
                    //black short castle
                    if (!hasMoved && board[0][7] instanceof Rook) { //king hasnt moved and rook is on h8
                        Rook shortCastle = (Rook) board[0][7];
                        if (!shortCastle.hasMoved && Board.coordinatesToPiece("f8") == null &&
                                Board.coordinatesToPiece("g8") == null) { //rook hasnt moved and no pieces are in kings way
                            //now have to check for if king would be in check on f1
                            board[Board.number("f8")][Board.letter("f8")] = this;
                            board[Board.number(coordinates1)][Board.letter(coordinates1)] = null;
                            changeCoordinates("f8");
                            Board.whiteMoves();
                            board[Board.number("e8")][Board.letter("e8")] = this;
                            board[Board.number("f8")][Board.letter("f8")] = null;
                            changeCoordinates(coordinates1);
                            if (Board.blackInCheck) {
                                System.out.println("Trying to castle puts yourself in check!");
                                Board.blackInCheck = false;
                                return false;
                            } else {
                                isCastlingShort = true;
                                return true;
                            }
                        }
                    }
                }
                if (coordinates2.equals("c8")) {
                    //black long castle
                    if (!hasMoved && board[0][0] instanceof Rook) { //king hasnt moved and rook is on a1
                        Rook longCastle = (Rook) board[0][0];
                        if (!longCastle.hasMoved && Board.coordinatesToPiece("d8") == null &&
                                Board.coordinatesToPiece("c8") == null && Board.coordinatesToPiece("b8") == null) { //rook hasnt moved and no pieces are in kings way
                            //now have to check for if king would be in check on f1
                            board[Board.number("d8")][Board.letter("d8")] = this;
                            board[Board.number(coordinates1)][Board.letter(coordinates1)] = null;
                            changeCoordinates("d8");
                            Board.whiteMoves();
                            board[Board.number("e8")][Board.letter("e8")] = this;
                            board[Board.number("d8")][Board.letter("d8")] = null;
                            changeCoordinates(coordinates1);
                            if (Board.blackInCheck) {
                                System.out.println("Trying to castle puts yourself in check!");
                                Board.blackInCheck = false;
                                return false;
                            } else {
                                isCastlingLong = true;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        int letter1 = Board.letter(coordinates1);
        int number1 = Board.number(coordinates1);
        int letter2 = Board.letter(coordinates2);
        int number2 = Board.number(coordinates2);
        return Math.pow(letter1 - letter2, 2) + Math.pow(number1 - number2, 2) <= 2;
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
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (Math.pow(i, 2) + Math.pow(j, 2) <= 2) {
                    String destination = Board.indicesToCoordinates(number + i, letter + j);
                    if (destination != null && (Board.coordinatesToPiece(destination) == null ||
                            Board.coordinatesToPiece(destination).color() == -1 * color)) {
                        possibleMoves.add(destination);
                    }
                }
            }
        }

        if (color == 1) {
            if (coordinates.equals("e1")) {
                //white short castle
                if (!hasMoved && board[7][7] instanceof Rook) { //king hasnt moved and rook is on h1
                    Rook shortCastle = (Rook) board[7][7];
                    if (!shortCastle.hasMoved && Board.coordinatesToPiece("f1") == null &&
                            Board.coordinatesToPiece("g1") == null) { //rook hasnt moved and no pieces are in kings way
                        //now have to check for if king would be in check on f1
                        board[Board.number("f1")][Board.letter("f1")] = this;
                        board[Board.number(coordinates)][Board.letter(coordinates)] = null;
                        changeCoordinates("f1");
                        Board.blackMoves();
                        board[Board.number("e1")][Board.letter("e1")] = this;
                        board[Board.number("f1")][Board.letter("f1")] = null;
                        changeCoordinates(coordinates);
                        if (Board.whiteInCheck) {
                            Board.whiteInCheck = false;
                        } else {
                            isCastlingShort = false;
                            possibleMoves.add("g1");
                        }
                    }
                }
                //white long castle
                if (!hasMoved && board[7][0] instanceof Rook) { //king hasnt moved and rook is on a1
                    Rook longCastle = (Rook) board[7][0];
                    if (!longCastle.hasMoved && Board.coordinatesToPiece("d1") == null &&
                            Board.coordinatesToPiece("c1") == null && Board.coordinatesToPiece("b1") == null) { //rook hasnt moved and no pieces are in kings way
                        //now have to check for if king would be in check on f1
                        board[Board.number("d1")][Board.letter("d1")] = this;
                        board[Board.number(coordinates)][Board.letter(coordinates)] = null;
                        changeCoordinates("d1");
                        Board.blackMoves();
                        board[Board.number("e1")][Board.letter("e1")] = this;
                        board[Board.number("d1")][Board.letter("d1")] = null;
                        changeCoordinates(coordinates);
                        if (Board.whiteInCheck) {
                            Board.whiteInCheck = false;
                        } else {
                            isCastlingLong = false;
                            possibleMoves.add("c1");
                        }
                    }
                }

            }
        } else {
            if (coordinates.equals("e8")) {
                //black short castle
                if (!hasMoved && board[0][7] instanceof Rook) { //king hasnt moved and rook is on h8
                    Rook shortCastle = (Rook) board[0][7];
                    if (!shortCastle.hasMoved && Board.coordinatesToPiece("f8") == null &&
                            Board.coordinatesToPiece("g8") == null) { //rook hasnt moved and no pieces are in kings way
                        //now have to check for if king would be in check on f1
                        board[Board.number("f8")][Board.letter("f8")] = this;
                        board[Board.number(coordinates)][Board.letter(coordinates)] = null;
                        changeCoordinates("f8");
                        Board.whiteMoves();
                        board[Board.number("e8")][Board.letter("e8")] = this;
                        board[Board.number("f8")][Board.letter("f8")] = null;
                        changeCoordinates(coordinates);
                        if (Board.blackInCheck) {
                            Board.blackInCheck = false;
                        } else {
                            isCastlingShort = false;
                            possibleMoves.add("g8");
                        }
                    }
                }
                //black long castle
                if (!hasMoved && board[0][0] instanceof Rook) { //king hasnt moved and rook is on a1
                    Rook longCastle = (Rook) board[0][0];
                    if (!longCastle.hasMoved && Board.coordinatesToPiece("d8") == null &&
                            Board.coordinatesToPiece("c8") == null && Board.coordinatesToPiece("b8") == null) { //rook hasnt moved and no pieces are in kings way
                        //now have to check for if king would be in check on f1
                        board[Board.number("d8")][Board.letter("d8")] = this;
                        board[Board.number(coordinates)][Board.letter(coordinates)] = null;
                        changeCoordinates("d8");
                        Board.whiteMoves();
                        board[Board.number("e8")][Board.letter("e8")] = this;
                        board[Board.number("d8")][Board.letter("d8")] = null;
                        changeCoordinates(coordinates);
                        if (Board.blackInCheck) {
                            Board.blackInCheck = false;
                        } else {
                            isCastlingLong = false;
                            possibleMoves.add("c8");
                        }
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
