package pl.truszewski.visitor;

import pl.truszewski.programstructure.basic.Block;
import pl.truszewski.programstructure.basic.FunctionDefinition;
import pl.truszewski.programstructure.basic.Parameter;
import pl.truszewski.programstructure.basic.Program;
import pl.truszewski.programstructure.expression.AccessExpression;
import pl.truszewski.programstructure.expression.AddExpression;
import pl.truszewski.programstructure.expression.AndExpression;
import pl.truszewski.programstructure.expression.CastingExpression;
import pl.truszewski.programstructure.expression.DivideExpression;
import pl.truszewski.programstructure.expression.DoubleNumber;
import pl.truszewski.programstructure.expression.EqualityExpression;
import pl.truszewski.programstructure.expression.FunctionCall;
import pl.truszewski.programstructure.expression.GreaterEqualExpression;
import pl.truszewski.programstructure.expression.GreaterExpression;
import pl.truszewski.programstructure.expression.IdentifierExpression;
import pl.truszewski.programstructure.expression.InequalityExpression;
import pl.truszewski.programstructure.expression.IntNumber;
import pl.truszewski.programstructure.expression.LessEqualExpression;
import pl.truszewski.programstructure.expression.LessExpression;
import pl.truszewski.programstructure.expression.MultiplyExpression;
import pl.truszewski.programstructure.expression.NegationExpression;
import pl.truszewski.programstructure.expression.OrExpression;
import pl.truszewski.programstructure.expression.StringExpression;
import pl.truszewski.programstructure.expression.SubtractExpression;
import pl.truszewski.programstructure.statements.AssignmentStatement;
import pl.truszewski.programstructure.statements.DeclarationStatement;
import pl.truszewski.programstructure.statements.IfStatement;
import pl.truszewski.programstructure.statements.ReturnStatement;
import pl.truszewski.programstructure.statements.WhileStatement;

public interface Visitor {
    void visit(Program program);

    void visit(Block block);

    void visit(FunctionDefinition functionDefinition);

    void visit(Parameter parameter);

    void visit(AccessExpression accessExpression);

    void visit(AddExpression addExpression);

    void visit(AndExpression andExpression);

    void visit(CastingExpression castingExpression);

    void visit(DivideExpression divideExpression);

    void visit(DoubleNumber doubleNumber);

    void visit(EqualityExpression equalityExpression);

    void visit(FunctionCall functionCall);

    void visit(GreaterEqualExpression greaterEqualExpression);

    void visit(GreaterExpression greaterExpression);

    void visit(IdentifierExpression identifierExpression);

    void visit(InequalityExpression inequalityExpression);

    void visit(IntNumber intNumber);

    void visit(LessEqualExpression lessEqualExpression);

    void visit(LessExpression lessExpression);

    void visit(MultiplyExpression multiplyExpression);

    void visit(NegationExpression negationExpression);

    void visit(OrExpression orExpression);

    void visit(StringExpression stringExpression);

    void visit(SubtractExpression subtractExpression);

    void visit(AssignmentStatement assignmentStatement);

    void visit(DeclarationStatement declarationStatement);

    void visit(IfStatement ifStatement);

    void visit(ReturnStatement returnStatement);

    void visit(WhileStatement whileStatement);
}
