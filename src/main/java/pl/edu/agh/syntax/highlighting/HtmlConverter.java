package pl.edu.agh.syntax.highlighting;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HtmlConverter {

    private static final String filePath = "Program_test_1.html";
    private static final String TAB = "&nbsp;&nbsp;&nbsp;&nbsp;";

    private static final Set<String> INDENT_INCREASE_KEYWORDS = new HashSet<>();
    private static final Set<String> INDENT_DECREASE_KEYWORDS = new HashSet<>();
    private static final Set<String> INDENT_SAME_LEVEL_KEYWORDS = new HashSet<>();

    private static TokenType prevTokenType = null;

    static {
        INDENT_INCREASE_KEYWORDS.add("if");
        INDENT_INCREASE_KEYWORDS.add("while");
        INDENT_INCREASE_KEYWORDS.add("for");
        INDENT_INCREASE_KEYWORDS.add("function");
        INDENT_INCREASE_KEYWORDS.add("class");
        INDENT_INCREASE_KEYWORDS.add("constructor");
        INDENT_INCREASE_KEYWORDS.add("try");

        INDENT_DECREASE_KEYWORDS.add("endif");
        INDENT_DECREASE_KEYWORDS.add("endwhile");
        INDENT_DECREASE_KEYWORDS.add("endfor");
        INDENT_DECREASE_KEYWORDS.add("endfunction");
        INDENT_DECREASE_KEYWORDS.add("endclass");
        INDENT_DECREASE_KEYWORDS.add("endconstructor");
        INDENT_DECREASE_KEYWORDS.add("endtry");

        INDENT_SAME_LEVEL_KEYWORDS.add("catch");
        INDENT_SAME_LEVEL_KEYWORDS.add("else");
        INDENT_SAME_LEVEL_KEYWORDS.add("elsif");
    }

    public static void convert(List<Token> tokens) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append(generateHtmlHeader());

        int indentLevel = 0;
        boolean newLine = true;
        prevTokenType = null;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            TokenType tokenType = token.getType();
            String tokenValue = escapeHtml(token.getValue());
            String tokenLowerCase = tokenValue.toLowerCase();

            if (tokenType == TokenType.NEW_LINE) {
                htmlContent.append(handleNewLine());

                i++;
                if (i < tokens.size()) {
                    Token nextToken = tokens.get(i);
                    String nextTokenValue = nextToken.getValue().toLowerCase();
                    if (nextToken.getType() == TokenType.KEYWORD) {
                        if (INDENT_DECREASE_KEYWORDS.contains(nextTokenValue) ||
                                INDENT_SAME_LEVEL_KEYWORDS.contains(nextTokenValue)) {
                            indentLevel = Math.max(0, indentLevel - 1);
                        }
                    }
                    i--;
                }

                newLine = true;
                prevTokenType = tokenType;
                continue;
            }

            if (newLine) {
                htmlContent.append(generateIndent(indentLevel));
                newLine = false;
            }

            htmlContent.append(formatToken(token, prevTokenType));

            if (tokenType == TokenType.KEYWORD) {
                if (INDENT_INCREASE_KEYWORDS.contains(tokenLowerCase) ||
                        INDENT_SAME_LEVEL_KEYWORDS.contains(tokenLowerCase)) {
                    indentLevel++;
                }
            }
            prevTokenType = tokenType;
        }

        htmlContent.append(generateHtmlFooter());
        writeHtmlToFile(htmlContent.toString());
    }

    private static String generateHtmlHeader() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Syntax Highlighted Code</title>\n" +
                "    <style>\n" +
                "        body { font-family: monospace; background-color: #f8f8f8; padding: 20px; }\n" +
                "        .token { display: inline; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n";
    }

    private static String generateHtmlFooter() {
        return "\n</div>\n" +
                "</body>\n" +
                "</html>";
    }

    private static String handleNewLine() {
        return "<br>\n";
    }

    private static String generateIndent(int indentLevel) {
        StringBuilder indent = new StringBuilder("<span class=\"indent\">");
        for (int j = 0; j < indentLevel; j++) {
            indent.append(TAB);
        }
        indent.append("</span>");
        return indent.toString();
    }

    private static String formatToken(Token token, TokenType prevTokenType) {
        TokenType tokenType = token.getType();
        String tokenValue = escapeHtml(token.getValue());
        String color = Colors.getColorFor(tokenType);

        StringBuilder formattedToken = new StringBuilder();
        formattedToken.append("<span class=\"token\" style=\"color:")
                .append(color)
                .append("\">");

        if (prevTokenType == TokenType.LEFT_BRACKET ||
                prevTokenType == TokenType.DOT ||
                prevTokenType == TokenType.LEFT_SQUARE_BRACKET) {
            formattedToken.append(tokenValue);
        }
        else if (tokenType == TokenType.SEMICOLON ||
                tokenType == TokenType.LEFT_BRACKET ||
                tokenType == TokenType.LEFT_SQUARE_BRACKET ||
                tokenType == TokenType.INCREMENT ||
                tokenType == TokenType.DECREMENT ||
                tokenType == TokenType.DOT ||
                tokenType == TokenType.COMMA) {
            formattedToken.append(tokenValue);
        } else if (tokenType == TokenType.RIGHT_BRACKET ||
                tokenType == TokenType.RIGHT_SQUARE_BRACKET) {
            formattedToken.append(tokenValue);
        }
        else {
            formattedToken.append(" ").append(tokenValue);
        }

        formattedToken.append("</span>");
        return formattedToken.toString();
    }

    private static void writeHtmlToFile(String htmlContent) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
