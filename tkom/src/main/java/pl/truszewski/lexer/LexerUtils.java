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

        public static final Map<String, TokenType> singleCharacterTokens = Map.ofEntries(
                        Map.entry("(", TokenType.LEFT_ROUND_BRACKET),
                        Map.entry(")", TokenType.RIGHT_ROUND_BRACKET),
                        Map.entry("[", TokenType.LEFT_SQUARE_BRACKET),
                        Map.entry("]", TokenType.RIGHT_SQUARE_BRACKET),
                        Map.entry("{", TokenType.LEFT_CURLY_BRACKET),
                        Map.entry("}", TokenType.RIGHT_CURLY_BRACKET),
                        Map.entry(",", TokenType.COMMA),
                        Map.entry(".", TokenType.DOT),
                        Map.entry("=", TokenType.EQUAL_SIGN),
                        Map.entry(">", TokenType.GREATER_SIGN),
                        Map.entry("<", TokenType.LESS_SIGN),
                        Map.entry("!", TokenType.EXCLAMATION_MARK),
                        Map.entry(";", TokenType.SEMICOLON),
                        Map.entry("+", TokenType.PLUS_SIGN),
                        Map.entry("-", TokenType.MINUS_SIGN),
                        Map.entry("*", TokenType.ASTERISK_SIGN),
                        Map.entry("/", TokenType.SLASH),
                        Map.entry("\\", TokenType.BACKSLASH));

        public static final Map<String, TokenType> doubleCharacterTokens = Map.ofEntries(
                        Map.entry(">=", TokenType.GREATER_OR_EQUAL),
                        Map.entry("<=", TokenType.LESS_OR_EQUAL),
                        Map.entry("==", TokenType.EQUALS),
                        Map.entry("!=", TokenType.NOT_EQUALS),
                        Map.entry("&&", TokenType.AND),
                        Map.entry("||", TokenType.OR),
                        Map.entry("//", TokenType.COMMENT));

        public static final Map<String, Integer> escapeCharactersMap = Map.ofEntries(
                        Map.entry("b", 8),
                        Map.entry("t", 9),
                        Map.entry("n", 10),
                        Map.entry("f", 12),
                        Map.entry("r", 13),
                        Map.entry("\"", 34),
                        Map.entry("'", 39),
                        Map.entry("\\", 92));

        private LexerUtils() {
        }
}
