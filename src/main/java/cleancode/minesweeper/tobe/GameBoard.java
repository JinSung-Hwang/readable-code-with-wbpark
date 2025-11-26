package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import java.util.Arrays;
import java.util.Random;

public class GameBoard {

  private final Cell[][] board;
  private final int landMindCount;

  public GameBoard(GameLevel gameLevel) {
    int rowSize = gameLevel.getRowSize();
    int colSize = gameLevel.getColSize();
    board = new Cell[rowSize][colSize];

    landMindCount = gameLevel.getLandMineCount();
  }

  public void flag(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    cell.flag();
  }

  public void open(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    cell.open();
  }

  public void openSurroundedCells(int row, int col) {
    if (row < 0 || row >= getRowSize() || col < 0 || col >= getColSize()) {
      return;
    }
    if (isOpenedCell(row, col)) {
      return;
    }
    if (isLandMineCell(row, col)) {
      return;
    }

    open(row, col);

    if (doesCellHaveLandMineCount(row, col)) {
      return;
    }
    openSurroundedCells(row - 1, col - 1);
    openSurroundedCells(row - 1, col);
    openSurroundedCells(row - 1, col + 1);
    openSurroundedCells(row, col - 1);
    openSurroundedCells(row, col + 1);
    openSurroundedCells(row + 1, col - 1);
    openSurroundedCells(row + 1, col);
    openSurroundedCells(row + 1, col + 1);
  }

  private boolean doesCellHaveLandMineCount(int row, int col) {
    return findCell(row, col).hasLandMineCount();
  }

  private boolean isOpenedCell(int row, int col) {
    return findCell(row, col).isOpened();
  }

  public boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
    Cell cell = findCell(selectedRowIndex, selectedColIndex);
    return cell.isLandMind();
  }

  // note: 리펙토링할 메서드가 있으면 직접 바꾸지 말고 복사하고 리펙토링 한다음에 사용되는곳에 하나 하나 적용해서 테스트해보며 반영하는것이 좋다.
//    private static boolean isAllCellOpened() {
//        boolean isAllOpened = true;
//        for (int i = 0; i < BOARD_ROW_SIZE; i++) {
//            for (int j = 0; j < BOARD_COL_SIZE; j++) {
//                if (BOARD[i][j].equals(CLOSED_CELL_SIGN)) {
//                    isAllOpened = false;
//                }
//            }
//        }
//        return isAllOpened;

  //    }
  public boolean isAllCellChecked() {
    return Arrays.stream(board)
        .flatMap(Arrays::stream)
//            .noneMatch(cell -> CLOSED_CELL_SIGN.equals(cell)); // note: cell이 null 가능성이 있기에 상수에서 equals를 사용한다.
        .allMatch(Cell::isChecked);
  }

  public void initializeGame() {
    int rowSize = getRowSize();
    int colSize = getColSize();

    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        board[row][col] = new EmptyCell();
      }
    }

    for (int i = 0; i < landMindCount; i++) {
      int landMineCol = new Random().nextInt(colSize);
      int landMineRow = new Random().nextInt(rowSize);
      board[landMineRow][landMineCol] = new LandMineCell();
    }

    for (int row = 0; row < rowSize; row++) {
      for (int col = 0; col < colSize; col++) {
        if (isLandMineCell(row, col)) {
          continue;
        }
        int count = countNearbyLandMines(row, col);
        if (count == 0) {
          continue;
        }
        board[row][col] = new NumberCell(count);
      }
    }
  }

// note: 아래와 같이 "상속 구조에서는 instanceof등으로 타입을 체크해서 예외적으로 처리하지 않아야 정상적이다."
// note: 아래와 같은 코드가 발생하면 LSP 원칙을 위반한것이므로 이런 타입체크 로직이 필요하지 않도록 객체 설계나 구조를 변경하는것이 필요하다.
//  public void temp(Cell cell) {
//    if (cell instanceof NumberCell) {
//      cell.updateNearbyLandMineCount(0);
//    }
//  }

  public String getSign(int rowIndex, int colIndex) {
    Cell cell = findCell(rowIndex, colIndex);
    return cell.getSign();
  }

  private Cell findCell(int rowIndex, int colIndex) {
    return board[rowIndex][colIndex];
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  private int countNearbyLandMines(int row, int col) {
    int rowSize = getRowSize();
    int colSize = getColSize();

    int count = 0;
    if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
      count++;
    }
    if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
      count++;
    }
    if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
      count++;
    }
    if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
      count++;
    }
    if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
      count++;
    }
    if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
      count++;
    }
    if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
      count++;
    }
    if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(row + 1, col + 1)) {
      count++;
    }
    return count;
  }

}
