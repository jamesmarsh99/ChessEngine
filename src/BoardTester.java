import java.math.BigInteger;

public class BoardTester {
    public static Board b;

    public static void main(String[] args) {
        b = new Board();
        //play1v1();
        //playComputerLevel1();
        debug6();
    }

    public static void play1v1() {
        b.printBoard();
        b.play2v2();
    }

    public static void playComputerLevel1() {
        b.printBoard();
        b.playComputerLevel1();
    }

    public static void PiecesValueTester() {

        move("e2", "e4");
        move("d7", "d5");

        move("e4", "d5");
        move("d8", "d5");


        move("h2", "h3");
        move("d5", "d2");

        move("d1", "d2");

        System.out.println(b.whitePiecesValue());
        System.out.println(b.blackPiecesValue());
    }

    public static void debug7() {
        move("e2", "e4");
        move("g7", "g6");

        move("d2", "d4");
        move("d7", "d5");

        move("e4", "e5");
        move("f7", "f5");

        move("e5", "f6");
    }

    public static void debug6() {
        //FIXED
        move("e2", "e4");
        move("g7", "g6");

        move("d2", "d4");
        move("d7", "d5");

        move("e4", "e5");
        move("f7", "f5");

        move("e5", "f6");
    }

    public static void debug5() {
        //FIXED
        move("e2", "e4");
        move("d7", "d5");

        move("e4", "e5");
        move("g7", "g6");

        move("e5", "e6");
        move("f8", "g7");

        move("e6", "f7");
        move("e8", "f8");

        move("f7", "g8");
    }

    public static void testStalemate() {
        move("e2", "e3");
        move("a7", "a5");

        move("d1", "h5");
        move("a8", "a6");

        move("h5", "a5");
        move("h7", "h5");

        move("h2", "h4");
        move("a6", "h6");

        move("a5", "c7");
        move("f7", "f6");

        move("c7", "d7");
        move("e8", "f7");

        move("d7", "b7");
        move("d8", "d3");

        move("b7", "b8");
        move("d3", "h7");

        move("b8", "c8");
        move("f7", "g6");

        move("c8", "e6");
    }

    public static void testWhiteShortCastling() {
        move("e2", "e4");
        move("e7", "e5");
        move("g1", "h3");
        move("b7", "b6");
        move("f1", "a6");
        move("c8", "a6");
        move("f2", "f3");
        move("d7", "d6");
        move("e1", "g1");
    }

    public static void testWhiteLongCastling() {
        move("e2", "e4");
        move("e7", "e5");
        move("d1", "f3");
        move("b7", "b6");
        move("b2", "b3");
        move("c8", "b7");
        move("c1", "b2");
        move("d7", "d6");
        move("e1", "e2");
        move("h7", "h6");
        move("e2", "e1");
        move("h6", "h5");
        move("b1", "c3");
        move("d6", "d5");
        move("e1", "c1");
    }

    public static void checkQueening() {
        move("e2", "e4");
        move("d7", "d5");
        move("e4", "e5");
        move("d5", "d4");
        move("e5", "e6");
        move("d4", "d3");
        move("e6", "f7");
        move("e8", "d7");
        move("f7", "g8");
        move("d3", "c2");
        move("g8", "h8");
        move("c2", "b1");
    }

    public static void debug1() {
        move("e2", "e4");
        move("e7", "e5");
        move("f1", "c4");
        move("d7", "d6");
        move("d1", "h5");
        move("b8", "a6");
        //Solved!!
    }
    public static void debug2() {
        move("e2", "e4");
        move("e7", "e5");
        move("f1", "c4");
        move("d7", "d6");
        move("d1", "h5");
        move("g8", "f6");
        move("h5", "f7");
        //Solved!!
    }
    public static void debug3() {
        move("e2", "e4");
        move("d7", "d5");
        move("e4", "e5");
        move("d5", "d4");
        move("e5", "e6");
        move("d4", "d3");
        move("e6", "f7");
        //solved
    }

