package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.Middle;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {
  public static void main(String[] args) {
    GameLevel gameLevel = new Middle();
    InputHandler inputHandler = new ConsoleInputHandler();
    OutputHandler outputHandler = new ConsoleOutputHandler();

    Minesweeper minesweeper = new Minesweeper(gameLevel, inputHandler, outputHandler);
    minesweeper.initialize();
    minesweeper.run();
  }

  /**
   * DIP (Dependency Inversion Principle): 추상화에 의존해야한다.
   *
   * DI (Dependency Injection): 의존성 주입한다.
   *
   * IoC (Inversion Of Control): 프로그램의 흐름이 개발자 코드가 아니라 프레임워크가 움직인다. IOC컨테이너가 DI를 주입해준다.
   */

}
