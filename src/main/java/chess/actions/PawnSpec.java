package chess.actions;

import static chess.actions.GameActionSupplier.*;

public class PawnSpec {

    private static GameActionSupplier PAWN_MOVES = (current, gameState) -> {
        long limit = current.getPiece().getOwner().isInitialForPawn(current.getPosition()) ? 2 : 1;
        GameActionSupplier supplier = current.isWhite()
            ? createBoundedMovePieceSupplier(Direction.UP, limit)
            : createBoundedMovePieceSupplier(Direction.DOWN, limit);
        return supplier.apply(current, gameState);
    };

    private static GameActionSupplier PAWN_KILLS = (current, gameState) -> {
        GameActionSupplier supplier = current.isWhite()
            ? concatSuppliers(createBoundedEatPieceSupplier(Direction.UP_LEFT, 1), createBoundedEatPieceSupplier(Direction.UP_RIGHT, 1))
            : concatSuppliers(createBoundedEatPieceSupplier(Direction.DOWN_LEFT, 1), createBoundedEatPieceSupplier(Direction.DOWN_RIGHT, 1));
        return supplier.apply(current, gameState);
    };

    public static GameActionSupplier PAWN_ACTIONS = filterSuppliers(piece -> piece.getPiece() instanceof chess.pieces.Pawn, PAWN_MOVES, PAWN_KILLS);
}
