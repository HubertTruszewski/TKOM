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

    void visit(PrintFunction printFunction);

    void visit(CuboidFunction cuboidFunction);

    void visit(CuboidVolumeFunction cuboidVolumeFunction);

    void visit(CuboidBaseSurfaceFunction cuboidBaseSurfaceFunction);

    void visit(CuboidLateralSurfaceFunction cuboidLateralSurfaceFunction);

    void visit(CuboidTotalSurfaceFunction cuboidTotalSurfaceFunction);

    void visit(PyramidBaseSurfaceFunction pyramidBaseSurfaceFunction);

    void visit(PyramidFunction pyramidFunction);

    void visit(PyramidLateralSurfaceFunction pyramidLateralSurfaceFunction);

    void visit(PyramidTotalSurfaceFunction pyramidTotalSurfaceFunction);

    void visit(PyramidVolumeFunction pyramidVolumeFunction);

    void visit(ConeVolumeFunction cuboidVolumeFunction);

    void visit(ConeTotalSurfaceFunction cuboidTotalSurfaceFunction);

    void visit(ConeLateralSurfaceFunction coneLateralSurfaceFunction);

    void visit(ConeBaseSurfaceFunction coneBaseSurfaceFunction);

    void visit(ConeFunction coneFunction);

    void visit(CylinderTotalSurfaceFunction cylinderTotalSurfaceFunction);

    void visit(CylinderBaseSurfaceFunction cylinderBaseSurfaceFunction);

    void visit(CylinderFunction cylinderFunction);

    void visit(CylinderLateralSurfaceFunction cylinderLateralSurfaceFunction);

    void visit(CylinderVolumeFunction cylinderVolumeFunction);

    void visit(SphereTotalSurfaceFunction sphereTotalSurfaceFunction);

    void visit(SphereVolumeFunction sphereVolumeFunction);

    void visit(SphereFunction sphereFunction);

    void visit(SphereDiameterFunction sphereDiameterFunction);

    void visit(ListFunction listFunction);

    void visit(AddListFunction addListFunction);

    void visit(IteratorNextFunction iteratorNextFunction);

    void visit(ListIteratorFunction listIteratorFunction);

    void visit(IteratorHasNextFunction iteratorHasNextFunction);

    void visit(ShowFiguresFunction showFiguresFunction);
}
