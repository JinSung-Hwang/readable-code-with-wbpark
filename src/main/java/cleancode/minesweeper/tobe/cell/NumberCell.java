package cleancode.minesweeper.tobe.cell;

public class NumberCell implements Cell {

  private final int nearbyLandMindCount;
  private final CellState cellState = CellState.initialize();

  public NumberCell(int nearbyLandMindCount) {
    this.nearbyLandMindCount = nearbyLandMindCount;
  }

//  @Override
//  public void turnOnLandMine() {
//    throw new UnsupportedOperationException("지원하지 않는 기능입니다.");
//  }

//  @Override
//  public void updateNearbyLandMineCount(int count) {
//    this.nearbyLandMindCount = count;
//  }

  @Override
  public boolean isLandMind() {
    return false;
  }

  @Override
  public boolean hasLandMineCount() {
    return true;
  }

  @Override
  public String getSign() {
    if (cellState.isOpened()) {
      return String.valueOf(nearbyLandMindCount);
    }
    if (cellState.isFlagged()) {
      return FLAG_SIGN;
    }
    return UNCHECKED_SIGN;
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
