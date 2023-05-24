package pl.truszewski.programstructure.expression;

import pl.truszewski.visitor.Visitor;

public record GreaterExpression(Expression left, Expression right) implements BinaryExpression  {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
