package pl.truszewski.programstructure.expression;

import pl.truszewski.visitor.Visitor;

public record AccessExpression(Expression left, Expression right) implements Expression {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
