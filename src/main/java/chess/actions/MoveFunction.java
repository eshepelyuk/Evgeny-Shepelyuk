package chess.actions;

import chess.GameState;
import chess.Position;
import chess.pieces.Piece;

import java.util.Optional;
import java.util.Set;

@FunctionalInterface
public interface MoveFunction {
    Optional<Set<Position>> availableMoves(Piece piece, Position currentPosition, GameState gameState);
}
