package pl.truszewski.programstructure.expression;

import java.util.List;

import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.visitor.Visitor;

public record FunctionCall(String functionName, List<Expression> arguments) implements Statement, Expression {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
