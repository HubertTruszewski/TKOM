package pl.truszewski.interpreter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import pl.truszewski.programstructure.basic.Block;
import pl.truszewski.programstructure.basic.FunctionDefinition;
import pl.truszewski.programstructure.basic.Program;
import pl.truszewski.programstructure.basic.ValueType;
import pl.truszewski.programstructure.expression.AccessExpression;
import pl.truszewski.programstructure.expression.CastingExpression;
import pl.truszewski.programstructure.expression.DoubleNumber;
import pl.truszewski.programstructure.expression.FunctionCall;
import pl.truszewski.programstructure.expression.IdentifierExpression;
import pl.truszewski.programstructure.statements.DeclarationStatement;

public class InterpreterTests {
    @Test
    public void testCuboidAccessFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CUBOID,
                                        "c",
                                        new FunctionCall("newCuboid",
                                                List.of(new DoubleNumber(12.0),
                                                        new DoubleNumber(20.0),
                                                        new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("a")), ValueType.STRING))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("b")), ValueType.STRING))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("H")), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("12.0\n20.0\n15.5\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testCuboidVolumeFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CUBOID,
                                        "c",
                                        new FunctionCall("newCuboid",
                                                List.of(new DoubleNumber(12.0),
                                                        new DoubleNumber(20.0),
                                                        new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("volume", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("3720.0\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testCuboidBaseSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CUBOID,
                                        "c",
                                        new FunctionCall("newCuboid",
                                                List.of(new DoubleNumber(12.0),
                                                        new DoubleNumber(20.0),
                                                        new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("baseSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("240.0\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testCuboidLateralSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CUBOID,
                                        "c",
                                        new FunctionCall("newCuboid",
                                                List.of(new DoubleNumber(12.0),
                                                        new DoubleNumber(20.0),
                                                        new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("lateralSurface", List.of())),
                                                ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("992.0\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testCuboidTotalSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CUBOID,
                                        "c",
                                        new FunctionCall("newCuboid",
                                                List.of(new DoubleNumber(12.0),
                                                        new DoubleNumber(20.0),
                                                        new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("totalSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("1472.0\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testPyramidAccessFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.PYRAMID,
                                        "c",
                                        new FunctionCall("newPyramid",
                                                List.of(new DoubleNumber(12.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("a")), ValueType.STRING))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("H")), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("12.0\n15.5\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testPyramidBaseSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.PYRAMID,
                                        "c",
                                        new FunctionCall("newPyramid",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("baseSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("173.205"));
    }

    @Test
    public void testPyramidLateralSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.PYRAMID,
                                        "c",
                                        new FunctionCall("newPyramid",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("lateralSurface", List.of())),
                                                ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("496.210"));
    }

    @Test
    public void testPyramidTotalSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.PYRAMID,
                                        "c",
                                        new FunctionCall("newPyramid",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("totalSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("669.415"));
    }

    @Test
    public void testPyramidVolumeFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.PYRAMID,
                                        "c",
                                        new FunctionCall("newPyramid",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("volume", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("894.892"));
    }

    @Test
    public void testConeAccessFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CONE,
                                        "c",
                                        new FunctionCall("newCone", List.of(new DoubleNumber(12.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("r")), ValueType.STRING))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("l")), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("12.0\n15.5\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testConeBaseSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CONE,
                                        "c",
                                        new FunctionCall("newCone", List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("baseSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("1256.637"));
    }

    @Test
    public void testConeLateralSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CONE,
                                        "c",
                                        new FunctionCall("newCone", List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("lateralSurface", List.of())),
                                                ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("973.893"));
    }

    @Test
    public void testConeTotalSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CONE,
                                        "c",
                                        new FunctionCall("newCone", List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("totalSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("2230.530"));
    }

    @Test
    public void testConeVolumeFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CONE,
                                        "c",
                                        new FunctionCall("newCone", List.of(new DoubleNumber(5.0), new DoubleNumber(6.0)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("volume", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("86.829"));
    }

    @Test
    public void testCylinderAccessFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CYLINDER,
                                        "c",
                                        new FunctionCall("newCylinder",
                                                List.of(new DoubleNumber(12.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("r")), ValueType.STRING))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("H")), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("12.0\n15.5\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testCylinderBaseSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CYLINDER,
                                        "c",
                                        new FunctionCall("newCylinder",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("baseSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("1256.637"));
    }

    @Test
    public void testCylinderLateralFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CYLINDER,
                                        "c",
                                        new FunctionCall("newCylinder",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("lateralSurface", List.of())),
                                                ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("1947.787"));
    }

    @Test
    public void testCylinderTotalSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CYLINDER,
                                        "c",
                                        new FunctionCall("newCylinder",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("totalSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("4461.061"));
    }

    @Test
    public void testCylinderVolumeFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.CYLINDER,
                                        "c",
                                        new FunctionCall("newCylinder",
                                                List.of(new DoubleNumber(20.0), new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("volume", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("19477.874"));
    }

    @Test
    public void testSphereAccessFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.SPHERE,
                                        "c",
                                        new FunctionCall("newSphere", List.of(new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new IdentifierExpression("R")), ValueType.STRING))))))));
        interpreter.execute(program);
        assertEquals("15.5\n", data.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testSphereVolumeFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.SPHERE,
                                        "c",
                                        new FunctionCall("newSphere", List.of(new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("volume", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("15598.531"));
    }

    @Test
    public void testSphereTotalSurfaceFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.SPHERE,
                                        "c",
                                        new FunctionCall("newSphere", List.of(new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("totalSurface", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("3019.070"));
    }

    @Test
    public void testSphereDiameterFunction() {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(data);
        Interpreter interpreter = new InterpreterImpl(stream);
        Program program = new Program(Map.of("main",
                new FunctionDefinition(ValueType.VOID,
                        "main",
                        List.of(),
                        new Block(List.of(new DeclarationStatement(ValueType.SPHERE,
                                        "c",
                                        new FunctionCall("newSphere", List.of(new DoubleNumber(15.5)))),
                                new FunctionCall("print",
                                        List.of(new CastingExpression(new AccessExpression(new IdentifierExpression("c"),
                                                new FunctionCall("diameter", List.of())), ValueType.STRING))))))));
        interpreter.execute(program);
        assertTrue(data.toString().contains("31.0"));
    }
}
