import java.util.Scanner;
public class Gamestate {

    String player1Name, player2Name, winner;
    int player1Score, player2Score;
    Scanner sc = new Scanner(System.in);
    Scanner pos = new Scanner(System.in);


    public Gamestate(){
        player1Name = "Player 1";
        player2Name = "Player 2";
    }

    public Gamestate(String p1Name, String p2Name){
        player1Name = p1Name;
        player2Name = p2Name;
    }

    public void initializeGame(){
        Bitboard game = new Bitboard();

        System.out.println("IMPORTANT: Movement for this game relies on standard checkers board notation. You can view it here https://www.britannica.com/topic/checkers, or use this sample layout below.");
        System.out.println("IMPORTANT: Capturing pieces is done for you automatically in this system. Simply land on top of the opponents piece and the game will jump the piece for you.");
        System.out.println("---------------------------------\n| - | 1 | - | 2 | - | 3 | - | 4 |\n| 5 | - | 6 | - | 7 | - | 8 | - |\n| - | 9 | - |10 | - |11 | - |12 |\n|13 | - |14 | - |15 | - |16 | - |\n| - |17 | - |18 | - |19 | - |20 |\n|21 | - |22 | - |23 | - |24 | - |\n| - |25 | - |26 | - |27 | - |28 |\n|29 | - |30 | - |31 | - |32 | - |\n---------------------------------\n");
        System.out.println("Press Enter to start.");
        sc.nextLine();
        System.out.println("\nScores\n" + player1Name +": " + player1Score + "      |      " + player2Name + ": " + player2Score + "\n");

        game.printBoard();

        boolean nextTurn;
        boolean gameOver = false;

        int piece, movement;

        //This is the main loop handling the game. It will loop turns until a victor is found.
        while(!gameOver){

            nextTurn = false;

            //This loop handles player 1's turn. It will not break until player 1 inputs a legal move.
            while(!nextTurn){

                System.out.println(player1Name + "'s turn.");
                System.out.println("Which piece would you like to move? (You will be asked where to afterwards)\nLocation: ");
                piece = pos.nextInt();

                System.out.println("Where would you like to move to?\nLocation: ");
                movement = pos.nextInt();

                //This is where the method to move is used and where the inner loop can potentially break.
                nextTurn = game.playerMove(1, piece, movement);

                //Checks after every attempted move whether the player wins or not. If they do it will assign the winner, increase score, and break the outer loop.
                if(game.winCheck(1)){
                    gameOver = true;
                    winner = player1Name;
                    player1Score++;
                    break;
                }

            }

            game.printBoard();
            nextTurn = false; //Sets up player 2 loop

            //This loop handles player 2's turn similarly to player 1. The only difference is this loop will not execute if gameOver is true, ensure that if player 1 wins there will not be another turn.
            while(!nextTurn && !gameOver){

                System.out.println(player2Name + "'s turn.");
                System.out.println("Which piece would you like to move? (You will be asked where to afterwards)\n Location: ");
                piece = pos.nextInt();

                System.out.println("Where would you like to move to?\nLocation: ");
                movement = pos.nextInt();

                nextTurn = game.playerMove(2, piece, movement);

                if(game.winCheck(2)){
                    gameOver = true;
                    winner = player2Name;
                    player2Score++;
                    break;
                }

            }

            game.printBoard();
        }

        System.out.println(winner + " is the winner!");

    }



}
