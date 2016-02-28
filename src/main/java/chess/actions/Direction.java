package chess.actions;

import chess.Position;

import java.util.Optional;
import java.util.function.Function;

public interface Direction extends Function<Position, Optional<Position>> {
    public static Direction DOWN_RIGHT = position -> position.down(1).flatMap(p -> p.right(1));
    public static Direction DOWN_LEFT = position -> position.down(1).flatMap(p -> p.left(1));
    public static Direction UP_LEFT = position -> position.up(1).flatMap(p -> p.left(1));
    public static Direction UP_RIGHT = position -> position.up(1).flatMap(p -> p.right(1));

    public static Direction LEFT = position -> position.left(1);
    public static Direction RIGHT = position -> position.right(1);
    public static Direction UP = position -> position.up(1);
    public static Direction DOWN = position -> position.down(1);
}
