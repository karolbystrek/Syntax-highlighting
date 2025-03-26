package pl.edu.agh.syntax.highlighting;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        String filePath = "Program_test_1.txt";

        List<Token> tokens = new LinkedList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(filePath))) {
            Token token;
            while ((token = scanner.nextToken()) != null) {
                tokens.add(token);
                System.out.println(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
