package chess.actions;

import chess.GameState;
import chess.PiecePosition;
import chess.Player;
import chess.Position;
import chess.pieces.Queen;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MovePieceIteratorTest {
    @Test
    public void testName() throws Exception {
        GameState gameState = new GameState();
        PiecePosition current = new PiecePosition(new Queen(Player.Black), "d8");
        Stream<MovePiece> stream = StreamSupport
            .stream(((Iterable<Position>) () -> new MovePieceIterator(gameState, current.getPosition(), Direction.DOWN_RIGHT)).spliterator(), false)
            .map(p -> new MovePiece(current, p));

        QueenActions.QUEEN_MOVES.apply(current, gameState).map(GameAction::getTarget)
            .collect(Collectors.toSet())
            .forEach(move -> System.out.println("1 " + move));
    }
}