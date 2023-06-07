package pl.truszewski.interpreter.objects;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class FunctionCallContext {
    private final String functionName;
    private final Deque<Scope> scopes = new ArrayDeque<>(List.of(new Scope()));

    public FunctionCallContext(final String functionName) {
        this.functionName = functionName;
    }

    public void addNewScope() {
        scopes.add(new Scope());
    }

    public void removeScope() {
        scopes.removeLast();
    }

    public void addVariable(Variable variable) {
        scopes.getLast().addVariable(variable);
    }

    public boolean updateVariable(String name, Value value) {
        Iterator<Scope> iterator = scopes.descendingIterator();
        while (iterator.hasNext()) {
            Scope scope = iterator.next();
            if (scope.updateVariable(name, value)) {
                return true;
            }
        }
        return false;
    }

    public Optional<Variable> findVariable(String name) {
        Iterator<Scope> iterator = scopes.descendingIterator();
        while (iterator.hasNext()) {
            Scope scope = iterator.next();
            Optional<Variable> variable = scope.getVariable(name);
            if (variable.isPresent()) {
                return variable;
            }
        }
        return Optional.empty();
    }

    public String getFunctionName() {
        return functionName;
    }
}
