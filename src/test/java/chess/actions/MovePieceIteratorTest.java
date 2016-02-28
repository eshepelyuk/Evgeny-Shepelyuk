package chess.actions;

import chess.GameState;
import chess.PiecePosition;
import chess.Player;
import chess.Position;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static chess.actions.MovePieceIterator.createMovePieceActionSupplier;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

@RunWith(Parameterized.class)
public class MovePieceIteratorTest {

    private final GameActionSupplier testSupplier;
    private final Position[] expectedPositions;

    private GameState gameState = new GameState();

    PiecePosition position = new PiecePosition(new Queen(Player.Black), "d5");

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
            new Object[]{
                createMovePieceActionSupplier(Direction.DOWN_RIGHT),
                new Position[]{new Position("e4"), new Position("f3"), new Position("g2"), new Position("h1")}
            }, new Object[]{
                createMovePieceActionSupplier(Direction.DOWN_LEFT),
                new Position[]{new Position("c4"), new Position("b3"), new Position("a2")}
            }, new Object[]{
                createMovePieceActionSupplier(Direction.UP_LEFT),
                new Position[]{new Position("c6"), new Position("b7"), new Position("a8")}
            }, new Object[]{
                createMovePieceActionSupplier(Direction.UP_RIGHT),
                new Position[]{new Position("e6"), new Position("f7"), new Position("g8")}
            }
        );
    }

    public MovePieceIteratorTest(GameActionSupplier supplier, Position[] positions) {
        this.testSupplier = supplier;
        this.expectedPositions = positions;
    }

    @Test
    public void shouldAllowMoves() throws Exception {
        // direction is free
        Set<Position> positions = extractPositions(testSupplier, position);
        assertThat(positions.size(), is(expectedPositions.length));
        assertThat(positions, hasItems(expectedPositions));

        // place obstacle
        gameState.placePiece(new Pawn(Player.Black), expectedPositions[1]);
        positions = extractPositions(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(expectedPositions[0]));
    }

    Set<Position> extractPositions(GameActionSupplier supplier, PiecePosition piecePosition) {
        return supplier.apply(piecePosition, gameState).map(GameAction::getTarget).collect(Collectors.toSet());
    }
}