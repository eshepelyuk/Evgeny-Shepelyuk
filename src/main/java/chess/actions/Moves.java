package chess.actions;

import chess.pieces.Pawn;

import java.util.Optional;
import java.util.function.Predicate;

import static chess.actions.GameActionSupplier.filteredSupplier;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

public class Moves {

    public static Predicate<PiecePosition> IS_PAWN = piece -> piece.getPiece() instanceof Pawn;

    public static GameActionSupplier ONE_CELL_FWD = (current, gameState) -> (current.isWhite() ? current.getPosition().up(1) : current.getPosition().down(1))
        .filter(gameState::isFreeAt)
        .map(p -> of(new MovePiece(current, p))).orElse(empty());

    public static GameActionSupplier TWO_CELL_FWD = (current, gameState) -> Optional.of(current)
        .map(p -> p.getPiece().getOwner().isInitialForPawn(p.getPosition()) ? p : null)
        .flatMap(p -> current.isWhite() ? p.getPosition().up(1) : p.getPosition().down(1))
        .flatMap(p -> current.isWhite() ? p.up(1) : p.down(1))
        .filter(gameState::isFreeAt)
        .map(p -> of(new MovePiece(current, p))).orElse(empty());

    public static GameActionSupplier PAWN_MOVES = filteredSupplier(IS_PAWN, ONE_CELL_FWD, TWO_CELL_FWD);

    public static GameActionSupplier KILL_FWD_RIGHT = (current, gameState) -> current.getPosition().right(1)
        .flatMap(p -> current.isWhite() ? p.up(1) : p.down(1))
        .filter(p -> !gameState.isFreeAt(p) && gameState.getPieceAt(p).getOwner() != current.getPiece().getOwner())
        .map(p -> of(new KillPiece(current, p)))
        .orElse(empty());

    public static GameActionSupplier KILL_FWD_LEFT = (current, gameState) -> current.getPosition().left(1)
        .flatMap(p -> current.isWhite() ? p.up(1) : p.down(1))
        .filter(p -> !gameState.isFreeAt(p) && gameState.getPieceAt(p).getOwner() != current.getPiece().getOwner())
        .map(p -> of(new KillPiece(current, p)))
        .orElse(empty());

    public static GameActionSupplier PAWN_KILLS = filteredSupplier(IS_PAWN, KILL_FWD_LEFT, KILL_FWD_RIGHT);

    public static GameActionSupplier PAWN_ACTIONS = filteredSupplier(IS_PAWN, PAWN_MOVES, PAWN_KILLS);
}
