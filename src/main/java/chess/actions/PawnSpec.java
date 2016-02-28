package chess.actions;

import chess.PiecePosition;

import java.util.Optional;
import java.util.function.Predicate;

import static chess.actions.GameActionSupplier.*;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

public class PawnSpec {

    private static Predicate<PiecePosition> IS_PAWN = piece -> piece.getPiece() instanceof chess.pieces.Pawn;

    protected static GameActionSupplier ONE_CELL_FWD = (current, gameState) -> (current.isWhite() ? current.getPosition().up(1) : current.getPosition().down(1))
        .filter(gameState::isFreeAt)
        .map(p -> of(new MovePiece(current, p))).orElse(empty());

    protected static GameActionSupplier TWO_CELL_FWD = (current, gameState) -> Optional.of(current)
        .map(p -> p.getPiece().getOwner().isInitialForPawn(p.getPosition()) ? p : null)
        .flatMap(p -> current.isWhite() ? p.getPosition().up(1) : p.getPosition().down(1))
        .filter(gameState::isFreeAt)
        .flatMap(p -> current.isWhite() ? p.up(1) : p.down(1))
        .filter(gameState::isFreeAt)
        .map(p -> of(new MovePiece(current, p))).orElse(empty());

    protected static GameActionSupplier PAWN_MOVES = filterSuppliers(IS_PAWN, ONE_CELL_FWD, TWO_CELL_FWD);

    protected static GameActionSupplier PAWN_KILLS = (current, gameState) -> {
        GameActionSupplier supplier = current.isWhite()
            ? concatSuppliers(createBoundedEatPieceSupplier(Direction.UP_LEFT, 1), createBoundedEatPieceSupplier(Direction.UP_RIGHT, 1))
            : concatSuppliers(createBoundedEatPieceSupplier(Direction.DOWN_LEFT, 1), createBoundedEatPieceSupplier(Direction.DOWN_RIGHT, 1));
        return filterSuppliers(IS_PAWN, supplier).apply(current, gameState);
    };

    public static GameActionSupplier PAWN_ACTIONS = filterSuppliers(IS_PAWN, PAWN_MOVES, PAWN_KILLS);
}
