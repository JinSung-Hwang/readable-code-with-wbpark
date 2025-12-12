package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class EmptyCell implements Cell {

  private final CellState cellState = CellState.initialize();

//  @Override
//  public void turnOnLandMine() {
//    throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
//  }

//  @Override
//  public void updateNearbyLandMineCount(int count) {
//    throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
//  }

  @Override
  public boolean isLandMind() {
    return false;
  }

  @Override
  public boolean hasLandMineCount() {
    return false;
  }

  @Override
  public CellSnapshot getSnapshot() {
    if (cellState.isOpened()) {
      return CellSnapshot.ofEmpty();
    }
    if (cellState.isFlagged()) {
      return CellSnapshot.ofFlag();
    }
    return CellSnapshot.ofUnChecked();
  }

  @Override
  public void flag() {
    cellState.flag();
  }

  @Override
  public void open() {
    cellState.open();
  }

  @Override
  public boolean isChecked() {
    return cellState.isChecked();
  }

  @Override
  public boolean isOpened() {
    return cellState.isOpened();
  }
}
