package pl.truszewski.interpreter.objects.cuboid;

import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.visitor.Visitor;

public class CuboidLateralSurfaceFunction implements Statement {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
