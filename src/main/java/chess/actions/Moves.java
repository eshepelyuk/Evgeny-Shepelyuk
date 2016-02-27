package chess.actions;

import chess.GameState;
import chess.pieces.Pawn;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import static chess.Player.White;
import static java.util.Optional.ofNullable;

public class Moves {
    public interface GameActionSupplier<T extends GameAction> extends BiFunction<PiecePosition, GameState, Stream<T>> {
    }

    public interface MovePieceActionSupplier extends GameActionSupplier<MovePiece> {
    }

    public interface KillPieceActionSupplier extends GameActionSupplier<KillPiece> {
    }

    public static MovePieceActionSupplier ONE_CELL_FWD = (current, gameState) -> {
        Optional<MovePiece> o = ofNullable(current.getPiece() instanceof Pawn ? current.getPiece() : null)
            .flatMap(p -> p.getOwner() == White ? current.getPosition().up(1) : current.getPosition().down(1))
            .filter(gameState::isFreeAt)
            .map(p -> new MovePiece(current, p));
        return o.isPresent() ? Stream.of(o.get()) : Stream.empty();
    };

    public static MovePieceActionSupplier TWO_CELL_FWD = (current, gameState) -> {
        Optional<MovePiece> o = ofNullable(current.getPiece() instanceof Pawn ? current.getPiece() : null)
            .flatMap(p -> p.getOwner() == White ? current.getPosition().up(2) : current.getPosition().down(2))
            .filter(gameState::isFreeAt)
            .map(p -> new MovePiece(current, p));
        return o.isPresent() ? Stream.of(o.get()) : Stream.empty();
    };

    public static MovePieceActionSupplier PAWN_ACTIONS = (current, gameState) -> {
        return current.getPiece().getOwner().isInitialForPawn(current.getPosition())
            ? Stream.concat(ONE_CELL_FWD.apply(current, gameState), TWO_CELL_FWD.apply(current, gameState))
            : ONE_CELL_FWD.apply(current, gameState);
    };
}
