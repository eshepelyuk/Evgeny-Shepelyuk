package chess.actions;

import chess.GameState;
import chess.PiecePosition;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.Optional.of;
import static java.util.stream.Stream.empty;

public interface GameActionSupplier extends BiFunction<PiecePosition, GameState, Stream<? extends GameAction>> {
    static GameActionSupplier filterSuppliers(Predicate<PiecePosition> predicate, GameActionSupplier... suppliers) {
        return (current, gameState) -> Optional.of(current).filter(predicate)
            .map(p -> Stream.of(suppliers).flatMap(f -> f.apply(p, gameState)))
            .orElse(empty());
    }

    static GameActionSupplier concatSuppliers(GameActionSupplier... suppliers) {
        return (current, gameState) -> Optional.of(current)
            .map(p -> Stream.of(suppliers).flatMap(f -> f.apply(p, gameState)))
            .orElse(empty());
    }

    static GameActionSupplier createMovePieceSupplier(Direction direction) {
        return (PiecePosition pp, GameState gs) -> direction.streamFrom(pp, of(gs::isFreeAt)).map(p -> new MovePiece(pp, p));
    }


}
