import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;

import edu.princeton.cs.introcs.StdDraw;

public class Board {
    static Piece[][] board;
    final int WHITE = 1;
    final int BLACK = -1;
    int turn;
    boolean playComp;
    Pawn enPassant1W;
    Pawn enPassant2W;
    Pawn enPassant1B;
    Pawn enPassant2B;
    static Rook whiteShortCastle;
    static Rook blackShortCastle;
    static Rook whiteLongCastle;
    static Rook blackLongCastle;
    static Set<Piece> blackPieces;
    static Set<Piece> whitePieces;
    static King whiteKing;
    static King blackKing;
    static boolean whiteInCheck;
    static boolean blackInCheck;
    static boolean whiteCanBeInCheck;
    static boolean blackCanBeInCheck;
    static boolean gameOver;


    public Board() {
        gameOver = false;
        enPassant1B = null;
        enPassant2B = null;
        enPassant1W = null;
        enPassant2W = null;
        turn = 1; //white's turn to move
        Piece[] backRow = {new Rook(BLACK, "a8"), new Knight(BLACK, "b8"), new Bishop(BLACK, "c8"), new Queen(BLACK, "d8"),
                new King(BLACK, "e8"), new Bishop(BLACK, "f8"), new Knight(BLACK, "g8"), new Rook(BLACK, "h8")};
        Piece[] blackPawns = {new Pawn(BLACK, "a7"), new Pawn(BLACK, "b7"), new Pawn(BLACK, "c7"), new Pawn(BLACK, "d7"),
                new Pawn(BLACK, "e7"), new Pawn(BLACK, "f7"), new Pawn(BLACK, "g7"), new Pawn(BLACK, "h7")};
        Piece[] frontRow = {new Rook(WHITE, "a1"), new Knight(WHITE, "b1"), new Bishop(WHITE, "c1"), new Queen(WHITE, "d1"),
                new King(WHITE, "e1"), new Bishop(WHITE, "f1"), new Knight(WHITE, "g1"), new Rook(WHITE, "h1")};
        Piece[] whitePawns = {new Pawn(WHITE, "a2"), new Pawn(WHITE, "b2"), new Pawn(WHITE, "c2"), new Pawn(WHITE, "d2"),
                new Pawn(WHITE, "e2"), new Pawn(WHITE, "f2"), new Pawn(WHITE, "g2"), new Pawn(WHITE, "h2")};
        Piece[] emptySquares1 = new Piece[8];
        Piece[] emptySquares2 = new Piece[8];
        Piece[] emptySquares3 = new Piece[8];
        Piece[] emptySquares4 = new Piece[8];
        Piece[][] board1 = {backRow,
                blackPawns,
                emptySquares1,
                emptySquares2,
                emptySquares3,
                emptySquares4,
                whitePawns,
                frontRow};
        board = board1;
        for (Piece[] row : board) {
            for (Piece piece : row) {
                if (piece != null) {
                    piece.setBoard(board);
                }
            }
        }
        blackPieces = new HashSet<>();
        whitePieces = new HashSet<>();
        for (Piece b : backRow) {
            blackPieces.add(b);
        }
        for (Piece b : blackPawns) {
            blackPieces.add(b);
        }
        for (Piece w : frontRow) {
            whitePieces.add(w);
        }
        for (Piece w : whitePawns) {
            whitePieces.add(w);
        }
        whiteKing = (King) board[7][4];
        blackKing = (King) board[0][4];
        whiteShortCastle = (Rook) board[7][7];
        blackShortCastle = (Rook) board[0][7];
        whiteLongCastle = (Rook) board[7][0];
        blackLongCastle = (Rook) board[0][0];
    }

    public static String whiteKingCoordinate() {
        return whiteKing.getCoordinates();
    }

    public static String blackKingCoordinate() {
        return blackKing.getCoordinates();
    }

    //moves will be in format of the starting coordinate followed by the ending coordinate
    public static HashSet<String> blackMoves() {
        whiteInCheck = false;
        whiteCanBeInCheck = false;
        HashSet<String> blackmoves = new HashSet<>();
        for (Piece b : blackPieces) {
            HashMap<String, HashSet<String>> pieceMoves = b.moves();
            HashSet<String> possibleMoves = pieceMoves.get(b.getCoordinates());
            for (String move : possibleMoves) {
                blackmoves.add(b.getCoordinates() + move);
                if (move.equals(whiteKingCoordinate())) {
                    whiteInCheck = true;
                    whiteCanBeInCheck = true;
                }
            }
        }
        return blackmoves;
    }

