import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class GameTest {

    private Game game;
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayOutputStream testOut;

    @Spy
    Player spyPlayer1 = new Player("Player 1");

    @Spy
    Player spyPlayer2 = new Player("Player 2");

    @BeforeEach
    void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        game = new Game();
    }

    @AfterEach
    void tearDown() {
        System.setOut(systemOut);
    }

    void playGame(String player1Choice, String player2Choice, String expectedOutput) {
        game.player1 = spyPlayer1;
        game.player2 = spyPlayer2;
        spyPlayer1.setChoice(player1Choice);
        spyPlayer2.setChoice(player2Choice);
        doNothing().when(spyPlayer1).setChoice(any());
        doNothing().when(spyPlayer2).setChoice(any());

        game.start();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("=== Welcome to Rock-Paper-Scissors game ===");
        printWriter.println("Enter Player 1 choice (rock, paper or scissors):");
        printWriter.println("Enter Player 2 choice (rock, paper or scissors):");
        printWriter.println(expectedOutput);

        assertEquals(stringWriter.toString(), testOut.toString());
    }

    @ParameterizedTest
    @CsvSource({"rock,scissors", "paper,rock", "scissors,paper"})
    void testAllPlayer1WinCases(String player1Choice, String player2Choice) {
        playGame(player1Choice, player2Choice, "Player 1 win!");
    }

    @ParameterizedTest
    @CsvSource({"scissors,rock", "rock,paper", "paper,scissors"})
    void testAllPlayer2WinCases(String player1Choice, String player2Choice) {
        playGame(player1Choice, player2Choice, "Player 2 win!");
    }

    @ParameterizedTest
    @CsvSource({"rock,rock", "paper,paper", "scissors,scissors"})
    void testAllPlayerDraw(String player1Choice, String player2Choice) {
        playGame(player1Choice, player2Choice, "Draw!");
    }

    @ParameterizedTest(name = "{index} {0}, {1}")
    @CsvSource({"Kirito,paper", "rock,starBurstStream", "haoYouWo,p3k0"})
    void testInvalidCase(String player1Choice, String player2Choice) {
        assertThrows(IllegalArgumentException.class, () -> {
            spyPlayer1.setChoice(player1Choice);
            spyPlayer2.setChoice(player2Choice);
        });
    }
}