import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        boolean playAgain;

        //Asks if the user would like to use custom names rather than Player 1 and 2
        System.out.println("Would you like to use custom names? (Y/N)");
        String userInput = sc.nextLine().toLowerCase();

        //This if-else route leads to the two main possible loops that will be running the game until the user desides to not play
        if(userInput.equals("y")){
            System.out.println("What is player 1's name?");
            String player1 = sc.nextLine();
            System.out.println("What is player 2's name?");
            String player2 = sc.nextLine();
            Gamestate checkers = new Gamestate(player1, player2);

            do{
                playAgain = false;
                //Begins the game - Loop inside method continues till a winner is found
                checkers.initializeGame();
                System.out.println("Would you like to play again? (Y/N)");
                userInput = sc.nextLine().toLowerCase();

                if(userInput.equals("y")){
                    playAgain = true;
                }

            }while (!playAgain);


        }

        else{
            System.out.println("Using defaults.");
            Gamestate checkersDefault = new Gamestate();

            do{
                playAgain = false;
                checkersDefault.initializeGame();
                System.out.println("Would you like to play again? (Y/N)");
                sc.nextLine();
                userInput = sc.nextLine().toLowerCase();

                if(userInput.equals("y")){
                    playAgain = true;
                }

            }while (!playAgain);

        }




    }
}
