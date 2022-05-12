package Comp_Sci_II_Project;
import java.util.*;
import java.io.*;
public class Jeopardy
{
    static Scanner In = new Scanner(System.in);
    static String[][] questions = new String[5][5];
    static String[][] answers = new String[5][5];
    static String[] categories = {"HISTORIC DATE", "SPORTS SHORTS", "ONE LETTER DIFFERENT", "THE LAND", "OF MILK"};
    static int[] points = {200,400,600,800,1000};
    static int score = 0;
    static boolean[][] state = {{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false},{false,false,false,false,false}};

    public static void fileImport() //imports CSV files for questions and answers
    {
        Scanner fin = null;
        String filename = "Comp_Sci_II_Project/JEOPARDY_QUESTIONS.csv";
        try {
            fin = new Scanner(new File(filename));
        } catch (Exception ex) { //Check if file exists
            System.out.println(ex);
            System.exit(1);
        }
        for(int i = 0; i<questions.length; i++){
            for(int j = 0; j<questions[0].length; j++){
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
    }

    public static void print(String[][] str) //prints questions and answers for debugging
    {
        int i=0;
        for(i = 0; i<str.length; i++){
            System.out.println();
            for(int j = 0; j<str[0].length; j++){
                System.out.print("["+ str[i][j] + "] ");}}
        System.out.println();
    }

    public static void rungame () //actual running of the game that uses the questions function
    {
        int col,row;
        System.out.println();
        System.out.println("Please select your category: \n" +
                    "[1] HISTORIC DATE\n" +
                    "[2] SPORTS SHORTS\n" +
                    "[3] ONE LETTER DIFFERENT\n" +
                    "[4] THE LAND\n" +
                    "[5] OF MILK");
        col = In.nextInt();
        if(col < 1 || col > 6)
        {
            System.out.println("Not a valid category, please pick again");
            rungame(); }
        else
        {
            System.out.println("Please select your value: \n" +
                        "[1] $200\n" +
                        "[2] $400\n"+
                        "[3] $600\n" +
                        "[4] $800\n" +
                        "[5] $1000" );
            row = In.nextInt();
            if(row < 1 || row > 5)
            {
                System.out.println("Not a valid value, please pick again") ;
                rungame();
            }
            else
            {
                if (!state[row-1][col-1])
                    questions(row,col);
                else
                    System.out.println("This category has already been selected, please pick again");
            }
        }
    }

    public static void questions(int row, int col) //function that compares the question and answer and checks if it is correct
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(categories[col-1] + ": " + points[row-1]);
        System.out.println(questions[row-1][col-1]);
        String answer = scan.nextLine();
        if (answer.equalsIgnoreCase(answers[row-1][col-1])){
            System.out.println("Congrats, you are correct!");
            score += points[row-1];
            state[row-1][col-1] = true;
            System.out.println("Your score is: " + score);
        }
        else{
            System.out.println("Sorry, you got the wrong answer");
            System.out.println("The answer is " + answers[row-1][col-1]);
            state[row-1][col-1] = true;
            score -= points[row-1];
            System.out.println("Your score is: "+ score);}
    }

    public static void startup () //menu
    {
        System.out.println("Welcome to Jeopardy!\n" +
                "Rules [1]\n" +
                "Start Game [2]\n" +
                "Quit [3]\n" +
                "Press [1] , [2] , or [3]");
        int x = In.nextInt();
        if (x == 1)
        {
            System.out.println("These are the rules of the game: When prompted you will enter your category\n" +
                    "number which is the column number(1,2,3,4,5,6) followed by the row number(1,2,3,4,5)\n" +
                    "You will answer the question and press enter accordingly\n" +
                    "Go back to home screen? [1] = yes [anything else] = no");
            int y = In.nextInt();
            if (y == 1)
            {
                startup();
            }
            else
            {
                System.exit(0);
            }
        }
        else if (x == 2)
        {
            do{rungame();}while(true);
        }
        else
        {
            System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        fileImport();
        startup();
    }
}
