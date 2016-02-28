package chess.actions;

import chess.*;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static chess.actions.GameActionSupplier.createBoundedEatPieceSupplier;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

@RunWith(Parameterized.class)
public class GameActionSupplierBoundedEatsTest {

    private final GameActionSupplier testSupplier;
    private final Position piecePosition;
    private final Position closeTarget;
    private final Position distantTarget;

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
            new Object[]{
                createBoundedEatPieceSupplier(Direction.DOWN_RIGHT, 1L),
                new Position("f3"), new Position("g2"), new Position("h1")
            }, new Object[]{
                createBoundedEatPieceSupplier(Direction.DOWN_LEFT, 1L),
                new Position("c3"), new Position("b2"), new Position("a1")
            }, new Object[]{
                createBoundedEatPieceSupplier(Direction.UP_LEFT, 1L),
                new Position("c6"), new Position("b7"), new Position("a8")
            }, new Object[]{
                createBoundedEatPieceSupplier(Direction.UP_RIGHT, 1L),
                new Position("f6"), new Position("g7"), new Position("a8")
            }, new Object[]{
                createBoundedEatPieceSupplier(Direction.UP, 1L),
                new Position("c6"), new Position("c7"), new Position("c8")
            }, new Object[]{
                createBoundedEatPieceSupplier(Direction.RIGHT, 1L),
                new Position("f6"), new Position("g6"), new Position("h6")
            }, new Object[]{
                createBoundedEatPieceSupplier(Direction.DOWN, 1L),
                new Position("f3"), new Position("f2"), new Position("f1")
            }, new Object[]{
                createBoundedEatPieceSupplier(Direction.LEFT, 1L),
                new Position("c3"), new Position("b3"), new Position("a3")
            }
        );
    }

    public GameActionSupplierBoundedEatsTest(GameActionSupplier supplier, Position position, Position closeTarget, Position distantTarget) {
        this.testSupplier = supplier;
        this.piecePosition = position;
        this.closeTarget = closeTarget;
        this.distantTarget = distantTarget;
    }

    @Test
    public void shouldAllowEatsWithinBounds() {
        PiecePosition position = new PiecePosition(new Queen(Player.White), piecePosition);

        // when target out of bounds, eating impossible
        extractor.getGameState().placePiece(new Pawn(Player.Black), distantTarget);
        Set<Position> positions = extractor.extractEats(testSupplier, position);
        assertThat(positions.size(), is(0));

        // when target withing bounds, eating allowed
        extractor.getGameState().placePiece(new Pawn(Player.Black), closeTarget);
        positions = extractor.extractEats(testSupplier, position);
        assertThat(positions, hasItems(closeTarget));
    }
}