package pl.truszewski.interpreter.objects;

import java.util.Map;
import java.util.Optional;

import pl.truszewski.programstructure.basic.FunctionDefinition;
import pl.truszewski.programstructure.basic.ValueType;

public class CustomObject {
    private final ValueType valueType;
    private final Map<String, Variable> fields;
    private final Map<String, FunctionDefinition> methods;

    public CustomObject(final ValueType valueType, final Map<String, Variable> fields,
            final Map<String, FunctionDefinition> methods) {
        this.valueType = valueType;
        this.fields = fields;
        this.methods = methods;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public Optional<Variable> findVariable(String name) {
        return Optional.ofNullable(fields.get(name));
    }

    public Optional<FunctionDefinition> findMethodDefinition(String name) {
        return Optional.ofNullable(methods.get(name));
    }

    public void updateVariable(Variable variable) {
        fields.replace(variable.name(), variable);
    }
}
