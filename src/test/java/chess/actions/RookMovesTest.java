package chess.actions;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.Rook;
import org.junit.Before;
import org.junit.Test;

public class RookMovesTest {
    private GameState gameState;
    private Rook whiteRook;
    private Rook blackRook;
    private Position whitePosition;
    private Position blackPosition;

    @Before
    public void setUp() {
        gameState = new GameState();
        whiteRook = new Rook(Player.White);
        whitePosition = new Position("a1");
        blackRook = new Rook(Player.Black);
        blackPosition = new Position("h8");
    }

    @Test
    public void shouldMoveUpUntilEndOfBoard() throws Exception {
//        Optional<Set<Position>> positions = Moves.MOVE_UP.availableMoves(whiteRook, whitePosition, gameState);
//        assertThat(positions.isPresent(), is(true));
//        assertThat(positions.get().size(), is(7));
    }
}