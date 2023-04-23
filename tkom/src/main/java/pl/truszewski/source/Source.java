package pl.truszewski.source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

import pl.truszewski.ErrorHandler;
import pl.truszewski.error.InvalidNewLineCharacterException;
import pl.truszewski.token.Position;

public class Source {
    private BufferedReader reader;
    private Position position;
    private String prevChar = null;
    private String currentChar = null;
    private String nextChar = null;
    private boolean newLineCharactersChecked = false;
    private String firstNewLineCharacter = null;
    private String secondNewLineChatacter = null;
    private boolean noNextChar;
    private ErrorHandler errorHandler;

    public Source(Reader reader, ErrorHandler errorHandler) {
        this.position = new Position();
        this.reader = new BufferedReader(reader);
        this.errorHandler = errorHandler;
    }

    public String getCharacter() {
        return this.currentChar;
    }

    public void nextCharacter() {
        try {
            if (nextChar == null)
                nextChar = readCharacter();
            this.prevChar = this.currentChar;
            this.currentChar = this.nextChar;
            this.nextChar = readCharacter();
            position.nextColumn();

            if (Objects.equals(currentChar, "\n") && Objects.equals(nextChar, "\r") && !newLineCharactersChecked) {
                firstNewLineCharacter = "\n";
                secondNewLineChatacter = "\r";
                newLineCharactersChecked = true;
            } else if (Objects.equals(currentChar, "\r") && Objects.equals(nextChar, "\n")
                    && !newLineCharactersChecked) {
                firstNewLineCharacter = "\r";
                secondNewLineChatacter = "\n";
                newLineCharactersChecked = true;
            } else if (Objects.equals(currentChar, "\n") && !newLineCharactersChecked) {
                firstNewLineCharacter = "\n";
                newLineCharactersChecked = true;
            } else if (Objects.equals(currentChar, "\r") && !newLineCharactersChecked) {
                secondNewLineChatacter = "\r";
                newLineCharactersChecked = true;
            }

            if (Objects.equals(prevChar, secondNewLineChatacter)
                    && Objects.equals(currentChar, firstNewLineCharacter)) {
                errorHandler.handleError(new InvalidNewLineCharacterException("Invalid new line character"), position);
            }
            if (firstNewLineCharacter != null && Objects.equals(prevChar, firstNewLineCharacter)
                    && secondNewLineChatacter == null) {
                position.nextRow();
                position.nextColumn();
            } else if (Objects.equals(prevChar, secondNewLineChatacter) && secondNewLineChatacter != null) {
                position.nextRow();
                position.nextColumn();
            }

        } catch (IOException e) {
            errorHandler.handleError(e, position);
        }

    }

    public Position getPosition() {
        return this.position;
    }

    public boolean isEOF() {
        return noNextChar && currentChar == null;
    }

    private String readCharacter() throws IOException {
        int readCharacter = this.reader.read();
        if (readCharacter == -1) {
            this.noNextChar = true;
            return null;
        }

        return Character.toString(readCharacter);
    }
}
