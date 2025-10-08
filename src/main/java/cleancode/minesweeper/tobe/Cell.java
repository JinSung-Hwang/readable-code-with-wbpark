package cleancode.minesweeper.tobe;

public class Cell {
  private static final String FLAG_SIGN = "⚑";
  private static final String LAND_MINE_SIGN = "☼";
  private static final String UNCHECKED_SIGN = "□";
  private static final String EMPTY_SIGN = "■";

  private int nearbyLandMindCount;
  private boolean isLandMine;
  private boolean isFlagged;
  private boolean isOpened;

  public Cell(int nearbyLandMindCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
    this.nearbyLandMindCount = nearbyLandMindCount;
    this.isLandMine = isLandMine;
    this.isFlagged = isFlagged;
    this.isOpened = isOpened;
  }

  // note: 정적 팩토리 메서드를 사용하면 이름을 의미있게 만들고 각 팩토리메서드마다 벨리데이션을 따로 둘 수 있다.
  public static Cell of(int nearbyLandMindCount, boolean isLandMine, boolean isFlagged, boolean isOpened) {
    return new Cell(nearbyLandMindCount, isLandMine, isFlagged, isOpened);
  }

  public static Cell create() {
    return of(0, false, false, false);
  }

  public void turnOnLandMine() {
    this.isLandMine = true;
  }

  public void updateNearbyLandMineCount(int count) {
    this.nearbyLandMindCount = count;
  }

  public void flag() {
    this.isFlagged = true;
  }

  public void open() {
    this.isOpened = true;
  }

  public boolean isChecked() {
    return isFlagged || isOpened;
  }

  public boolean isLandMind() {
    return isLandMine;
  }

  public boolean isOpened() {
    return isOpened;
  }

  public boolean hasLandMineCount() {
    return this.nearbyLandMindCount != 0;
  }

  public String getSign() {
    if (isOpened) {
      if (isLandMine) {
        return LAND_MINE_SIGN;
      }
      if (hasLandMineCount()) {
        return String.valueOf(nearbyLandMindCount);
      }
      return EMPTY_SIGN;
    }

    if (isFlagged) {
      return FLAG_SIGN;
    }

    return UNCHECKED_SIGN;
  }
}
