package chess.actions;

import chess.PiecePosition;
import chess.Position;

public class MovePiece extends GameAction {
    public MovePiece(PiecePosition piecePosition, Position target) {
        super(piecePosition, target);
    }
}
