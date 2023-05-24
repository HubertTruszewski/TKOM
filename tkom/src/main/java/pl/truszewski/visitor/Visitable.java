package pl.truszewski.visitor;

public interface Visitable {
    void accept(Visitor visitor);
}
