package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class PawnMovesTest {

    private GameState gameState;

    @Before
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void shouldAllowMovesForWhitePawn() {
        Piece piece = new Pawn(Player.White);
        Position position = new Position("a2");

        Optional<Set<Position>> positions = Moves.PAWN_ONE_STEP.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItem(new Position("a3")));

        positions = Moves.PAWN_TWO_STEP.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItem(new Position("a4")));

        //
        positions = Moves.PAWN_MOVES.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(2));

        assertThat(positions.get(), hasItems(new Position("a3"), new Position("a4")));
    }

    @Test
    public void shouldAllowMovesForBlackPawn() {
        Piece piece = new Pawn(Player.Black);
        Position position = new Position("a7");

        Optional<Set<Position>> positions = Moves.PAWN_ONE_STEP.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItem(new Position("a6")));

        positions = Moves.PAWN_TWO_STEP.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItem(new Position("a5")));

        //
        positions = Moves.PAWN_MOVES.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(2));
        assertThat(positions.get(), hasItems(new Position("a6"), new Position("a5")));
    }

    @Test
    public void shouldSkipNonPawnPieces() throws Exception {
        Piece piece1 = new King(Player.White);
        Position position1 = new Position("a1");

        Piece piece2 = new Queen(Player.Black);
        Position position2 = new Position("a2");

        assertThat(Moves.PAWN_ONE_STEP.availableMoves(piece1, position1, gameState).isPresent(), is(false));
        assertThat(Moves.PAWN_ONE_STEP.availableMoves(piece2, position2, gameState).isPresent(), is(false));

        assertThat(Moves.PAWN_TWO_STEP.availableMoves(piece1, position1, gameState).isPresent(), is(false));
        assertThat(Moves.PAWN_TWO_STEP.availableMoves(piece2, position2, gameState).isPresent(), is(false));
    }

    @Test
    public void shouldSkipMovesToOccupiedCell() {
        Piece pieceWhite = new Pawn(Player.White);
        Position positionWhite = new Position("a2");

        Piece pieceBlack = new Pawn(Player.Black);
        Position positionBlack = new Position("a7");

        //when occupy some cells
        gameState.placePiece(new King(Player.White), new Position("a3"));
        gameState.placePiece(new King(Player.Black), new Position("a5"));

        //then only 1 move is available
        //white
        Optional<Set<Position>> positions = Moves.PAWN_MOVES.availableMoves(pieceWhite, positionWhite, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItems(new Position("a4")));

        //black
        positions = Moves.PAWN_MOVES.availableMoves(pieceBlack, positionBlack, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItems(new Position("a6")));

    }
}