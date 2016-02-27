package chess.actions;

import chess.GameState;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface GameActionSupplier<T extends GameAction> extends BiFunction<PiecePosition, GameState, Stream<T>> {
}
