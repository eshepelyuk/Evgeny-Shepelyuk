package chess.actions;

import chess.*;
import chess.pieces.Pawn;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class PawnSpecTest {

    private PositionExtractor extractor = new PositionExtractor(new GameState());

    @Test
    public void whitePawnBehaviour() throws Exception {
        //
        PiecePosition positionInitial = new PiecePosition(new Pawn(Player.White), "b2");
        PiecePosition positionMoved = new PiecePosition(new Pawn(Player.White), "d3");

        //when
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("a3"));
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("c3"));

        //then moves for initial pawn
        Set<Position> positions = extractor.extractMoves(PawnSpec.PAWN_ACTIONS, positionInitial);
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItems(new Position("b3"), new Position("b4")));

        // and eats for initial pawn
        positions = extractor.extractEats(PawnSpec.PAWN_ACTIONS, positionInitial);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("c3")));

        //then moves for moved pawn
        positions = extractor.extractMoves(PawnSpec.PAWN_ACTIONS, positionMoved);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("d4")));

        // and eats for initial pawn
        positions = extractor.extractEats(PawnSpec.PAWN_ACTIONS, positionMoved);
        assertThat(positions.size(), is(0));
    }

    @Test
    public void blackPawnBehaviour() throws Exception {
        //
        PiecePosition positionInitial = new PiecePosition(new Pawn(Player.Black), "b7");
        PiecePosition positionMoved = new PiecePosition(new Pawn(Player.Black), "d6");

        //when
        extractor.getGameState().placePiece(new Pawn(Player.Black), new Position("a6"));
        extractor.getGameState().placePiece(new Pawn(Player.White), new Position("c6"));

        //then moves for initial pawn
        Set<Position> positions = extractor.extractMoves(PawnSpec.PAWN_ACTIONS, positionInitial);
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItems(new Position("b6"), new Position("b5")));

        // and eats for initial pawn
        positions = extractor.extractEats(PawnSpec.PAWN_ACTIONS, positionInitial);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("c6")));

        //then moves for moved pawn
        positions = extractor.extractMoves(PawnSpec.PAWN_ACTIONS, positionMoved);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("d5")));

        // and eats for initial pawn
        positions = extractor.extractEats(PawnSpec.PAWN_ACTIONS, positionMoved);
        assertThat(positions.size(), is(0));
    }

}