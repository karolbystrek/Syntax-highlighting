package pl.edu.agh.syntax.highlighting;

public enum TokenType {
    IDENTIFIER, // variable name i.e. myVar100
    INTEGER, // int i.e. -100
    FLOAT, // float i.e. 1.0
    STRING, // string i.e. "hello"
    BOOLEAN, // bool
    NULL, // null
    PLUS, // +
    MINUS, // -
    MULTIPLICATION, // *
    DIVISION, // /
    MODULO, // %
    INCREMENT, // ++
    ASSIGN, // =
    EQUALS, // ==
    DECREMENT, // --
    PLUS_EQUALS, // +=
    MINUS_EQUALS, // -=
    MULTIPLICATION_EQUALS, // *=
    DIVISION_EQUALS, // /=
    MODULO_EQUALS, // %=
    NOT_EQUALS, // !=
    GREATER_THAN, // >
    LESS_THAN, // <
    GREATER_THAN_EQUALS, // >=
    LESS_THAN_EQUALS, // <=
    LEFT_BRACKET, // (
    RIGHT_BRACKET, // )
    LEFT_SQUARE_BRACKET, // [
    RIGHT_SQUARE_BRACKET, // ]
    CONTINUE, // continue
    BREAK, // break
    SEMICOLON, // ;
    COMMA, // ,
    DOT, // .
    AND, // and
    OR, // or
    NOT, // not
    IF, // if
    THEN, // then
    ELSIF, // elsif
    ELSE, // else
    ENDIF, // endif
    WHILE, // while
    ENDWHILE, // endwhile
    FOR, // for
    ENDFOR, // endfor
    RETURN, // return
    FUNCTION, // function
    ENDFUNCTION, // endfunction
    CLASS, // class
    ENDCLASS, // endclass
    CONSTRUCTOR, // constructor
    ENDCONSTRUCTOR, // endconstructor
    PUBLIC, // public
    PRIVATE, // private
    PROTECTED, // protected
    STATIC, // static
    CONST, // const
    COMMENT, // #
    NEW, // new
    UNKNOWN; // unknown token

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
            case "!=":
                return NOT_EQUALS;
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
            case "#":
                return COMMENT;
        }

        switch (value.toLowerCase()) {
            case "and":
                return AND;
            case "or":
                return OR;
            case "not":
                return NOT;
            case "if":
                return IF;
            case "then":
                return THEN;
            case "elsif":
                return ELSIF;
            case "else":
                return ELSE;
            case "endif":
                return ENDIF;
            case "while":
                return WHILE;
            case "endwhile":
                return ENDWHILE;
            case "for":
                return FOR;
            case "endfor":
                return ENDFOR;
            case "return":
                return RETURN;
            case "function":
                return FUNCTION;
            case "endfunction":
                return ENDFUNCTION;
            case "class":
                return CLASS;
            case "endclass":
                return ENDCLASS;
            case "constructor":
                return CONSTRUCTOR;
            case "endconstructor":
                return ENDCONSTRUCTOR;
            case "public":
                return PUBLIC;
            case "private":
                return PRIVATE;
            case "protected":
                return PROTECTED;
            case "static":
                return STATIC;
            case "const":
                return CONST;
            case "new":
                return NEW;
            case "continue":
                return CONTINUE;
            case "break":
                return BREAK;
            case "null":
                return NULL;
            case "true":
            case "false":
                return BOOLEAN;
        }

        if (isInteger(value)) {
            return INTEGER;
        } else if (isFloat(value)) {
            return FLOAT;
        } else if (isString(value)) {
            return STRING;
        } else if (isIdentifier(value)) {
            return IDENTIFIER;
        }

        return UNKNOWN;
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
        int valueLength = value.length();
        int startIndex = 0;

        if (value.charAt(0) == '+' || value.charAt(0) == '-') {
            startIndex = 1;
        }
        boolean hasDecimalPoint = false;
        boolean hasDigits = false;
        for (int i = startIndex; i < valueLength; i++) {
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
        if (value.charAt(0) != '"' || value.charAt(value.length() - 1) != '"') {
            return false;
        }
        return true;
    }
}
