package chess.actions;

import chess.*;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class QueenActionsTest {

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    private PiecePosition position = new PiecePosition(new Queen(Player.Black), "d5");

    @Test
    public void queenBehaviour() throws Exception {
        // put pawn around D5
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("b7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("d7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f7"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f5"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("f3"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("d3"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("b3"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("b5"));

        Set<Position> set = extractor.extractMoves(QueenActions.QUEEN_ACTIONS, position);
        assertThat(set.size(), is(8));

        assertThat(set, hasItems(new Position("c6"), new Position("d6"), new Position("e6")));
        assertThat(set, hasItems(new Position("e5"), new Position("e4"), new Position("d4")));
        assertThat(set, hasItems(new Position("c4"), new Position("c5")));

        set = extractor.extractKills(QueenActions.QUEEN_ACTIONS, position);
        assertThat(set.size(), is(0));

    }
}