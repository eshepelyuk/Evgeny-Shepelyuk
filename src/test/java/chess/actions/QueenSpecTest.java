package chess.actions;

import chess.*;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class QueenSpecTest {

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    private PiecePosition position = new PiecePosition(new Queen(Player.White), "d5");

    @Test
    public void queenBehaviour() throws Exception {
        // put pawn around D5
        // of the different color
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("b7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("d7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f5"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f3"));
        // of same color
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("d3"));
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("b3"));
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("b5"));

        Set<Position> set = extractor.extractMoves(QueenSpec.QUEEN_ACTIONS, position);
        assertThat(set.size(), is(8));

        assertThat(set, hasItems(new Position("c6"), new Position("d6"), new Position("e6")));
        assertThat(set, hasItems(new Position("e5"), new Position("e4"), new Position("d4")));
        assertThat(set, hasItems(new Position("c4"), new Position("c5")));

        set = extractor.extractEats(QueenSpec.QUEEN_ACTIONS, position);
        assertThat(set.size(), is(5));
        assertThat(set, hasItems(new Position("b7"), new Position("d7"), new Position("f7")));
        assertThat(set, hasItems(new Position("f5"), new Position("f3")));

    }
}