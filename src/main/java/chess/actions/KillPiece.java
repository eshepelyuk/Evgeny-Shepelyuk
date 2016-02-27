package chess.actions;

import chess.Position;

public class KillPiece extends GameAction {
    public KillPiece(PiecePosition piece, Position target) {
        super(piece, target);
    }
}
