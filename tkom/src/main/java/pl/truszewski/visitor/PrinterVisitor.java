package pl.truszewski.visitor;

import pl.truszewski.interpreter.objects.PrintFunction;
import pl.truszewski.interpreter.objects.ShowFiguresFunction;
import pl.truszewski.interpreter.objects.cone.ConeBaseSurfaceFunction;
import pl.truszewski.interpreter.objects.cone.ConeFunction;
import pl.truszewski.interpreter.objects.cone.ConeLateralSurfaceFunction;
import pl.truszewski.interpreter.objects.cone.ConeTotalSurfaceFunction;
import pl.truszewski.interpreter.objects.cone.ConeVolumeFunction;
import pl.truszewski.interpreter.objects.cuboid.CuboidBaseSurfaceFunction;
import pl.truszewski.interpreter.objects.cuboid.CuboidFunction;
import pl.truszewski.interpreter.objects.cuboid.CuboidLateralSurfaceFunction;
import pl.truszewski.interpreter.objects.cuboid.CuboidTotalSurfaceFunction;
import pl.truszewski.interpreter.objects.cuboid.CuboidVolumeFunction;
import pl.truszewski.interpreter.objects.cylinder.CylinderBaseSurfaceFunction;
import pl.truszewski.interpreter.objects.cylinder.CylinderFunction;
import pl.truszewski.interpreter.objects.cylinder.CylinderLateralSurfaceFunction;
import pl.truszewski.interpreter.objects.cylinder.CylinderTotalSurfaceFunction;
import pl.truszewski.interpreter.objects.cylinder.CylinderVolumeFunction;
import pl.truszewski.interpreter.objects.iterator.IteratorHasNextFunction;
import pl.truszewski.interpreter.objects.iterator.IteratorNextFunction;
import pl.truszewski.interpreter.objects.list.AddListFunction;
import pl.truszewski.interpreter.objects.list.ListFunction;
import pl.truszewski.interpreter.objects.list.ListIteratorFunction;
import pl.truszewski.interpreter.objects.pyramid.PyramidBaseSurfaceFunction;
import pl.truszewski.interpreter.objects.pyramid.PyramidFunction;
import pl.truszewski.interpreter.objects.pyramid.PyramidLateralSurfaceFunction;
import pl.truszewski.interpreter.objects.pyramid.PyramidTotalSurfaceFunction;
import pl.truszewski.interpreter.objects.pyramid.PyramidVolumeFunction;
import pl.truszewski.interpreter.objects.sphere.SphereDiameterFunction;
import pl.truszewski.interpreter.objects.sphere.SphereFunction;
import pl.truszewski.interpreter.objects.sphere.SphereTotalSurfaceFunction;
import pl.truszewski.interpreter.objects.sphere.SphereVolumeFunction;
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
import pl.truszewski.programstructure.expression.Expression;
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
import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.programstructure.statements.WhileStatement;

public class PrinterVisitor implements Visitor {
    private static final int INDENTATION_LEVEL = 1;
    private static final String INDENTATION_CHARACTER = "*";
    private int currentIndentation;

    public PrinterVisitor() {
        currentIndentation = 0;
    }

    @Override
    public void visit(final Program program) {
        increaseIndentation();
        print(program.toString());
        for (FunctionDefinition functionDefinition : program.functions().values()) {
            functionDefinition.accept(this);
        }
        decreaseIndentation();
    }

    @Override
    public void visit(final Block block) {
        increaseIndentation();
        print(block.toString());
        for (Statement statement : block.statements()) {
            statement.accept(this);
        }
        decreaseIndentation();
    }

    @Override
    public void visit(final FunctionDefinition functionDefinition) {
        increaseIndentation();
        print(functionDefinition.toString());
        for (Parameter parameter : functionDefinition.parameters()) {
            parameter.accept(this);
        }
        functionDefinition.block().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final Parameter parameter) {
        increaseIndentation();
        print(parameter.toString());
        decreaseIndentation();
    }

    @Override
    public void visit(final AccessExpression accessExpression) {
        increaseIndentation();
        print(accessExpression.toString());
        accessExpression.left().accept(this);
        accessExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final AddExpression addExpression) {
        increaseIndentation();
        print(addExpression.toString());
        addExpression.left().accept(this);
        addExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final AndExpression andExpression) {
        increaseIndentation();
        print(andExpression.toString());
        andExpression.left().accept(this);
        andExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final CastingExpression castingExpression) {
        increaseIndentation();
        print(castingExpression.toString());
        castingExpression.castingExpression().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final DivideExpression divideExpression) {
        increaseIndentation();
        print(divideExpression.toString());
        divideExpression.left().accept(this);
        divideExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final DoubleNumber doubleNumber) {
        print(doubleNumber.toString());
    }

    @Override
    public void visit(final EqualityExpression equalityExpression) {
        increaseIndentation();
        print(equalityExpression.toString());
        equalityExpression.left().accept(this);
        equalityExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final FunctionCall functionCall) {
        increaseIndentation();
        print(functionCall.toString());
        for (Expression expression : functionCall.arguments()) {
            expression.accept(this);
        }
        decreaseIndentation();
    }

    @Override
    public void visit(final GreaterEqualExpression greaterEqualExpression) {
        increaseIndentation();
        print(greaterEqualExpression.toString());
        greaterEqualExpression.left().accept(this);
        greaterEqualExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final GreaterExpression greaterExpression) {
        increaseIndentation();
        print(greaterExpression.toString());
        greaterExpression.left().accept(this);
        greaterExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final IdentifierExpression identifierExpression) {
        increaseIndentation();
        print(identifierExpression.toString());
        decreaseIndentation();
    }

