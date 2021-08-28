package com.example.workshop2;

public class Question {

    private int operatorA;
    private int operatorB;
    private String operation;
    private String[] operations = {"+", "-","x", "/"};

    public Question(){
        this.operatorA= (int) (Math.random() * 11);
        this.operatorB= (int) ((Math.random() * 11)+1);

        this.operation= operations[(int) (Math.random() * 4)];

    }


    public String getQuestion(){
        return operatorA+ " "+ operation + " "+ operatorB;
    }

    public int getAnswer(){
        int answer=0;

        switch(operation){
            case "+":
                answer= this.operatorA+this.operatorB;
                break;
            case "-":
                answer= this.operatorA-this.operatorB;
                break;
            case "x":
                answer= this.operatorA*this.operatorB;
                break;
            case "/":
                answer= this.operatorA/this.operatorB;
                break;
        }

        return answer;
    }

}
