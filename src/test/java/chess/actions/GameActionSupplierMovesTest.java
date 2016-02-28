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

import static chess.actions.GameActionSupplier.createMovePieceSupplier;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

@RunWith(Parameterized.class)
public class GameActionSupplierMovesTest {

    private final GameActionSupplier testSupplier;
    private final Position[] expectedPositions;

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    PiecePosition position = new PiecePosition(new Queen(Player.Black), "d5");

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        return Arrays.asList(
            new Object[]{
                createMovePieceSupplier(Direction.DOWN_RIGHT),
                new Position[]{new Position("e4"), new Position("f3"), new Position("g2"), new Position("h1")}
            }, new Object[]{
                createMovePieceSupplier(Direction.DOWN_LEFT),
                new Position[]{new Position("c4"), new Position("b3"), new Position("a2")}
            }, new Object[]{
                createMovePieceSupplier(Direction.UP_LEFT),
                new Position[]{new Position("c6"), new Position("b7"), new Position("a8")}
            }, new Object[]{
                createMovePieceSupplier(Direction.UP_RIGHT),
                new Position[]{new Position("e6"), new Position("f7"), new Position("g8")}
            }, new Object[]{
                createMovePieceSupplier(Direction.UP),
                new Position[]{new Position("d6"), new Position("d7"), new Position("d8")}
            }, new Object[]{
                createMovePieceSupplier(Direction.RIGHT),
                new Position[]{new Position("e5"), new Position("f5"), new Position("g5"), new Position("h5")}
            }, new Object[]{
                createMovePieceSupplier(Direction.DOWN),
                new Position[]{new Position("d4"), new Position("d3"), new Position("d2"), new Position("d1")}
            }, new Object[]{
                createMovePieceSupplier(Direction.LEFT),
                new Position[]{new Position("c5"), new Position("b5"), new Position("a5")}
            }
        );
    }

    public GameActionSupplierMovesTest(GameActionSupplier supplier, Position[] positions) {
        this.testSupplier = supplier;
        this.expectedPositions = positions;
    }

    @Test
    public void shouldAllowMoves() throws Exception {
        // when direction is free then can move ntil end of board
        Set<Position> positions = extractor.extractMoves(testSupplier, position);
        assertThat(positions.size(), is(expectedPositions.length));
        assertThat(positions, hasItems(expectedPositions));

        // when place obstacle
        extractor.getGameState().placePiece(new Pawn(Player.Black), expectedPositions[1]);

        // then moves are limited
        positions = extractor.extractMoves(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(expectedPositions[0]));
    }

    @Test
    public void shouldAllowLimitedMoves() throws Exception {
        // when direction is free then can move ntil end of board
        Set<Position> positions = extractor.extractMoves(testSupplier, position);
        assertThat(positions.size(), is(expectedPositions.length));
        assertThat(positions, hasItems(expectedPositions));

        // when place obstacle
        extractor.getGameState().placePiece(new Pawn(Player.Black), expectedPositions[1]);

        // then moves are limited
        positions = extractor.extractMoves(testSupplier, position);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(expectedPositions[0]));
    }
}