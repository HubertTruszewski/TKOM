package pl.truszewski.interpreter;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import pl.truszewski.Window;
import pl.truszewski.error.interpreter.BadArgumentsListSizeError;
import pl.truszewski.error.interpreter.BadValueTypeError;
import pl.truszewski.error.interpreter.ListOutOfBoundryError;
import pl.truszewski.error.interpreter.MissingReturnedValueError;
import pl.truszewski.error.interpreter.NoSuchFunctionError;
import pl.truszewski.error.interpreter.NoSuchVariableError;
import pl.truszewski.error.interpreter.UnsupportedOperationError;
import pl.truszewski.error.interpreter.ZeroDivisionError;
import pl.truszewski.interpreter.objects.CustomObject;
import pl.truszewski.interpreter.objects.FunctionCallContext;
import pl.truszewski.interpreter.objects.MethodCall;
import pl.truszewski.interpreter.objects.PrintFunction;
import pl.truszewski.interpreter.objects.Result;
import pl.truszewski.interpreter.objects.ShowFiguresFunction;
import pl.truszewski.interpreter.objects.Value;
import pl.truszewski.interpreter.objects.Variable;
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
import pl.truszewski.programstructure.basic.ValueType;
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
import pl.truszewski.visitor.Visitor;

@Slf4j
public class InterpreterImpl implements Interpreter, Visitor {
    private static final String MAIN_FUNCTION_NAME = "main";
    private static final List<ValueType> FIGURE_VALUE_TYPES = List.of(ValueType.CUBOID,
            ValueType.PYRAMID,
            ValueType.CYLINDER,
            ValueType.SPHERE,
            ValueType.CONE);
    private final Map<String, FunctionDefinition> functionDefinitions = new HashMap<>(InterpreterUtils.BUILTIN_FUNCTIONS);
    private final Deque<FunctionCallContext> functionCallContexts = new ArrayDeque<>();
    private final PrintStream out;
    private Result result = Result.empty();

    public InterpreterImpl(final PrintStream stream) {
        this.out = stream;
    }

