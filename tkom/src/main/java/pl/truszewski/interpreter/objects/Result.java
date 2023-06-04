package pl.truszewski.interpreter.objects;

public record Result(boolean returned, boolean present, Value value) {
    public static Result empty() {
        return new Result(false, false, null);
    }
}
