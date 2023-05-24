package pl.truszewski.programstructure.expression;

import pl.truszewski.visitor.Visitor;

public record LessEqualExpression(Expression left, Expression right) implements BinaryExpression  {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
