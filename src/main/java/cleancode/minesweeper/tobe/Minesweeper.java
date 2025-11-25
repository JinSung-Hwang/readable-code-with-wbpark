package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.io.ConsoleInputHandler;
import cleancode.minesweeper.tobe.io.ConsoleOutputHandler;

// note: tip1: 메서드 추출해서 리펙토링하다보면 공통 로직도 같이 리펙토링 하는 경우가 있다. IDE에서 공통 로직이라고 해도 내가 눈으로 꼭 확인해봐야한다. 같은 코드여도 맥락이 다를 수 있다.
public class Minesweeper {

  private final GameBoard gameBoard;
  private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
  private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
  private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
  private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

  public Minesweeper(GameLevel gameLevel) {
    gameBoard = new GameBoard(gameLevel);
  }

  public void run() {
    consoleOutputHandler.showGameStartComments();
    gameBoard.initializeGame();

    while (true) {
      try {
        consoleOutputHandler.showBoard(gameBoard);

        if (doesUserWinTheGame()) { // note: if문의 간단한 로직이지만 추상화 레벨을 맞추기위해서 메서드로 추상화했다.
          consoleOutputHandler.printGameWinningComment();
          break;
        }
        if (doesUserLoseTheGame()) {
          consoleOutputHandler.printGameLosingComment();
          break;
        }

        // note: scanner가 true문 밖에서 선언되었는데 사용은 이쪽까지 와서 사용되어 사용되는곳으로 옮겼다. 그러니 while안에서 반복 생성되어 상수로 올렸다.
        String cellInput = getCellInputFromUser();
        String userActionInput = getUserActionInputFromUser();
        actOnCell(cellInput, userActionInput);
      } catch (GameException e) {
        consoleOutputHandler.printExceptionMessage(e);
//        consoleOutputHandler.printExceptionMessage(e.getMessage()); // note: printExceptionMessage(e)와 printExceptionMessage(e.getMessage())중 어떤 메세지 시그니처가 좋은가? e를 넣어주는것이 좀더 범용성이 높다?
      } catch (Exception e) {
        consoleOutputHandler.printSimpleMessage("프로그램에 문제가 생겼습니다.");
      }
    }
  }

  private void actOnCell(String cellInput, String userActionInput) {
    int selectedColIndex = boardIndexConverter.getSelectedColIndex(cellInput, gameBoard.getColSize());
    int selectedRowIndex = boardIndexConverter.getSelectedRowIndex(cellInput, gameBoard.getRowSize());

    if (doesUserChooseToPlantFlag(userActionInput)) {
      gameBoard.flag(selectedRowIndex, selectedColIndex);
      checkIfGameIsOver();
      return;
    }

    if (doesUserChooseToOpenCell(userActionInput)) {
      if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
        gameBoard.open(selectedRowIndex, selectedColIndex);
        changeGameStatusToLose();
        return;
      }

      gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
      checkIfGameIsOver();
      return;
    }
    throw new GameException("잘못된 번호를 선택하셨습니다.");
  }

  private void changeGameStatusToLose() {
    gameStatus = -1;
  }


  private boolean doesUserChooseToOpenCell(String userActionInput) {
    return userActionInput.equals("1");
  }

  private boolean doesUserChooseToPlantFlag(String userActionInput) {
    return userActionInput.equals("2");
  }

  private String getUserActionInputFromUser() {
    consoleOutputHandler.printCommentForUserAction();
    return consoleInputHandler.getUserInput();
  }

  private String getCellInputFromUser() {
    consoleOutputHandler.printCommentForSelectingCell();
    return consoleInputHandler.getUserInput();
  }

  private boolean doesUserLoseTheGame() {
    return gameStatus == -1;
  }

  private boolean doesUserWinTheGame() {
    return gameStatus == 1;
  }

  private void checkIfGameIsOver() {
    if (gameBoard.isAllCellChecked()) {
      changeGameStatusToWin();
    }
  }

  private void changeGameStatusToWin() {
    gameStatus = 1;
  }

}
