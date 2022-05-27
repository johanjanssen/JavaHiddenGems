package com.example;

public class PitestExample {
    private int variable;

    public PitestExample(int variable) {
        this.variable = variable;
    }

    public int getVariable() {
        return variable;
    }

    public void setVariable(int variable) {
        this.variable = variable;
    }

    public int higherOrVariable(int value1, int value2) {
        int highest = 0;
        if (value2 > value1) {
            highest = value2;
        } else {
            highest = value1;
        }

        if (variable > highest) {
            return variable;
        } else {
            return highest;
        }
    }
}
