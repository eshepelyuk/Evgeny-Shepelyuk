package chess.actions;

import chess.PiecePosition;
import chess.pieces.Rook;

import java.util.function.Predicate;

import static chess.actions.EatPieceIterator.createEatPieceSupplier;
import static chess.actions.GameActionSupplier.filterSuppliers;
import static chess.actions.MovePieceIterator.createMovePieceSupplier;

public class RookSpec {

    private static Predicate<PiecePosition> IS_ROOK = piece -> piece.getPiece() instanceof Rook;

    public static GameActionSupplier ROOK_ACTIONS = filterSuppliers(IS_ROOK,
        createMovePieceSupplier(Direction.UP),
        createMovePieceSupplier(Direction.RIGHT),
        createMovePieceSupplier(Direction.DOWN),
        createMovePieceSupplier(Direction.LEFT),
        createEatPieceSupplier(Direction.UP),
        createEatPieceSupplier(Direction.RIGHT),
        createEatPieceSupplier(Direction.DOWN),
        createEatPieceSupplier(Direction.LEFT)
    );
}
