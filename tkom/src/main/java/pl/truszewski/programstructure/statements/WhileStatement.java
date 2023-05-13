package pl.truszewski.programstructure.statements;

import pl.truszewski.programstructure.basic.Block;
import pl.truszewski.programstructure.expression.Expression;
import pl.truszewski.visitor.Visitor;

public record WhileStatement(Expression expression, Block codeBlock) implements ConditionalStatement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
