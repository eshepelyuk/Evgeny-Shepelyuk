package chess.actions;

import chess.Position;
import chess.pieces.Pawn;
import chess.pieces.Rook;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chess.Player.White;
import static java.util.Optional.ofNullable;

public class Moves {
    private static Set<Position> toSet(Position... positions) {
        Set<Position> retval = new HashSet<>();
        Collections.addAll(retval, positions);
        return retval;
    }

    public static MoveFunction ONE_CELL_FWD = (piece, curPos, gameState) -> {
        return ofNullable(piece instanceof Pawn ? piece : null)
            .flatMap(p -> p.getOwner() == White ? curPos.up(1) : curPos.down(1))
            .filter(gameState::isFreeAt)
            .map(Moves::toSet);
    };

    public static MoveFunction TWO_CELL_FWD = (piece, curPos, gameState) -> {
        return ofNullable(piece instanceof Pawn ? piece : null)
            .flatMap(p -> p.getOwner() == White ? curPos.up(2) : curPos.down(2))
            .filter(gameState::isFreeAt)
            .map(Moves::toSet);
    };

    public static MoveFunction MOVE_UP = (piece, curPos, gameState) -> {
        return ofNullable(piece instanceof Rook ? piece : null)
            .flatMap(p -> p.getOwner() == White ? curPos.up(2) : curPos.down(2))
            .filter(gameState::isFreeAt)
            .map(Moves::toSet);
    };

    public static MoveFunction PAWN_ACTIONS = (piece, curPos, gameState) -> {
        Stream<MoveFunction> moves = piece.getOwner().isInitialForPawn(curPos)
            ? Stream.of(ONE_CELL_FWD, TWO_CELL_FWD)
            : Stream.of(ONE_CELL_FWD);

        Set<Position> set = moves.map(f -> f.availableMoves(piece, curPos, gameState))
            .filter(Optional::isPresent).map(Optional::get)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        return (set.size() > 0) ? Optional.of(set) : Optional.empty();
    };
}
