package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.config.GameConfig;
import cleancode.minesweeper.tobe.game.GameInitializable;
import cleancode.minesweeper.tobe.game.GameRunnable;
import cleancode.minesweeper.tobe.io.InputHandler;
import cleancode.minesweeper.tobe.io.OutputHandler;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;

// note: tip1: 메서드 추출해서 리펙토링하다보면 공통 로직도 같이 리펙토링 하는 경우가 있다. IDE에서 공통 로직이라고 해도 내가 눈으로 꼭 확인해봐야한다. 같은 코드여도 맥락이 다를 수 있다.
public class Minesweeper implements GameInitializable, GameRunnable {

  private final GameBoard gameBoard;
  private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
  private final InputHandler inputHandler;
  private final OutputHandler outputHandler;

//  public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
//    gameBoard = new GameBoard(gameLevel);
//    this.inputHandler = inputHandler;
//    this.outputHandler = outputHandler;
//  }

  public Minesweeper(GameConfig gameConfig) {
    gameBoard = new GameBoard(gameConfig.getGameLevel());
    this.inputHandler = gameConfig.getInputHandler();
    this.outputHandler = gameConfig.getOutputHandler();
  }

  @Override
  public void initialize() {
    gameBoard.initializeGame();
  }

  @Override
  public void run() {
    outputHandler.showGameStartComments();

    while (gameBoard.isInProgress()) {
      try {
        outputHandler.showBoard(gameBoard);

        // note: scanner가 true문 밖에서 선언되었는데 사용은 이쪽까지 와서 사용되어 사용되는곳으로 옮겼다. 그러니 while안에서 반복 생성되어 상수로 올렸다.
        CellPosition cellPosition = getCellInputFromUser();
        UserAction userActionInput = getUserActionInputFromUser();
        actOnCell(cellPosition, userActionInput);
      } catch (GameException e) {
        outputHandler.showExceptionMessage(e);
//        consoleOutputHandler.printExceptionMessage(e.getMessage()); // note: printExceptionMessage(e)와 printExceptionMessage(e.getMessage())중 어떤 메세지 시그니처가 좋은가? e를 넣어주는것이 좀더 범용성이 높다?
      } catch (Exception e) {
        outputHandler.showSimpleMessage("프로그램에 문제가 생겼습니다.");
      }
    }

    outputHandler.showBoard(gameBoard);
    if (gameBoard.isWinStatus() ) { // note: if문의 간단한 로직이지만 추상화 레벨을 맞추기위해서 메서드로 추상화했다.
      outputHandler.showGameWinningComment();
    }
    if (gameBoard.isLoseStatus()) {
      outputHandler.showGameLosingComment();
    }
  }

  private CellPosition getCellInputFromUser() {
    outputHandler.showCommentForSelectingCell();
    CellPosition cellPosition = inputHandler.getCellPositionFromUser();
    if (gameBoard.isInvalidCellPosition(cellPosition)) {
      throw new GameException("잘못된 좌표를 선택하셨습니다.");
    }

    return cellPosition;
  }

  private UserAction getUserActionInputFromUser() {
    outputHandler.showCommentForUserAction();
    return inputHandler.getUserActionFromUser();
  }

  private void actOnCell(CellPosition cellPosition, UserAction userAction) {
    if (doesUserChooseToPlantFlag(userAction)) {
      gameBoard.flagAt(cellPosition);
      return;
    }

    if (doesUserChooseToOpenCell(userAction)) {
      gameBoard.openAt(cellPosition);
      return;
    }
    throw new GameException("잘못된 번호를 선택하셨습니다.");
  }

  private boolean doesUserChooseToPlantFlag(UserAction userAction) {
    return userAction == UserAction.FLAG;
  }

  private boolean doesUserChooseToOpenCell(UserAction userAction) {
    return userAction == UserAction.OPEN;
  }

}
