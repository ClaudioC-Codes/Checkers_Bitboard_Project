public class Bitboard {

    //Make player 1 board. Translates onto board right to left
    long p1 = 0b0000000000000000000000000000000000000000101010100101010110101010L;

    long p1King = 0b0000000000000000000000000000000000000000000000000000000000000000L;

    //Make player 2 board
    long p2 = 0b0101010110101010010101010000000000000000000000000000000000000000L;

    long p2King = 0b0000000000000000000000000000000000000000000000000000000000000000L;

    Utility util = new Utility();

    //Method to display entire game board
    public void printBoard(){

            String fullBoard = "";

                //Creates the entire board using player 2s bitboard
                for(int i = 63; i >= 0 ; i--){
                   int space = util.getBit(p2, i);

                   if(space > 0){fullBoard = fullBoard + "2";}
                   else{fullBoard = fullBoard + " ";}

                }



            StringBuilder sb = new StringBuilder(fullBoard);

                //By using a string builder, scans through p1 bitboard and replaces 0's with 1's from p1 bitboard.
                for(int i = 0; i < 64; i++ ){
                    int space = util.getBit(p1, i);

                    if(space > 0){sb.setCharAt(63 - i, '1');}
                }

                //Scans through and adds Player 1's King board.
                for(int i = 0; i < 64; i++ ){
                    int space = util.getBit(p1King, i);

                    if(space > 0){sb.setCharAt(63 - i, 'K');}
                }

                //Scans through and adds Player 2's King board.
                for(int i = 0; i < 64; i++ ){
                    int space = util.getBit(p2King, i);

                    if(space > 0){sb.setCharAt(63 - i, 'M');}
                }

                fullBoard = sb.toString();

            //Prints out the Binary and Hex Bit boards.
            System.out.println("Binary Bitboard: " + util.convertToBinary(util.entireBoard(p1,p2)) + "\nHex Bitboard: " + util.convertToHex(util.entireBoard(p1,p2)));




            //Formatting for the board
            String formattedBoard= "-----------------\n";
            int counter = 0;

            //Scans through each character in fillBoard whilst having a second counter.
            //Second counter keeps track of what column we are at in each row. It the 8th column it prints a different pattern and prepares the next row.
            for(int i = 0; i < fullBoard.length(); i++){
                counter++;


                if(counter == 8){formattedBoard = formattedBoard + "|" + fullBoard.charAt(i) + "|\n"; counter = 0;}
                else formattedBoard = formattedBoard + "|" + fullBoard.charAt(i);
            }

            formattedBoard = formattedBoard + "-----------------";

            System.out.println(formattedBoard + "\n");


        }


    //Method for player movement. This method will handles gameboard to bitboard conversion, then verifies valid piece chosen, checks for legal moves, kings any pieces that make it to the end, and finally returns true or false.
    public boolean playerMove(int playerNumber, int piece, int position){



        //Converts the piece and position numbers from checkers board notation location to bitboards binary notation location. It then takes the difference between the value and 63 to mirror the direction binary is read
        if(piece > 4 && piece < 9 || piece > 12 && piece < 17 || piece > 20 && piece < 25 || piece > 28 && piece < 33){
                piece = piece * 2 - 2;

                piece = 63 - piece; //Mirrors reading direction by taking the difference
            }
        else {piece = piece * 2 - 1; piece = 63 - piece;}


        //Same formula but with the position
        if(position > 4 && position < 9 || position > 12 && position < 17 || position > 20 && position < 25 || position > 28 && position < 33){
            position = position * 2 - 2;

            position = 63 - position; //Mirrors reading direction by taking the difference
        }
        else {position = position * 2 - 1; position = 63 - position;}

        int movement = piece - position;




        //Initiates movement checks
        if(playerNumber == 1 ){

            //Checks if spot is empty or not. Returns error if empty.
            if(util.getBit(p1, piece) > 0){

                //Checks if the piece is a king
                if(isKing(p1, p1King, piece)){

                    if(movement == -9 || movement == 9 || movement == -7 || movement == 7){

                        //Prints Error if players own piece is in the desired location.
                        if(util.getBit(p1, position) == 1){ System.out.println("Error: Your piece is in the way."); return false;}


                        //Checks if slot is empty, if so it moves players piece through toggling.
                        else if(util.getBit(p2, position) == 0){
                            p1 = util.toggleBit(p1,piece); p1 = util.toggleBit(p1, position);
                            p1King = util.toggleBit(p1King,piece); p1King = util.toggleBit(p1King, position);
                            return true;
                        }


                        //Checks for enemy piece in location, if so it jumps the piece. there is a branch for all 4 diagonal movements.
                        else if(movement == -7 && util.getBit(p2, position) == 1 && util.getBit(p2, position + 7) == 0){
                            p1 = util.toggleBit(p1,piece);
                            p1 = util.toggleBit(p1, position + 7);
                            p1King = util.toggleBit(p1King,piece);
                            p1King = util.toggleBit(p1King, position + 7);

                            if(isKing(p2, p2King, position)){
                                p2King = util.toggleBit(p2King, position);
                            }
                            p2 =util.toggleBit(p2, position);
                            return true;
                        }

                        else if(movement == 7 && util.getBit(p2, position) == 1 && util.getBit(p2, position - 7) == 0){
                            p1 = util.toggleBit(p1,piece);
                            p1 = util.toggleBit(p1, position - 7);
                            p1King = util.toggleBit(p1King,piece);
                            p1King = util.toggleBit(p1King, position - 7);

                            if(isKing(p2, p2King, position)){
                                p2King = util.toggleBit(p2King, position);
                            }
                            p2 =util.toggleBit(p2, position);
                            return true;
                        }

                        else if(movement == -9 && util.getBit(p2, position) == 1 && util.getBit(p2, position + 9) == 0){
                            p1 = util.toggleBit(p1,piece);
                            p1 = util.toggleBit(p1, position + 9);
                            p1King = util.toggleBit(p1King,piece);
                            p1King = util.toggleBit(p1King, position + 9);

                            if(isKing(p2, p2King, position)){
                                p2King =util.toggleBit(p2King, position);
                            }
                            p2 =util.toggleBit(p2, position);
                            return true;
                        }

                        else if(movement == 9 && util.getBit(p2, position) == 1 && util.getBit(p2, position - 9) == 0){
                            p1 = util.toggleBit(p1,piece);
                            p1 = util.toggleBit(p1, position - 9);
                            p1King = util.toggleBit(p1King,piece);
                            p1King = util.toggleBit(p1King, position - 9);

                            if(isKing(p2, p2King, position)){
                                p2King =util.toggleBit(p2King, position);
                            }
                            p2 =util.toggleBit(p2, position);
                            return true;
                        }


                        //If none of the other conditions are met, then it is an illegal move.
                        else{
                            System.out.println("Error: Illegal Move"); return false;}

                    }

                    else{System.out.println("Error: Invalid Movement. The game will automatically jump a piece if you select a location containing a capturable piece."); return false;}


                }

                //Checks for valid Non-King diagonal movement by calculated whether the positional difference is 7 or 9. Returns error for invalid movements
                else if(movement == -9 || movement == -7){

                        //Prints Error if players own piece is in the desired location.
                        if(util.getBit(p1, position) == 1){ System.out.println("Error: Your piece is in the way."); return false; }

                        //Checks if slot is empty, if so moves piece through toggling.
                        else if(util.getBit(p2, position) == 0){
                            p1 = util.toggleBit(p1,piece); p1 = util.toggleBit(p1, position); System.out.println("TEST2");

                            //After a successful movement, if the position is at the end of the enemy board it will be kinged.
                            if(position > 55 && position < 63){
                                makeKing(1, position);
                                System.out.println("TEST");
                            }

                            return true;

                        }

                        //Checks for enemy piece in location, if so it jumps the piece.
                        else if(movement == -7 && util.getBit(p2, position) == 1 && util.getBit(p2, position + 7) == 0){
                            p1 = util.toggleBit(p1,piece);
                            p1 = util.toggleBit(p1, position + 7);

                            if(isKing(p2, p2King, position)){
                                p2King =util.toggleBit(p2King, position);
                            }
                            p2 =util.toggleBit(p2, position);

                            if(position + 7 > 55 && position + 7 < 63){
                                makeKing(1, position + 7);
                            }

                            return true;
                        }

                        //Checks for enemy piece in opposite diagonal movement.
                        else if(movement == -9 && util.getBit(p2, position) == 1 && util.getBit(p2, position + 9) == 0){
                            p1 = util.toggleBit(p1,piece);
                            p1 = util.toggleBit(p1, position + 9);

                            if(isKing(p2, p2King, position)){
                                p2King =util.toggleBit(p2King, position);
                            }
                            p2 =util.toggleBit(p2, position);

                            if(position + 9 > 55 && position + 9 < 63){
                                makeKing(1, position + 9);
                            }

                            return true;
                        }

                        //If no other conditions are met, then the move is illegal.
                        else{System.out.println("Error: Illegal move."); return false;}

                }

                else{System.out.println("Error: Invalid Movement. The game will automatically jump a piece if you select a location containing a capturable piece."); return false;}


            }

            else {System.out.println("Error: Invalid piece"); return false;}



        }


        //Uses the same logic as player 1 movements. Some values are just flipped to negative, and bitboards are swapped in methods.
        else if(playerNumber == 2 ){

            if(util.getBit(p2, piece) > 0){

                if(isKing(p2, p2King, piece)){

                    if(movement == -9 || movement == 9 || movement == -7 || movement == 7){

                        if(util.getBit(p2, position) == 1){System.out.println("Error: Your piece is in the way."); return false;}

                        else if(util.getBit(p1, position) == 0){
                            p2 = util.toggleBit(p2,piece); p2 = util.toggleBit(p2, position);
                            p2King = util.toggleBit(p2King,piece); p2King = util.toggleBit(p2King, position);
                            return true;
                        }

                        else if(movement == 7 && util.getBit(p1, position) == 1 && util.getBit(p1, position - 7) == 0){
                            p2 = util.toggleBit(p2,piece);
                            p2 = util.toggleBit(p2, position - 7);
                            p2King = util.toggleBit(p2King,piece);
                            p2King = util.toggleBit(p2King, position - 7);

                            if(isKing(p1, p1King, position)){
                                p1King = util.toggleBit(p1King, position);
                            }
                            p1 = util.toggleBit(p1, position);
                            return true;
                        }

                        else if(movement == -7 && util.getBit(p1, position) == 1 && util.getBit(p1, position + 7) == 0){
                            p2 = util.toggleBit(p2,piece);
                            p2 = util.toggleBit(p2, position + 7);
                            p2King = util.toggleBit(p2King,piece);
                            p2King = util.toggleBit(p2King, position + 7);

                            if(isKing(p1, p1King, position)){
                                p1King = util.toggleBit(p1King, position);
                            }
                            p1 = util.toggleBit(p1, position);
                            return true;
                        }

                        else if(movement == 9 && util.getBit(p1, position) == 1 && util.getBit(p1, position - 9) == 0){
                            p2 = util.toggleBit(p2,piece);
                            p2 = util.toggleBit(p2, position - 9);
                            p2King = util.toggleBit(p2King,piece);
                            p2King = util.toggleBit(p2King, position - 9);

                            if(isKing(p1, p1King, position)){
                                p1King = util.toggleBit(p1King, position);
                            }
                            p1 = util.toggleBit(p1, position);
                            return true;
                        }

                        else if(movement == -9 && util.getBit(p1, position) == 1 && util.getBit(p1, position + 9) == 0){
                            p2 = util.toggleBit(p2,piece);
                            p2 = util.toggleBit(p2, position + 9);
                            p2King = util.toggleBit(p2King,piece);
                            p2King = util.toggleBit(p2King, position + 9);

                            if(isKing(p1, p1King, position)){
                                p1King = util.toggleBit(p1King, position);
                            }
                            p1 = util.toggleBit(p1, position);
                            return true;
                        }

                        else{
                            System.out.println("Error: Illegal move.");
                            return false;
                        }
                    }

                    else{System.out.println("Error: Invalid Movement"); return false;}
                }

                else if(movement == 9 || movement == 7){

                    if(util.getBit(p2, position) == 1){System.out.println("Error: Your piece is in the way."); return false;}

                    else if(util.getBit(p1, position) == 0){
                        p2 = util.toggleBit(p2,piece); p2 = util.toggleBit(p2, position);

                        if(position > 0 && position < 8){
                            makeKing(2, position);
                        }

                        return true;
                    }

                    else if(movement == 7 && util.getBit(p1, position) == 1 && util.getBit(p1, position - 7) == 0){
                        p2 = util.toggleBit(p2,piece);
                        p2 = util.toggleBit(p2, position - 7);

                        if(isKing(p1, p1King, position)){
                            p1King = util.toggleBit(p1King, position);
                        }
                        p1 = util.toggleBit(p1, position);

                        if(position - 7 > 0 && position - 7 < 8){
                            makeKing(2, position - 7);
                        }

                        return true;
                    }

                    else if(movement == 9 && util.getBit(p1, position) == 1 && util.getBit(p1, position - 9) == 0){
                        p2 = util.toggleBit(p2,piece);
                        p2 = util.toggleBit(p2, position - 9);

                        if(isKing(p1, p1King, position)){
                            p1King = util.toggleBit(p1King, position);
                        }
                        p1 = util.toggleBit(p1, position);

                        if(position - 9 > 0 && position - 9 < 8){
                            makeKing(2, position - 9);
                        }

                        return true;
                    }

                    else{System.out.println("Error: Illegal move."); return false;}
                }

                else{System.out.println("Error: Invalid Movement"); return false;}

            }

            else {System.out.println("Error: Invalid piece"); return false;}



        }

        else {
            System.out.println("loop here");
            return false;}

    }



    //Checks if a piece is king or not by matching the bitboard with the king bitboard
    public boolean isKing (long bitboard, long kingBoard, int piece){

        if(util.getBit(bitboard, piece) == 1 && util.getBit(kingBoard, piece) == 1){
            return true;
        }

        else return false;
    }

    //Toggles the normal piece location on the king board
    public void makeKing (int player, int piece){

        if(player == 1){
            p1King = util.toggleBit(p1King, piece);
        }

        else{
            p2King = util.toggleBit(p2King, piece);
        }


    }


    //Check for win method. Done by checking if any player is missing all pieces. will be checked every move
    //Their bitboard value should be 0 if they've lost
    public boolean winCheck(int player){
            long bitboard;

            if(player == 1){
                bitboard = p2;
            }

            else{bitboard = p1;}


            if(bitboard == 0){return true;}
            else return false;

    }


}
