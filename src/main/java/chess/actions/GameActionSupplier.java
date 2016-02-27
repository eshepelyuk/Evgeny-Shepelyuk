package chess.actions;

import chess.GameState;

import java.util.function.BiFunction;
import java.util.stream.Stream;

public interface GameActionSupplier extends BiFunction<PiecePosition, GameState, Stream<? extends GameAction>> {
}