    public static HashSet<String> whiteMoves() {
        blackInCheck = false;
        blackCanBeInCheck = false;
        HashSet<String> whitemoves = new HashSet<>();
        for (Piece w : whitePieces) {
            HashMap<String, HashSet<String>> pieceMoves = w.moves();
            HashSet<String> possibleMoves = pieceMoves.get(w.getCoordinates());
            for (String move : possibleMoves) {
                whitemoves.add(w.getCoordinates() + move);
                if (move.equals(blackKingCoordinate())) {
                    blackInCheck = true;
                    blackCanBeInCheck = true;
                }
            }
        }
        return whitemoves;
    }

    public static int whitePiecesValue() {
        int total = 0;
        for (Piece whitePiece : whitePieces) {
            total += pieceValue(whitePiece);
        }
        return total;
    }

    public static int blackPiecesValue() {
        int total = 0;
        for (Piece blackPiece : blackPieces) {
            total += pieceValue(blackPiece);
        }
        return total;
    }

    public static int pieceValue(Piece p) {
        if (p instanceof Queen) {
            return 9;
        } else if (p instanceof Rook) {
            return 5;
        } else if (p instanceof Bishop || p instanceof Knight) {
            return 3;
        } else if (p instanceof Pawn) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String indicesToCoordinates(int number, int letter) {
        if (letter < 0 || number < 0 || letter > 7 || number > 7) {
            return null;
        }
        String num = "" + (8 - number);
        char let = (char) (97 + letter);
        return let + num;
    }

    public static Piece coordinatesToPiece(String coordinates) {
        if (coordinates.length() != 2) {
            throw new IllegalArgumentException("Coordinates must be 2 characters");
        }
        int letter = coordinates.charAt(0) - 97;
        int number = 7 - (coordinates.charAt(1) - 49);
        if (letter < 0 || number < 0 || letter > 7 || number > 7) {
            throw new IllegalArgumentException("first character must be letter from a-g" +
                    "and second character must be number 1-8");
        }
        return board[number][letter];
    }

    public static int letter(String coordinates) {
        return coordinates.charAt(0) - 97;
    }

    public static int number(String coordinates) {
        return 7 - (coordinates.charAt(1) - 49);
    }

    public void removePiece(Piece p) {
        if (turn == WHITE) {
            blackPieces.remove(p);
        } else {
            whitePieces.remove(p);
        }
    }
    public static void removeBlackPiece(int number, int letter) {
        blackPieces.remove(coordinatesToPiece(indicesToCoordinates(number, letter)));
    }

    public static void removeWhitePiece(int number, int letter) {
        whitePieces.remove(coordinatesToPiece(indicesToCoordinates(number, letter)));
    }

    public boolean movePiece(String coordinates1, String coordinates2) {
        if (gameOver) {
            System.out.println("Checkmate! The game is Over.");
            return false;
        }
        Piece one = coordinatesToPiece(coordinates1);
        Piece two = coordinatesToPiece(coordinates2);
        if (one == null) {
            System.out.println("There is no piece to move at " + coordinates1 + "!");
            return false;
        } else if (one.color() != turn) {
            System.out.println("It is not your turn to move.");
            return false;
        } else if (two != null && one.color() == two.color()) {
            System.out.println("You cannot capture your own piece at " + coordinates2 + "!");
            return false;
        } else {
            if (one.validMove(coordinates1, coordinates2)) {
                board[number(coordinates2)][letter(coordinates2)] = one;
                board[number(coordinates1)][letter(coordinates1)] = null;
                one.changeCoordinates(coordinates2);
                if (turn == WHITE) {
                    if (two != null) {
                        blackPieces.remove(two);
                    }
                    blackMoves();
                    if (two != null) {
                        blackPieces.add(two);
                    }
                    if (whiteInCheck) {
                        //move pieces back to original position
                        board[number(coordinates2)][letter(coordinates2)] = two;
                        board[number(coordinates1)][letter(coordinates1)] = one;
                        if (!playComp) {
                            System.out.println("That move puts yourself in check!");
                        }
                        whiteInCheck = false;
                        one.changeCoordinates(coordinates1);
                        return false;
                    }
                } else {
                    if (two != null) {
                        whitePieces.remove(two);
                    }
                    whiteMoves();
                    if (two != null) {
                        whitePieces.add(two);
                    }
                    if (blackInCheck) {
                        board[number(coordinates2)][letter(coordinates2)] = two;
                        board[number(coordinates1)][letter(coordinates1)] = one;
                        if (!playComp) {
                            System.out.println("That move puts yourself in check!");
                        }
                        blackInCheck = false;
                        one.changeCoordinates(coordinates1);
                        return false;
                    }
                }
                //from here on, the move is valid
                if (one instanceof King) {
                    ((King) one).hasMoved();
                    if (turn == WHITE && ((King) one).isCastlingShort) {
                        board[number("f1")][letter("f1")] = whiteShortCastle;
                        board[number("h1")][letter("h1")] = null;
                        ((King) one).isCastlingShort = false;
                        whiteShortCastle.changeCoordinates("f1");
                    }
                    if (turn == WHITE && ((King) one).isCastlingLong) {
                        board[number("d1")][letter("d1")] = whiteLongCastle;
                        board[number("a1")][letter("a1")] = null;
                        ((King) one).isCastlingLong = false;
                        whiteLongCastle.changeCoordinates("d1");
                    }
                    if (turn == BLACK && ((King) one).isCastlingShort) {
                        board[number("f8")][letter("f8")] = blackShortCastle;
                        board[number("h8")][letter("h8")] = null;
                        ((King) one).isCastlingShort = false;
                        blackShortCastle.changeCoordinates("f8");
                    }
                    if (turn == BLACK && ((King) one).isCastlingLong) {
                        board[number("d8")][letter("d8")] = blackLongCastle;
                        board[number("a8")][letter("a8")] = null;
                        ((King) one).isCastlingLong = false;
                        blackLongCastle.changeCoordinates("d8");
                    }
                }
                if (one instanceof Rook) {
                    ((Rook) one).hasMoved();
                }
                if (two != null) {
                    removePiece(two);
                }
                one.changeCoordinates(coordinates2);
                checkAndSetEnPassant(one, two, coordinates1, coordinates2);
                //queening
                if (one instanceof Pawn) {
                    if (((Pawn) one).color == WHITE) {
                        if (number(((Pawn) one).coordinates) == 0) {
                            whitePieces.remove(one);
                            Queen queened = new Queen(1, coordinates2);
                            board[number(coordinates2)][letter(coordinates2)] = queened;
                            whitePieces.add(queened);
                            queened.setBoard(board);
                        }
                    } else {
                        if (number(((Pawn) one).coordinates) == 7) {
                            blackPieces.remove(one);
                            Queen queened = new Queen(-1, coordinates2);
                            board[number(coordinates2)][letter(coordinates2)] = queened;
                            blackPieces.add(queened);
                            queened.setBoard(board);
                        }
                    }
                }
                if (!playComp) {
                    checkCheckmateStalemate();
                }
                turn *= -1;
                setEnPassantFalses();
                return true;
            } else {
                System.out.println("You played an invalid move: " + coordinates1 + "-"
                        + coordinates2);
                return false;
            }
        }
    }

    public void checkCheckmateStalemate() {
        //if a piece queens, I have to change the board
        if (turn == WHITE) {
            boolean blackIsSafe = false;
            HashSet<String> canBlackMove = blackMoves();
            for (String blackMove : canBlackMove) {
                String coordinates11 = blackMove.substring(0, 2);
                String coordinates22 = blackMove.substring(2, 4);
                Piece one1 = coordinatesToPiece(coordinates11);
                Piece two2 = coordinatesToPiece(coordinates22);
                int number1 = number(coordinates11);
                int letter1 = letter(coordinates11);
                int number2 = number(coordinates22);
                int letter2 = letter(coordinates22);
                Piece three = null;
                int number3 = -1;
                int letter3 = -1;
                boolean enPassant = false;
                if (one1 instanceof Pawn && ((Pawn) one1).checkEnPassant()) {
                    int direction = ((Pawn) one1).getDirection();
                    if (letter2 == letter1 + direction) {
                        number3 = number1;
                        letter3 = letter1 + direction;
                        three = board[number3][letter3];
                        board[number3][letter3] = null; // clearing off the piece taken by en passant
                        blackPieces.remove(three);
                        enPassant = true;
                    }
                }
                board[number2][letter2] = one1;
                board[number1][letter1] = null;
                one1.changeCoordinates(coordinates22);
                if (two2 != null && two2.color() == 1) {
                    whitePieces.remove(two2);
                }
                whiteMoves();
                if (!blackCanBeInCheck) {
                    blackIsSafe = true;
                }
                if (two2 != null && two2.color() == 1) {
                    whitePieces.add(two2);
                }
                board[number(coordinates22)][letter(coordinates22)] = two2;
                board[number(coordinates11)][letter(coordinates11)] = one1;
                if (enPassant) {
                    board[number3][letter3] = three;
                    blackPieces.add(three);
                }
                one1.changeCoordinates(coordinates11);
            }
            if (!blackIsSafe) {
                gameOver = true;
                whiteMoves();
                if (blackInCheck) {
                    System.out.println("White checkmated! Congrats");
                } else {
                    System.out.println("Stalemate... Sucks to suck");
                }
            }
        }
        if (turn == BLACK) {
            boolean whiteIsSafe = false;
            HashSet<String> canWhiteMove = whiteMoves();
            for (String whiteMove : canWhiteMove) {
                String coordinates11 = whiteMove.substring(0, 2);
                String coordinates22 = whiteMove.substring(2, 4);
                Piece one1 = coordinatesToPiece(coordinates11);
                Piece two2 = coordinatesToPiece(coordinates22);
                int number1 = number(coordinates11);
                int letter1 = letter(coordinates11);
                int number2 = number(coordinates22);
                int letter2 = letter(coordinates22);
                Piece three = null;
                int number3 = -1;
                int letter3 = -1;
                boolean enPassant = false;
                if (one1 instanceof Pawn && ((Pawn) one1).checkEnPassant()) {
                    int direction = ((Pawn) one1).getDirection();
                    if (letter2 == letter1 + direction) {
                        number3 = number1;
                        letter3 = letter1 + direction;
                        three = board[number3][letter3];
                        board[number3][letter3] = null; // clearing off the piece taken by en passant
                        whitePieces.remove(three);
                        enPassant = true;
                    }
                }
                board[number(coordinates22)][letter(coordinates22)] = one1;
                board[number(coordinates11)][letter(coordinates11)] = null;
                one1.changeCoordinates(coordinates22);
                if (two2 != null && two2.color() == -1) {
                    blackPieces.remove(two2);
                }
                blackMoves();
                if (!whiteCanBeInCheck) {
                    whiteIsSafe = true;
                }
                if (two2 != null && two2.color() == -1) {
                    blackPieces.add(two2);
                }
                board[number(coordinates22)][letter(coordinates22)] = two2;
                board[number(coordinates11)][letter(coordinates11)] = one1;
                if (enPassant) {
                    board[number3][letter3] = three;
                    whitePieces.add(three);
                }
                one1.changeCoordinates(coordinates11);
            }
            if (!whiteIsSafe) {
                gameOver = true;
                whiteMoves();
                if (whiteInCheck) {
                    System.out.println("Black checkmated! Congrats");
                } else {
                    System.out.println("Stalemate... Sucks to suck");
                }
            }
        }
    }

    public void setEnPassantFalses() {
        if (enPassant1W != null) {
            enPassant1W.setEnPassantFalse();
            if (enPassant1W.checkEnPassant()) {
                enPassant1W = null;
            }
        }
        if (enPassant2W != null) {
            enPassant2W.setEnPassantFalse();
            if (enPassant2W.checkEnPassant()) {
                enPassant2W = null;
            }
        }
        if (enPassant1B != null) {
            enPassant1B.setEnPassantFalse();
            if (enPassant1B.checkEnPassant()) {
                enPassant1B = null;
            }
        }
        if (enPassant2B != null) {
            enPassant2B.setEnPassantFalse();
            if (enPassant2B.checkEnPassant()) {
                enPassant2B = null;
            }
        }
    }

    public void checkAndSetEnPassant(Piece one, Piece two, String coordinates1, String coordinates2) {
        if (one instanceof Pawn) {
            if (((Pawn) one).color == BLACK) {
                if (number(coordinates2) == 3 && number(coordinates1) == 1) {
                    if (letter(coordinates2) < 7 && board[3][letter(coordinates2) + 1] instanceof Pawn) {
                        Pawn a = (Pawn) board[3][letter(coordinates2) + 1];
                        a.setEnPassant(-1);
                        enPassant1B = a;
                    }
                    if (letter(coordinates2) > 0 && board[3][letter(coordinates2) - 1] instanceof Pawn) {
                        Pawn b = (Pawn) board[3][letter(coordinates2) - 1];
                        b.setEnPassant(1);
                        enPassant2B = b;
                    }
                }
            } else {
                if (number(coordinates2) == 4 && number(coordinates1) == 6) {
                    if (letter(coordinates2) < 7 && board[4][letter(coordinates2) + 1] instanceof Pawn) {
                        Pawn a = (Pawn) board[4][letter(coordinates2) + 1];
                        a.setEnPassant(-1);
                        enPassant1W = a;
                    }
                    if (letter(coordinates2) > 0 && board[4][letter(coordinates2) - 1] instanceof Pawn) {
                        Pawn b = (Pawn) board[4][letter(coordinates2) - 1];
                        b.setEnPassant(1);
                        enPassant2W = b;
                    }
                }
            }
        }
    }

    public Piece[][] getBoard() {
        return board;
    }

    //White pieces are denoted by capital letters
    //black pieces are denoted by lower case letters
    public void printBoard() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 8);
        StdDraw.setYscale(0, 8);
        for (double x = 0.5; x < 8; x++) {
            for (double y = 0.5; y < 8; y++) {
                if ((x + (8 - y)) % 2 == 1) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                } else {
                    StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                }
                StdDraw.filledSquare(x, 8 - y, 0.5);
                Piece p = board[(int) y][(int) x];
                if (p == null) {
                } else if (p.color() == 1) {
                    if (p instanceof King) {
                        StdDraw.picture(x, 8 - y, "White King.png", 0.8, 0.8);
                    }
                    if (p instanceof Queen) {
                        StdDraw.picture(x, 8 - y, "White Queen.png", 0.8, 0.8);
                    }
                    if (p instanceof Knight) {
                        StdDraw.picture(x, 8 - y, "White Knight.png", 0.8, 0.8);
                    }
                    if (p instanceof Bishop) {
                        StdDraw.picture(x, 8 - y, "White Bishop.png", 0.8, 0.8);
                    }
                    if (p instanceof Rook) {
                        StdDraw.picture(x, 8 - y, "White Rook.png", 0.8, 0.8);
                    }
                    if (p instanceof Pawn) {
                        StdDraw.picture(x, 8 - y, "White Pawn.png", 0.8, 0.8);
                    }
                } else {
                    if (p instanceof King) {
                        StdDraw.picture(x, 8 - y, "Black King.png", 0.8, 0.8);
                    }
                    if (p instanceof Queen) {
                        StdDraw.picture(x, 8 - y, "Black Queen.png", 0.8, 0.8);
                    }
                    if (p instanceof Knight) {
                        StdDraw.picture(x, 8 - y, "Black Knight.png", 0.8, 0.8);
                    }
                    if (p instanceof Bishop) {
                        StdDraw.picture(x, 8 - y, "Black Bishop.png", 0.8, 0.8);
                    }
                    if (p instanceof Rook) {
                        StdDraw.picture(x, 8 - y, "Black Rook.png", 0.8, 0.8);
                    }
                    if (p instanceof Pawn) {
                        StdDraw.picture(x, 8 - y, "Black Pawn.png", 0.8, 0.8);
                    }
                }
            }
        }
        StdDraw.show();
        for (Piece[] row : board) {
            for (Piece piece : row) {
                printPiece(piece);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printPiece(Piece p) {
        if (p == null) {
            System.out.print(" ");
        } else if (p.color() == 1) {
            if (p instanceof King) {
                System.out.print("K");
            }
            if (p instanceof Queen) {
                System.out.print("Q");
            }
            if (p instanceof Knight) {
                System.out.print("N");
            }
            if (p instanceof Bishop) {
                System.out.print("B");
            }
            if (p instanceof Rook) {
                System.out.print("R");
            }
            if (p instanceof Pawn) {
                System.out.print("P");
            }
        } else {
            if (p instanceof King) {
                System.out.print("k");
            }
            if (p instanceof Queen) {
                System.out.print("q");
            }
            if (p instanceof Knight) {
                System.out.print("n");
            }
            if (p instanceof Bishop) {
                System.out.print("b");
            }
            if (p instanceof Rook) {
                System.out.print("r");
            }
            if (p instanceof Pawn) {
                System.out.print("p");
            }
        }
    }

    public void play2v2() {
        String one = null;
        String two = null;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
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
                    movePiece(one, two);
                    printBoard();
                    one = null;
                    two = null;
                }
            }
        }
    }

    public void playComputerLevel1() {
        String one = null;
        String two = null;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
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
                    movePiece(one, two);
                    printBoard();
                    turn *= -1;
                    checkCheckmateStalemate();
                    turn *= -1;
                    one = null;
                    two = null;
                    //black will make a random move now
                    playComp = true;
                    HashSet<String> blacksPossibleMoves = blackMoves();
                    String moveToPlay = null;
                    if (turn == BLACK && !gameOver) {
                        while (moveToPlay == null || !movePiece(moveToPlay.substring(0, 2), moveToPlay.substring(2, 4))) {
                            int size = blacksPossibleMoves.size();
                            int item = new Random().nextInt(size);
                            int i = 0;
                            for (String move : blacksPossibleMoves) {
                                if (i == item)
                                    moveToPlay = move;
                                i++;
                            }
                        }
                        printBoard();
                        turn *= -1;
                        checkCheckmateStalemate();
                        turn *= -1;
                    }
                }
            }
        }
    }
}
