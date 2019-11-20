package com.company;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.Lab1Lexer;
import parser.Lab1Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    Parser() {
    }

    void readFromKeyboard() throws ParseCancellationException {
        Scanner scanner = new Scanner(System.in);
        Lab1Listener calc = new Lab1Listener();
        while (true) {
            try {
                System.out.print(">> ");
                parsing(scanner, calc);

            } catch (Exception e) {
                System.out.println("Error!");
            }
        }
    }

    void readFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            Lab1Listener calc = new Lab1Listener();
            while (scanner.hasNextLine()) {
                try {
                    parsing(scanner, calc);
                } catch (Exception e) {
                    System.out.println("Error!");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File couldn't be found");
        }
    }

    private void parsing(Scanner scanner, Lab1Listener calc) {
        String line = scanner.nextLine().trim();
        line = line + "\n";

        CharStream lineStream = CharStreams.fromString(line);
        Lexer lexer = new Lab1Lexer(lineStream);
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Lab1Parser parser = new Lab1Parser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        ParseTree tree = parser.start();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(calc, tree);
    }

}
