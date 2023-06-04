package pl.truszewski.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.SneakyThrows;
import pl.truszewski.ErrorHandler;
import pl.truszewski.error.parser.CannotAssignValueToExpressionError;
import pl.truszewski.error.parser.DuplicatedFunctionNameError;
import pl.truszewski.error.parser.DuplicatedParameterError;
import pl.truszewski.error.parser.MissingCharacterError;
import pl.truszewski.error.parser.MissingCodeBlockError;
import pl.truszewski.error.parser.MissingExpressionError;
import pl.truszewski.error.parser.MissingExpressionSideError;
import pl.truszewski.error.parser.MissingIdentifierError;
import pl.truszewski.error.parser.MissingParameterError;
import pl.truszewski.error.parser.MissingTypeError;
import pl.truszewski.error.parser.ParserError;
import pl.truszewski.error.parser.UnexpectedStatementError;
import pl.truszewski.lexer.Lexer;
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
import pl.truszewski.programstructure.statements.ConditionalStatement;
import pl.truszewski.programstructure.statements.DeclarationStatement;
import pl.truszewski.programstructure.statements.IfStatement;
import pl.truszewski.programstructure.statements.ReturnStatement;
import pl.truszewski.programstructure.statements.Statement;
import pl.truszewski.programstructure.statements.WhileStatement;
import pl.truszewski.token.Position;
import pl.truszewski.token.Token;
import pl.truszewski.token.TokenType;

public class ParserImpl implements Parser {
    private final Lexer lexer;
    private Token currentToken;
    private final ErrorHandler errorHandler;

    public ParserImpl(final Lexer lexer, final ErrorHandler errorHandler) {
        this.lexer = lexer;
        this.errorHandler = errorHandler;
        nextToken();
    }

    // program = {function_declaration};
    @Override
    public Program parse() {
        final Map<String, FunctionDefinition> functionDefinitions = new HashMap<>();
        while (parseFunctionDefinition(functionDefinitions))
            ;
        if (!isTokenType(TokenType.EOF)) {
            errorHandler.handleParserError(new UnexpectedStatementError("Expected end of file",
                    new Position(currentToken.getPosition())));
        }
        return new Program(functionDefinitions);
    }

