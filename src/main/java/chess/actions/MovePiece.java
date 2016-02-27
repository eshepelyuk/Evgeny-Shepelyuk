package chess.actions;

import chess.Position;

public class MovePiece extends GameAction {
    public MovePiece(PiecePosition piecePosition, Position target) {
        super(piecePosition, target);
    }
}
