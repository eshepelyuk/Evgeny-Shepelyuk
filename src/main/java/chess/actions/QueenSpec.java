package chess.actions;

import chess.PiecePosition;
import chess.pieces.Queen;

import java.util.function.Predicate;

import static chess.actions.EatPieceIterator.createEatPieceSupplier;
import static chess.actions.GameActionSupplier.filterSuppliers;
import static chess.actions.MovePieceIterator.createMovePieceSupplier;

public class QueenSpec {

    private static Predicate<PiecePosition> IS_QUEEN = piece -> piece.getPiece() instanceof Queen;

    private static GameActionSupplier QUEEN_MOVES = filterSuppliers(IS_QUEEN,
        createMovePieceSupplier(Direction.DOWN_LEFT),
        createMovePieceSupplier(Direction.DOWN_RIGHT),
        createMovePieceSupplier(Direction.UP_LEFT),
        createMovePieceSupplier(Direction.UP_RIGHT),
        createMovePieceSupplier(Direction.UP),
        createMovePieceSupplier(Direction.RIGHT),
        createMovePieceSupplier(Direction.DOWN),
        createMovePieceSupplier(Direction.LEFT)
    );

    private static GameActionSupplier QUEEN_KILLS = filterSuppliers(IS_QUEEN,
        createEatPieceSupplier(Direction.DOWN_LEFT),
        createEatPieceSupplier(Direction.DOWN_RIGHT),
        createEatPieceSupplier(Direction.UP_LEFT),
        createEatPieceSupplier(Direction.UP_RIGHT),
        createEatPieceSupplier(Direction.UP),
        createEatPieceSupplier(Direction.RIGHT),
        createEatPieceSupplier(Direction.DOWN),
        createEatPieceSupplier(Direction.LEFT)
    );

    public static GameActionSupplier QUEEN_ACTIONS = filterSuppliers(IS_QUEEN, QUEEN_MOVES, QUEEN_KILLS);
}
