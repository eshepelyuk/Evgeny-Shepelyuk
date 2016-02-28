package chess.actions;

import chess.GameState;
import chess.PiecePosition;
import chess.Position;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class MovePieceIterator implements Iterator<Position> {

    private final GameState gameState;
    private final Function<Position, Optional<Position>> movement;

    private Position currentPosition;

    public MovePieceIterator(GameState gameState, Position initialPosition, Function<Position, Optional<Position>> movement) {
        this.gameState = gameState;
        this.movement = movement;
        this.currentPosition = initialPosition;
    }

    @Override
    public boolean hasNext() {
        return movement.apply(currentPosition).filter(gameState::isFreeAt).isPresent();
    }

    @Override
    public Position next() {
        this.currentPosition = movement.apply(currentPosition).filter(gameState::isFreeAt).orElseThrow(NoSuchElementException::new);
        return currentPosition;
    }

    public static GameActionSupplier createMovePieceActionSupplier(Direction direction) {
        return (PiecePosition pp, GameState gs) -> StreamSupport
            .stream(((Iterable<Position>) () -> new MovePieceIterator(gs, pp.getPosition(), direction)).spliterator(), false)
            .map(p -> new MovePiece(pp, p));
    }


}
