package chess.actions;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class PawnMovesTest {

    private GameState gameState;

    Pawn pawnWhite = new Pawn(Player.White);
    Pawn pawnBlack = new Pawn(Player.Black);

    @Before
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void shouldAllowMovesForWhitePawn() {
        PiecePosition position = new PiecePosition(pawnWhite, new Position("a2"));

        Set<Position> positions = Moves.ONE_CELL_FWD.apply(position, gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a3")));

        positions = Moves.TWO_CELL_FWD.apply(position, gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a4")));

        //
        positions = Moves.PAWN_ACTIONS.apply(position, gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItems(new Position("a3"), new Position("a4")));
    }

    @Test
    public void shouldAllowMovesForBlackPawn() {
        PiecePosition position = new PiecePosition(pawnBlack, new Position("a7"));

        Set<Position> positions = Moves.ONE_CELL_FWD.apply(position, gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a6")));

        positions = Moves.TWO_CELL_FWD.apply(position, gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a5")));

        positions = Moves.PAWN_ACTIONS.apply(position, gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItems(new Position("a6"), new Position("a5")));
    }

    @Test
    public void shouldAllowOnlyOneCellMovementIfNotAtInitialPosition() {
        Set<Position> positions = Moves.PAWN_ACTIONS.apply(new PiecePosition(pawnWhite, new Position("a3")), gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("a4")));

        positions = Moves.PAWN_ACTIONS.apply(new PiecePosition(pawnBlack, new Position("a6")), gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("a5")));
    }

    @Test
    public void shouldSkipNonPawnPieces() throws Exception {
        Piece piece1 = new King(Player.White);
        Position position1 = new Position("a1");

        Piece piece2 = new Queen(Player.Black);
        Position position2 = new Position("a2");

        assertThat(Moves.ONE_CELL_FWD.apply(new PiecePosition(piece1, position1), gameState).findFirst().isPresent(), is(false));
        assertThat(Moves.ONE_CELL_FWD.apply(new PiecePosition(piece2, position2), gameState).findFirst().isPresent(), is(false));

        assertThat(Moves.TWO_CELL_FWD.apply(new PiecePosition(piece1, position1), gameState).findFirst().isPresent(), is(false));
        assertThat(Moves.TWO_CELL_FWD.apply(new PiecePosition(piece2, position2), gameState).findFirst().isPresent(), is(false));
    }

    @Test
    public void shouldSkipMovesToOccupiedCell() {
        Position positionWhite = new Position("a2");
        Position positionBlack = new Position("a7");

        //when occupy some cells
        gameState.placePiece(new King(Player.White), new Position("a3"));
        gameState.placePiece(new King(Player.Black), new Position("a5"));

        //then only 1 move is available
        //white
        Set<Position> positions = Moves.PAWN_ACTIONS.apply(new PiecePosition(pawnWhite, positionWhite), gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("a4")));

        //black
        positions = Moves.PAWN_ACTIONS.apply(new PiecePosition(pawnBlack, positionBlack), gameState).map(MovePiece::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("a6")));
    }
}