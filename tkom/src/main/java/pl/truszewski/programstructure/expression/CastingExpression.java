package pl.truszewski.programstructure.expression;

import pl.truszewski.programstructure.basic.ValueType;
import pl.truszewski.visitor.Visitor;

public record CastingExpression(Expression castingExpression, ValueType targetType) implements Expression {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
