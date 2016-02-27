package chess.actions;

import chess.Player;
import chess.Position;
import chess.pieces.Piece;

import java.util.Objects;

public class PiecePosition {
    private final Piece piece;
    private final Position position;

    public PiecePosition(Piece piece, Position position) {
        this.piece = piece;
        this.position = position;
    }

    public PiecePosition(Piece piece, String position) {
        this.piece = piece;
        this.position = new Position(position);
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getPosition() {
        return position;
    }

    public boolean isWhite() {
        return getPiece().getOwner() == Player.White;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PiecePosition that = (PiecePosition) o;
        return Objects.equals(piece, that.piece) &&
            Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, position);
    }
}
