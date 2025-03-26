package pl.edu.agh.syntax.highlighting;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum TokenType {
    IDENTIFIER,

    KEYWORD,
    PRIMITIVE_DATA_TYPE,

    INTEGER,
    FLOAT,
    STRING,

    BOOL,

    AND,
    OR,
    NOT,

    PLUS,
    MINUS,
    MULTIPLICATION,
    DIVISION,
    MODULO,

    INCREMENT,
    DECREMENT,
    ASSIGN,

    EQUALS,
    PLUS_EQUALS,
    MINUS_EQUALS,
    MULTIPLICATION_EQUALS,
    DIVISION_EQUALS,
    MODULO_EQUALS,
    NOT_EQUALS,
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUALS,
    LESS_THAN_EQUALS,

    LEFT_BRACKET,
    RIGHT_BRACKET,
    LEFT_SQUARE_BRACKET,
    RIGHT_SQUARE_BRACKET,

    SEMICOLON,
    COMMA,
    DOT,
    COLON,

    COMMENT,

    UNKNOWN,

    NEW_LINE;

    private static final Set<String> KEYWORDS;

    static {
        Set<String> temp = new HashSet<>();

        Collections.addAll(
                temp,
                "if",
                "then",
                "elsif",
                "else",
                "endif",
                "while",
                "endwhile",
                "for",
                "endfor",
                "return",
                "function",
                "endfunction",
                "class",
                "endclass",
                "constructor",
                "endconstructor",
                "public",
                "private",
                "protected",
                "static",
                "const",
                "new",
                "continue",
                "break",
                "extends",
                "implements",
                "try",
                "catch",
                "throw");
        KEYWORDS = Collections.unmodifiableSet(temp);
    }

    public static TokenType fromValue(String value) {
        if (value == null || value.isEmpty()) {
            return UNKNOWN;
        }

        switch (value) {
            case "+":
                return PLUS;
            case "-":
                return MINUS;
            case "*":
                return MULTIPLICATION;
            case "/":
                return DIVISION;
            case "%":
                return MODULO;
            case "++":
                return INCREMENT;
            case "--":
                return DECREMENT;
            case "!=":
                return NOT_EQUALS;
            case "=":
                return ASSIGN;
            case "==":
                return EQUALS;
            case "+=":
                return PLUS_EQUALS;
            case "-=":
                return MINUS_EQUALS;
            case "*=":
                return MULTIPLICATION_EQUALS;
            case "/=":
                return DIVISION_EQUALS;
            case "%=":
                return MODULO_EQUALS;
            case ">":
                return GREATER_THAN;
            case "<":
                return LESS_THAN;
            case ">=":
                return GREATER_THAN_EQUALS;
            case "<=":
                return LESS_THAN_EQUALS;
            case "(":
                return LEFT_BRACKET;
            case ")":
                return RIGHT_BRACKET;
            case "[":
                return LEFT_SQUARE_BRACKET;
            case "]":
                return RIGHT_SQUARE_BRACKET;
            case ";":
                return SEMICOLON;
            case ",":
                return COMMA;
            case ".":
                return DOT;
            case ":":
                return COLON;
            case "#":
                return COMMENT;
            default:
        }

        String lowerCase = value.toLowerCase();
        if (KEYWORDS.contains(lowerCase)) {
            return KEYWORD;
        }
        if (isDataType(value)) {
            return PRIMITIVE_DATA_TYPE;
        }
        if (lowerCase.equals("true") || lowerCase.equals("false")) {
            return BOOL;
        }
        if (isInteger(value)) {
            return INTEGER;
        } else if (isFloat(value)) {
            return FLOAT;
        } else if (isString(value)) {
            return STRING;
        }

        if (isIdentifier(value)) {
            return IDENTIFIER;
        }

        return UNKNOWN;
    }



    private static boolean isDataType(String value) {
        Set<String> dataTypes = new HashSet<>();
        Collections.addAll(dataTypes,
                "int",
                "float",
                "double",
                "bool");
        if (dataTypes.contains(value)) {
            return true;
        }
        return false;
    }

    private static boolean isIdentifier(String value) {
        if (Character.isDigit(value.charAt(0))) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isInteger(String value) {
        int startIndex = 0;
        if (value.length() > 1 && (value.charAt(0) == '+' || value.charAt(0) == '-')) {
            startIndex = 1;
        }
        for (int i = startIndex; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return value.length() > startIndex;
    }

    private static boolean isFloat(String value) {
        int startIndex = 0;
        if (value.charAt(0) == '+' || value.charAt(0) == '-') {
            startIndex = 1;
        }
        boolean hasDecimalPoint = false;
        boolean hasDigits = false;
        for (int i = startIndex; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '.') {
                if (hasDecimalPoint) {
                    return false;
                }
                hasDecimalPoint = true;
            } else if (Character.isDigit(c)) {
                hasDigits = true;
            } else {
                return false;
            }
        }
        return hasDigits && hasDecimalPoint;
    }

    private static boolean isString(String value) {
        return value.length() >= 2
                && value.charAt(0) == '"'
                && value.charAt(value.length() - 1) == '"';
    }
}
