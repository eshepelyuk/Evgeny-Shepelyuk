package chess;

import chess.actions.EatPiece;
import chess.actions.MovePiece;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static junit.framework.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;

/**
 * Basic unit tests for the GameState class
 */
public class GameStateTest {

    private GameState state;

    @Before
    public void setUp() {
        state = new GameState();
    }

    @Test
    public void testStartsEmpty() {
        // Make sure all the positions are empty
        for (char col = Position.MIN_COLUMN; col <= Position.MAX_COLUMN; col++) {
            for (int row = Position.MIN_ROW; row <= Position.MAX_ROW; row++) {
                assertNull("All pieces should be empty", state.getPieceAt(String.valueOf(col) + row));
            }
        }
    }

    @Test
    public void testInitialGame() {
        // Start the game
        state.reset();

        // White should be the first player
        Player current = state.getCurrentPlayer();
        assertEquals("The initial player should be White", Player.White, current);

        // Spot check a few pieces
        Piece whiteRook = state.getPieceAt("a1");
        assertTrue("A rook should be at a1", whiteRook instanceof Rook);
        assertEquals("The rook at a1 should be owned by White", Player.White, whiteRook.getOwner());


        Piece blackQueen = state.getPieceAt("d8");
        assertTrue("A queen should be at d8", blackQueen instanceof Queen);
        assertEquals("The queen at d8 should be owned by Black", Player.Black, blackQueen.getOwner());
    }

    @Test
    public void shouldAllowToSwitchCurrentPlayer() throws Exception {
        // white by default
        assertEquals(Player.White, state.getCurrentPlayer());

        //should switch to black
        state.switchPlayer();
        assertEquals(Player.Black, state.getCurrentPlayer());

        // should switch to white on reset
        state.reset();
        assertEquals(Player.White, state.getCurrentPlayer());
    }

    @Test
    public void shouldListPiecesForCurrentPlayer() {
        // place some white pieces
        state.placePiece(new Pawn(Player.White), new Position('a', 1));
        state.placePiece(new Pawn(Player.White), new Position('b', 8));
        // place some black pieces
        state.placePiece(new Pawn(Player.Black), new Position('c', 8));
        state.placePiece(new Pawn(Player.Black), new Position('d', 1));

        // check whites
        assertThat(state.getCurrentPlayer(), is(Player.White));
        Set<PiecePosition> pieces = state.getCurrentPlayerPieces();
        assertThat(pieces.size(), is(2));
        assertThat(pieces, hasItem(new PiecePosition(new Pawn(Player.White), new Position('a', 1))));
        assertThat(pieces, hasItem(new PiecePosition(new Pawn(Player.White), new Position('b', 8))));

        //check blacks
        state.switchPlayer();
        assertThat(state.getCurrentPlayer(), is(Player.Black));
        pieces = state.getCurrentPlayerPieces();
        assertThat(pieces.size(), is(2));
        assertThat(pieces, hasItem(new PiecePosition(new Pawn(Player.Black), new Position('c', 8))));
        assertThat(pieces, hasItem(new PiecePosition(new Pawn(Player.Black), new Position('d', 1))));
    }

    @Test
    public void shouldProcessMovePieceAction() {
        PiecePosition original = new PiecePosition(new Pawn(Player.White), "a2");

        state.placePiece(original.getPiece(), original.getPosition());

        //when
        Position newPosition = new Position("a3");
        assertThat(state.getCurrentPlayer(), is(Player.White));
        state.applyAction(new MovePiece(original, newPosition));

        //then piece if moved, palyer is switched
        assertThat(state.getPieceAt(original.getPosition()), is((Piece) null));
        assertThat(state.getPieceAt(newPosition), is(original.getPiece()));
        assertThat(state.getCurrentPlayer(), is(Player.Black));
    }

    @Test
    public void shouldProcessKillPieceAction() {
        PiecePosition current = new PiecePosition(new Pawn(Player.White), "a2");
        PiecePosition target = new PiecePosition(new Pawn(Player.Black), "b3");

        state.placePiece(current.getPiece(), current.getPosition());
        state.placePiece(target.getPiece(), target.getPosition());

        //when
        assertThat(state.getCurrentPlayer(), is(Player.White));
        state.applyAction(new EatPiece(current, target.getPosition()));

        //then piece if moved, palyer is switched
        assertThat(state.getPieceAt(current.getPosition()), is((Piece) null));
        assertThat(state.getPieceAt(target.getPosition()), is(current.getPiece()));
        assertThat(state.getCurrentPlayer(), is(Player.Black));
    }
}
