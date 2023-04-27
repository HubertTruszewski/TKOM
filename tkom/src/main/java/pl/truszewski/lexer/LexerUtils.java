package pl.truszewski.lexer;

import java.util.Map;

import pl.truszewski.token.TokenType;

public class LexerUtils {
    public static final Map<String, TokenType> keywordsMap = Map.ofEntries(
            Map.entry("return", TokenType.RETURN),
            Map.entry("while", TokenType.WHILE),
            Map.entry("if", TokenType.IF),
            Map.entry("as", TokenType.AS),
            Map.entry("int", TokenType.INTEGER),
            Map.entry("string", TokenType.STRING),
            Map.entry("double", TokenType.DOUBLE),
            Map.entry("bool", TokenType.BOOL),
            Map.entry("void", TokenType.VOID),
            Map.entry("Cone", TokenType.CONE),
            Map.entry("Cylinder", TokenType.CYLINDER),
            Map.entry("Sphere", TokenType.SPHERE),
            Map.entry("Cuboid", TokenType.CUBOID),
            Map.entry("Pyramid", TokenType.PYRAMID),
            Map.entry("Screen", TokenType.SCREEN));

    private LexerUtils() {
    }
}
