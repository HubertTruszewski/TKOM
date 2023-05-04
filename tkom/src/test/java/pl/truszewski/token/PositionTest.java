package pl.truszewski.token;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PositionTest {

    @Test
    public void testGetColumn() {
        Position position = new Position();
        assertEquals(position.getColumn(), 0);
    }

    @Test
    public void testGetRow() {
        Position position = new Position();
        assertEquals(position.getRow(), 1);

    }

    @Test
    public void testNextColumn() {
        Position position = new Position();
        assertEquals(position.getColumn(), 0);
        assertEquals(position.getRow(), 1);
        position.nextColumn();
        assertEquals(position.getColumn(), 1);
        assertEquals(position.getRow(), 1);

    }

    @Test
    public void testNextRow() {
        Position position = new Position();
        assertEquals(position.getColumn(), 0);
        assertEquals(position.getRow(), 1);
        position.nextRow();
        assertEquals(position.getColumn(), 0);
        assertEquals(position.getRow(), 2);
    }

    @Test
    public void testToString() {
        Position position = new Position();
        assertEquals(position.toString(), "Position: row: 1, column: 0");

    }

}
