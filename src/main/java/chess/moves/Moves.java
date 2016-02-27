package chess.moves;

import chess.Position;
import chess.pieces.Pawn;

import java.util.*;
import java.util.stream.Collectors;

import static chess.Player.White;
import static java.util.Optional.ofNullable;

public class Moves {
    private static Set<Position> toSet(Position... positions) {
        Set<Position> retval = new HashSet<>();
        Collections.addAll(retval, positions);
        return retval;
    }

    public static MoveFunction PAWN_ONE_STEP = (piece, curPos, gameState) -> {
        return ofNullable(piece instanceof Pawn ? piece : null)
            .flatMap(p -> p.getOwner() == White ? curPos.up(1) : curPos.down(1))
            .filter(gameState::isFreeAt)
            .map(Moves::toSet);
    };

    public static MoveFunction PAWN_TWO_STEP = (piece, curPos, gameState) -> {
        return ofNullable(piece instanceof Pawn ? piece : null)
            .flatMap(p -> p.getOwner() == White ? curPos.up(2) : curPos.down(2))
            .filter(gameState::isFreeAt)
            .map(Moves::toSet);
    };

    public static MoveFunction PAWN_MOVES = (piece, curPos, gameState) -> {
        Set<Position> set = Arrays.asList(PAWN_ONE_STEP, PAWN_TWO_STEP).stream()
            .map(f -> f.availableMoves(piece, curPos, gameState))
            .filter(Optional::isPresent).map(Optional::get)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        return (set.size() > 0) ? Optional.of(set) : Optional.empty();
    };
}
