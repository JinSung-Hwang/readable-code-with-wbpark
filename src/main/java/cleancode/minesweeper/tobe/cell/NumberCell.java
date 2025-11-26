package cleancode.minesweeper.tobe.cell;

public class NumberCell extends Cell {

  private final int nearbyLandMindCount;

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
    if (isOpened) {
      return String.valueOf(nearbyLandMindCount);
    }
    if (isFlagged) {
      return FLAG_SIGN;
    }
    return UNCHECKED_SIGN;
  }
}
