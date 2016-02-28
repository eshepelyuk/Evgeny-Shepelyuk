package chess.actions;

import chess.*;
import chess.pieces.Pawn;
import chess.pieces.Rook;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class RookSpecTest {

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    private PiecePosition position = new PiecePosition(new Rook(Player.White), "d5");

    @Test
    public void queenBehaviour() throws Exception {
        // put pawn around D5
        // of the different color
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("d7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f5"));
        // of same color
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("d3"));
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("b5"));

        Set<Position> set = extractor.extractMoves(RookSpec.ROOK_ACTIONS, position);
        assertThat(set.size(), is(4));

        assertThat(set, hasItems(new Position("d6"), new Position("e5"), new Position("d4"), new Position("c5")));

        set = extractor.extractEats(RookSpec.ROOK_ACTIONS, position);
        assertThat(set.size(), is(2));
        assertThat(set, hasItems(new Position("d7"), new Position("f5")));
    }
}