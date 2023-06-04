package pl.truszewski.interpreter.objects.iterator;

import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.visitor.Visitor;

public class IteratorHasNextFunction implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
