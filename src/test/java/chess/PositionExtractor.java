package chess;

import chess.actions.GameAction;
import chess.actions.GameActionSupplier;
import chess.actions.KillPiece;
import chess.actions.MovePiece;

import java.util.Set;
import java.util.stream.Collectors;

public class PositionExtractor {
    private GameState gameState;

    public PositionExtractor(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Set<Position> extractMoves(GameActionSupplier supplier, PiecePosition piecePosition) {
        return supplier.apply(piecePosition, gameState)
            .filter(action -> action instanceof MovePiece)
            .map(GameAction::getTarget)
            .collect(Collectors.toSet());
    }

    public Set<Position> extractKills(GameActionSupplier supplier, PiecePosition piecePosition) {
        return supplier.apply(piecePosition, gameState)
            .filter(action -> action instanceof KillPiece)
            .map(GameAction::getTarget)
            .collect(Collectors.toSet());
    }

}
