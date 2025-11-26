package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.game.GameRunnable;

//public class AnotherGame implements Game {
// note: 기존에는 Game interface를 상속했지만 이 AnotherGame에서는 initialize가 필요없어서 구현할 필요가없다.
// note: 그래서 이것은 SOLID의 ISP원칙을 도입해서 Game interface를 GameInitializable과 GameRunnable로 쪼개고 필요한 GameRunnable만 상속했다.
public class AnotherGame implements GameRunnable {

//  @Override
//  public void initialize() {
//    System.out.println("구현할 필요가 없음");
//  }

  @Override
  public void run() {
  }
}
