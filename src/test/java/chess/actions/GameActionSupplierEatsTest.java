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

import static chess.actions.GameActionSupplier.createEatPieceSupplier;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

@RunWith(Parameterized.class)
public class GameActionSupplierEatsTest {
    private final GameActionSupplier testSupplier;

    private final Position closeTarget;
    private final Position distantTarget;

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    PiecePosition position = new PiecePosition(new Queen(Player.White), "d5");

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
            new Object[]{
                createEatPieceSupplier(Direction.DOWN_RIGHT), new Position("e4"), new Position("f3")
            }, new Object[]{
                createEatPieceSupplier(Direction.DOWN_LEFT), new Position("c4"), new Position("b3")
            }, new Object[]{
                createEatPieceSupplier(Direction.UP_LEFT), new Position("c6"), new Position("b7")
            }, new Object[]{
                createEatPieceSupplier(Direction.UP_RIGHT), new Position("e6"), new Position("f7")
            }, new Object[]{
                createEatPieceSupplier(Direction.UP), new Position("d6"), new Position("d7")
            }, new Object[]{
                createEatPieceSupplier(Direction.RIGHT), new Position("e5"), new Position("f5")
            }, new Object[]{
                createEatPieceSupplier(Direction.DOWN), new Position("d4"), new Position("d3")
            }, new Object[]{
                createEatPieceSupplier(Direction.LEFT), new Position("c5"), new Position("b5")
            }
        );
    }

    public GameActionSupplierEatsTest(GameActionSupplier supplier, Position closeTarget, Position distantTarget) {
        this.testSupplier = supplier;
        this.closeTarget = closeTarget;
        this.distantTarget = distantTarget;
    }

    @Test
    public void shouldAllowKills() {
        // when direction is free then no kills
        Set<Position> positions = extractor.extractEats(testSupplier, position);
        assertThat(positions.size(), is(0));

        // when place distant target
        extractor.getGameState().placePiece(new Pawn(Player.Black), distantTarget);

        // then distant target is allowed to be eaten
        positions = extractor.extractEats(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(distantTarget));

        // when place close target
        extractor.getGameState().placePiece(new Pawn(Player.Black), closeTarget);

        // then allowed to be eaten
        positions = extractor.extractEats(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(closeTarget));
    }

    @Test
    public void shouldAllowEatingOnlyForDifferentColor() {
        // when place distant target with the same color
        extractor.getGameState().placePiece(new Pawn(Player.White), distantTarget);

        // then not allowed to be eaten
        Set<Position> positions = extractor.extractEats(testSupplier, position);
        assertThat(positions.size(), is(0));

        // when place close target of different color
        extractor.getGameState().placePiece(new Pawn(Player.Black), closeTarget);

        // then allowed to be eaten
        positions = extractor.extractEats(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(closeTarget));
    }
}