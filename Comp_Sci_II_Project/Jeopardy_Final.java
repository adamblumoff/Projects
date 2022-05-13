package Comp_Sci_II_Project;
import java.io.File;
import java.util.Scanner;

public class Jeopardy_Final {

    public static void main(String[] args) {
        fileImport();
        startup();
        // startup is separate method so that we can restart if needed
    }
    static Player p1 = new Player(0, "");
    static Player p2 = new Player(0,"");
    static String[][] questions = new String[5][5];
    static String[][] answers = new String[5][5];
    static String[] categories = {"HISTORIC DATE", "SPORTS SHORTS", "ONE LETTER DIFFERENT", "THE LAND", "OF MILK"};
    static int[][] points = { { 100, 200, 300, 400, 500 }, { 100, 200, 300, 400, 500 }, { 100, 200, 300, 400, 500 },
            { 100, 200, 300, 400, 500 }, { 100, 200, 300, 400, 500 }, { 100, 200, 300, 400, 500 } }; // keeps track of
    // point scores
    static int score1 = 0;
    static int score2 = 0;
    static boolean[][] state = { { false, false, false, false, false }, { false, false, false, false, false },
            { false, false, false, false, false }, { false, false, false, false, false },
            { false, false, false, false, false } }; // this allows us to keep track of which questions have been attempted

    public static void fileImport() //imports CSV files for questions and answers.
                                    //CHANGE FILENAME FOR DIFFERENT DIRECTORY
    {
        Scanner fin = null;
        String filename = "Comp_Sci_II_Project/JEOPARDY_QUESTIONS.csv";
        try {
            fin = new Scanner(new File(filename));
        } catch (Exception ex) { //Check if file exists
            System.out.println(ex);
            System.exit(1);
        }
        for(int i = 0; i< questions.length; i++){
            for(int j = 0; j< questions[0].length; j++){
                questions[i][j] = fin.nextLine();}}
        fin.close();

        Scanner fin2 = null;
        String filename2 = "Comp_Sci_II_Project/JEOPARDY_ANSWERS.csv";
        try
        {
            fin2 = new Scanner(new File(filename2));
        } catch (Exception ex) { //Check if file exists
            System.out.println(ex);
            System.exit(1);
        }
        for(int i = 0; i<answers.length; i++){
            for(int j = 0; j<answers[0].length; j++){
                answers[i][j] = fin2.nextLine();}}
        fin2.close();
    } // fileImport

