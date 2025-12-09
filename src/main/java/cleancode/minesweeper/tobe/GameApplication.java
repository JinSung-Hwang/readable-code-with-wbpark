package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.gameLevel.Middle;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;

public class GameApplication {
  public static void main(String[] args) {
    // note: 이전에는 GameLevel, InputHandler, OutputHandler를 각각 넣어줬는데 게임이 발전함에 따라 config 종류가 계속 생성될거 같다고 판단되어 새롭게 도메인 개념 도출하여 만들었다.
    // note: 이렇게 미래의 상황을 예측하여 도메인 객체를 새로 만들었는데 매번 과하게 하면 오버엔지니어링이 될 수 있기에 조심해야한다.
    GameConfig gameConfig = new GameConfig(
        new Middle(),
        new ConsoleInputHandler(),
        new ConsoleOutputHandler()
    );

    Minesweeper minesweeper = new Minesweeper(gameConfig);
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
