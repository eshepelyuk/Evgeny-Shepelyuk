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

public class PawnMovesTest {

    private GameState gameState;

    @Before
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void shouldReturnOneStepMoveForWhitePawn() throws Exception {
        Piece piece = new Pawn(Player.White);
        Position position = new Position("a2");

        Optional<Set<Position>> positions = Moves.PAWN_ONE_STEP.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));

        Position pos = positions.get().iterator().next();
        assertThat(pos.getRow(), is(3));
        assertThat(pos.getColumn(), is('a'));
    }

    @Test
    public void shouldReturnOneStepMoveForBlackPawn() throws Exception {
        Piece piece = new Pawn(Player.Black);
        Position position = new Position("a7");

        Optional<Set<Position>> positions = Moves.PAWN_ONE_STEP.availableMoves(piece, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));

        Position pos = positions.get().iterator().next();
        assertThat(pos.getRow(), is(6));
        assertThat(pos.getColumn(), is('a'));
    }
}