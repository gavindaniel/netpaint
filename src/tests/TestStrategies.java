package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.ComputerPlayer;
import model.IGotNowhereToGoException;
import model.RandomAI;
import model.IntermediateAI;
import model.TicTacToeGame;

public class TestStrategies {

 
  @Test
  public void testIntermediateToWin() {
    TicTacToeGame theGame = new TicTacToeGame();
    // When testing is true, humanMove does not set the computer player move
    theGame.humanMove(0, 0, true); // X
    theGame.computerMove(2, 0); // O
    theGame.humanMove(1, 0, true); // X
    theGame.computerMove(2, 1); // O
    theGame.humanMove(1, 0, true); // X

    //  System.out.println(theGame + "\n" + computerMove);
    //  X  _  _ 
    //  X  _  _ 
    //  O  O   
    // Should get Point(2,2)
    ComputerPlayer playerWithIntermediateStrategy = new ComputerPlayer();
    playerWithIntermediateStrategy.setStrategy(new IntermediateAI());
    Point computerMove = playerWithIntermediateStrategy.desiredMove(theGame);
    theGame.computerMove(computerMove.x, computerMove.y);
    assertEquals(2, computerMove.x);
    assertEquals(2, computerMove.y);
  }

  @Test
  public void testIntermediateToBlock() {
    TicTacToeGame theGame = new TicTacToeGame();
    // When testing is true, humanMove does not set the computer player move
    theGame.humanMove(0, 0, true); // X
    theGame.computerMove(2, 0); // O
    theGame.humanMove(0, 1, true); // X

    // System.out.println(theGame + "\n" +computerMove);
    // X  X  O 
    //  _  _  _ 
    //  O  _  _ 
    // Should get Point(0,2) to block
    ComputerPlayer playerWithIntermediateStrategy = new ComputerPlayer();
    playerWithIntermediateStrategy.setStrategy(new IntermediateAI());
    Point computerMove = playerWithIntermediateStrategy.desiredMove(theGame);
    theGame.computerMove(computerMove.x, computerMove.y);
    assertEquals(0, computerMove.x);
    assertEquals(2, computerMove.y);
  }

  @Test(expected = IGotNowhereToGoException.class)
  public void showWhatHappensWhenAnExceptionMustBeThrown() {
    TicTacToeGame theGame = new TicTacToeGame();
    theGame.humanMove(0, 0, true); // X
    theGame.computerMove(0, 1); // O
    theGame.humanMove(1, 0, true); // X
    theGame.computerMove(1, 1); // O
    theGame.humanMove(0, 2, true); // X
    theGame.computerMove(1, 2); // O
    theGame.humanMove(2, 1, true); // X
    theGame.computerMove(2, 0); // O
    theGame.humanMove(2, 2, true); // X
    
    assertTrue(theGame.tied());
//    System.out.println(theGame + "\n" );
//    X  O  X 
//    X  O  O 
//    O  X  X 
 
    // This should throw an exception since the board is filled 
    ComputerPlayer player = new ComputerPlayer();
    player.setStrategy(new RandomAI());
    // This should throw an exception
    Point tryToMakeMoveWhenFilled = player.desiredMove(theGame);
  }

  @Test
  public void run1000TicTacToeGames() {
    ComputerPlayer randomBot = new ComputerPlayer();
    randomBot.setStrategy(new RandomAI());
    ComputerPlayer IntermediateBot = new ComputerPlayer();
    IntermediateBot.setStrategy(new IntermediateAI());

    int randomPlayerWins = 0;
    int IntermediatePlayerWins = 0;
    int ties = 0;

    for (int game = 1; game <= 1000; game++) {
      char winner = playOneGame(IntermediateBot, randomBot);
      if (winner == 'X')
        IntermediatePlayerWins++;
      if (winner == 'O')
        randomPlayerWins++;
      if (winner == 'T')
        ties++;
    }

    System.out.println("IntermediateAI strategy should have many more wins than the");
    System.out.println("RandomAI strategy. This tournament has the Intermediate");
    System.out.println("strategy choose first.  Ties can certainly happen.");
    System.out.println("===========================================");
    System.out.println("Intermediate win percentage: " + IntermediatePlayerWins / 10. + "%");
    System.out.println("Random win percentage: " + randomPlayerWins / 10. + "%");
    System.out.println("Ties happened " + ties / 10. + "% of the games");
  }

  private char playOneGame(ComputerPlayer first, ComputerPlayer second) {
    TicTacToeGame theGame = new TicTacToeGame();

    while (true) {
      Point firstsMove = first.desiredMove(theGame);
      theGame.humanMove(firstsMove.x, firstsMove.y, true);

      if (theGame.tied())
        return 'T';

      if (theGame.didWin('X'))
        return 'X';
      if (theGame.didWin('O'))
        return 'O';

      Point secondsMove = second.desiredMove(theGame);
      theGame.computerMove(secondsMove.x, secondsMove.y);

      if (theGame.tied())
        return 'T';

      if (theGame.didWin('X'))
        return 'X';
      if (theGame.didWin('O'))
        return 'O';
    }
  }

  @Test
  public void testIntermediate2() {
    TicTacToeGame theGame = new TicTacToeGame();

    ComputerPlayer playerWithIntermediateStrategy = new ComputerPlayer();
    playerWithIntermediateStrategy.setStrategy(new IntermediateAI());
    // X
    theGame.humanMove(0, 0, true);
    // O
    theGame.computerMove(2, 0);
    // X
    theGame.humanMove(0, 2, true);

    Point computerMove = playerWithIntermediateStrategy.desiredMove(theGame);
    theGame.humanMove(computerMove.x, computerMove.y, true);
    assertEquals(0, computerMove.x);
    assertEquals(1, computerMove.y);
  }

  @Test
  public void testIntermediate3() {
    TicTacToeGame theGame = new TicTacToeGame();

    ComputerPlayer playerWithIntermediateStrategy = new ComputerPlayer();
    playerWithIntermediateStrategy.setStrategy(new IntermediateAI());
    // X
    theGame.humanMove(0, 0, true);
    // O
    theGame.computerMove(0, 2);
    // X
    theGame.humanMove(1, 0, true);

    Point computerMove = playerWithIntermediateStrategy.desiredMove(theGame);
    theGame.computerMove(computerMove.x, computerMove.y);
    assertEquals(2, computerMove.x);
    assertEquals(0, computerMove.y);
  }
}
