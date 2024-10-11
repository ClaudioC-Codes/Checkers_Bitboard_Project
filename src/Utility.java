public class Utility {

    public long setBit(long bitboard, int pos){
        return bitboard | (1L << pos);
    }

    public long clearBit(long bitboard, int pos){
        return bitboard & ~(1L << pos);
    }

    public long toggleBit(long bitboard, int pos){
        return bitboard ^ (1L << pos);
    }

    public int getBit(long bitboard, int pos){
        boolean Int = (bitboard & (1L << pos)) !=0;

        //Uses boolean values to easily represent 0 or 1
        if(Int) {return 1;}
        else return 0;
    }

    //Converts the long value into binary and adds the leading zeros to maintain 64-bit
    public String convertToBinary(long binaryValue){
        int padding = 64 - Long.toBinaryString(binaryValue).length();

        String finalBitboard = Long.toBinaryString(binaryValue);

        for (int i = 0; i < padding+1; i++){
            finalBitboard = "0" + finalBitboard;
        }

        return finalBitboard;


    }

    public String convertToHex(long binaryValue){
        return Long.toHexString(binaryValue);
    }

    public long entireBoard(long player1Board, long player2Board){
        return player1Board | player2Board;
    }

}
