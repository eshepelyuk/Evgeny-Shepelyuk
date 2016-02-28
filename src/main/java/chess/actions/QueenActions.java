package chess.actions;

import chess.PiecePosition;

import java.util.function.Predicate;

import static chess.actions.GameActionSupplier.filterSuppliers;
import static chess.actions.MovePieceIterator.createMovePieceActionSupplier;

public class QueenActions {

    private static Predicate<PiecePosition> IS_QUEEN = piece -> piece.getPiece() instanceof chess.pieces.Queen;

    protected static GameActionSupplier QUEEN_MOVES = filterSuppliers(IS_QUEEN,
        createMovePieceActionSupplier(Direction.DOWN_LEFT),
        createMovePieceActionSupplier(Direction.DOWN_RIGHT),
        createMovePieceActionSupplier(Direction.UP_LEFT),
        createMovePieceActionSupplier(Direction.UP_RIGHT)
    );

    protected static GameActionSupplier QUEEN_KILLS = filterSuppliers(IS_QUEEN);

    public static GameActionSupplier QUEEN_ACTIONS = filterSuppliers(IS_QUEEN, QUEEN_MOVES, QUEEN_KILLS);
}
