package chess.moves;

import chess.Player;
import chess.Position;
import chess.pieces.Pawn;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Moves {
    private static Set<Position> toSet(Position... positions) {
        Set<Position> retval = new HashSet<>();
        Collections.addAll(retval, positions);
        return retval;
    }

    public static MoveFunction PAWN_ONE_STEP = (piece, curPos, gameState) -> {
        if (piece instanceof Pawn) {
            Optional<Position> newPos = null;
            if (piece.getOwner() == Player.White) {
                newPos = curPos.up(1);
            } else {
                newPos = curPos.down(1);
            }
            return newPos.map(Moves::toSet);
        }
        return Optional.empty();
    };
}
