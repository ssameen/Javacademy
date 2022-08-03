package com.itzystan.javacademy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    int currentQuestionIndex;
    int totalCorrect;
    int totalQuestions;
    ArrayList<Question> questions;

    // TODO 3-A: Declare View member variables
    TextView questionCodeTextView;
    TextView questionTextView;
    TextView questionsRemainingTextView;
    Button answer0Button;
    Button answer1Button;
    Button answer2Button;
    Button answer3Button;
    Button submitButton;
    TextView questionRemainingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO 2-G: Show app icon in ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setElevation(0);

        setContentView(R.layout.activity_main);

        // TODO 3-B: assign View member variables
        questionCodeTextView = findViewById(R.id.tv_main_question_code);
        questionTextView = findViewById(R.id.tv_main_question_title);
        questionsRemainingTextView = findViewById(R.id.tv_main_questions_remaining_count);
        answer0Button = findViewById(R.id.btn_main_answer_0);
        answer1Button = findViewById(R.id.btn_main_answer_1);
        answer2Button = findViewById(R.id.btn_main_answer_2);
        answer3Button = findViewById(R.id.btn_main_answer_3);
        submitButton = findViewById(R.id.btn_main_submit_answer);
        questionRemainingText = findViewById(R.id.tv_main_questions_remaining);
        questionRemainingText.setText("QUESTIONS REMAINING");

        // TODO 4-E: set onClickListener for each answer Button
        answer0Button.setOnClickListener(v -> onAnswerSelected(0));
        answer1Button.setOnClickListener(v -> onAnswerSelected(1));
        answer2Button.setOnClickListener(v -> onAnswerSelected(2));
        answer3Button.setOnClickListener(v -> onAnswerSelected(3));

        // TODO 5-A: set onClickListener for the submit answer Button
        submitButton.setOnClickListener(v -> onAnswerSubmission());
        startNewGame();
    }

    // TODO 3-F: displayQuestion(Question question) {...}
    void displayQuestion(Question question){
        questionTextView.setText(question.questionText);
        answer0Button.setText(question.answer0);
        answer1Button.setText(question.answer1);
        answer2Button.setText(question.answer2);
        answer3Button.setText(question.answer3);
        questionCodeTextView.setText(question.codeText);
    }
    // TODO 3-C: displayQuestionsRemaining(int questionRemaining) {...}
    void displayQuestionsRemaining(int questionRemaining){
        questionsRemainingTextView.setText(Integer.valueOf(questionRemaining).toString());
    }
    // TODO 4-A: onAnswerSelected(int answerSelected) {...}
    void onAnswerSelected(int answerSelected){
        Question currentQuestion = getCurrentQuestion();
        currentQuestion.playerAnswer = answerSelected;
        answer0Button.setText(currentQuestion.answer0);
        answer1Button.setText(currentQuestion.answer1);
        answer2Button.setText(currentQuestion.answer2);
        answer3Button.setText(currentQuestion.answer3);
        // Include a check mark with the selected answer
        switch (answerSelected) {
            case 0:
                answer0Button.setText(new StringBuilder().append("✔ ").append(currentQuestion.answer0).toString());
                break;
            case 1:
                answer1Button.setText(new StringBuilder().append("✔ ").append(currentQuestion.answer1).toString());
                break;
            case 2:
                answer2Button.setText(new StringBuilder().append("✔ ").append(currentQuestion.answer2).toString());
                break;
            case 3:
                answer3Button.setText(new StringBuilder().append("✔ ").append(currentQuestion.answer3).toString());
                break;
            default:
                break;
        }
    }

    void onAnswerSubmission() {
        Question currentQuestion = getCurrentQuestion();
        if(currentQuestion.playerAnswer == -1) return;
        else{
            if (currentQuestion.isCorrect()) {
                totalCorrect = totalCorrect + 1;
            }
            questions.remove(currentQuestion);

            // TODO 3-D.i: Uncomment the line below after implementing displayQuestionsRemaining(int)
            displayQuestionsRemaining(questions.size());

            if (questions.size() == 0) {
                String gameOverMessage = getGameOverMessage(totalCorrect, totalQuestions);

                // TODO 5-D: Show a popup instead
                AlertDialog.Builder gameOverDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                gameOverDialogBuilder.setCancelable(false);
                gameOverDialogBuilder.setTitle("Game Over!");
                gameOverDialogBuilder.setMessage(gameOverMessage);
                gameOverDialogBuilder.setPositiveButton("Play Again!", (dialog, which) -> startNewGame());
                gameOverDialogBuilder.create().show();
            } else {
                chooseNewQuestion();

                // TODO 3-H.i: uncomment after implementing displayQuestion(Question)
                displayQuestion(getCurrentQuestion());
            }
        }
    }

    void startNewGame() {
        questions = new ArrayList<>();

        // TODO 2-H: Provide actual drawables for each of these questions!
        Question question0 = new Question("The following Boolean expression will be evaluated to what value?", "!(false) && (5 > 4)", "True", "False", "(5 > 4)", "Unknown", 0);
        Question question1 = new Question("How can you modify this code so that it won’t cause an infinite while loop?", "int i = 7;\n" +
                "while (i > 0) {\n" +
                "  System.out.println(i);\n" +
                "}", "Add i-- inside the while loop", "Add i++ inside the while loop", "Add i-- after the while loop", "Add i++ after the while loop", 0);
        Question question2 = new Question("When would it be helpful to use a for loop instead of a for-each loop when iterating over an ArrayList of items?", "", "When you're worried about causing an infinite loop.", "When you don't know how long the arraylist would be.", "When each each item in the arraylist has different value.", "When you aren't starting from the beginning of the arraylist.", 3);
        Question question3 = new Question("The OR logical operator is represented in Java by which operator?", "", "&&", "||", "!", "OR", 1);
        Question question4 = new Question("The value of a variable declared with the final keyword can be changed after its initial declaration.", "", "True", "False", "Not enough information", "There is no such keyword", 1);
        Question question5 = new Question("What is the value of num?", "int num = (10 - (4 + 3)) * 6;", "20", "-32", "18", "24", 2);

        questions.add(question0);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);

        totalCorrect = 0;
        totalQuestions = questions.size();

        Question firstQuestion = chooseNewQuestion();

        // TODO 3-D.ii: Uncomment the line below after implementing displayQuestionsRemaining(int)
        displayQuestionsRemaining(questions.size());

        // TODO 3-H.ii: Uncomment after implementing displayQuestion(Question)
        displayQuestion(firstQuestion);
    }

    Question chooseNewQuestion() {
        currentQuestionIndex = generateRandomNumber(questions.size());
        return questions.get(currentQuestionIndex);
    }

    int generateRandomNumber(int max) {
        double randomNumber = Math.random();
        double result = max * randomNumber;
        return (int) result;
    }

    Question getCurrentQuestion() {
        return questions.get(currentQuestionIndex);
    }

    String getGameOverMessage(int totalCorrect, int totalQuestions) {
        if (totalCorrect == totalQuestions) {
            return "You got all " + totalQuestions + " right! You won!";
        } else {
            return "You got " + totalCorrect + " right out of " + totalQuestions + ". Better luck next time!";
        }
    }
}