    @Override
    public void visit(final InequalityExpression inequalityExpression) {
        increaseIndentation();
        print(inequalityExpression.toString());
        inequalityExpression.left().accept(this);
        inequalityExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final IntNumber intNumber) {
        increaseIndentation();
        print(intNumber.toString());
        decreaseIndentation();
    }

    @Override
    public void visit(final LessEqualExpression lessEqualExpression) {
        increaseIndentation();
        print(lessEqualExpression.toString());
        lessEqualExpression.left().accept(this);
        lessEqualExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final LessExpression lessExpression) {
        increaseIndentation();
        print(lessExpression.toString());
        lessExpression.left().accept(this);
        lessExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final MultiplyExpression multiplyExpression) {
        increaseIndentation();
        print(multiplyExpression.toString());
        multiplyExpression.left().accept(this);
        multiplyExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final NegationExpression negationExpression) {
        increaseIndentation();
        print(negationExpression.toString());
        negationExpression.expression().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final OrExpression orExpression) {
        increaseIndentation();
        print(orExpression.toString());
        orExpression.left().accept(this);
        orExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final StringExpression stringExpression) {
        increaseIndentation();
        print(stringExpression.toString());
        decreaseIndentation();

    }

    @Override
    public void visit(final SubtractExpression subtractExpression) {
        increaseIndentation();
        print(subtractExpression.toString());
        subtractExpression.left().accept(this);
        subtractExpression.right().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final AssignmentStatement assignmentStatement) {
        increaseIndentation();
        print(assignmentStatement.toString());
        assignmentStatement.expression().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final DeclarationStatement declarationStatement) {
        increaseIndentation();
        print(declarationStatement.toString());
        declarationStatement.expression().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final IfStatement ifStatement) {
        increaseIndentation();
        print(ifStatement.toString());
        ifStatement.expression().accept(this);
        ifStatement.trueBlock().accept(this);
        if (ifStatement.elseBlock() != null) {
            ifStatement.elseBlock().accept(this);
        }
        decreaseIndentation();
    }

    @Override
    public void visit(final ReturnStatement returnStatement) {
        increaseIndentation();
        print(returnStatement.toString());
        returnStatement.returnValue().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final WhileStatement whileStatement) {
        increaseIndentation();
        print(whileStatement.toString());
        whileStatement.expression().accept(this);
        whileStatement.codeBlock().accept(this);
        decreaseIndentation();
    }

    @Override
    public void visit(final PrintFunction printFunction) {

    }

    @Override
    public void visit(final CuboidFunction cuboidFunction) {

    }

    @Override
    public void visit(final CuboidVolumeFunction cuboidVolumeFunction) {

    }

    @Override
    public void visit(final CuboidBaseSurfaceFunction cuboidBaseSurfaceFunction) {

    }

    @Override
    public void visit(final CuboidLateralSurfaceFunction cuboidLateralSurfaceFunction) {

    }

    @Override
    public void visit(final CuboidTotalSurfaceFunction cuboidTotalSurfaceFunction) {

    }

    @Override
    public void visit(final PyramidBaseSurfaceFunction pyramidBaseSurfaceFunction) {

    }

    @Override
    public void visit(final PyramidFunction pyramidFunction) {

    }

    @Override
    public void visit(final PyramidLateralSurfaceFunction pyramidLateralSurfaceFunction) {

    }

    @Override
    public void visit(final PyramidTotalSurfaceFunction pyramidTotalSurfaceFunction) {

    }

    @Override
    public void visit(final PyramidVolumeFunction pyramidVolumeFunction) {

    }

    @Override
    public void visit(final ConeVolumeFunction cuboidVolumeFunction) {

    }

    @Override
    public void visit(final ConeTotalSurfaceFunction cuboidTotalSurfaceFunction) {

    }

    @Override
    public void visit(final ConeLateralSurfaceFunction coneLateralSurfaceFunction) {

    }

    @Override
    public void visit(final ConeBaseSurfaceFunction coneBaseSurfaceFunction) {

    }

    @Override
    public void visit(final ConeFunction coneFunction) {

    }

    @Override
    public void visit(final CylinderTotalSurfaceFunction cylinderTotalSurfaceFunction) {

    }

    @Override
    public void visit(final CylinderBaseSurfaceFunction cylinderBaseSurfaceFunction) {

    }

    @Override
    public void visit(final CylinderFunction cylinderFunction) {

    }

    @Override
    public void visit(final CylinderLateralSurfaceFunction cylinderLateralSurfaceFunction) {

    }

    @Override
    public void visit(final CylinderVolumeFunction cylinderVolumeFunction) {

    }

    @Override
    public void visit(final SphereTotalSurfaceFunction sphereTotalSurfaceFunction) {

    }

    @Override
    public void visit(final SphereVolumeFunction sphereVolumeFunction) {

    }

    @Override
    public void visit(final SphereFunction sphereFunction) {

    }

    @Override
    public void visit(final SphereDiameterFunction sphereDiameterFunction) {

    }

    @Override
    public void visit(final ListFunction listFunction) {

    }

    @Override
    public void visit(final AddListFunction addListFunction) {

    }

    @Override
    public void visit(final IteratorNextFunction iteratorNextFunction) {

    }

    @Override
    public void visit(final ListIteratorFunction listIteratorFunction) {

    }

    @Override
    public void visit(final IteratorHasNextFunction iteratorHasNextFunction) {

    }

    @Override
    public void visit(final ShowFiguresFunction showFiguresFunction) {

    }

    private void increaseIndentation() {
        currentIndentation += INDENTATION_LEVEL;
    }

    private void decreaseIndentation() {
        currentIndentation -= INDENTATION_LEVEL;
    }

    private void print(String text) {
        System.out.println(INDENTATION_CHARACTER.repeat(currentIndentation) + text);
    }
}
