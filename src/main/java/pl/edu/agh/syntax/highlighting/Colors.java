package pl.edu.agh.syntax.highlighting;

public enum Colors {
    IDENTIFIER("#000000"),
    KEYWORD("#0000FF"),
    PRIMITIVE_DATA_TYPE("#008080"),
    INTEGER("#800000"),
    FLOAT("#800000"),
    STRING("#008000"),
    BOOL("#0000FF"),
    AND("#A52A2A"),
    OR("#A52A2A"),
    NOT("#A52A2A"),
    PLUS("#A52A2A"),
    MINUS("#A52A2A"),
    MULTIPLICATION("#A52A2A"),
    DIVISION("#A52A2A"),
    MODULO("#A52A2A"),
    INCREMENT("#A52A2A"),
    DECREMENT("#A52A2A"),
    ASSIGN("#A52A2A"),
    EQUALS("#A52A2A"),
    PLUS_EQUALS("#A52A2A"),
    MINUS_EQUALS("#A52A2A"),
    MULTIPLICATION_EQUALS("#A52A2A"),
    DIVISION_EQUALS("#A52A2A"),
    MODULO_EQUALS("#A52A2A"),
    NOT_EQUALS("#A52A2A"),
    GREATER_THAN("#A52A2A"),
    LESS_THAN("#A52A2A"),
    GREATER_THAN_EQUALS("#A52A2A"),
    LESS_THAN_EQUALS("#A52A2A"),
    LEFT_BRACKET("#000000"),
    RIGHT_BRACKET("#000000"),
    LEFT_SQUARE_BRACKET("#000000"),
    RIGHT_SQUARE_BRACKET("#000000"),
    SEMICOLON("#000000"),
    COMMA("#000000"),
    DOT("#000000"),
    COLON("#000000"),
    COMMENT("#808080"),
    UNKNOWN("#FF0000"),
    NEW_LINE("#000000");

    private final String hexColor;

    Colors(String hexColor) {
        this.hexColor = hexColor;
    }

    public static String getColorFor(TokenType tokenType) {
        try {
            return valueOf(tokenType.name()).getHexColor();
        } catch (IllegalArgumentException e) {
            return "#000000";
        }
    }

    public String getHexColor() {
        return hexColor;
    }
}
