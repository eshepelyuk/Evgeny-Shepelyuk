package chess.actions;

import chess.GameState;
import chess.Position;
import chess.pieces.Pawn;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chess.Player.White;
import static java.util.Optional.ofNullable;

public class Moves {
    private static Set<Position> toSet(Position... positions) {
        Set<Position> retval = new HashSet<>();
        Collections.addAll(retval, positions);
        return retval;
    }

    private static Set<MovePiece> toSet(PiecePosition current, Position... newPositions) {
        return Stream.of(newPositions).map(p -> new MovePiece(current, p)).collect(Collectors.toSet());
    }

    public interface GameActionSupplier<T extends GameAction> extends BiFunction<PiecePosition, GameState, Stream<T>> {
    }

    public interface MovePieceActionSupplier extends GameActionSupplier<MovePiece> {
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

    public static MoveFunction ONE_CELL_FWD_OLD = (piece, curPos, gameState) -> {
        return ofNullable(piece instanceof Pawn ? piece : null)
            .flatMap(p -> p.getOwner() == White ? curPos.up(1) : curPos.down(1))
            .filter(gameState::isFreeAt)
            .map(Moves::toSet);
    };

    public static MoveFunction TWO_CELL_FWD_OLD = (piece, curPos, gameState) -> {
        return ofNullable(piece instanceof Pawn ? piece : null)
            .flatMap(p -> p.getOwner() == White ? curPos.up(2) : curPos.down(2))
            .filter(gameState::isFreeAt)
            .map(Moves::toSet);
    };

    public static MoveFunction PAWN_ACTIONS_OLD = (piece, curPos, gameState) -> {
        Stream<MoveFunction> moves = piece.getOwner().isInitialForPawn(curPos)
            ? Stream.of(ONE_CELL_FWD_OLD, TWO_CELL_FWD_OLD)
            : Stream.of(ONE_CELL_FWD_OLD);

        Set<Position> set = moves.map(f -> f.availableMoves(piece, curPos, gameState))
            .filter(Optional::isPresent).map(Optional::get)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        return (set.size() > 0) ? Optional.of(set) : Optional.empty();
    };
}
