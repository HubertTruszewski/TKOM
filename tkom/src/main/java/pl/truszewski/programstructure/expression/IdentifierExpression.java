package pl.truszewski.programstructure.expression;

import pl.truszewski.visitor.Visitor;

public record IdentifierExpression(String identifierName) implements Expression {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
