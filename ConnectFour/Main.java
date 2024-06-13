import java.util.Scanner;
import java.util.Arrays;

public class Main {

    static int south(Column[] grid, int colver, int rowver, int player, int stretch) { //no need for an accompanying north function, as there cannot be any tokens above the current one
        if (rowver == -1) {
            return 0;
        } 
        if (stretch == 0) {
            return 0;
        }
        else if (grid[colver].verify(player, rowver)) {
            return (1 + (south(grid, colver, rowver - 1, player, stretch - 1)));
        }
        else {
            return 0;
        }
    }

    static int southwest(Column[] grid, int colver, int rowver, int player, int stretch) { //each of these checks if the cell is in the grid, then if it matches the current colour, it checks for the next one in this direction
        if (colver == -1) {
            return 0;
        } 
        if (rowver == -1) {
            return 0;
        } 
        if (stretch == 0) {
            return 0;
        }
        else if (grid[colver].verify(player, rowver)) {
            return (1 + (southwest(grid, colver - 1, rowver - 1, player, stretch - 1)));
        }
        else {
            return 0;
        }
    }

    static int northeast(Column[] grid, int colver, int rowver, int player, int stretch) {
        if (colver == grid.length) {
            return 0;
        }
        if (rowver == (grid[0].revealCol()).length) {
            return 0;
        } 
        if (stretch == 0) {
            return 0;
        }
        else if (grid[colver].verify(player, rowver)) {
            return (1 + (northeast(grid, colver + 1, rowver + 1, player, stretch - 1)));
        }
        else {
            return 0;
        }
    }

    static int northwest(Column[] grid, int colver, int rowver, int player, int stretch) {
        if (colver == -1) {
            return 0;
        }
        if (rowver == (grid[0].revealCol()).length) {
            return 0;
        } 
        if (stretch == 0) {
            return 0;
        }
        else if (grid[colver].verify(player, rowver)) {
            return (1 + (northwest(grid, colver - 1, rowver + 1, player, stretch - 1)));
        }
        else {
            return 0;
        }
    }

    static int southeast(Column[] grid, int colver, int rowver, int player, int stretch) {
        if (colver == grid.length) {
            return 0;
        }
        if (rowver == -1) {
            return 0;
        } 
        if (stretch == 0) {
            return 0;
        }
        else if (grid[colver].verify(player, rowver)) {
            return (1 + (southeast(grid, colver + 1, rowver - 1, player, stretch - 1)));
        }
        else {
            return 0;
        }
    }

    static int west(Column[] grid, int colver, int rowver, int player, int stretch) {
        if (colver == -1) {
            return 0;
        } 
        if (stretch == 0) {
            return 0;
        }
        else if (grid[colver].verify(player, rowver)) {
            return (1 + (west(grid, colver - 1, rowver, player, stretch - 1)));
        }
        else {
            return 0;
        }
    }

    static int east(Column[] grid, int colver, int rowver, int player, int stretch) {
        if (colver == grid.length) {
            return 0;
        }
        if (stretch == 0) {
            return 0;
        }
        else if (grid[colver].verify(player, rowver)) {
            return (1 + (east(grid, colver + 1, rowver, player, stretch - 1)));
        }
        else {
            return 0;
        }
    }

    static void display(Column[] grid, int colnum, int rownum) {
        int cell;
        String[] vis = {"O", "R", "Y"};
        for (int j = rownum -1; j > -1; j--) {
            for (int i = 0; i < colnum; i++) {
                cell = grid[i].revealCell(j);
                System.out.print(vis[cell] + " ");
            }
            System.out.println("");
        }
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Boolean play = true;
        int colnum;
        int rownum;
        int winnum = 0;
        Boolean valid;
        String again;
        int colcur = 0;
        int rowcur;
        int streak;
        int player = 1; //red = 1, yellow = 2
        String[] tokens = {"Empty", "Red", "Yellow"};
        Boolean win = false;

        while (play) {
            System.out.println("How many columns would you like?");
            colnum = input.nextInt();
            System.out.println("How many rows would you like?");
            rownum = input.nextInt();
            valid = false;
            while (valid == false) {
                System.out.println("How many tokens in a row would you like to get to win?");
                winnum = input.nextInt() - 1;  //checks for rows occur whenever a token is placed, based on potential rows including that token, so always have a streak of at least 1
                if (winnum < (Math.min(colnum, rownum))){
                    valid = true;
                }
                else {
                    System.out.println("No one will be able to win!");
                }
            }

            Column[] grid = new Column[colnum];
            for (int i = 0; i < colnum; i++) {
                grid[i] = new Column(rownum);
            }
            win = false;
            while (win == false){

                display(grid, colnum, rownum);

                System.out.println("Player " + tokens[player] + "'s turn:");
                valid = false;
                while (valid == false) {
                    System.out.println("Which column?");
                    colcur = input.nextInt() -1;
                    if ((colcur < 0) || (colcur > colnum - 1)) {
                        System.out.println("Out of range!");
                    }
                    else {
                        valid = true;
                    }
                }
                grid[colcur].push(player);

                display(grid, colnum, rownum);


                streak = south(grid, colcur, (grid[colcur].topmost() - 2), player, winnum); //if streak sums to winnum, then there is a winning streak in some direction
                if (streak == winnum) {
                    win = true;
                }
                streak = southwest(grid, colcur - 1, (grid[colcur].topmost() - 2), player, winnum);
                streak = streak + northeast(grid, colcur + 1, (grid[colcur].topmost()), player, streak);
                if (streak == winnum) {
                    win = true;
                }
                streak = northwest(grid, colcur - 1, (grid[colcur].topmost()), player, winnum);
                streak = streak + southeast(grid, colcur + 1, (grid[colcur].topmost() - 2), player, streak);
                if (streak == winnum) {
                    win = true;
                }
                streak = west(grid, colcur - 1, (grid[colcur].topmost() - 1), player, winnum);
                streak = streak + east(grid, colcur + 1, (grid[colcur].topmost() - 1), player, streak);
                if (streak == winnum) {
                    win = true;
                }

                if (win == true) {
                    System.out.println(tokens[player] + " is the winner!");
                }

                player = (player % 2) + 1; //next player 

            }
            
            valid = false;
            while (valid == false) {
                System.out.println("Would you like to play again?  (Y/N)");
                again = (input.next()).toString();
                System.out.println(again);
                if (again.equals("N")) {
                    valid = true;
                    play = false;
                    System.out.println("Ending game");
                }
                else if (again.equals("Y")) {
                    valid = true;
                }
                else {
                    System.out.println("Invalid input");
                }
            }

        }

        input.close();
    }
    
}
