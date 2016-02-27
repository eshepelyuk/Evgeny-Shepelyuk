package chess.moves;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.Pawn;
import chess.pieces.Piece;
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


}