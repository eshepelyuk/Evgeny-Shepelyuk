package chess;

/**
 * Which side of the board is being played
 */
public enum Player {
    White {
        @Override
        public boolean isInitialForPawn(Position pos) {
            return pos.getRow() == 2;
        }
    }, Black {
        @Override
        public boolean isInitialForPawn(Position pos) {
            return pos.getRow() == 7;
        }
    };

    abstract public boolean isInitialForPawn(Position pos);
}
