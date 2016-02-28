package chess.actions;

import chess.GameState;
import chess.PiecePosition;
import chess.Player;
import chess.Position;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

public class QueenActionsTest {

    private GameState gameState = new GameState();

    Queen queen = new chess.pieces.Queen(Player.Black);

    @Test
    public void shouldAllowMoves() {
        PiecePosition position = new PiecePosition(queen, "d5");

        Set<Position> positions = extractPositions(QueenActions.QUEEN_MOVES, position);
        assertThat(positions.size(), is(13));
        assertThat(positions, hasItems(new Position("c6"), new Position("b7"), new Position("a8")));
        assertThat(positions, hasItems(new Position("e6"), new Position("f7"), new Position("g8")));
        assertThat(positions, hasItems(new Position("c4"), new Position("b3"), new Position("a2")));
        assertThat(positions, hasItems(new Position("e4"), new Position("f3"), new Position("g2"), new Position("h1")));
    }

    @Test
    public void shouldDisallowAllowMovesToOccupiedCells() {
        PiecePosition position = new PiecePosition(queen, "d5");

        gameState.placePiece(new Pawn(Player.Black), new Position("b7"));
        gameState.placePiece(new Pawn(Player.Black), new Position("b3"));
        gameState.placePiece(new Pawn(Player.Black), new Position("f3"));
        gameState.placePiece(new Pawn(Player.Black), new Position("f7"));

        Set<Position> positions = extractPositions(QueenActions.QUEEN_MOVES, position);
        assertThat(positions.size(), is(4));
        assertThat(positions, hasItems(new Position("c6"), new Position("e6"), new Position("c4"), new Position("e4")));
    }

    Set<Position> extractPositions(GameActionSupplier supplier, PiecePosition piecePosition) {
        return supplier.apply(piecePosition, gameState).map(GameAction::getTarget).collect(Collectors.toSet());
    }
}