    @Override
    public void execute(final Program program) {
        try {
            visit(program);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void visit(final Program program) {
        functionDefinitions.putAll(program.functions());
        FunctionCall mainFunctionCall = new FunctionCall(MAIN_FUNCTION_NAME, List.of());
        visit(mainFunctionCall);
    }

    @Override
    public void visit(final Block block) {
        FunctionCallContext context = functionCallContexts.getLast();
        context.addNewScope();
        for (Statement statement : block.statements()) {
            statement.accept(this);
            if (result.returned()) {
                break;
            }
        }
        context.removeScope();
    }

    @Override
    public void visit(final FunctionDefinition functionDefinition) {
        throw new UnsupportedOperationError("Cannot interpret function definition");
    }

    @Override
    public void visit(final Parameter parameter) {
        throw new UnsupportedOperationError("Cannot interpret function parameter");
    }

    @Override
    public void visit(final AccessExpression accessExpression) {
        accessExpression.left().accept(this);
        Value leftValue = result.value();
        if (!(leftValue.value() instanceof CustomObject)) {
            throw new UnsupportedOperationError("Cannot access value from this type");
        }
        CustomObject customObject = (CustomObject) result.value().value();
        if (accessExpression.right() instanceof IdentifierExpression identifierExpression) {
            Optional<Variable> variable = customObject.findVariable(identifierExpression.identifierName());
            Value value = variable.orElseThrow(() -> new NoSuchVariableError(
                    "No such field in object: " + identifierExpression.identifierName())).value();
            result = new Result(false, true, value);
        } else if (accessExpression.right() instanceof FunctionCall functionCall) {
            visit(new MethodCall(customObject, functionCall.functionName(), functionCall.arguments()));
        }
    }

    @Override
    public void visit(final AddExpression addExpression) {
        addExpression.left().accept(this);
        Value leftValue = result.value();
        addExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.INT, (int) leftValue.value() + (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.DOUBLE, (double) leftValue.value() + (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final AndExpression andExpression) {
        andExpression.left().accept(this);
        Value leftValue = result.value();
        if (!((boolean) leftValue.value())) {
            result = new Result(false, true, new Value(ValueType.BOOL, false));
            return;
        }
        andExpression.right().accept(this);
        Value rightValue = result.value();
        result = new Result(false,
                true,
                new Value(ValueType.BOOL, (boolean) leftValue.value() && (boolean) rightValue.value()));
    }

    @Override
    public void visit(final CastingExpression castingExpression) {
        castingExpression.castingExpression().accept(this);
        Value castingValue = result.value();
        if (castingValue.type() == ValueType.INT && castingExpression.targetType() == ValueType.DOUBLE) {
            result = new Result(false, true, new Value(ValueType.DOUBLE, (double) (int) castingValue.value()));
        } else if (castingValue.type() == ValueType.DOUBLE && castingExpression.targetType() == ValueType.INT) {
            result = new Result(false, true, new Value(ValueType.INT, (int) (double) castingValue.value()));
        } else if (castingExpression.targetType() == ValueType.STRING) {
            result = switch (castingValue.type()) {
                case INT -> new Result(false,
                        true,
                        new Value(ValueType.STRING, String.valueOf((int) castingValue.value())));
                case DOUBLE -> new Result(false,
                        true,
                        new Value(ValueType.STRING, String.valueOf((double) castingValue.value())));
                case BOOL -> new Result(false,
                        true,
                        new Value(ValueType.STRING, String.valueOf((boolean) castingValue.value())));
                default -> throw new UnsupportedOperationError("Unsupported casting");
            };
        } else {
            throw new UnsupportedOperationError("Unsupported casting");
        }

    }

    @Override
    public void visit(final DivideExpression divideExpression) {
        divideExpression.left().accept(this);
        Value leftValue = result.value();
        divideExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            if ((int) rightValue.value() == 0) {
                throw new ZeroDivisionError("Cannot divide by 0");
            }
            result = new Result(false,
                    true,
                    new Value(ValueType.INT, (int) leftValue.value() / (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            if ((double) rightValue.value() == 0) {
                throw new ZeroDivisionError("Cannot divide by 0");
            }
            result = new Result(false,
                    true,
                    new Value(ValueType.DOUBLE, (double) leftValue.value() / (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final DoubleNumber doubleNumber) {
        result = new Result(false, true, new Value(ValueType.DOUBLE, doubleNumber.value()));
    }

    @Override
    public void visit(final EqualityExpression equalityExpression) {
        equalityExpression.left().accept(this);
        Value leftValue = result.value();
        equalityExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (int) leftValue.value() == (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (double) leftValue.value() == (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final FunctionCall functionCall) {
        if (!functionDefinitions.containsKey(functionCall.functionName())) {
            throw new NoSuchFunctionError("No such function: " + functionCall.functionName());
        }
        FunctionDefinition functionDefinition = functionDefinitions.get(functionCall.functionName());
        List<Expression> functionArguments = functionCall.arguments();
        if (functionDefinition.parameters().size() != functionArguments.size()) {
            throw new BadArgumentsListSizeError(String.format("Expected %s arguments, but got %s",
                    functionDefinition.parameters().size(),
                    functionArguments.size()));
        }
        FunctionCallContext context = new FunctionCallContext(functionCall.functionName());
        for (int i = 0; i < functionDefinition.parameters().size(); ++i) {
            functionArguments.get(i).accept(this);
            Parameter parameter = functionDefinition.parameters().get(i);
            if (parameter.type() != result.value().type()) {
                throw new BadValueTypeError("Bad value type for parameter: " + parameter.name());
            }
            context.addVariable(new Variable(parameter.name(), new Value(parameter.type(), result.value().value())));
        }
        functionCallContexts.add(context);
        functionDefinition.block().accept(this);
        if (functionDefinition.returnValueType() == ValueType.VOID) {
            result = Result.empty();
        } else if (result.returned()) {
            if (result.value().type() != functionDefinition.returnValueType()) {
                throw new BadValueTypeError("Bad returned type in function: " + functionCall.functionName());
            }
            result = new Result(false, true, result.value());
        } else {
            throw new MissingReturnedValueError("Missing returned value from function: " + functionCall.functionName());
        }
        functionCallContexts.removeLast();
    }

    @Override
    public void visit(final GreaterEqualExpression greaterEqualExpression) {
        greaterEqualExpression.left().accept(this);
        Value leftValue = result.value();
        greaterEqualExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (int) leftValue.value() <= (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (double) leftValue.value() <= (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }

    }

    @Override
    public void visit(final GreaterExpression greaterExpression) {
        greaterExpression.left().accept(this);
        Value leftValue = result.value();
        greaterExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (int) leftValue.value() > (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (double) leftValue.value() > (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final IdentifierExpression identifierExpression) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable variable = context.findVariable(identifierExpression.identifierName())
                .orElseThrow(() -> new NoSuchVariableError(
                        "No such variable: " + identifierExpression.identifierName()));
        result = new Result(false, true, variable.value());
    }

    @Override
    public void visit(final InequalityExpression inequalityExpression) {
        inequalityExpression.left().accept(this);
        Value leftValue = result.value();
        inequalityExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (int) leftValue.value() != (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (double) leftValue.value() != (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final IntNumber intNumber) {
        result = new Result(false, true, new Value(ValueType.INT, intNumber.value()));
    }

    @Override
    public void visit(final LessEqualExpression lessEqualExpression) {
        lessEqualExpression.left().accept(this);
        Value leftValue = result.value();
        lessEqualExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (int) leftValue.value() <= (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (double) leftValue.value() <= (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final LessExpression lessExpression) {
        lessExpression.left().accept(this);
        Value leftValue = result.value();
        lessExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (int) leftValue.value() < (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.BOOL, (double) leftValue.value() < (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final MultiplyExpression multiplyExpression) {
        multiplyExpression.left().accept(this);
        Value leftValue = result.value();
        multiplyExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.INT, (int) leftValue.value() * (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.DOUBLE, (double) leftValue.value() * (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final NegationExpression negationExpression) {
        negationExpression.expression().accept(this);
        if (result.value().type() == ValueType.BOOL) {
            result = new Result(false, true, new Value(ValueType.BOOL, !(boolean) result.value().value()));
        } else if (result.value().type() == ValueType.INT) {
            result = new Result(false, true, new Value(ValueType.INT, -(int) result.value().value()));
        } else if (result.value().type() == ValueType.DOUBLE) {
            result = new Result(false, true, new Value(ValueType.DOUBLE, -(double) result.value().value()));
        } else {
            throw new UnsupportedOperationError("Cannot negate expression");
        }
    }

    @Override
    public void visit(final OrExpression orExpression) {
        orExpression.left().accept(this);
        Value leftValue = result.value();
        if ((boolean) leftValue.value()) {
            result = new Result(false, true, new Value(ValueType.BOOL, true));
            return;
        }
        orExpression.right().accept(this);
        Value rightValue = result.value();
        result = new Result(false,
                true,
                new Value(ValueType.BOOL, (boolean) leftValue.value() || (boolean) rightValue.value()));
    }

    @Override
    public void visit(final StringExpression stringExpression) {
        result = new Result(false, true, new Value(ValueType.STRING, stringExpression.value()));
    }

    @Override
    public void visit(final SubtractExpression subtractExpression) {
        subtractExpression.left().accept(this);
        Value leftValue = result.value();
        subtractExpression.right().accept(this);
        Value rightValue = result.value();
        if (leftValue.type() == ValueType.INT && rightValue.type() == ValueType.INT) {
            result = new Result(false,
                    true,
                    new Value(ValueType.INT, (int) leftValue.value() - (int) rightValue.value()));
        } else if (leftValue.type() == ValueType.DOUBLE && rightValue.type() == ValueType.DOUBLE) {
            result = new Result(false,
                    true,
                    new Value(ValueType.DOUBLE, (double) leftValue.value() - (double) rightValue.value()));
        } else {
            throw new UnsupportedOperationError("Cannot add " + leftValue.type() + " and " + rightValue.type());
        }
    }

    @Override
    public void visit(final AssignmentStatement assignmentStatement) {
        assignmentStatement.expression().accept(this);
        Value value = result.value();
        functionCallContexts.getLast().updateVariable(assignmentStatement.name(), value);
    }

    @Override
    public void visit(final DeclarationStatement declarationStatement) {
        FunctionCallContext context = functionCallContexts.getLast();
        declarationStatement.expression().accept(this);
        Value value = result.value();
        if (!result.present()) {
            throw new BadValueTypeError("Cannot assign value because right side is null");
        }
        if (!Objects.equals(declarationStatement.valueType(), value.type())) {
            throw new BadValueTypeError(
                    "Bad value type: expected " + declarationStatement.valueType() + " but got: " + value.type());
        }
        context.addVariable(new Variable(declarationStatement.name(),
                new Value(declarationStatement.valueType(), value.value())));
    }

    @Override
    public void visit(final IfStatement ifStatement) {
        ifStatement.expression().accept(this);
        Value value = result.value();
        if ((boolean) value.value()) {
            ifStatement.trueBlock().accept(this);
        } else {
            ifStatement.elseBlock().accept(this);
        }
    }

    @Override
    public void visit(final ReturnStatement returnStatement) {
        returnStatement.returnValue().accept(this);
        result = new Result(true, true, result.value());
    }

    @Override
    public void visit(final WhileStatement whileStatement) {
        whileStatement.expression().accept(this);
        Value value = result.value();
        while ((boolean) value.value()) {
            whileStatement.codeBlock().accept(this);
            whileStatement.expression().accept(this);
            value = result.value();
        }
    }

    @Override
    public void visit(final PrintFunction printFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable variable = context.findVariable("value")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument"));
        out.println(variable.value().value().toString());
    }

    @Override
    public void visit(final CuboidFunction cuboidFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable a = context.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"));
        Variable b = context.findVariable("b")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument b"));
        Variable H = context.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"));
        result = new Result(true,
                true,
                new Value(ValueType.CUBOID,
                        new CustomObject(ValueType.CUBOID,
                                Map.ofEntries(Map.entry("a",
                                                new Variable("a", new Value(ValueType.DOUBLE, a.value().value()))),
                                        Map.entry("b",
                                                new Variable("b", new Value(ValueType.DOUBLE, b.value().value()))),
                                        Map.entry("H",
                                                new Variable("H", new Value(ValueType.DOUBLE, H.value().value())))),
                                InterpreterUtils.CUBOID_FUNCTIONS)));
    }

    @Override
    public void visit(final CuboidVolumeFunction cuboidVolumeFunction) {
        CustomObject cuboid = (CustomObject) result.value().value();
        double a = (double) cuboid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        double b = (double) cuboid.findVariable("b")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument b"))
                .value()
                .value();
        double H = (double) cuboid.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, a * b * H));
    }

    @Override
    public void visit(final CuboidBaseSurfaceFunction cuboidBaseSurfaceFunction) {
        CustomObject cuboid = (CustomObject) result.value().value();
        double a = (double) cuboid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        double b = (double) cuboid.findVariable("b")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument b"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, a * b));
    }

    @Override
    public void visit(final CuboidLateralSurfaceFunction cuboidLateralSurfaceFunction) {
        CustomObject cuboid = (CustomObject) result.value().value();
        double a = (double) cuboid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        double b = (double) cuboid.findVariable("b")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument b"))
                .value()
                .value();
        double H = (double) cuboid.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, 2 * a * H + 2 * b * H));
    }

    @Override
    public void visit(final CuboidTotalSurfaceFunction cuboidTotalSurfaceFunction) {
        CustomObject cuboid = (CustomObject) result.value().value();
        double a = (double) cuboid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        double b = (double) cuboid.findVariable("b")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument b"))
                .value()
                .value();
        double H = (double) cuboid.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, 2 * (a * b + a * H + b * H)));
    }

    @Override
    public void visit(final PyramidBaseSurfaceFunction pyramidBaseSurfaceFunction) {
        CustomObject pyramid = (CustomObject) result.value().value();
        double a = (double) pyramid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, Math.pow(a, 2) * Math.sqrt(3) / 4));
    }

    @Override
    public void visit(final PyramidFunction pyramidFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable a = context.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"));
        Variable H = context.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"));
        result = new Result(true,
                true,
                new Value(ValueType.PYRAMID,
                        new CustomObject(ValueType.PYRAMID,
                                Map.ofEntries(Map.entry("a",
                                                new Variable("a", new Value(ValueType.DOUBLE, a.value().value()))),
                                        Map.entry("H",
                                                new Variable("H", new Value(ValueType.DOUBLE, H.value().value())))),
                                InterpreterUtils.PYRAMID_FUNCTIONS)));
    }

    @Override
    public void visit(final PyramidLateralSurfaceFunction pyramidLateralSurfaceFunction) {
        CustomObject cuboid = (CustomObject) result.value().value();
        double a = (double) cuboid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        double H = (double) cuboid.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, 3 * pyramidWallSurface(a, H)));
    }

    @Override
    public void visit(final PyramidTotalSurfaceFunction pyramidTotalSurfaceFunction) {
        CustomObject cuboid = (CustomObject) result.value().value();
        double a = (double) cuboid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        double H = (double) cuboid.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true,
                true,
                new Value(ValueType.DOUBLE, Math.pow(a, 2) * Math.sqrt(3) / 4 + 3 * pyramidWallSurface(a, H)));
    }

    @Override
    public void visit(final PyramidVolumeFunction pyramidVolumeFunction) {
        CustomObject cuboid = (CustomObject) result.value().value();
        double a = (double) cuboid.findVariable("a")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument a"))
                .value()
                .value();
        double H = (double) cuboid.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, H * Math.pow(a, 2) * Math.sqrt(3) / 4 / 3));
    }

    @Override
    public void visit(final ConeVolumeFunction cuboidVolumeFunction) {
        CustomObject cone = (CustomObject) result.value().value();
        double r = (double) cone.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        double l = (double) cone.findVariable("l")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument l"))
                .value()
                .value();
        result = new Result(true,
                true,
                new Value(ValueType.DOUBLE, Math.PI * Math.pow(r, 2) * Math.sqrt(Math.pow(l, 2) - Math.pow(r, 2)) / 3));
    }

    @Override
    public void visit(final ConeTotalSurfaceFunction cuboidTotalSurfaceFunction) {
        CustomObject cone = (CustomObject) result.value().value();
        double r = (double) cone.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        double l = (double) cone.findVariable("l")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument l"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, Math.PI * Math.pow(r, 2) + Math.PI * r * l));
    }

    @Override
    public void visit(final ConeLateralSurfaceFunction coneLateralSurfaceFunction) {
        CustomObject cone = (CustomObject) result.value().value();
        double r = (double) cone.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        double l = (double) cone.findVariable("l")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument l"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, Math.PI * r * l));
    }

    @Override
    public void visit(final ConeBaseSurfaceFunction coneBaseSurfaceFunction) {
        CustomObject cone = (CustomObject) result.value().value();
        double r = (double) cone.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, Math.PI * Math.pow(r, 2)));
    }

    @Override
    public void visit(final ConeFunction coneFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable r = context.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"));
        Variable l = context.findVariable("l")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument l"));
        result = new Result(true,
                true,
                new Value(ValueType.CONE,
                        new CustomObject(ValueType.CONE,
                                Map.ofEntries(Map.entry("r",
                                                new Variable("r", new Value(ValueType.DOUBLE, r.value().value()))),
                                        Map.entry("l",
                                                new Variable("l", new Value(ValueType.DOUBLE, l.value().value())))),
                                InterpreterUtils.CONE_FUNCTIONS)));
    }

    @Override
    public void visit(final CylinderTotalSurfaceFunction cylinderTotalSurfaceFunction) {
        CustomObject cylinder = (CustomObject) result.value().value();
        double r = (double) cylinder.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        double H = (double) cylinder.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true,
                true,
                new Value(ValueType.DOUBLE, 2 * Math.PI * Math.pow(r, 2) + 2 * Math.PI * r * H));
    }

    @Override
    public void visit(final CylinderBaseSurfaceFunction cylinderBaseSurfaceFunction) {
        CustomObject cylinder = (CustomObject) result.value().value();
        double r = (double) cylinder.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, Math.PI * Math.pow(r, 2)));
    }

    @Override
    public void visit(final CylinderFunction cylinderFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable r = context.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"));
        Variable H = context.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"));
        result = new Result(true,
                true,
                new Value(ValueType.CYLINDER,
                        new CustomObject(ValueType.CYLINDER,
                                Map.ofEntries(Map.entry("r",
                                                new Variable("r", new Value(ValueType.DOUBLE, r.value().value()))),
                                        Map.entry("H",
                                                new Variable("H", new Value(ValueType.DOUBLE, H.value().value())))),
                                InterpreterUtils.CYLINDER_FUNCTIONS)));
    }

    @Override
    public void visit(final CylinderLateralSurfaceFunction cylinderLateralSurfaceFunction) {
        CustomObject cylinder = (CustomObject) result.value().value();
        double r = (double) cylinder.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        double H = (double) cylinder.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, 2 * Math.PI * r * H));
    }

    @Override
    public void visit(final CylinderVolumeFunction cylinderVolumeFunction) {
        CustomObject cylinder = (CustomObject) result.value().value();
        double r = (double) cylinder.findVariable("r")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument r"))
                .value()
                .value();
        double H = (double) cylinder.findVariable("H")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument H"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, Math.PI * Math.pow(r, 2) * H));
    }

    @Override
    public void visit(final SphereTotalSurfaceFunction sphereTotalSurfaceFunction) {
        CustomObject sphere = (CustomObject) result.value().value();
        double R = (double) sphere.findVariable("R")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument R"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, 4 * Math.PI * Math.pow(R, 2)));
    }

    @Override
    public void visit(final SphereVolumeFunction sphereVolumeFunction) {
        CustomObject sphere = (CustomObject) result.value().value();
        double R = (double) sphere.findVariable("R")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument R"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, 4 * Math.PI * Math.pow(R, 3) / 3));
    }

    @Override
    public void visit(final SphereFunction sphereFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable R = context.findVariable("R")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument R"));
        result = new Result(true,
                true,
                new Value(ValueType.SPHERE,
                        new CustomObject(ValueType.SPHERE,
                                Map.ofEntries(Map.entry("R",
                                        new Variable("R", new Value(ValueType.DOUBLE, R.value().value())))),
                                InterpreterUtils.SPHERE_FUNCTIONS)));
    }

    @Override
    public void visit(final SphereDiameterFunction sphereDiameterFunction) {
        CustomObject sphere = (CustomObject) result.value().value();
        double R = (double) sphere.findVariable("R")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument R"))
                .value()
                .value();
        result = new Result(true, true, new Value(ValueType.DOUBLE, 2 * R));
    }

    @Override
    public void visit(final ListFunction listFunction) {
        result = new Result(true,
                true,
                new Value(ValueType.LIST,
                        new CustomObject(ValueType.LIST,
                                Map.of("list", new Variable("list", new Value(ValueType.LIST, new ArrayList<>()))),
                                InterpreterUtils.LIST_FUNCTIONS)));
    }

    @Override
    public void visit(final AddListFunction addListFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable object = context.findVariable("object")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument object"));
        Variable value = context.findVariable("value")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument value"));
        ((List) ((CustomObject) object.value().value()).findVariable("list").get().value().value()).add(value.value()
                .value());
    }

    @Override
    public void visit(final IteratorNextFunction iteratorNextFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable iterator = context.findVariable("object")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument object"));
        int iteratorCurrentPos = (int) ((CustomObject) iterator.value().value()).findVariable("currentPos")
                .orElseThrow(() -> new BadArgumentsListSizeError("Not provided argument currentPos"))
                .value()
                .value();

        var customIterator = (CustomObject) iterator.value().value();

        List list = (List) ((CustomObject) ((Variable) customIterator.findVariable("list")
                .get()
                .value()
                .value()).value().value()).findVariable("list").get().value().value();
        if (iteratorCurrentPos >= list.size()) {
            throw new ListOutOfBoundryError("Iterator out of boundary");
        }
        result = new Result(true, true, new Value(ValueType.SPHERE, list.get(iteratorCurrentPos)));
        ((CustomObject) iterator.value().value()).updateVariable(new Variable("currentPos",
                new Value(ValueType.INT, iteratorCurrentPos + 1)));

    }

    @Override
    public void visit(final ListIteratorFunction listIteratorFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Variable object = context.findVariable("object").get();
        result = new Result(true,
                true,
                new Value(ValueType.ITERATOR,
                        new CustomObject(ValueType.ITERATOR,
                                new HashMap<>(Map.ofEntries(Map.entry("list",
                                                new Variable("list", new Value(ValueType.LIST, object))),
                                        Map.entry("currentPos",
                                                new Variable("currentPos", new Value(ValueType.INT, 0))))),
                                Map.ofEntries(Map.entry("hasNext",
                                                new FunctionDefinition(ValueType.BOOL,
                                                        "hasNext",
                                                        List.of(),
                                                        new Block(List.of(new IteratorHasNextFunction())))),
                                        Map.entry("next",
                                                new FunctionDefinition(ValueType.SPHERE,
                                                        "next",
                                                        List.of(),
                                                        new Block(List.of(new IteratorNextFunction()))))))));
    }

    @Override
    public void visit(final IteratorHasNextFunction iteratorHasNextFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        CustomObject iterator = (CustomObject) context.findVariable("object").get().value().value();
        int currentIteratorPos = (int) iterator.findVariable("currentPos").get().value().value();
        CustomObject listObject = (CustomObject) context.findVariable("object").get().value().value();
        List list = (List) ((CustomObject) ((Variable) listObject.findVariable("list").get().value().value()).value()
                .value()).findVariable("list").get().value().value();
        result = new Result(true, true, new Value(ValueType.BOOL, currentIteratorPos < list.size()));
    }

    @Override
    public void visit(final ShowFiguresFunction showFiguresFunction) {
        FunctionCallContext context = functionCallContexts.getLast();
        Window.FIGURES_TO_DRAW = ((List) ((CustomObject) context.findVariable("list")
                .get()
                .value()
                .value()).findVariable("list").get().value().value());
        Application.launch(Window.class);
    }

    private void visit(final MethodCall methodCall) {
        if (methodCall.object().findMethodDefinition(methodCall.name()).isEmpty()) {
            throw new NoSuchFunctionError("No such function: " + methodCall.name());
        }
        FunctionDefinition functionDefinition = methodCall.object().findMethodDefinition(methodCall.name()).get();
        List<Expression> functionArguments = methodCall.arguments();
        if (functionDefinition.parameters().size() != functionArguments.size()) {
            throw new BadArgumentsListSizeError(String.format("Expected %s arguments, but got %s",
                    functionDefinition.parameters().size(),
                    functionArguments.size()));
        }
        FunctionCallContext context = new FunctionCallContext(methodCall.name());
        for (int i = 0; i < functionDefinition.parameters().size(); ++i) {
            functionArguments.get(i).accept(this);
            Parameter parameter = functionDefinition.parameters().get(i);
            if (parameter.type() != result.value().type() && (parameter.type() == ValueType.FIGURE
                    && !FIGURE_VALUE_TYPES.contains(parameter.type()))) {
                throw new BadValueTypeError("Bad value type for parameter: " + parameter.name());
            }
            context.addVariable(new Variable(parameter.name(), new Value(parameter.type(), result.value().value())));
        }
        context.addVariable(new Variable("object", new Value(ValueType.LIST, methodCall.object())));
        functionCallContexts.add(context);
        functionDefinition.block().accept(this);
        if (functionDefinition.returnValueType() == ValueType.VOID) {
            result = Result.empty();
        } else if (result.returned()) {
            if (result.value().type() != functionDefinition.returnValueType()) {
                throw new BadValueTypeError("Bad returned type in function: " + methodCall.name());
            }
            result = new Result(false, true, result.value());
        } else {
            throw new MissingReturnedValueError("Missing returned value from function: " + methodCall.name());
        }
        functionCallContexts.removeLast();
    }

    private double pyramidWallSurface(double a, double H) {
        return 0.5 * a * Math.sqrt(Math.pow(H, 2) + Math.pow(a, 2) / 12);
    }
}
