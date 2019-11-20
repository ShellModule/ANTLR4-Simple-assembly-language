package com.company;

import parser.Lab1BaseListener;
import parser.Lab1Parser;

import java.util.Random;
import java.util.Stack;

public class Lab1Listener extends Lab1BaseListener {
    private Stack<Integer> numberStack = new Stack<>();
    private Stack<String> registerStack = new Stack<>();
    private Integer eax = new Random().nextInt(1000) + 50;
    private Integer ebx = new Random().nextInt(1000) + 50;
    private Integer ecx = new Random().nextInt(1000) + 50;
    private Integer edx = new Random().nextInt(1000) + 50;


    @Override
    public void exitNumber(Lab1Parser.NumberContext ctx) {
        Integer number = Integer.parseInt(ctx.NUMBER().getText());
        this.numberStack.push(number);
    }

    @Override
    public void exitRegister(Lab1Parser.RegisterContext ctx) {
        String register = ctx.REGISTER().getText();
        this.registerStack.push(register);
    }

    @Override
    public void exitExpRegister(Lab1Parser.ExpRegisterContext ctx) {
        String register = ctx.registers().children.get(0).getText();
        switch (register.toLowerCase()) {
            case "%eax":
                this.numberStack.push(eax);
                break;
            case "%ebx":
                this.numberStack.push(ebx);
                break;
            case "%ecx":
                this.numberStack.push(ecx);
                break;
            case "%edx":
                this.numberStack.push(edx);
                break;
        }
    }

    @Override
    public void exitMov(Lab1Parser.MovContext ctx) {
        String register = registerStack.pop();
        switch (register.toLowerCase()) {
            case "%eax":
                this.eax = this.numberStack.pop();
                break;
            case "%ebx":
                this.ebx = this.numberStack.pop();
                break;
            case "%ecx":
                this.ecx = this.numberStack.pop();
                break;
            case "%edx":
                this.edx = this.numberStack.pop();
                break;
        }
    }

    @Override
    public void exitXor(Lab1Parser.XorContext ctx) {
        String register = registerStack.pop();
        switch (register.toLowerCase()) {
            case "%eax":
                this.eax = this.numberStack.pop() ^ this.eax;
                break;
            case "%ebx":
                this.ebx = this.numberStack.pop() ^ this.ebx;
                break;
            case "%ecx":
                this.ecx = this.numberStack.pop() ^ this.ecx;
                break;
            case "%edx":
                this.edx = this.numberStack.pop() ^ this.edx;
                break;
        }
    }

    @Override
    public void exitInt(Lab1Parser.IntContext ctx) {
        System.out.println(this.numberStack.pop());
    }

    @Override
    public void exitAdd(Lab1Parser.AddContext ctx) {
        Integer right = this.numberStack.pop();
        Integer left = this.numberStack.pop();
        this.numberStack.push(left + right);
    }

    @Override
    public void exitSubtract(Lab1Parser.SubtractContext ctx) {
        Integer right = this.numberStack.pop();
        Integer left = this.numberStack.pop();

        this.numberStack.push(left - right);
    }

    @Override
    public void exitMultiply(Lab1Parser.MultiplyContext ctx) {
        Integer right = this.numberStack.pop();
        Integer left = this.numberStack.pop();

        this.numberStack.push(left * right);
    }

    @Override
    public void exitDivide(Lab1Parser.DivideContext ctx) {
        Integer right = this.numberStack.pop();
        Integer left = this.numberStack.pop();

        this.numberStack.push(left / right);
    }

    @Override
    public void exitExpression(Lab1Parser.ExpressionContext ctx) {
        System.out.println(this.numberStack.pop());
    }
}
