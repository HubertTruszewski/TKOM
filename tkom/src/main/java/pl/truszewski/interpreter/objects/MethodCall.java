package pl.truszewski.interpreter.objects;

import java.util.List;

import pl.truszewski.programstructure.expression.Expression;

public record MethodCall(CustomObject object, String name, List<Expression> arguments) {
}
