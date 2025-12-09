package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.cell.Cell;
import cleancode.minesweeper.tobe.cell.Cells;
import cleancode.minesweeper.tobe.cell.EmptyCell;
import cleancode.minesweeper.tobe.cell.LandMineCell;
import cleancode.minesweeper.tobe.cell.NumberCell;
import cleancode.minesweeper.tobe.gameLevel.GameLevel;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.position.CellPositions;
import cleancode.minesweeper.tobe.position.RelativePosition;
import java.util.List;

public class GameBoard {

  private final Cell[][] board;
  private final int landMindCount;

  public GameBoard(GameLevel gameLevel) {
    int rowSize = gameLevel.getRowSize();
    int colSize = gameLevel.getColSize();
    board = new Cell[rowSize][colSize];

    landMindCount = gameLevel.getLandMineCount();
  }

  public void flagAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.flag();
  }

  public void openAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    cell.open();
  }

  public void openSurroundedCells(CellPosition cellPosition) {
    if (isOpenedCell(cellPosition)) {
      return;
    }
    if (isLandMineCellAt(cellPosition)) {
      return;
    }

    openAt(cellPosition);

    if (doesCellHaveLandMineCount(cellPosition)) {
      return;
    }

    List<CellPosition> cellPositions = calculateSurroundedPositions(cellPosition, getRowSize(), getColSize());
    cellPositions.forEach(this::openSurroundedCells);
  }

  private boolean doesCellHaveLandMineCount(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.hasLandMineCount();
  }

  private boolean isOpenedCell(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.isOpened();
  }

  public boolean isLandMineCellAt(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
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
    Cells cells = Cells.from(board);
    return cells.isAllChecked();
  }

  public boolean isInvalidCellPosition(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();

    return cellPosition.isRowIndexMoreThanOrEqual(rowSize)
        || cellPosition.isColIndexMoreThanOrEqual(colSize);
  }

  public void initializeGame() {
    CellPositions cellPositions = CellPositions.from(board);

    initializeEmptyCells(cellPositions);

    List<CellPosition> landMinePositions = cellPositions.extractRandomPositions(landMindCount);
    initializeLandMineCells(landMinePositions);

    List<CellPosition> numberPositionCandidates = cellPositions.subtract(landMinePositions);
    initializeNumberCells(numberPositionCandidates);
  }

  private void initializeEmptyCells(CellPositions cellPositions) {
    List<CellPosition> allPositions = cellPositions.getPositions();
    for (CellPosition position: allPositions) {
      updateCellAt(position, new EmptyCell());
    }
  }

  private void initializeLandMineCells(List<CellPosition> landMinePositions) {
    for (CellPosition position: landMinePositions) {
      updateCellAt(position, new LandMineCell());
    }
  }

  private void initializeNumberCells(List<CellPosition> numberPositionCandidates) {
    for (CellPosition candidatePosition: numberPositionCandidates) {
      int count = countNearbyLandMines(candidatePosition);
      if (count != 0) {
        updateCellAt(candidatePosition, new NumberCell(count));
      }
    }
  }

  private void updateCellAt(CellPosition position, Cell cell) {
    board[position.getRowIndex()][position.getColIndex()] = cell;
  }

// note: 아래와 같이 "상속 구조에서는 instanceof등으로 타입을 체크해서 예외적으로 처리하지 않아야 정상적이다."
// note: 아래와 같은 코드가 발생하면 LSP 원칙을 위반한것이므로 이런 타입체크 로직이 필요하지 않도록 객체 설계나 구조를 변경하는것이 필요하다.
//  public void temp(Cell cell) {
//    if (cell instanceof NumberCell) {
//      cell.updateNearbyLandMineCount(0);
//    }
//  }

  public String getSign(CellPosition cellPosition) {
    Cell cell = findCell(cellPosition);
    return cell.getSign();
  }

  private Cell findCell(CellPosition cellPosition) {
    return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
  }

  public int getRowSize() {
    return board.length;
  }

  public int getColSize() {
    return board[0].length;
  }

  private int countNearbyLandMines(CellPosition cellPosition) {
    int rowSize = getRowSize();
    int colSize = getColSize();

    long count = calculateSurroundedPositions(cellPosition, rowSize, colSize)
        .stream()
        .filter(this::isLandMineCellAt)
        .count();

    return (int) count;
  }

  // note: 재사용하기 좋게 하기 위해서 stream을 리턴하는것이 아니라 toList로 리턴하도록했다.
  private List<CellPosition> calculateSurroundedPositions(CellPosition cellPosition, int rowSize, int colSize) {
    return RelativePosition.SURROUNDED_POSITIONS.stream()
        .filter(cellPosition::canCalculatePositionBy)
        .map(cellPosition::calculatePositionBy)
        .filter(position -> position.isRowIndexLessThan(rowSize))
        .filter(position -> position.isColIndexLessThan(colSize))
        .toList();
  }
}
