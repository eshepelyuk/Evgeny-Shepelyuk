package chess.actions;

import chess.PiecePosition;
import chess.pieces.Queen;

import java.util.function.Predicate;

import static chess.actions.GameActionSupplier.filterSuppliers;
import static chess.actions.KillPieceIterator.createKillPieceActionSupplier;
import static chess.actions.MovePieceIterator.createMovePieceActionSupplier;

public class QueenSpec {

    private static Predicate<PiecePosition> IS_QUEEN = piece -> piece.getPiece() instanceof Queen;

    private static GameActionSupplier QUEEN_MOVES = filterSuppliers(IS_QUEEN,
        createMovePieceActionSupplier(Direction.DOWN_LEFT),
        createMovePieceActionSupplier(Direction.DOWN_RIGHT),
        createMovePieceActionSupplier(Direction.UP_LEFT),
        createMovePieceActionSupplier(Direction.UP_RIGHT),
        createMovePieceActionSupplier(Direction.UP),
        createMovePieceActionSupplier(Direction.RIGHT),
        createMovePieceActionSupplier(Direction.DOWN),
        createMovePieceActionSupplier(Direction.LEFT)
    );

    private static GameActionSupplier QUEEN_KILLS = filterSuppliers(IS_QUEEN,
        createKillPieceActionSupplier(Direction.DOWN_LEFT),
        createKillPieceActionSupplier(Direction.DOWN_RIGHT),
        createKillPieceActionSupplier(Direction.UP_LEFT),
        createKillPieceActionSupplier(Direction.UP_RIGHT),
        createKillPieceActionSupplier(Direction.UP),
        createKillPieceActionSupplier(Direction.RIGHT),
        createKillPieceActionSupplier(Direction.DOWN),
        createKillPieceActionSupplier(Direction.LEFT)
    );

    public static GameActionSupplier QUEEN_ACTIONS = filterSuppliers(IS_QUEEN, QUEEN_MOVES, QUEEN_KILLS);
}
