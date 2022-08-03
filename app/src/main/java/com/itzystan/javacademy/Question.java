package com.itzystan.javacademy;

public class Question {
    String questionText;
    String codeText;
    String answer0;
    String answer1;
    String answer2;
    String answer3;
    int correctAnswer;
    int playerAnswer;

    public Question(String questionString,
                    String codeString,
                    String answerZero,
                    String answerOne,
                    String answerTwo,
                    String answerThree,
                    int correctAnswerIndex) {
        questionText = questionString;
        codeText = codeString;
        answer0 = answerZero;
        answer1 = answerOne;
        answer2 = answerTwo;
        answer3 = answerThree;
        correctAnswer = correctAnswerIndex;
        playerAnswer = -1;
    }

    public boolean isCorrect() {
        return playerAnswer == correctAnswer;
    }
}
