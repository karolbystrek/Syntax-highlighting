package pl.edu.agh.syntax.highlighting;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Scanner implements Closeable {

    private final BufferedReader reader;
    private String currentLine;
    private int lineIndex;
    private int columnIndex;
    private boolean loadedNewLine;
    private boolean isFirstLine;


    public Scanner(InputStream inputStream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.lineIndex = 0;
        this.columnIndex = 0;
        this.currentLine = null;
        this.loadedNewLine = false;
        this.isFirstLine = true;
    }


    public Token nextToken() throws IOException {
        if (currentLine != null) {
            skipWhitespaces();
        }
        while (currentLine == null || columnIndex >= currentLine.length()) {
            try {
                loadNextLine();
                if (!isFirstLine) {
                    loadedNewLine = true;
                } else {
                    isFirstLine = false;
                }
            } catch (EOFException e) {
                return null;
            }

            skipWhitespaces();
            if (currentLine.isEmpty()) {
                return new Token(TokenType.NEW_LINE, "\\n", lineIndex, 0);
            }
        }

        if (loadedNewLine) {
            loadedNewLine = false;
            return new Token(TokenType.NEW_LINE, "\\n", lineIndex, 0);
        }

        char currentChar = currentLine.charAt(columnIndex);

        if (currentChar == '"') {
            return handleString();
        }

        if (currentChar == '#') {
            return handleComment();
        }

        return handleDefaultToken();
    }


    private Token handleDefaultToken() {
        int startColumn = columnIndex;
        StringBuilder tokenValueBuilder = new StringBuilder();
        TokenType tokenValueType = TokenType.UNKNOWN;

        while (columnIndex < currentLine.length()) {
            char c = currentLine.charAt(columnIndex);
            String tentative = tokenValueBuilder.toString() + c;
            TokenType tentativeType = TokenType.fromValue(tentative);

            if (tentativeType == TokenType.UNKNOWN) {
                if (tokenValueBuilder.length() > 0) {
                    break;
                } else {
                    columnIndex++;
                    startColumn = columnIndex;
                    continue;
                }
            } else {
                tokenValueBuilder.append(c);
                tokenValueType = tentativeType;
                columnIndex++;
            }
        }
        return new Token(tokenValueType, tokenValueBuilder.toString(), lineIndex, startColumn);
    }


    private Token handleString() {
        int startColumn = columnIndex;
        StringBuilder tokenValueBuilder = new StringBuilder();

        char currentChar = currentLine.charAt(columnIndex);
        tokenValueBuilder.append(currentChar);
        columnIndex++;

        while (columnIndex < currentLine.length()) {
            currentChar = currentLine.charAt(columnIndex);
            tokenValueBuilder.append(currentChar);
            columnIndex++;
            if (currentChar == '"') {
                break;
            }
        }
        return new Token(
                TokenType.STRING,
                tokenValueBuilder.toString(),
                lineIndex,
                startColumn
        );
    }


    private Token handleComment() {
        int startColumn = columnIndex;
        StringBuilder tokenValueBuilder = new StringBuilder();

        char currentChar = currentLine.charAt(columnIndex);
        tokenValueBuilder.append(currentChar);
        columnIndex++;

        while (columnIndex < currentLine.length()) {
            currentChar = currentLine.charAt(columnIndex);
            tokenValueBuilder.append(currentChar);
            columnIndex++;
        }

        return new Token(
                TokenType.COMMENT,
                tokenValueBuilder.toString(),
                lineIndex,
                startColumn
        );
    }


    private void loadNextLine() throws IOException, EOFException {
        currentLine = reader.readLine();
        if (currentLine == null) {
            throw new EOFException();
        }
        lineIndex++;
        columnIndex = 0;
    }


    private void skipWhitespaces() {
        while (columnIndex < currentLine.length()
                && Character.isWhitespace(currentLine.charAt(columnIndex))) {
            columnIndex++;
        }
    }


    @Override
    public void close() throws IOException {
        reader.close();
    }
}
