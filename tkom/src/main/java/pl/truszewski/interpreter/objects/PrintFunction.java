package pl.truszewski.interpreter.objects;

import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.visitor.Visitor;

public class PrintFunction implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
