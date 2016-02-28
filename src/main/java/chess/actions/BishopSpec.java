package chess.actions;

import chess.PiecePosition;
import chess.pieces.Bishop;

import java.util.function.Predicate;

import static chess.actions.EatPieceIterator.createEatPieceSupplier;
import static chess.actions.GameActionSupplier.filterSuppliers;
import static chess.actions.MovePieceIterator.createMovePieceSupplier;

public class BishopSpec {

    private static Predicate<PiecePosition> IS_BISHOP = piece -> piece.getPiece() instanceof Bishop;

    private static GameActionSupplier BISHOP_MOVES = filterSuppliers(IS_BISHOP,
        createMovePieceSupplier(Direction.DOWN_LEFT),
        createMovePieceSupplier(Direction.DOWN_RIGHT),
        createMovePieceSupplier(Direction.UP_LEFT),
        createMovePieceSupplier(Direction.UP_RIGHT)
    );

    private static GameActionSupplier BISHOP_KILLS = filterSuppliers(IS_BISHOP,
        createEatPieceSupplier(Direction.DOWN_LEFT),
        createEatPieceSupplier(Direction.DOWN_RIGHT),
        createEatPieceSupplier(Direction.UP_LEFT),
        createEatPieceSupplier(Direction.UP_RIGHT)
    );

    public static GameActionSupplier BISHOP_ACTIONS = filterSuppliers(IS_BISHOP, BISHOP_MOVES, BISHOP_KILLS);
}
