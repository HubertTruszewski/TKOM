package pl.truszewski.programstructure.statements;

import pl.truszewski.programstructure.expression.Expression;
import pl.truszewski.visitor.Visitor;

public record ReturnStatement(Expression returnValue) implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
