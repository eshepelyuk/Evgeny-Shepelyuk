package chess.actions;

import chess.PiecePosition;
import chess.Position;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Iterates via available {@link Position} in {@link Direction} within board bounds.
 * Iteration is stopped when {@link #stopPredicate} evaluates to false.
 */
class DirectionIterator implements Iterator<Position> {

    private final Direction direction;
    private final Predicate<Position> stopPredicate;

    private Optional<Position> currentPosition;

    public DirectionIterator(Position initialPosition, Direction direction, Optional<Predicate<Position>> stopPredicate) {
        this.direction = direction;
        this.currentPosition = Optional.of(initialPosition);
        this.stopPredicate = stopPredicate.orElse(arg -> true);
    }

    @Override
    public boolean hasNext() {
        this.currentPosition = currentPosition.flatMap(direction::apply).filter(stopPredicate);
        return this.currentPosition.isPresent();
    }

    @Override
    public Position next() {
        return currentPosition.orElseThrow(NoSuchElementException::new);
    }
}

public interface Direction extends Function<Position, Optional<Position>> {

    Direction DOWN_RIGHT = position -> position.down(1).flatMap(p -> p.right(1));
    Direction DOWN_LEFT = position -> position.down(1).flatMap(p -> p.left(1));
    Direction UP_LEFT = position -> position.up(1).flatMap(p -> p.left(1));
    Direction UP_RIGHT = position -> position.up(1).flatMap(p -> p.right(1));

    Direction LEFT = position -> position.left(1);
    Direction RIGHT = position -> position.right(1);
    Direction UP = position -> position.up(1);
    Direction DOWN = position -> position.down(1);

    /**
     * Creates finite {@link Stream} using {@link DirectionIterator} as a source.
     *
     * @param position
     * @param stopPredicate
     * @return stream
     */
    default Stream<Position> streamFrom(PiecePosition position, Optional<Predicate<Position>> stopPredicate) {
        return StreamSupport.stream(((Iterable<Position>) () -> new DirectionIterator(position.getPosition(), this, stopPredicate)).spliterator(), false);
    }
}