    public static boolean stillQuestionsToAnswer() { // checks whether there are any remaining questions
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if (state[i][j] == false) {
                    return true;
                }
            }
        }
        return false;
    } // stillQuestionsToAnswer

    static boolean isPlayer1 = false;
    static boolean P1gotCorrect = true;
    static boolean P2gotCorrect = true;

    public static void runGame() { // this is where the game logic lies
        System.out.println("     ");
        System.out.println("Let's begin!\n" + p1.getName()+ ", you are up first.");
        while (stillQuestionsToAnswer()) { // while there are still questions available
            if(P1gotCorrect || !P2gotCorrect)
                isPlayer1 = true;
            else if(P2gotCorrect || !P1gotCorrect)
                isPlayer1 = false;
            int category = getCategory(); // gets category
            int question = getQuestion(category); // gets question
            questions(question, category);
        }
        System.out.println("The final score is:\n" + p1.getName()+ ": " + p1.getScore() + "\n"+ p2.getName()+ ": " + p2.getScore());
        if (p1.getScore() > p2.getScore()) { // once we run out of questions
            System.out.println("Congratulations, " + p1.getName() + ", you won!");
        } else {
            System.out.println("Congratulations, "+ p2.getName()+ ", you won!");
        }
    } // runGame

    public static int getCategory() { // gets category
        Scanner cin = new Scanner(System.in); // establishes scanner for new question choice
        boolean noQuestionPicked = true;
        int col = 0;
        while (noQuestionPicked) {
            System.out.println(
                    "Please select your category: \n[1] HISTORIC DATE\n[2] SPORTS SHORTS\n[3] ONE LETTER DIFFERENT\n[4] THE LAND\n[5] OF MILK");
            col = cin.nextInt();
            if (col < 1 || col > 6) {
                System.out.println("Not a valid category, please pick again");
            }
            else {
                for(int i = 0; i<state[0].length; i++){
                    if(!(state[i][col-1]))
                        noQuestionPicked = false;}
            }
        }
        return col;
    }

    public static int getQuestion(int category) { // gets question
        Scanner cin = new Scanner(System.in); // establishes scanner for new question choice
        boolean noValuePicked = true;
        int row = 0;
        while (noValuePicked) {
            System.out.println("Please select your value:\n[1] $200\n[2] $400\n[3] $600\n[4] $800\n[5] $1000");
            row = cin.nextInt();
            if (row < 1 || row > 5) {
                System.out.println("Not a valid value. Please pick again");
            } else {
                if (!state[row - 1][category - 1]) { //change
                    noValuePicked = false;
                } else {
                    System.out.println("This value has already been selected. Please pick again");
                }
            }
        }
        return row;
    }

    public static void questions(int row, int col) {
        Scanner user = new Scanner(System.in);
        System.out.println(questions[row - 1][col - 1]);
        String answer = user.nextLine();
        if (answer.equalsIgnoreCase(answers[row-1][col-1])) { // just handles correct scores
            System.out.println("Congrats, you are correct!");
            if (isPlayer1) { // if we are dealing with P1
                p1.addScore(points[row - 1][col - 1]);
                System.out.println(p1.toString());
                P1gotCorrect = true;
            } else { // if we are dealing with "+ p2.getName()+ "
                p2.addScore(points[row - 1][col - 1]);
                System.out.println(p2.toString());
                P2gotCorrect = true;
            }

        } else { // handles incorrect scores
            System.out.println(answers[row - 1][col - 1]);
            if (isPlayer1) {
                p1.remScore(points[row - 1][col - 1]);
                System.out.println("Sorry, " + p1.getName() + " is wrong.\n" + p1.toString());
                P1gotCorrect = false;
            } else {
                p2.remScore(points[row - 1][col - 1]);
                System.out.println("Sorry, "+ p2.getName()+ " is wrong.\n"+ p2.toString());
                P2gotCorrect = false;
            }
        }
        state[row - 1][col - 1] = true;
    } // questions

    public static void print(String[][] str) //prints questions and answers for testing
    {
        int i=0;
        for(i = 0; i<str.length; i++){
            System.out.println();
            for(int j = 0; j<str[0].length; j++){
                System.out.print("["+ str[i][j] + "] ");}}
        System.out.println();
    }
    public static void printbool(boolean[][] str) //prints state for testing
    {
        int i=0;
        for(i = 0; i<str.length; i++){
            System.out.println();
            for(int j = 0; j<str[0].length; j++){
                System.out.print("["+ str[i][j] + "] ");}}
        System.out.println();
    }

    public static void startup() {
        Scanner choice = new Scanner(System.in);
        System.out.println("Welcome to Jeopardy!");
        System.out.println("To view the rules, please press [1]");
        System.out.println("To set the players and start the game, please press [2]");
        System.out.println("To quit the game, please press [3]");
        System.out.println();
        System.out.println("Press [1], [2], or [3]");
        int x = choice.nextInt();

        if (x == 1) {
            System.out.println(
                    "The rules are as follows:\nWhen prompted, the player will enter their category\nnumber, which is the column number (1,2,3,4,5,6), followed by the question number, which is the row number (1,2,3,4,5).\nThe player will answer the question and press enter accordingly.");
            System.out.println(
                    "If a player is correct, the question's point value is added to their score.\nIf they are wrong, then the point value is deducted.\nEach player gets one turn.");
            System.out.println("Go back to home screen? [1] = yes [2] = no");
            int y = choice.nextInt();
            if (y == 1) {
                startup();
            } else {
                System.exit(0);
            }
        } else if (x == 2) {
            p1.setName(1);
            p2.setName(2);
            runGame();
        }
        else
        System.exit(0);
    } // startup
} // class JeopardyFinal
class Player
{
    private int score;
    private String name;
    public Player(int score, String name){
        this.score = score; this.name = name;
    }
    public int getScore(){return score;}
    public String getName(){return name;}
    public void setName(int numPlayer)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the name of Player " + numPlayer + "?");
        String p1 = sc.next();
        this.name = p1;
    }
    public void addScore(int x){score += x;}
    public void remScore(int x){score -= x;}
    public String toString(){return name + "'s score is: " + score;}
} //class Player
