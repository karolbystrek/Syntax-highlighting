package pl.edu.agh.syntax.highlighting;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HtmlConverter {

    private static final String filePath = "Program_test_1.html";
    private static final String TAB = "&nbsp;&nbsp;&nbsp;&nbsp;";

    // Keywords that increase indentation level
    private static final Set<String> INDENT_INCREASE_KEYWORDS = new HashSet<>();
    // Keywords that decrease indentation level
    private static final Set<String> INDENT_DECREASE_KEYWORDS = new HashSet<>();
    // Keywords that maintain current indentation level (like "else")
    private static final Set<String> INDENT_SAME_LEVEL_KEYWORDS = new HashSet<>();

    static {
        // Keywords that increase indentation for the following block
        INDENT_INCREASE_KEYWORDS.add("if");
        INDENT_INCREASE_KEYWORDS.add("while");
        INDENT_INCREASE_KEYWORDS.add("for");
        INDENT_INCREASE_KEYWORDS.add("function");
        INDENT_INCREASE_KEYWORDS.add("class");
        INDENT_INCREASE_KEYWORDS.add("constructor");
        INDENT_INCREASE_KEYWORDS.add("try");

        // Keywords that decrease indentation because they end a block
        INDENT_DECREASE_KEYWORDS.add("endif");
        INDENT_DECREASE_KEYWORDS.add("endwhile");
        INDENT_DECREASE_KEYWORDS.add("endfor");
        INDENT_DECREASE_KEYWORDS.add("endfunction");
        INDENT_DECREASE_KEYWORDS.add("endclass");
        INDENT_DECREASE_KEYWORDS.add("endconstructor");
        INDENT_DECREASE_KEYWORDS.add("endtry");

        // Keywords that maintain the current indentation level
        INDENT_SAME_LEVEL_KEYWORDS.add("catch");
        INDENT_SAME_LEVEL_KEYWORDS.add("else");
        INDENT_SAME_LEVEL_KEYWORDS.add("elsif");
    }

    public static void convert(List<Token> tokens) {
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<!DOCTYPE html>\n")
                  .append("<html>\n")
                  .append("<head>\n")
                  .append("    <meta charset=\"UTF-8\">\n")
                  .append("    <title>Syntax Highlighted Code</title>\n")
                  .append("    <style>\n")
                  .append("        body { font-family: monospace; background-color: #f8f8f8; padding: 20px; }\n")
                  .append("        .token { display: inline; }\n")
                  .append("    </style>\n")
                  .append("</head>\n")
                  .append("<body>\n")
                  .append("<div>\n");

        int indentLevel = 0;
        boolean newLine = true;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            TokenType tokenType = token.getType();
            String tokenValue = escapeHtml(token.getValue());
            String tokenLowerCase = tokenValue.toLowerCase();

            if (tokenType == TokenType.NEW_LINE) {
                htmlContent.append("<br>\n");
                newLine = true;

                if (i + 1 < tokens.size()) {
                    Token nextToken = tokens.get(i + 1);
                    String nextTokenValue = nextToken.getValue().toLowerCase();
                    if (nextToken.getType() == TokenType.KEYWORD) {
                        if (INDENT_DECREASE_KEYWORDS.contains(nextTokenValue)) {
                            indentLevel = Math.max(0, indentLevel - 1);
                        } else if (INDENT_SAME_LEVEL_KEYWORDS.contains(nextTokenValue)) {
                            indentLevel = Math.max(0, indentLevel - 1);
                        }
                    }
                }
                continue;
            }

            if (newLine) {
                htmlContent.append("<span class=\"indent\">");
                for (int j = 0; j < indentLevel; j++) {
                    htmlContent.append(TAB);
                }
                htmlContent.append("</span>");
                newLine = false;
            }

            String color = Colors.getColorFor(tokenType);
            htmlContent.append("<span class=\"token\" style=\"color:")
                    .append(color)
                    .append("\">");

            if (tokenType == TokenType.SEMICOLON ||
                tokenType == TokenType.LEFT_BRACKET ||
                tokenType == TokenType.LEFT_SQUARE_BRACKET) {
                htmlContent.append(tokenValue).append("</span>");
            } else if (tokenType == TokenType.RIGHT_BRACKET ||
                      tokenType == TokenType.RIGHT_SQUARE_BRACKET) {
                htmlContent.append(tokenValue).append(" </span>");
            } else {
                htmlContent.append(tokenValue).append(" </span>");
            }

            if (tokenType == TokenType.KEYWORD) {
                if (INDENT_INCREASE_KEYWORDS.contains(tokenLowerCase)) {
                    indentLevel++;
                } else if (INDENT_SAME_LEVEL_KEYWORDS.contains(tokenLowerCase)) {
                    indentLevel++;
                }
            }
        }

        htmlContent.append("\n</div>\n")
                  .append("</body>\n")
                  .append("</html>");

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(htmlContent.toString());
            System.out.println("HTML file generated successfully at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing HTML file: " + e.getMessage());
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
