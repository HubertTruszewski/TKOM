package pl.truszewski.lexer;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;

import pl.truszewski.ErrorHandler;
import pl.truszewski.error.InvalidTokenException;
import pl.truszewski.error.MissingSecondTokenCharacter;
import pl.truszewski.error.TooLargeNumberException;
import pl.truszewski.error.TooLongIdentifierException;
import pl.truszewski.error.TooLongStringException;
import pl.truszewski.source.Source;
import pl.truszewski.token.DoubleToken;
import pl.truszewski.token.EmptyToken;
import pl.truszewski.token.IntegerToken;
import pl.truszewski.token.Position;
import pl.truszewski.token.StringToken;
import pl.truszewski.token.Token;
import pl.truszewski.token.TokenType;

public class LexerImpl implements Lexer {
    private static final int MAX_IDENTIFIER_LENGTH = 50;
    private static final int STRING_LENGTH_LIMIT = 1000;
    private Source source;
    private String character;
    private Token currentToken;
    private ErrorHandler errorHandler;

    public LexerImpl(Source source, ErrorHandler errorHandler) {
        this.source = source;
        this.errorHandler = errorHandler;
        this.character = null;
        this.currentToken = null;
        nextCharacter();
    }

    @Override
    public Token next() {
        while ((this.character != null && this.character.isBlank()) && !this.source.isEOF())
            nextCharacter();
        if (tryBuildEOFToken()
                || tryBuildNumber()
                || tryBuildIdentOrKeyword()
                || tryBuildString()
                || tryBuildSlashOrComment()
                || tryBuildSimpleTokens())
            return this.currentToken;
        errorHandler.handleError(new InvalidTokenException("Unknown token"), new Position(source.getPosition()));
        return new EmptyToken(TokenType.UKNKOWN, new Position(source.getPosition()));
    }

    private boolean tryBuildEOFToken() {
        if (this.source.isEOF()) {
            this.currentToken = new EmptyToken(TokenType.EOF, new Position(source.getPosition()));
            return true;
        }
        return false;
    }

    private boolean tryBuildSlashOrComment() {
        if (this.character.equals("/")) {
            Position tokenPosition = new Position(source.getPosition());
            nextCharacter();
            if (this.character.equals("/")) {
                nextCharacter();
                StringBuilder stringBuilder = new StringBuilder();
                while (!source.isEOF() && !this.character.equals("\n") && !this.character.equals("\r")) {
                    stringBuilder.append(this.character);
                    nextCharacter();
                }
                this.currentToken = new StringToken(TokenType.COMMENT, stringBuilder.toString(), tokenPosition);
                return true;
            }
            this.currentToken = new EmptyToken(TokenType.SLASH, tokenPosition);
            return true;
        }
        return false;
    }

    private boolean tryBuildSimpleTokens() {
        if (tryBuildSingleOrDoubleCharacterToken())
            return true;
        if (this.character.equals("&")) {
            Position position = new Position(source.getPosition());
            nextCharacter();
            if (this.character.equals("&")) {
                this.currentToken = new EmptyToken(TokenType.AND, new Position(source.getPosition()));
                return true;
            }
            errorHandler.handleError(new MissingSecondTokenCharacter("Missing second token character"),
                    new Position(source.getPosition()));
            this.currentToken = new EmptyToken(TokenType.UKNKOWN, position);
            return true;
        }
        if (this.character.equals("|")) {
            Position position = new Position(source.getPosition());
            nextCharacter();
            if (this.character.equals("||")) {
                this.currentToken = new EmptyToken(TokenType.AND, new Position(source.getPosition()));
                return true;
            }
            errorHandler.handleError(new MissingSecondTokenCharacter("Missing second token character"),
                    new Position(source.getPosition()));
            this.currentToken = new EmptyToken(TokenType.UKNKOWN, position);
            return true;
        }

        if (LexerUtils.singleCharacterTokens.containsKey(this.character)) {
            this.currentToken = new EmptyToken(LexerUtils.singleCharacterTokens.get(this.character),
                    new Position(source.getPosition()));
            return true;
        }

        return false;
    }

