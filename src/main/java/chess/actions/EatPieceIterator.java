package chess.actions;

import chess.GameState;
import chess.PiecePosition;
import chess.Position;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

public class EatPieceIterator implements Iterator<Position> {

    private final Direction direction;
    private Position currentPosition;

    public EatPieceIterator(Position initialPosition, Direction direction) {
        this.direction = direction;
        this.currentPosition = initialPosition;
    }

    @Override
    public boolean hasNext() {
        return direction.apply(currentPosition).isPresent();
    }

    @Override
    public Position next() {
        this.currentPosition = direction.apply(currentPosition).orElseThrow(NoSuchElementException::new);
        return currentPosition;
    }

    public static GameActionSupplier createEatPieceSupplier(Direction direction) {
        return (PiecePosition pp, GameState gs) -> StreamSupport
            .stream(((Iterable<Position>) () -> new EatPieceIterator(pp.getPosition(), direction)).spliterator(), false)
            .filter(p -> !gs.isFreeAt(p))
            .limit(1)
            .map(p -> new EatPiece(pp, p));
    }

}
