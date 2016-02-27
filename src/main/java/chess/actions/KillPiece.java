package chess.actions;

import chess.PiecePosition;
import chess.Position;

public class KillPiece extends GameAction {
    public KillPiece(PiecePosition piece, Position target) {
        super(piece, target);
    }
}
