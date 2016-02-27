package chess.actions;

import chess.GameState;
import chess.Player;
import chess.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class PawnMovesTest {

    private GameState gameState;

    Pawn pawnWhite = new Pawn(Player.White);
    Pawn pawnBlack = new Pawn(Player.Black);

    @Before
    public void setUp() {
        gameState = new GameState();
    }

    @Test
    public void shouldAllowMovesForWhitePawn() {
        Position position = new Position("a2");

        Optional<Set<Position>> positions = Moves.ONE_CELL_FWD_OLD.availableMoves(pawnWhite, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItem(new Position("a3")));

        positions = Moves.TWO_CELL_FWD_OLD.availableMoves(pawnWhite, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItem(new Position("a4")));

        //
        positions = Moves.PAWN_ACTIONS_OLD.availableMoves(pawnWhite, position, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(2));

        assertThat(positions.get(), hasItems(new Position("a3"), new Position("a4")));
    }

    @Test
    public void shouldAllowMovesForBlackPawn() {
        Position position = new Position("a7");

        Set<MovePiece> positions = Moves.ONE_CELL_FWD.apply(new PiecePosition(pawnBlack, position), gameState).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions.iterator().next().getTarget(), is(new Position("a6")));

        positions = Moves.TWO_CELL_FWD.apply(new PiecePosition(pawnBlack, position), gameState).collect(toSet());
        assertThat(positions.size(), is(1));
        assertThat(positions.iterator().next().getTarget(), is(new Position("a5")));

        positions = Moves.PAWN_ACTIONS.apply(new PiecePosition(pawnBlack, position), gameState).collect(toSet());
        assertThat(positions.size(), is(2));
        assertThat(positions.stream().map(MovePiece::getTarget).collect(toSet()), hasItems(new Position("a6"), new Position("a5")));
    }

    @Test
    public void shouldAllowOnlyOneCellMovementIfNotAtInitialPosition() {
        Optional<Set<Position>> positions = Moves.PAWN_ACTIONS_OLD.availableMoves(pawnWhite, new Position("a3"), gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItems(new Position("a4")));

        positions = Moves.PAWN_ACTIONS_OLD.availableMoves(pawnBlack, new Position("a6"), gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItems(new Position("a5")));
    }

    @Test
    public void shouldSkipNonPawnPieces() throws Exception {
        Piece piece1 = new King(Player.White);
        Position position1 = new Position("a1");

        Piece piece2 = new Queen(Player.Black);
        Position position2 = new Position("a2");

        assertThat(Moves.ONE_CELL_FWD_OLD.availableMoves(piece1, position1, gameState).isPresent(), is(false));
        assertThat(Moves.ONE_CELL_FWD_OLD.availableMoves(piece2, position2, gameState).isPresent(), is(false));

        assertThat(Moves.TWO_CELL_FWD_OLD.availableMoves(piece1, position1, gameState).isPresent(), is(false));
        assertThat(Moves.TWO_CELL_FWD_OLD.availableMoves(piece2, position2, gameState).isPresent(), is(false));
    }

    @Test
    public void shouldSkipMovesToOccupiedCell() {
        Position positionWhite = new Position("a2");
        Position positionBlack = new Position("a7");

        //when occupy some cells
        gameState.placePiece(new King(Player.White), new Position("a3"));
        gameState.placePiece(new King(Player.Black), new Position("a5"));

        //then only 1 move is available
        //white
        Optional<Set<Position>> positions = Moves.PAWN_ACTIONS_OLD.availableMoves(pawnWhite, positionWhite, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItems(new Position("a4")));

        //black
        positions = Moves.PAWN_ACTIONS_OLD.availableMoves(pawnBlack, positionBlack, gameState);
        assertThat(positions.isPresent(), is(true));
        assertThat(positions.get().size(), is(1));
        assertThat(positions.get(), hasItems(new Position("a6")));

    }
}