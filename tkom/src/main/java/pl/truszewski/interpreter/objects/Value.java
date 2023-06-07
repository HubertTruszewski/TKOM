package pl.truszewski.interpreter.objects;

import pl.truszewski.programstructure.basic.ValueType;

public record Value(ValueType type, Object value) {
}
