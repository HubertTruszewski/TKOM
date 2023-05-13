package pl.truszewski.programstructure.basic;

import java.util.List;

import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.visitor.Visitable;
import pl.truszewski.visitor.Visitor;

public record Block(List<Statement> statements) implements Visitable {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
