package pl.truszewski.interpreter.objects.cone;

import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.visitor.Visitor;

public class ConeBaseSurfaceFunction implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}