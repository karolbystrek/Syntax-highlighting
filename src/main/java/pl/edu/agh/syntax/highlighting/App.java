package pl.edu.agh.syntax.highlighting;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        String input = "2+3*(76+8/3)+ 3*(9-3) endclass class if ifif == =";
        String filePath = "src/main/resources/Program_test_1.txt";

        List<Token> tokens = new LinkedList<>();

        try (Scanner scanner = new Scanner(
                // new ByteArrayInputStream(input.getBytes()))) {
                new FileInputStream(filePath))) {
            Token token;
            while ((token = scanner.nextToken()) != null) {
                tokens.add(token);
                System.out.println(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (Token token : tokens) {
//            System.out.println(token.toString());
//        }

    }
}
