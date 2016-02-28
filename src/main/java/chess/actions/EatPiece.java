package chess.actions;

import chess.PiecePosition;
import chess.Position;

public class EatPiece extends GameAction {
    public EatPiece(PiecePosition piece, Position target) {
        super(piece, target);
    }
}
