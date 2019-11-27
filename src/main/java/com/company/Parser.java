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
import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Parser {
    Parser() {
    }

    void readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        Lab1Listener lab1 = new Lab1Listener();

        while (scanner.hasNextLine()) {
            parsing(scanner, lab1);
        }
    }

    void readFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            Lab1Listener lab1 = new Lab1Listener();
            while (scanner.hasNextLine()) {
                parsing(scanner, lab1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File couldn't be found");
        }
    }

    private void parsing(Scanner scanner, Lab1Listener lab1) throws ParseCancellationException {
        try {
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
            walker.walk(lab1, tree);
        } catch (ParseCancellationException | EmptyStackException error) {
            System.out.println("Error!");
        } catch (NoSuchElementException ignored) {
        }
    }

}
