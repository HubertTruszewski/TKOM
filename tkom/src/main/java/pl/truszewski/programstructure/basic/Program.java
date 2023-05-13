package pl.truszewski.programstructure.basic;

import java.util.Map;

import pl.truszewski.visitor.Visitable;
import pl.truszewski.visitor.Visitor;

public record Program(Map<String, FunctionDefinition> functions) implements Visitable {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
