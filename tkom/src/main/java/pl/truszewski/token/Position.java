package pl.truszewski.token;

public class Position {
    private int row;
    private int column;

    public Position() {
        this.row = 1;
        this.column = 0;
    }

    private Position(int row, int column) {
        this.row = row;
        this.column = column;
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

    public Position clone() {
        return new Position(this.row, this.column);
    }

    @Override
    public String toString() {
        return "Position: row: " + this.row + ", column: " + this.column;
    }
}
