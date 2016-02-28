package chess.actions;

import chess.GameState;
import chess.PiecePosition;
import chess.Position;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

public class KillPieceIterator implements Iterator<Position> {

    private final GameState gameState;
    private final Direction direction;

    private Position currentPosition;

    public KillPieceIterator(Position initialPosition, GameState gameState, Direction direction) {
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

    public static GameActionSupplier createKillPieceActionSupplier(Direction direction) {
        return (PiecePosition pp, GameState gs) -> StreamSupport
            .stream(((Iterable<Position>) () -> new KillPieceIterator(pp.getPosition(), gs, direction)).spliterator(), false)
            .map(p -> new KillPiece(pp, p));
    }

}
