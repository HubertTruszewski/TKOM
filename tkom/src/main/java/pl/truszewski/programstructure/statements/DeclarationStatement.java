package pl.truszewski.programstructure.statements;

import pl.truszewski.programstructure.basic.ValueType;
import pl.truszewski.programstructure.expression.Expression;
import pl.truszewski.visitor.Visitor;

public record DeclarationStatement(ValueType valueType, String name, Expression expression) implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
