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

import static chess.actions.KillPieceIterator.createKillPieceActionSupplier;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

@RunWith(Parameterized.class)
public class KillPieceIteratorTest {
    private final GameActionSupplier testSupplier;

    private final Position closeTarget;
    private final Position distantTarget;

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    PiecePosition position = new PiecePosition(new Queen(Player.Black), "d5");

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
            new Object[]{
                createKillPieceActionSupplier(Direction.DOWN_RIGHT), new Position("e4"), new Position("f3")
            }, new Object[]{
                createKillPieceActionSupplier(Direction.DOWN_LEFT), new Position("c4"), new Position("b3")
            }, new Object[]{
                createKillPieceActionSupplier(Direction.UP_LEFT), new Position("c6"), new Position("b7")
            }, new Object[]{
                createKillPieceActionSupplier(Direction.UP_RIGHT), new Position("e6"), new Position("f7")
            }, new Object[]{
                createKillPieceActionSupplier(Direction.UP), new Position("d6"), new Position("d7")
            }, new Object[]{
                createKillPieceActionSupplier(Direction.RIGHT), new Position("e5"), new Position("f5")
            }, new Object[]{
                createKillPieceActionSupplier(Direction.DOWN), new Position("d4"), new Position("d3")
            }, new Object[]{
                createKillPieceActionSupplier(Direction.LEFT), new Position("c5"), new Position("b5")
            }
        );
    }

    public KillPieceIteratorTest(GameActionSupplier supplier, Position closeTarget, Position distantTarget) {
        this.testSupplier = supplier;
        this.closeTarget = closeTarget;
        this.distantTarget = distantTarget;
    }

    @Test
    public void shouldAllowKills() {
        // when direction is free then no kills
        Set<Position> positions = extractor.extractKills(testSupplier, position);
        assertThat(positions.size(), is(0));

        // when place distant target
        extractor.getGameState().placePiece(new Pawn(Player.Black), distantTarget);

        // then distant target is allowed to be killed
        positions = extractor.extractKills(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(distantTarget));

        // when place close target
        extractor.getGameState().placePiece(new Pawn(Player.Black), closeTarget);

        // then close target is allowed to be killed
        positions = extractor.extractKills(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(closeTarget));
    }
}