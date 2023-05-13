package pl.truszewski.programstructure.statements;

import pl.truszewski.programstructure.expression.Expression;
import pl.truszewski.visitor.Visitor;

public record AssignmentStatement(String name, Expression expression) implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
