package chess.actions;

import chess.*;
import chess.pieces.Queen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static chess.actions.GameActionSupplier.createBoundedMovePieceSupplier;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

@RunWith(Parameterized.class)
public class GameActionSupplierBoundedMovesTest {

    private final GameActionSupplier testSupplier;
    private final Position[] positions;

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
            new Object[]{
                createBoundedMovePieceSupplier(Direction.DOWN_RIGHT, 1L),
                new Position[]{new Position("f3"), new Position("g2")}
            }, new Object[]{
                createBoundedMovePieceSupplier(Direction.DOWN_LEFT, 1L),
                new Position[]{new Position("c3"), new Position("b2")}
            }, new Object[]{
                createBoundedMovePieceSupplier(Direction.UP_LEFT, 1L),
                new Position[]{new Position("c6"), new Position("b7")}
            }, new Object[]{
                createBoundedMovePieceSupplier(Direction.UP_RIGHT, 1L),
                new Position[]{new Position("f6"), new Position("g7")}
            }, new Object[]{
                createBoundedMovePieceSupplier(Direction.UP, 1L),
                new Position[]{new Position("c6"), new Position("c7")}
            }, new Object[]{
                createBoundedMovePieceSupplier(Direction.RIGHT, 1L),
                new Position[]{new Position("f6"), new Position("g6")}
            }, new Object[]{
                createBoundedMovePieceSupplier(Direction.DOWN, 1L),
                new Position[]{new Position("f3"), new Position("f2")}
            }, new Object[]{
                createBoundedMovePieceSupplier(Direction.LEFT, 1L),
                new Position[]{new Position("c3"), new Position("b3")}
            }
        );
    }

    public GameActionSupplierBoundedMovesTest(GameActionSupplier supplier, Position[] positions) {
        this.testSupplier = supplier;
        this.positions = positions;
    }

    @Test
    public void shouldAllowMovesWithinBounds() {
        PiecePosition position = new PiecePosition(new Queen(Player.Black), positions[0]);

        // when direction is free then can move withing bound
        Set<Position> positions = extractor.extractMoves(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(this.positions[1]));
    }
}