    private boolean tryBuildNumber() {
        if (!Character.isDigit(this.character.codePointAt(0)))
            return false;
        int value = this.character.codePointAt(0) - '0';
        Position position = new Position(source.getPosition());
        if (value != 0) {
            nextCharacter();
            while (Character.isDigit(this.character.codePointAt(0))) {
                int decimal = this.character.codePointAt(0) - '0';
                if ((Integer.MAX_VALUE - decimal) / 10 - value > 0) {
                    value = value * 10 + (character.codePointAt(0) - '0');
                } else {
                    errorHandler.handleError(new TooLargeNumberException("Too large number"), position);
                    this.currentToken = new EmptyToken(TokenType.UKNKOWN, position);
                }
                nextCharacter();
            }
        }
        if (this.character.equals(".")) {
            int decimalDigitCounter = 0;
            nextCharacter();
            int fraction = this.character.codePointAt(0) - '0';
            if (fraction != 0) {
                ++decimalDigitCounter;
                nextCharacter();
                while (Character.isDigit(this.character.codePointAt(0))) {
                    int decimal = this.character.codePointAt(0) - '0';
                    if ((Integer.MAX_VALUE - decimal) / 10 - fraction > 0) {
                        fraction = fraction * 10 + (character.codePointAt(0) - '0');
                        ++decimalDigitCounter;
                    } else {
                        errorHandler.handleError(new TooLargeNumberException("Too large number"), position);
                        this.currentToken = new EmptyToken(TokenType.UKNKOWN, position);
                    }
                    nextCharacter();
                }
            }
            this.currentToken = new DoubleToken(value + (fraction * Math.pow(10, -decimalDigitCounter)),
                    position);

        } else {
            this.currentToken = new IntegerToken(value, position);
        }
        return true;
    }

    private boolean tryBuildString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.character.equals("\"")) {
            Position position = new Position(source.getPosition());
            nextCharacter();
            boolean process = true;

            while (process) {
                if (stringBuilder.length() > STRING_LENGTH_LIMIT) {
                    errorHandler.handleError(new TooLongStringException("Too long string"), position);
                    return true;
                }
                if (this.character.equals("\"") && stringBuilder.charAt(stringBuilder.length() - 1) != '\\') {
                    process = false;
                } else {
                    stringBuilder.append(character);
                }
                nextCharacter();
            }
            String value = StringEscapeUtils.unescapeJava(stringBuilder.toString());
            this.currentToken = new StringToken(TokenType.TEXT, value, position);
            return true;
        }
        return false;
    }

    private boolean tryBuildIdentOrKeyword() {
        if (Character.isLetter(this.character.codePointAt(0))) {
            Position tokenPosition = new Position(source.getPosition());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.character);
            nextCharacter();
            while (Character.isLetter(this.character.codePointAt(0))
                    || Character.isDigit(this.character.codePointAt(0))) {
                stringBuilder.append(this.character);
                nextCharacter();
                if (stringBuilder.length() > MAX_IDENTIFIER_LENGTH) {
                    errorHandler.handleError(new TooLongIdentifierException("Too long identifier"), tokenPosition);
                    this.currentToken = new EmptyToken(TokenType.UKNKOWN, tokenPosition);
                    return true;
                }
            }

            if (LexerUtils.keywordsMap.containsKey(stringBuilder.toString())) {
                this.currentToken = new EmptyToken(LexerUtils.keywordsMap.get(stringBuilder.toString()), tokenPosition);
                return true;
            }

            this.currentToken = new StringToken(TokenType.IDENTIFIER, stringBuilder.toString(), tokenPosition);
            return true;
        }
        return false;

    }

    private void nextCharacter() {
        source.nextCharacter();
        this.character = source.getCharacter();
    }

    private boolean tryBuildSingleOrDoubleCharacterToken() {

        Set<String> doubleCharacterTokensBeginnings = LexerUtils.doubleCharacterTokens
                .keySet()
                .stream()
                .map(doubleCharacterToken -> doubleCharacterToken.substring(0, 1))
                .collect(Collectors.toSet());
        Set<String> commonTokenBeginnings = LexerUtils.singleCharacterTokens
                .keySet()
                .stream()
                .filter(doubleCharacterTokensBeginnings::contains)
                .collect(Collectors.toSet());

        if (commonTokenBeginnings.contains(this.character)) {
            Position tokenPosition = new Position(source.getPosition());
            String firstCharacter = this.character;
            nextCharacter();
            String doubleCharacterToken = firstCharacter.concat(this.character);

            if (LexerUtils.doubleCharacterTokens.containsKey(doubleCharacterToken)) {
                this.currentToken = new EmptyToken(LexerUtils.doubleCharacterTokens.get(doubleCharacterToken),
                        tokenPosition);
                return true;
            }

            this.currentToken = new EmptyToken(LexerUtils.singleCharacterTokens.get(firstCharacter), tokenPosition);
            return true;
        }
        return false;
    }
}