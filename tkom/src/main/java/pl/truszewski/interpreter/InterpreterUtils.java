package pl.truszewski.interpreter;

import java.util.List;
import java.util.Map;

import lombok.experimental.UtilityClass;
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
import pl.truszewski.programstructure.basic.ValueType;

@UtilityClass
public class InterpreterUtils {
    public static final Map<String, FunctionDefinition> BUILTIN_FUNCTIONS = Map.ofEntries(Map.entry("print",
                    new FunctionDefinition(ValueType.VOID,
                            "print",
                            List.of(new Parameter(ValueType.STRING, "value")),
                            new Block(List.of(new PrintFunction())))),
            Map.entry("newCuboid",
                    new FunctionDefinition(ValueType.CUBOID,
                            "newCuboid",
                            List.of(new Parameter(ValueType.DOUBLE, "a"),
                                    new Parameter(ValueType.DOUBLE, "b"),
                                    new Parameter(ValueType.DOUBLE, "H")),
                            new Block(List.of(new CuboidFunction())))),
            Map.entry("newCone",
                    new FunctionDefinition(ValueType.CONE,
                            "newCone",
                            List.of(new Parameter(ValueType.DOUBLE, "r"), new Parameter(ValueType.DOUBLE, "l")),
                            new Block(List.of(new ConeFunction())))),
            Map.entry("newCylinder",
                    new FunctionDefinition(ValueType.CYLINDER,
                            "newCylinder",
                            List.of(new Parameter(ValueType.DOUBLE, "r"), new Parameter(ValueType.DOUBLE, "H")),
                            new Block(List.of(new CylinderFunction())))),
            Map.entry("newSphere",
                    new FunctionDefinition(ValueType.SPHERE,
                            "newSphere",
                            List.of(new Parameter(ValueType.DOUBLE, "R")),
                            new Block(List.of(new SphereFunction())))),
            Map.entry("newPyramid",
                    new FunctionDefinition(ValueType.PYRAMID,
                            "newPyramid",
                            List.of(new Parameter(ValueType.DOUBLE, "a"), new Parameter(ValueType.DOUBLE, "H")),
                            new Block(List.of(new PyramidFunction())))),
            Map.entry("newList",
                    new FunctionDefinition(ValueType.LIST,
                            "newList",
                            List.of(),
                            new Block(List.of(new ListFunction())))),
            Map.entry("showFigures",
                    new FunctionDefinition(ValueType.VOID,
                            "showFigures",
                            List.of(new Parameter(ValueType.LIST, "list")),
                            new Block(List.of(new ShowFiguresFunction())))));

    public static final Map<String, FunctionDefinition> CUBOID_FUNCTIONS = Map.ofEntries(Map.entry("volume",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "volume",
                            List.of(),
                            new Block(List.of(new CuboidVolumeFunction())))),
            Map.entry("baseSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "baseSurface",
                            List.of(),
                            new Block(List.of(new CuboidBaseSurfaceFunction())))),
            Map.entry("lateralSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "lateralSurface",
                            List.of(),
                            new Block(List.of(new CuboidLateralSurfaceFunction())))),
            Map.entry("totalSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "totalSurface",
                            List.of(),
                            new Block(List.of(new CuboidTotalSurfaceFunction())))));

    public static final Map<String, FunctionDefinition> PYRAMID_FUNCTIONS = Map.ofEntries(Map.entry("volume",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "volume",
                            List.of(),
                            new Block(List.of(new PyramidVolumeFunction())))),
            Map.entry("baseSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "baseSurface",
                            List.of(),
                            new Block(List.of(new PyramidBaseSurfaceFunction())))),
            Map.entry("lateralSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "lateralSurface",
                            List.of(),
                            new Block(List.of(new PyramidLateralSurfaceFunction())))),
            Map.entry("totalSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "totalSurface",
                            List.of(),
                            new Block(List.of(new PyramidTotalSurfaceFunction())))));

    public static final Map<String, FunctionDefinition> CONE_FUNCTIONS = Map.ofEntries(Map.entry("volume",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "volume",
                            List.of(),
                            new Block(List.of(new ConeVolumeFunction())))),
            Map.entry("baseSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "baseSurface",
                            List.of(),
                            new Block(List.of(new ConeBaseSurfaceFunction())))),
            Map.entry("lateralSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "lateralSurface",
                            List.of(),
                            new Block(List.of(new ConeLateralSurfaceFunction())))),
            Map.entry("totalSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "totalSurface",
                            List.of(),
                            new Block(List.of(new ConeTotalSurfaceFunction())))));
    public static final Map<String, FunctionDefinition> CYLINDER_FUNCTIONS = Map.ofEntries(Map.entry("volume",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "volume",
                            List.of(),
                            new Block(List.of(new CylinderVolumeFunction())))),
            Map.entry("baseSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "baseSurface",
                            List.of(),
                            new Block(List.of(new CylinderBaseSurfaceFunction())))),
            Map.entry("lateralSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "lateralSurface",
                            List.of(),
                            new Block(List.of(new CylinderLateralSurfaceFunction())))),
            Map.entry("totalSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "totalSurface",
                            List.of(),
                            new Block(List.of(new CylinderTotalSurfaceFunction())))));
    public static final Map<String, FunctionDefinition> SPHERE_FUNCTIONS = Map.ofEntries(Map.entry("volume",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "volume",
                            List.of(),
                            new Block(List.of(new SphereVolumeFunction())))),
            Map.entry("diameter",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "diameter",
                            List.of(),
                            new Block(List.of(new SphereDiameterFunction())))),
            Map.entry("totalSurface",
                    new FunctionDefinition(ValueType.DOUBLE,
                            "totalSurface",
                            List.of(),
                            new Block(List.of(new SphereTotalSurfaceFunction())))));
    public static final Map<String, FunctionDefinition> LIST_FUNCTIONS = Map.ofEntries(Map.entry("add",
                    new FunctionDefinition(ValueType.VOID,
                            "add",
                            List.of(new Parameter(ValueType.SPHERE, "value")),
                            new Block(List.of(new AddListFunction())))),
            Map.entry("iterator",
                    new FunctionDefinition(ValueType.ITERATOR,
                            "iterator",
                            List.of(),
                            new Block(List.of(new ListIteratorFunction())))));
}
