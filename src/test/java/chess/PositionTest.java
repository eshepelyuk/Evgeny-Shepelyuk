package chess;

import org.junit.Test;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Basic Unit Tests for the Position class
 */
public class PositionTest {

    @Test
    public void testStringParsingConstructor() {
        Position pos = new Position("d5");

        assertEquals("The column should be 'd'", 'd', pos.getColumn());
        assertEquals("The row should be 5", 5, pos.getRow());
    }

    @Test
    public void testPositionEquality() {
        Position one = new Position('a', 1);
        Position other = new Position('a', 1);

        assertEquals("The positions should equal each other", one, other);
    }

    @Test
    public void shouldMoveUp() throws Exception {
        Position posOrig = new Position("a6");

        //move up - out of board
        Optional<Position> posNew = posOrig.up(2);

        assertThat(posNew.isPresent(), is(true));
        assertThat(posNew.get().getColumn(), is(posOrig.getColumn()));
        assertThat(posNew.get().getRow(), is(posOrig.getRow() + 2));

        // move up - out of board
        posNew = posNew.get().up(1);
        assertThat(posNew.isPresent(), is(false));
    }

    @Test
    public void shouldMoveDown() throws Exception {
        Position posOrig = new Position("a3");

        //move up - within board
        Optional<Position> posNew = posOrig.down(2);

        assertThat(posNew.isPresent(), is(true));
        assertThat(posNew.get().getColumn(), is(posOrig.getColumn()));
        assertThat(posNew.get().getRow(), is(posOrig.getRow() - 2));

        // move up - out of board
        posNew = posNew.get().down(1);
        assertThat(posNew.isPresent(), is(false));
    }

    @Test
    public void shouldMoveLeft() {
        Position posOrig = new Position("c3");

        //move left - within board
        Optional<Position> posNew = posOrig.left(2);

        assertThat(posNew.isPresent(), is(true));
        assertThat(posNew.get().getColumn(), is('a'));
        assertThat(posNew.get().getRow(), is(posOrig.getRow()));

        // move left - out of board
        posNew = posNew.get().left(1);
        assertThat(posNew.isPresent(), is(false));
    }

    @Test
    public void shouldMoveRight() {
        Position posOrig = new Position("f3");

        //move left - within board
        Optional<Position> posNew = posOrig.right(2);

        assertThat(posNew.isPresent(), is(true));
        assertThat(posNew.get().getColumn(), is('h'));
        assertThat(posNew.get().getRow(), is(posOrig.getRow()));

        // move left - out of board
        posNew = posNew.get().right(1);
        assertThat(posNew.isPresent(), is(false));
    }
}
