package com.example.karyc.calculator;

import java.util.Stack;

public class ExpressionSolver {
    static public double calculate(String input) {
        String modifiedInput = "";
        for (int i = input.length() - 1; i >= 0; i--) {
            char currentSymbol = input.charAt(i);
            boolean isFirstSymbol = i == 0;

            if (currentSymbol == '-') {
                if (isFirstSymbol == true) {
                    modifiedInput = "0-" + modifiedInput;//модификация выражения для унарного минуса
                } else {
                    char previousSymbol = input.charAt(i - 1);
                    boolean isDigit = isDigit(previousSymbol);
                    if (isDigit == true) {
                        modifiedInput = currentSymbol + modifiedInput;
                    } else {
                        boolean isCloseBracket = isCloseBracket(previousSymbol);
                        if (isCloseBracket == true) {
                            modifiedInput = currentSymbol + modifiedInput;
                        } else {
                            modifiedInput = "0-" + modifiedInput;
                        }
                    }
                }
            } else {
                modifiedInput = currentSymbol + modifiedInput;
            }
        }

        String output = getExpression(modifiedInput); //преобразование выражения в постфиксную запись
        double result = counting(output); //решение полученного выражения
        return result; //результат
    }

    static private String getExpression(String input) {
        String output = "";
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            if (isDelimeter(input.charAt(i)))
                continue;

            if (isDigit(input.charAt(i))) {
                while (!isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) {
                    output += input.charAt(i); //добавление каждой цифры числа к строке
                    i++;
                    if (i == input.length()) break;
                }
                output += " "; //Дописываем после числа пробел в строку с выражением
                i--;
            }

            if (isOperator(input.charAt(i))) {
                if (input.charAt(i) == '(')
                    operatorStack.push(input.charAt(i));
                else if (input.charAt(i) == ')') {
                    char s = operatorStack.pop();//Выписываем все операторы до открывающей скобки в строку

                    while (s != '(') {
                        output += s + " ";
                        s = operatorStack.pop();
                    }
                } else {
                    if (operatorStack.size() > 0) //если в стеке есть элементы
                        if (getPriority(input.charAt(i)) <= getPriority(operatorStack.peek()))
                            output += operatorStack.pop().toString() + " "; //то добавляем последний оператор из стека в строку с выражением

                    operatorStack.push(input.charAt(i)); //если стек пуст или же приоритет оператора выше - добавляем операторов на вершину стека
                }
            }
        }

        //когда прошли по всем символам, выкидываем из стека все оставшиеся там операторы в строку
        while (operatorStack.size() > 0)
            output += operatorStack.pop() + " ";

        return output; //выражение в постфиксной записи
    }

    static private double counting(String input) {
        double result = 0;
        Stack<Double> temp = new Stack<>(); //стек для решения

        for (int i = 0; i < input.length(); i++) {
            if (isDigit(input.charAt(i))) {
                String a = "";

                while (!isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) {
                    a += input.charAt(i);
                    i++;
                    if (i == input.length()) break;
                }
                temp.push(Double.valueOf(a));
                i--;
            } else if (isOperator(input.charAt(i))) {
                //возврат два последних значения из стека
                double a = temp.pop();
                double b = temp.pop();

                switch (input.charAt(i)) //действие, согласно оператору
                {
                    case '+':
                        result = b + a;
                        break;
                    case '-':
                        result = b - a;
                        break;
                    case '*':
                        result = b * a;
                        break;
                    case '/':
                        result = b / a;
                        break;
                }
                temp.push(result);
            }
        }
        return temp.peek();
    }

    static private boolean isDelimeter(char c) {
        if ((" =".indexOf(c) != -1)) { // true если символ - равно
            return true;
        }
        return false;
    }

    static private boolean isOperator(char с) {
        if ("+-/*()".indexOf(с) != -1) { //true если символ - оператор
            return true;
        }
        return false;
    }

    static private boolean isCloseBracket(char c) {
        if (")".indexOf(c) != -1) { //true если символ - закрывающая скобка
            return true;
        }
        return false;
    }

    static private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    static private byte getPriority(char s) {
        switch (s) {
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '*':
                return 4;
            case '/':
                return 4;
            default:
                return 5;
        }
    }
}