    // function_declaration = = type, identifier, "(", [function_parameters] ")", code_block;
    private boolean parseFunctionDefinition(final Map<String, FunctionDefinition> functionDefinitions) {
        if (!isValueType()) {
            return false;
        }
        ValueType functionReturnValueType = mapTokenToValueType(currentToken.getTokenType());
        consumeCurrent();
        if (!isTokenType(TokenType.IDENTIFIER)) {
            handleCriticalError(new MissingIdentifierError("Identifier expected",
                    new Position(currentToken.getPosition())));
        }
        String functionName = (String) currentToken.getValue();
        consumeCurrent();
        if (functionDefinitions.containsKey(functionName)) {
            handleCriticalError(new DuplicatedFunctionNameError("Duplicated function name: " + functionName,
                    new Position(currentToken.getPosition())));
        }

        if (!consumeIf(TokenType.LEFT_ROUND_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character (",
                    "(",
                    new Position(currentToken.getPosition())));

        }
        List<Parameter> parameters = parseParameters();
        if (!consumeIf(TokenType.RIGHT_ROUND_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character )",
                    ")",
                    new Position(currentToken.getPosition())));
        }
        Block block = parseBlock();
        functionDefinitions.put(functionName,
                new FunctionDefinition(functionReturnValueType, functionName, parameters, block));

        return true;
    }

    // function_parameters = parameter_declaration, {",", parameter_declaration};
    private List<Parameter> parseParameters() {
        List<Parameter> parameterList = new ArrayList<>();
        List<String> parameterNames = new ArrayList<>();
        Parameter parameter = parseParameter();
        if (parameter == null) {
            return parameterList;
        }
        parameterList.add(parameter);
        parameterNames.add(parameter.name());
        while (consumeIf(TokenType.COMMA)) {
            parameter = parseParameter();
            if (parameter == null) {
                handleCriticalError(new MissingParameterError("Missing parameter",
                        new Position(currentToken.getPosition())));
            } else if (parameterNames.contains(parameter.name())) {
                handleCriticalError(new DuplicatedParameterError("Duplicated parameter name",
                        parameter.name(),
                        new Position(currentToken.getPosition())));
            } else {
                parameterList.add(parameter);
                parameterNames.add(parameter.name());
            }
        }
        return parameterList;
    }

    // parameter_declaration = type, identifier;
    private Parameter parseParameter() {
        if (isTokenType(TokenType.RIGHT_ROUND_BRACKET)) {
            return null;
        }
        ValueType valueType = mapTokenToValueType(currentToken.getTokenType());
        consumeCurrent();
        if (!isTokenType(TokenType.IDENTIFIER)) {
            handleCriticalError(new MissingIdentifierError("Missing identifier",
                    new Position(currentToken.getPosition())));
            return null;
        }
        String parameterName = (String) currentToken.getValue();
        consumeCurrent();
        return new Parameter(valueType, parameterName);
    }

    // code_block = "{", {statement}, "}";
    private Block parseBlock() {
        if (!consumeIf(TokenType.LEFT_CURLY_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character {",
                    "{",
                    new Position(currentToken.getPosition())));
        }
        List<Statement> statements = new ArrayList<>();
        Statement statement = parseStatement();
        while (statement != null) {
            statements.add(statement);
            statement = parseStatement();
        }
        if (!consumeIf(TokenType.RIGHT_CURLY_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character }",
                    "}",
                    new Position(currentToken.getPosition())));
        }

        return new Block(statements);
    }

    /* statement = conditional_statement
                   | variable_declaration
                   | return_statement
                   | variable_assignment_or_expression;
     */
    private Statement parseStatement() {
        Statement statement = parseConditionalStatement();
        if (statement != null) {
            return statement;
        }
        statement = parseDeclarationStatement();
        if (statement != null) {
            return statement;
        }
        statement = parseReturnStatement();
        if (statement != null) {
            return statement;
        }
        return parseAssignmentStatementOrExpression();
    }

    // conditional_statement = if_statement | while_loop;
    private ConditionalStatement parseConditionalStatement() {
        ConditionalStatement statement = parseIfStatement();
        if (statement != null) {
            return statement;
        }
        return parseWhileStatement();
    }

    // if_statement = "if (", expression, ")", code_block, ["else", code_block];
    private ConditionalStatement parseIfStatement() {
        if (!consumeIf(TokenType.IF)) {
            return null;
        }
        if (!consumeIf(TokenType.LEFT_ROUND_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character (",
                    "(",
                    new Position(currentToken.getPosition())));
        }
        Expression expression = parseExpression();
        if (!consumeIf(TokenType.RIGHT_ROUND_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character )",
                    ")",
                    new Position(currentToken.getPosition())));
        }
        Block trueBlock = parseBlock();
        if (trueBlock == null) {
            handleCriticalError(new MissingCodeBlockError("Missing code block",
                    new Position(currentToken.getPosition())));
        }
        if (!consumeIf(TokenType.ELSE)) {
            return new IfStatement(expression, trueBlock, null);
        }
        Block elseBlock = parseBlock();
        if (elseBlock == null) {
            handleCriticalError(new MissingCodeBlockError("Missing code block",
                    new Position(currentToken.getPosition())));
        }
        return new IfStatement(expression, trueBlock, elseBlock);
    }

    // expression = or_expression;
    private Expression parseExpression() {
        return parseOrExpression();
    }

    // or_expression = and_expression, {or_operator, and_expression};
    private Expression parseOrExpression() {
        Expression left = parseAndExpression();
        if (left == null) {
            return null;
        }
        while (consumeIf(TokenType.OR)) {
            Expression right = parseAndExpression();
            if (right == null) {
                handleCriticalError(new MissingExpressionSideError("Missing right side expression",
                        "right",
                        new Position(currentToken.getPosition())));
            }
            left = new OrExpression(left, right);
        }
        return left;
    }

    // and_expression = comparison_expression, {and_operator, comparison_expression};
    private Expression parseAndExpression() {
        Expression left = parseComparisonExpression();
        if (left == null) {
            return null;
        }
        while (consumeIf(TokenType.AND)) {
            Expression right = parseComparisonExpression();
            if (right == null) {
                handleCriticalError(new MissingExpressionSideError("Missing right side expression",
                        "right",
                        new Position(currentToken.getPosition())));
            }
            left = new AndExpression(left, right);
        }
        return left;
    }

    // comparison_expression = addition_expression, {comparison_opearator, addition_expression};
    private Expression parseComparisonExpression() {
        Expression left = parseAdditionExpression();
        if (left == null) {
            return null;
        }
        while (isRelationOperator()) {
            TokenType relationTokenType = currentToken.getTokenType();
            consumeCurrent();
            Expression right = parseAdditionExpression();
            if (right == null) {
                handleCriticalError(new MissingExpressionSideError("Missing right side expression",
                        "right",
                        new Position(currentToken.getPosition())));
            }
            left = mapTokenTypeToExpression(relationTokenType, left, right);
        }
        return left;
    }

    // addition_expression = multiplication_expression, {addition_operator, multiplication_expression};
    private Expression parseAdditionExpression() {
        Expression left = parseMultiplicationExpression();
        if (left == null) {
            return null;
        }
        while (isAdditionOperator()) {
            TokenType additionTokenType = currentToken.getTokenType();
            consumeCurrent();
            Expression right = parseMultiplicationExpression();
            if (right == null) {
                handleCriticalError(new MissingExpressionSideError("Missing right side expression",
                        "right",
                        new Position(currentToken.getPosition())));
            }
            left = mapTokenTypeToExpression(additionTokenType, left, right);
        }
        return left;
    }

    // multiplication_expression = casting_expression, {multiplication_operator, casting_expression};
    private Expression parseMultiplicationExpression() {
        Expression left = parseCastingExpression();
        if (left == null) {
            return null;
        }
        while (isMultiplicationOperator()) {
            TokenType multiplicationTokenType = currentToken.getTokenType();
            consumeCurrent();
            Expression right = parseCastingExpression();
            if (right == null) {
                handleCriticalError(new MissingExpressionSideError("Missing right side expression",
                        "right",
                        new Position(currentToken.getPosition())));
            }
            left = mapTokenTypeToExpression(multiplicationTokenType, left, right);
        }
        return left;
    }

    // casting_expression = negation_expression, [casting_operator, type];
    private Expression parseCastingExpression() {
        Expression expression = parseNegationExpression();
        if (!consumeIf(TokenType.AS)) {
            return expression;
        }
        if (!isValueType()) {
            handleCriticalError(new MissingTypeError("Expected type", new Position(currentToken.getPosition())));
            return null;
        }
        TokenType tokenType = currentToken.getTokenType();
        consumeCurrent();
        return new CastingExpression(expression, mapTokenToValueType(tokenType));
    }

    // negation_expression = [negation_operator | minus_sign], access_expression;;
    private Expression parseNegationExpression() {
        boolean negated = consumeIf(TokenType.EXCLAMATION_MARK) || consumeIf(TokenType.MINUS_SIGN);
        Expression expression = parseAccessExpression();
        return negated ? new NegationExpression(expression) : expression;
    }

    // access_expression = simple_expression, {access_operator, identifier_or_function_call};
    private Expression parseAccessExpression() {
        Expression left = parseSimpleExpression();
        if (left == null) {
            return null;
        }
        while (consumeIf(TokenType.DOT)) {
            Expression right = parseIdentifierOrFunctionCall();
            if (right == null) {
                handleCriticalError(new MissingExpressionSideError("Missing right side expression",
                        "right",
                        new Position(currentToken.getPosition())));
            }
            left = new AccessExpression(left, right);
        }
        return left;
    }

    // simple_expression = number | string_literal | identifier_or_function_call | "(", expression, ")";
    private Expression parseSimpleExpression() {
        Expression expression = parseNumber();
        if (expression != null) {
            return expression;
        }
        if (isTokenType(TokenType.TEXT)) {
            String text = (String) currentToken.getValue();
            consumeCurrent();
            return new StringExpression(text);
        }
        expression = parseIdentifierOrFunctionCall();
        if (expression != null) {
            return expression;
        }
        if (!consumeIf(TokenType.LEFT_ROUND_BRACKET)) {
            return null;
        }
        expression = parseExpression();
        if (expression == null) {
            handleCriticalError(new MissingExpressionError("Missing expression",
                    new Position(currentToken.getPosition())));
            return null;
        }
        if (!consumeIf(TokenType.RIGHT_ROUND_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character )",
                    ")",
                    new Position(currentToken.getPosition())));
        }
        return expression;
    }

    // number = int_number | float_number;
    private Expression parseNumber() {
        if (isTokenType(TokenType.INT_NUMBER)) {
            Integer value = (Integer) currentToken.getValue();
            consumeCurrent();
            return new IntNumber(value);
        }
        if (isTokenType(TokenType.DOUBLE_NUMBER)) {
            Double value = (Double) currentToken.getValue();
            consumeCurrent();
            return new DoubleNumber(value);
        }
        return null;
    }

    // identifier_or_function_call = identifier, [function_call_parameters];
    private Expression parseIdentifierOrFunctionCall() {
        if (!isTokenType(TokenType.IDENTIFIER)) {
            return null;
        }
        String identifierName = (String) currentToken.getValue();
        consumeCurrent();
        List<Expression> parameters = parseFunctionCallParameters();
        return parameters == null ?
                new IdentifierExpression(identifierName) :
                new FunctionCall(identifierName, parameters);
    }

    // function_call_parameters = "(", [expression, {",", expression}], ")";
    private List<Expression> parseFunctionCallParameters() {
        if (!consumeIf(TokenType.LEFT_ROUND_BRACKET)) {
            return null;
        }
        List<Expression> parametersList = new ArrayList<>();
        if (!isTokenType(TokenType.LEFT_ROUND_BRACKET)) {
            Expression parameter = parseExpression();
            if (parameter == null) {
                if (!consumeIf(TokenType.RIGHT_ROUND_BRACKET)) {
                    handleCriticalError(new MissingCharacterError("Missing character )",
                            ")",
                            new Position(currentToken.getPosition())));
                }
                return parametersList;
            } else {
                parametersList.add(parameter);
            }
            while (consumeIf(TokenType.COMMA)) {
                parameter = parseExpression();
                if (parameter == null) {
                    handleCriticalError(new MissingExpressionError("Missing expression",
                            new Position(currentToken.getPosition())));
                } else {
                    parametersList.add(parameter);
                }
            }
            if (!consumeIf(TokenType.RIGHT_ROUND_BRACKET)) {
                handleCriticalError(new MissingCharacterError("Missing character )",
                        ")",
                        new Position(currentToken.getPosition())));
            }
            return parametersList;
        }
        return null;
    }

    // while_statement = "while (", expression, ")", code_block;
    private ConditionalStatement parseWhileStatement() {
        if (!consumeIf(TokenType.WHILE)) {
            return null;
        }
        if (!consumeIf(TokenType.LEFT_ROUND_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character (",
                    "(",
                    new Position(currentToken.getPosition())));
        }
        Expression expression = parseExpression();
        if (!consumeIf(TokenType.RIGHT_ROUND_BRACKET)) {
            handleCriticalError(new MissingCharacterError("Missing character )",
                    ")",
                    new Position(currentToken.getPosition())));
        }
        Block codeBlock = parseBlock();
        return new WhileStatement(expression, codeBlock);
    }

    // variable_declaration = type, identifier, "=", expression ";";
    private Statement parseDeclarationStatement() {
        if (!isValueType()) {
            return null;
        }
        ValueType tokenValueType = mapTokenToValueType(currentToken.getTokenType());
        consumeCurrent();
        if (!isTokenType(TokenType.IDENTIFIER)) {
            handleCriticalError(new MissingIdentifierError("Missing identifier",
                    new Position(currentToken.getPosition())));
        }
        String variableName = (String) currentToken.getValue();
        consumeCurrent();
        if (!consumeIf(TokenType.EQUAL_SIGN)) {
            handleCriticalError(new MissingCharacterError("Missing character =",
                    "=",
                    new Position(currentToken.getPosition())));
        }
        Expression expression = parseExpression();
        if (expression == null) {
            handleCriticalError(new MissingExpressionError("Missing expression",
                    new Position(currentToken.getPosition())));
        }
        if (!consumeIf(TokenType.SEMICOLON)) {
            errorHandler.handleParserError(new MissingCharacterError("Missing character ;",
                    ";",
                    new Position(currentToken.getPosition())));
        }
        return new DeclarationStatement(tokenValueType, variableName, expression);
    }

    // variable_assignment = [identifier, "="], expression, ";";
    private Statement parseAssignmentStatementOrExpression() {
        Expression leftExpression = parseExpression();
        if (leftExpression == null) {
            return null;
        }

        if (!consumeIf(TokenType.EQUAL_SIGN)) {
            if (consumeIf(TokenType.SEMICOLON)) {
                return leftExpression;
            }
            errorHandler.handleParserError(new MissingCharacterError("Missing character ;",
                    ";",
                    new Position(currentToken.getPosition())));
        }

        if (leftExpression instanceof IdentifierExpression identifierExpression) {
            Expression rightExpression = parseExpression();
            if (!consumeIf(TokenType.SEMICOLON)) {
                errorHandler.handleParserError(new MissingCharacterError("Missing character ;",
                        ";",
                        new Position(currentToken.getPosition())));
            }
            return new AssignmentStatement(identifierExpression.identifierName(), rightExpression);
        }
        handleCriticalError(new CannotAssignValueToExpressionError("Cannot assign value to expression",
                currentToken.getPosition()));
        return null;

    }

    // return_statement = "return", [expression], ";";
    private Statement parseReturnStatement() {
        if (!consumeIf(TokenType.RETURN)) {
            return null;
        }
        Expression expression = parseExpression();
        if (!consumeIf(TokenType.SEMICOLON)) {
            errorHandler.handleParserError(new MissingCharacterError("Missing character ;",
                    ";",
                    new Position(currentToken.getPosition())));
        }
        return new ReturnStatement(expression);
    }

    private void nextToken() {
        currentToken = lexer.next();
    }

    private boolean consumeIf(TokenType tokenType) {
        if (isTokenType(tokenType)) {
            nextToken();
            return true;
        }
        return false;
    }

    private void consumeCurrent() {
        nextToken();
    }

    private boolean isTokenType(TokenType tokenType) {
        return currentToken.getTokenType() == tokenType;
    }

    private boolean isValueType() {
        return isTokenType(TokenType.INTEGER) || isTokenType(TokenType.DOUBLE) || isTokenType(TokenType.STRING)
                || isTokenType(TokenType.VOID) || isTokenType(TokenType.BOOL) || isTokenType(TokenType.CONE)
                || isTokenType(TokenType.CYLINDER) || isTokenType(TokenType.SPHERE) || isTokenType(TokenType.CUBOID)
                || isTokenType(TokenType.PYRAMID) || isTokenType(TokenType.LIST) || isTokenType(TokenType.ITERATOR);
    }

    private boolean isRelationOperator() {
        return isTokenType(TokenType.GREATER_SIGN) || isTokenType(TokenType.GREATER_OR_EQUAL)
                || isTokenType(TokenType.LESS_SIGN) || isTokenType(TokenType.LESS_OR_EQUAL)
                || isTokenType(TokenType.EQUALS) || isTokenType(TokenType.NOT_EQUALS);
    }

    private boolean isAdditionOperator() {
        return isTokenType(TokenType.PLUS_SIGN) || isTokenType(TokenType.MINUS_SIGN);
    }

    private boolean isMultiplicationOperator() {
        return isTokenType(TokenType.ASTERISK_SIGN) || isTokenType(TokenType.SLASH);
    }

    private ValueType mapTokenToValueType(TokenType tokenType) {
        return switch (tokenType) {
            case INTEGER -> ValueType.INT;
            case DOUBLE -> ValueType.DOUBLE;
            case STRING -> ValueType.STRING;
            case BOOL -> ValueType.BOOL;
            case VOID -> ValueType.VOID;
            case CYLINDER -> ValueType.CYLINDER;
            case CONE -> ValueType.CONE;
            case PYRAMID -> ValueType.PYRAMID;
            case SPHERE -> ValueType.SPHERE;
            case CUBOID -> ValueType.CUBOID;
            case LIST -> ValueType.LIST;
            case ITERATOR -> ValueType.ITERATOR;
            default -> null;
        };
    }

    private Expression mapTokenTypeToExpression(TokenType tokenType, Expression left, Expression right) {
        return switch (tokenType) {
            case GREATER_SIGN -> new GreaterExpression(left, right);
            case GREATER_OR_EQUAL -> new GreaterEqualExpression(left, right);
            case LESS_SIGN -> new LessExpression(left, right);
            case LESS_OR_EQUAL -> new LessEqualExpression(left, right);
            case EQUALS -> new EqualityExpression(left, right);
            case NOT_EQUALS -> new InequalityExpression(left, right);
            case PLUS_SIGN -> new AddExpression(left, right);
            case MINUS_SIGN -> new SubtractExpression(left, right);
            case ASTERISK_SIGN -> new MultiplyExpression(left, right);
            case SLASH -> new DivideExpression(left, right);
            default -> null;
        };
    }

    @SneakyThrows
    private void handleCriticalError(ParserError error) {
        errorHandler.handleParserError(error);
        throw error;
    }

}
