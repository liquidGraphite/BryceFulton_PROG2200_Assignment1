import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GradeCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] assignments = new int[5];
        boolean[] onTime = new boolean[5];
        int prelim, finalExam;
        ArrayList<Integer> quizzes = new ArrayList<>();

        // Input and validation for assignments
        for (int i = 0; i < assignments.length; i++) {
            assignments[i] = getValidGrade(scanner, "Enter a grade for homework #" + (i + 1) + ": ");
            onTime[i] = getOnTimeStatus(scanner, "Was homework #" + (i + 1) + " submitted on time? (Y/N): ");

            if (!onTime[i]) {
                onTime[i] = getOnTimeStatus(scanner, "Was homework #" + (i + 1) + " submitted within 24 hours of the deadline? (Y/N): ");
                if (!onTime[i]) {
                    assignments[i] *= 0.5; // Penalty for late submission
                } else {
                    assignments[i] *= 0.75; // Partial penalty for submission within 24 hours
                }
            }
        }

        // Input and validation for prelim and final
        prelim = getValidGrade(scanner, "Enter a grade for prelim: ");
        finalExam = getValidGrade(scanner, "Enter a grade for the final: ");

        // Input and validation for quizzes
        for (int i = 1; i <= 6; i++) {
            int quizScore = getValidGrade(scanner, "Enter a grade for quiz #" + i + ": ");
            quizzes.add(quizScore);
        }

        // Dropping the lowest quiz score
        quizzes.remove(Collections.min(quizzes));

        // Calculating the final course score
        double finalScore = calculateFinalScore(assignments, prelim, finalExam, quizzes);
        char letterGrade = calculateLetterGrade(finalScore);

        // Output the results
        System.out.println("-------------------------------------");
        System.out.printf("Your final course score: %.2f\n", finalScore);
        System.out.println("Your letter grade will be at least: " + letterGrade);
    }

    // Method to get valid grade input
    private static int getValidGrade(Scanner scanner, String prompt) {
        int grade;
        while (true) {
            System.out.print(prompt);
            try {
                grade = Integer.parseInt(scanner.nextLine());
                if (grade < 0 || grade > 100) {
                    System.out.println("Not a legal entry: Please enter an integer between 0 and 100.");
                } else {
                    return grade;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Method to get submission status
    private static boolean getOnTimeStatus(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }

    // Calculate the final course score
    private static double calculateFinalScore(int[] assignments, int prelim, int finalExam, ArrayList<Integer> quizzes) {
        double assignmentAvg = 0;
        for (int score : assignments) {
            assignmentAvg += score;
        }
        assignmentAvg /= assignments.length;

        double quizAvg = 0;
        for (int score : quizzes) {
            quizAvg += score;
        }
        quizAvg /= quizzes.size();

        return 0.4 * assignmentAvg + 0.2 * quizAvg + 0.2 * prelim + 0.2 * finalExam;
    }

    // Calculate the letter grade based on final score
    private static char calculateLetterGrade(double score) {
        if (score >= 90) return 'A';
        else if (score >= 80) return 'B';
        else if (score >= 70) return 'C';
        else if (score >= 60) return 'D';
        else return 'F';
    }
}
