package pl.truszewski.programstructure.basic;

import java.util.List;

import pl.truszewski.visitor.Visitable;
import pl.truszewski.visitor.Visitor;

public record FunctionDefinition(ValueType returnValueType, String name, List<Parameter> parameters, Block block)
        implements Visitable {
    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }
}
