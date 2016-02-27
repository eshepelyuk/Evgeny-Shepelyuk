package chess.actions;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static chess.actions.Moves.*;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class PawnTest {

    private GameState gameState;

    Pawn pawnWhite = new Pawn(Player.White);
    Pawn pawnBlack = new Pawn(Player.Black);

    @Before
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void shouldAllowMovesForWhitePawn() {
        PiecePosition position = new PiecePosition(pawnWhite, new Position("a2"));

        Set<Position> positions = Moves.ONE_CELL_FWD.apply(position, gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a3")));

        positions = Moves.TWO_CELL_FWD.apply(position, gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a4")));

        //
        positions = PAWN_ACTIONS.apply(position, gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItems(new Position("a3"), new Position("a4")));
    }

    @Test
    public void shouldAllowWhitePawnToKillBlackPieces() {
        PiecePosition pawn = new PiecePosition(pawnWhite, "b2");

        // no kills
        Set<Position> positions = extractPositions(Moves.PAWN_KILLS, pawn);
        assertThat(positions.size(), is(0));

        // kill to left
        gameState.placePiece(new King(Player.Black), new Position("a3"));
        positions = extractPositions(Moves.KILL_FWD_LEFT, pawn);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a3")));

        // kill to right
        gameState.placePiece(new Queen(Player.Black), new Position("c3"));
        positions = extractPositions(Moves.KILL_FWD_RIGHT, pawn);
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("c3")));

        //
        positions = extractPositions(Moves.PAWN_KILLS, pawn);
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItem(new Position("a3")));
        assertThat(positions, hasItem(new Position("c3")));
    }

    @Test
    public void shouldDisallowWhitePawnToKillWhitePieces() {
        PiecePosition pawn = new PiecePosition(pawnWhite, "b2");

        // no kills
        Set<Position> positions = extractPositions(Moves.PAWN_KILLS, pawn);
        assertThat(positions.size(), is(0));

        // kill to left
        gameState.placePiece(new King(Player.White), new Position("a3"));
        positions = extractPositions(Moves.KILL_FWD_LEFT, pawn);
        assertThat(positions.size(), is(0));

        // kill to right
        gameState.placePiece(new Queen(Player.White), new Position("c3"));
        positions = extractPositions(Moves.KILL_FWD_RIGHT, pawn);
        assertThat(positions.size(), is(0));

        //
        positions = extractPositions(Moves.PAWN_KILLS, pawn);
        assertThat(positions.size(), is(0));
    }

    @Test
    public void shouldAllowMovesForBlackPawn() {
        PiecePosition position = new PiecePosition(pawnBlack, new Position("a7"));

        Set<Position> positions = Moves.ONE_CELL_FWD.apply(position, gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a6")));

        positions = Moves.TWO_CELL_FWD.apply(position, gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItem(new Position("a5")));

        positions = PAWN_ACTIONS.apply(position, gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItems(new Position("a6"), new Position("a5")));
    }

    @Test
    public void shouldAllowOnlyOneCellMovementIfNotAtInitialPosition() {
        Set<Position> positions = PAWN_ACTIONS.apply(new PiecePosition(pawnWhite, new Position("a3")), gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("a4")));

        positions = PAWN_ACTIONS.apply(new PiecePosition(pawnBlack, new Position("a6")), gameState).map(GameAction::getTarget).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions, hasItems(new Position("a5")));
    }

    @Test
    public void shouldSkipNonPawnPieces() throws Exception {
        assertThat(Moves.PAWN_MOVES.apply(new PiecePosition(new King(Player.White), "a1"), gameState).findFirst().isPresent(), is(false));
        assertThat(Moves.PAWN_KILLS.apply(new PiecePosition(new King(Player.White), "a1"), gameState).findFirst().isPresent(), is(false));
        assertThat(Moves.PAWN_ACTIONS.apply(new PiecePosition(new King(Player.White), "a1"), gameState).findFirst().isPresent(), is(false));
    }

    @Test
    public void shouldSkipMovesToOccupiedCell() {
        //when occupy some cells
        gameState.placePiece(new King(Player.White), new Position("a3"));
        gameState.placePiece(new King(Player.Black), new Position("a5"));

        //then only 1 move is available
        //white
        Set<Position> positions = extractPositions(ONE_CELL_FWD, new PiecePosition(pawnWhite, "a2"));
        assertThat(positions.size(), is(0));

        //black
        positions = extractPositions(TWO_CELL_FWD, new PiecePosition(pawnBlack, "a7"));
        assertThat(positions.size(), is(0));
    }

    @Test
    public void shouldAllowMovesAndKillsForPawn() {
        PiecePosition pawn = new PiecePosition(pawnWhite, new Position("b2"));

        // no kills
        Set<Position> positions = extractPositions(Moves.PAWN_ACTIONS, pawn);
        assertThat(positions.size(), is(2));
        assertThat(positions, hasItem(new Position("b3")));
        assertThat(positions, hasItem(new Position("b4")));

        // kill to left
        gameState.placePiece(new King(Player.Black), new Position("a3"));
        positions = extractPositions(Moves.PAWN_ACTIONS, pawn);
        assertThat(positions.size(), is(3));
        assertThat(positions, hasItem(new Position("b3")));
        assertThat(positions, hasItem(new Position("b4")));
        assertThat(positions, hasItem(new Position("a3")));
    }

    private Set<Position> extractPositions(GameActionSupplier supplier, PiecePosition piecePosition) {
        return supplier.apply(piecePosition, gameState).map(GameAction::getTarget).collect(toSet());
    }
}