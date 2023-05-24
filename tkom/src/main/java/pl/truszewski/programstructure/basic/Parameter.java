package pl.truszewski.programstructure.basic;

import pl.truszewski.visitor.Visitable;
import pl.truszewski.visitor.Visitor;

public record Parameter(ValueType type, String name) implements Visitable {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
