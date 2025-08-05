import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        boolean playAgain = true;
        int highScore = Integer.MAX_VALUE;
        Scanner guessNum = new Scanner(System.in);

        while (playAgain) {
            // Get today's date for the high score log
            LocalDate today = LocalDate.now();

            // Intro and difficulty selection
            System.out.println("Welcome to the number guessing game!");
            System.out.println("Choose your difficulty: easy, medium, or hard");

            int randomNum = 0;
            String difficulty = guessNum.nextLine();

            // Set number range based on difficulty
            switch (difficulty.toLowerCase()) {
                case "easy":
                    randomNum = (int) (Math.random() * 11); // 0–10
                    System.out.println("Enter a number from 0-10");
                    break;
                case "medium":
                    randomNum = (int) (Math.random() * 51); // 0–50
                    System.out.println("Enter a number from 0-50");
                    break;
                case "hard":
                    randomNum = (int) (Math.random() * 101); // 0–100
                    System.out.println("Enter a number from 0-100");
                    break;
                default:
                    randomNum = (int) (Math.random() * 101); // fallback
                    System.out.println("Wrong input.. now going to default..");
                    System.out.println("Enter a number from 0-100");
            }

            // (Optional) Reveal the number for testing
            System.out.println(randomNum);

            int output = -1;
            int attempts = 0;

            // Main guessing loop
            while (output != randomNum) {
                if (guessNum.hasNextInt()) {
                    output = guessNum.nextInt();
                    guessNum.nextLine(); // consume leftover newline
                    attempts++;

                    if (output < randomNum) {
                        System.out.println("Too low, Try again!");
                    } else if (output > randomNum) {
                        System.out.println("Too high, Try again!");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    guessNum.nextLine(); // clear bad input
                }
            }

            // Game summary
            System.out.println("Game Over! Summary:");
            System.out.println("Attempts: " + attempts);
            System.out.println("Date: " + today);

            // Save high score if it's the best so far
            if (attempts < highScore) {
                highScore = attempts;

                try {
                    BufferedWriter saveScore = new BufferedWriter(new FileWriter("HighScore.txt", true));
                    saveScore.write("Date: " + today + ", Difficulty: " + difficulty + ", Score: " + attempts);
                    saveScore.newLine();
                    saveScore.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Display best score of this session
            System.out.println("Past High Score: " + highScore);

            // Ask if user wants to play again
            System.out.println("Would you like to play again? Yes or No");
            String userAnswer = guessNum.nextLine();

            if (userAnswer.equalsIgnoreCase("yes")) {
                playAgain = true;
            } else if (userAnswer.equalsIgnoreCase("no")) {
                playAgain = false;
            }
        }
        guessNum.close();
    }
}
