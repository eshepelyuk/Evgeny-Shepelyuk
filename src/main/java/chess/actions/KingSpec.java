package chess.actions;

import chess.pieces.King;

import static chess.actions.GameActionSupplier.*;

public class KingSpec {

    public static GameActionSupplier KING_ACTIONS = filterSuppliers(piece -> piece.getPiece() instanceof King,
        createBoundedMovePieceSupplier(Direction.DOWN_LEFT, 1L),
        createBoundedMovePieceSupplier(Direction.DOWN_RIGHT, 1L),
        createBoundedMovePieceSupplier(Direction.UP_LEFT, 1L),
        createBoundedMovePieceSupplier(Direction.UP_RIGHT, 1L),
        createBoundedMovePieceSupplier(Direction.UP, 1L),
        createBoundedMovePieceSupplier(Direction.RIGHT, 1L),
        createBoundedMovePieceSupplier(Direction.DOWN, 1L),
        createBoundedMovePieceSupplier(Direction.LEFT, 1L),

        createBoundedEatPieceSupplier(Direction.DOWN_LEFT, 1L),
        createBoundedEatPieceSupplier(Direction.DOWN_RIGHT, 1L),
        createBoundedEatPieceSupplier(Direction.UP_LEFT, 1L),
        createBoundedEatPieceSupplier(Direction.UP_RIGHT, 1L),
        createBoundedEatPieceSupplier(Direction.UP, 1L),
        createBoundedEatPieceSupplier(Direction.RIGHT, 1L),
        createBoundedEatPieceSupplier(Direction.DOWN, 1L),
        createBoundedEatPieceSupplier(Direction.LEFT, 1L)
    );
}
