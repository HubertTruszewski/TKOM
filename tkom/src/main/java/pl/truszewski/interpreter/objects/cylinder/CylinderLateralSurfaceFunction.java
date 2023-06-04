package pl.truszewski.interpreter.objects.cylinder;

import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.visitor.Visitor;

public class CylinderLateralSurfaceFunction implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
