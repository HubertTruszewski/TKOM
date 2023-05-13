package pl.truszewski.programstructure.expression;

public interface BinaryExpression extends Expression {
    Expression left();
    Expression right();
}
