package pl.truszewski.interpreter.objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pl.truszewski.error.interpreter.BadValueTypeError;
import pl.truszewski.error.interpreter.DuplicatedVariableNameError;

public class Scope {
    private final Map<String, Variable> variables = new HashMap<>();

    public Optional<Variable> getVariable(String name) {
        return Optional.ofNullable(variables.get(name));
    }

    public void addVariable(Variable variable) {
        if (variables.containsKey(variable.name())) {
            throw new DuplicatedVariableNameError("Duplicated variable name: " + variable.name());
        }
        variables.put(variable.name(), variable);
    }

    public boolean updateVariable(String name, Value value) {
        if (!variables.containsKey(name)) {
            return false;
        }
        Variable previousVariable = variables.get(name);
        if (previousVariable.value().type() != value.type()) {
            throw new BadValueTypeError("Bad value type for variable: " + name);
        }
        variables.put(name, new Variable(name, value));
        return true;
    }
}
