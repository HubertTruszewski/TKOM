package pl.truszewski.token;

public enum TokenType {
    LEFT_ROUND_BRACKET("Left round brackets", "("),
    RIGHT_ROUND_BRACKET("Right round brackets", ")"),
    LEFT_SQUARE_BRACKET("Left square brackets", "["),
    RIGHT_SQUARE_BRACKET("Right square brackets", "]"),
    LEFT_CURLY_BRACKET("Left curly bracket", "{"),
    RIGHT_CURLY_BRACKET("Right curly bracket", "}"),
    COMMA("Comma", ","),
    DOT("Dot", "."),
    EQUAL_SIGN("Equal sign", "="),
    GREATER_SIGN("Greater sign", ">"),
    LESS_SIGN("Less sign", "<"),
    EXCLAMATION_MARK("Exclamation mark", "!"),
    SEMICOLON("Semicolon", ";"),
    PLUS_SIGN("Plus sign", "+"),
    MINUS_SIGN("Minus sign", "-"),
    ASTERISK_SIGN("Asterisk sign", "*"),
    SLASH("Slash", "/"),
    BACKSLASH("Backslash", "\\"),

    GREATER_OR_EQUAL("Greater or equal", ">="),
    LESS_OR_EQUAL("Less or equal", "<="),
    EQUALS("Equals", "=="),
    NOT_EQUALS("Not equals", "!="),
    AND("And", "&&"),
    OR("Or", "||"),
    COMMENT("Comment", "//"),

    RETURN("return keyword", "return"),
    WHILE("while loop keyword", "while"),
    IF("if instruction keyword", "if"),
    ELSE("if instruction else keyword", "else"),
    AS("As casting operator", "as"),

    INTEGER("Integer type keyword", "int"),
    STRING("String type keyword", "string"),
    DOUBLE("Double type keyword", "double"),
    BOOL("Bool type keyword", "bool"),
    VOID("Void type keyword", "void"),
    CONE("Cone type keyword", "Cone"),
    CYLINDER("Cylinder type keyword", "Cylinder"),
    SPHERE("Sphere type keyword", "Sphere"),
    CUBOID("Cuboid type keyword", "Cuboid"),
    PYRAMID("Pyramid type keyword", "Pyramid"),
    LIST("List type keyword", "List"),
    ITERATOR("Iterator type keyword", "Iterator"),

    IDENTIFIER("Identifier", null),
    TEXT("Text", null),

    INT_NUMBER("Int number", null),
    DOUBLE_NUMBER("Double number", null),

    UKNKOWN("Unknown token", null),

    EOF("End of file", null);

    private final String name;
    private final String character;

    TokenType(final String name, final String character) {
        this.name = name;
        this.character = character;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

}
