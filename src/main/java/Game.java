import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Game {
    Player player1;
    Player player2;

    public Game() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        System.out.println("=== Welcome to Rock-Paper-Scissors game ===");
    }

    public void start() {
        player1.play();
        player2.play();
        compareChoices();
    }

    void compareChoices() {
        if (player1.checkDraw(player2)) {
            System.out.println("Draw!");
        } else {
            System.out.printf("%s win!%n", player1.checkWin(player2) ? player1.getName() : player2.getName());
        }
    }
}

class Player {
    private final String name;
    private String choice;
    private final List<String> choiceList = Arrays.asList("rock", "paper", "scissors");
    BufferedReader br;

    public Player(String name) {
        this.name = name;
        choice = null;
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void play() {
        System.out.printf("Enter %s choice (rock, paper or scissors):%n", name);
        try {
            setChoice(br.readLine());
        } catch (IOException e) {
            System.err.println("Get IOException");
        }
    }

    void setChoice(String choice) {
        if (!choiceList.contains(choice)) {
            throw new IllegalArgumentException("Bad Choice!");
        }
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }

    public String getName() {
        return name;
    }

    public boolean checkDraw(Player opponent) {
        return choice.equals(opponent.getChoice());
    }

    public boolean checkWin(Player opponent) {
        return (choice.equals(choiceList.get(0)) && opponent.getChoice().equals(choiceList.get(2))) ||
                (choice.equals(choiceList.get(1)) && opponent.getChoice().equals(choiceList.get(0))) ||
                (choice.equals(choiceList.get(2)) && opponent.getChoice().equals(choiceList.get(1)));
    }
}