    public static void debug4() {
        move("e2", "e3");
        move("f7", "f5");
        move("d2", "d4");
        move("f5", "f4");
        move("e3", "f4");
        move("b8", "a6");
        move("g2", "g3");

        move("a6", "b4");
        move("f1", "g2");
        move("b4", "a6");
        move("g1", "e2");
        move("g8", "h6");
        move("e1", "g1");
    }



    public static void testBlackCheckmates() {
        move("f2", "f3");
        move("e7", "e6");
        move("g2", "g4");
        move("d8", "h4");
    }

    public static void testWhiteCheckmates() {
        move("e2", "e3");
        move("f7", "f6");
        move("e3", "e4");
        move("g7", "g5");
        move("d1", "h5");

    }

    public static void testPuttingSelfInCheck() {
        move("e2", "e4");
        move("d7", "d5");
        move("f1", "b5");
        move("d5", "d4");
        move("e8", "d7");

    }

    public static void testPossibleMoves() {
        System.out.println(Board.blackMoves());
        System.out.println(Board.whiteMoves());
    }


    public static void testIndicesToCoordinate() {
        System.out.println(b.indicesToCoordinates(0, 1));
        System.out.println(b.indicesToCoordinates(7, 2));

    }

    public static void testKan() {
        move("e2", "e4");
        move("c7", "c5");
        move("g1", "f3");
        move("e7", "e6");
        move("d2", "d4");
        move("c5", "d4");
        move("f3", "d4");
        move("a7", "a6");
        move("b1", "c3");
        move("d8", "c7");
        move("f1", "e2");
        move("f8", "e7");
        move("e1", "g1");
        move("g8", "f6");
        move("f2", "f4");
        move("e8", "g8");
        System.out.println(Board.coordinatesToPiece("c7").moves());
    }

    public static void testCoordinatesToPiece() {
        System.out.print("Piece at b2 = ");
        b.printPiece(b.coordinatesToPiece("b2"));
        System.out.println();
    }

    public static void testKnightValidMoves() {
        move("g1", "e3");
        move("g1", "f3");
        move("g8", "h6");
        move("f3", "e5");
        move("h6", "f5");
    }

    public static void testRookValidMoves() {
        move("h2", "h4");
        move("a7", "a5");
        move("h1", "h5");
        move("h1", "h3");
        System.out.println(b.getBoard()[5][7].moves());
        move("a8", "a3");
        move("a8", "a6");
        move("e2", "e3");
        move("h7", "h6");
        move("h3", "g3");
        move("a6", "g6");
        move("g3", "g7");
        System.out.println(b.getBoard()[5][6].moves());
        System.out.println(b.getBoard()[2][6].moves());
    }

    public static void testBishopValidMoves() {
        move("e2", "e3");
        move("e7", "e6");
        System.out.println(b.getBoard()[7][5].moves());
        System.out.println(b.getBoard()[0][5].moves());
        move("f1", "b5");
        move("f8", "b4");
        System.out.println(b.getBoard()[3][1].moves());
        System.out.println(b.getBoard()[4][1].moves());
        move("b5", "a4");
        move("b4", "a5");
        move("a4", "e8");
    }


    public static void testEnPassant() {
        move("e2", "e4");
        move("d7", "d5");
        move("e4", "e5");
        move("d5", "d4");
        move("c2", "c4");
        System.out.println(b.getBoard()[4][3].moves());
        move("d4", "c3");
        move("b2", "c3");
        move("f7", "f5");
        move("e5", "f6");
    }

    public static void testPawnValidMoves() {
        move("e2", "e4");
        move("d7", "d5");
        move("e4", "d5");
        //move("c7", "c6");
        //move("d2", "d3");
        //move("c6", "d4");
        //move("c6", "b5");
        //move("c6", "d4");
    }

    private static void move(String coordinates1, String coordinates2) {
        b.movePiece(coordinates1, coordinates2);
        b.printBoard();
    }
}
