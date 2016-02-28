package chess;


import chess.actions.*;
import chess.pieces.*;

import java.util.AbstractMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

/**
 * Class that represents the current state of the game.  Basically, what pieces are in which positions on the
 * board.
 */
public class GameState {

    /**
     * The current player
     */
    private Player currentPlayer = Player.White;

    /**
     * A map of board positions to pieces at that position
     */
    private Map<Position, Piece> positionToPieceMap;

    /**
     * Create the game state.
     */
    public GameState() {
        positionToPieceMap = new LinkedHashMap<>();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Call to initialize the game state into the starting positions
     */
    public void reset() {
        // White Pieces
        placePiece(new Rook(Player.White), new Position("a1"));
        placePiece(new Knight(Player.White), new Position("b1"));
        placePiece(new Bishop(Player.White), new Position("c1"));
        placePiece(new Queen(Player.White), new Position("d1"));
        placePiece(new King(Player.White), new Position("e1"));
        placePiece(new Bishop(Player.White), new Position("f1"));
        placePiece(new Knight(Player.White), new Position("g1"));
        placePiece(new Rook(Player.White), new Position("h1"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("a2"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("b2"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("c2"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("d2"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("e2"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("f2"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("g2"));
        placePiece(new chess.pieces.Pawn(Player.White), new Position("h2"));

        // Black Pieces
        placePiece(new Rook(Player.Black), new Position("a8"));
        placePiece(new Knight(Player.Black), new Position("b8"));
        placePiece(new Bishop(Player.Black), new Position("c8"));
        placePiece(new Queen(Player.Black), new Position("d8"));
        placePiece(new King(Player.Black), new Position("e8"));
        placePiece(new Bishop(Player.Black), new Position("f8"));
        placePiece(new Knight(Player.Black), new Position("g8"));
        placePiece(new Rook(Player.Black), new Position("h8"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("a7"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("b7"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("c7"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("d7"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("e7"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("f7"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("g7"));
        placePiece(new chess.pieces.Pawn(Player.Black), new Position("h7"));

        //current player
        this.currentPlayer = Player.White;
    }

    /**
     * Get the piece at the position specified by the String
     * @param colrow The string indication of position; i.e. "d5"
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(String colrow) {
        Position position = new Position(colrow);
        return getPieceAt(position);
    }

    /**
     * Get the piece at a given position on the board
     * @param position The position to inquire about.
     * @return The piece at that position, or null if it does not exist.
     */
    public Piece getPieceAt(Position position) {
        return positionToPieceMap.get(position);
    }

    /**
     * Method to place a piece at a given position
     * @param piece The piece to place
     * @param position The position
     */
    public void placePiece(Piece piece, Position position) {
        positionToPieceMap.put(position, piece);
    }

    public <T extends GameAction> void applyAction(T action) {
        if (action instanceof MovePiece) {
            MovePiece movePiece = (MovePiece) action;
            ofNullable(positionToPieceMap.remove(movePiece.getPiecePosition().getPosition())).ifPresent(piece -> placePiece(piece, movePiece.getTarget()));
            switchPlayer();
        } else if (action instanceof EatPiece) {
            EatPiece eatPiece = (EatPiece) action;
            positionToPieceMap.remove(eatPiece.getTarget());
            applyAction(new MovePiece(eatPiece.getPiecePosition(), eatPiece.getTarget()));
        }
    }

    void switchPlayer() {
        this.currentPlayer = this.currentPlayer == Player.White ? Player.Black : Player.White;
    }

    Set<PiecePosition> getCurrentPlayerPieces() {
        return positionToPieceMap.entrySet().stream()
            .filter(e -> e.getValue().getOwner() == this.currentPlayer)
            .map(e -> new PiecePosition(e.getValue(), e.getKey()))
            .collect(Collectors.toSet());
    }

    public boolean isFreeAt(Position position) {
        return !positionToPieceMap.containsKey(position);
    }

    protected static GameActionSupplier PIECES_ACTIONS = GameActionSupplier.concatSuppliers(
        PawnActions.PAWN_ACTIONS,
        QueenSpec.QUEEN_ACTIONS);

    public Map<Position, Set<? extends GameAction>> availableMoves() {
        return getCurrentPlayerPieces().stream()
            .map(pp -> new AbstractMap.SimpleEntry<>(pp.getPosition(), PIECES_ACTIONS.apply(pp, this).collect(Collectors.toSet())))
            .filter(e -> e.getValue().size() > 0)
            .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
    }

    public Set<Position> extractPositions(GameActionSupplier supplier, PiecePosition piecePosition) {
        return supplier.apply(piecePosition, this).map(GameAction::getTarget).collect(Collectors.toSet());
    }

}
