package chess.actions;

import chess.PiecePosition;
import chess.Position;

public abstract class GameAction {

    private final PiecePosition piecePosition;
    private final Position target;

    public GameAction(PiecePosition piecePosition, Position target) {
        this.piecePosition = piecePosition;
        this.target = target;
    }

    public PiecePosition getPiecePosition() {
        return piecePosition;
    }

    public Position getTarget() {
        return target;
    }
}
