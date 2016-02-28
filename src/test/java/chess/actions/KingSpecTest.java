package chess.actions;

import chess.*;
import chess.pieces.King;
import chess.pieces.Pawn;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class KingSpecTest {

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    private PiecePosition position = new PiecePosition(new King(Player.White), "d5");

    @Test
    public void kingBehaviour() throws Exception {
        // put pawn around D5
        // of the different color
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("c6"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("d7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f5"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f3"));
        // of same color
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("d3"));
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("b3"));
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("b5"));

        Set<Position> set = extractor.extractMoves(KingSpec.KING_ACTIONS, position);
        assertThat(set.size(), is(7));

        assertThat(set, hasItems(new Position("d6"), new Position("e6")));
        assertThat(set, hasItems(new Position("e5"), new Position("e4"), new Position("d4")));
        assertThat(set, hasItems(new Position("c4"), new Position("c5")));

        set = extractor.extractEats(KingSpec.KING_ACTIONS, position);
        assertThat(set.size(), is(1));
        assertThat(set, hasItems(new Position("c6")));

    }
}