package chess.actions;

import chess.*;
import chess.pieces.Bishop;
import chess.pieces.Pawn;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class BishopSpecTest {

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    private PiecePosition position = new PiecePosition(new Bishop(Player.White), "d5");

    @Test
    public void bishopBehaviour() throws Exception {
        // put pawn around D5
        // of the different color
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("b7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f3"));
        // of same color
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("b3"));

        Set<Position> set = extractor.extractMoves(BishopSpec.BISHOP_ACTIONS, position);
        assertThat(set.size(), is(4));
        assertThat(set, hasItems(new Position("c6"), new Position("e6"), new Position("e4"), new Position("c4")));

        set = extractor.extractEats(BishopSpec.BISHOP_ACTIONS, position);
        assertThat(set.size(), is(3));
        assertThat(set, hasItems(new Position("b7"), new Position("f3"), new Position("f7")));
    }
}