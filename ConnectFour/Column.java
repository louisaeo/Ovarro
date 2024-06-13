public class Column {

    int[] innards;
    int top = 0;
    int max;

    public Column(int size) {
        max = size;
        innards = new int[max];
        //System.out.println(innards[2]);
    }

    public Boolean push(int colour){
        if (top == max){
            System.out.println("Column is already full!");
            return false;
        }
        else {
            innards[top] = colour;
            top = top + 1;
            return true;
        }
    }

    public int topmost(){
        return top;
    }

    public Boolean verify(int colour, int position){
        if (innards[position] == colour) {return true;}
        else {return false;}
    }

    public int[] revealCol(){
        return innards;
    }

    public int revealCell(int position){
        return innards[position];
    }


}
