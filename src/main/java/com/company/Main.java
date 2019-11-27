package com.company;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        if (args.length > 0) {
            File file = new File(args[0]);
            parser.readFromFile(file);
        } else {
            parser.readFromKeyboard();
        }
    }
}
