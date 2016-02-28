package chess.actions;

import chess.GameState;
import chess.PiecePosition;
import chess.Position;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

public class MovePieceIterator implements Iterator<Position> {

    private final GameState gameState;
    private final Direction direction;

    private Position currentPosition;

    public MovePieceIterator(Position initialPosition, GameState gameState, Direction direction) {
        this.gameState = gameState;
        this.direction = direction;
        this.currentPosition = initialPosition;
    }

    @Override
    public boolean hasNext() {
        return direction.apply(currentPosition).filter(gameState::isFreeAt).isPresent();
    }

    @Override
    public Position next() {
        this.currentPosition = direction.apply(currentPosition).filter(gameState::isFreeAt).orElseThrow(NoSuchElementException::new);
        return currentPosition;
    }

    public static GameActionSupplier createMovePieceSupplier(Direction direction) {
        return (PiecePosition pp, GameState gs) -> StreamSupport
            .stream(((Iterable<Position>) () -> new MovePieceIterator(pp.getPosition(), gs, direction)).spliterator(), false)
            .map(p -> new MovePiece(pp, p));
    }

    public static GameActionSupplier createLimitedMovePieceSupplier(Direction direction, long limit) {
        return (PiecePosition pp, GameState gs) -> StreamSupport
            .stream(((Iterable<Position>) () -> new MovePieceIterator(pp.getPosition(), gs, direction)).spliterator(), false)
            .limit(limit)
            .map(p -> new MovePiece(pp, p));
    }

}
