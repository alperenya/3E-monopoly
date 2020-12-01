import java.util.Scanner;

public class Driver {

    public static GameEngine ge = new GameEngine();

    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the number of players: ");
        int playerCount = sc.nextInt();
        ge.startGame(playerCount);
        ge.gameFlow();
    }
}
