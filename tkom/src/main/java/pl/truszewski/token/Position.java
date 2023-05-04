package pl.truszewski.token;

public class Position {
    private int row;
    private int column;

    public Position() {
        this.row = 1;
        this.column = 0;
    }

    public Position(Position position) {
        this.column = position.column;
        this.row = position.row;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void nextColumn() {
        ++this.column;
    }

    public void nextRow() {
        ++this.row;
        this.column = 0;
    }

    @Override
    public String toString() {
        return "Position: row: " + this.row + ", column: " + this.column;
    }
}
