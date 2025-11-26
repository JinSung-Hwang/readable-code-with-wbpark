package cleancode.minesweeper.tobe.cell;

public abstract class Cell {

  protected static final String FLAG_SIGN = "⚑";
  protected static final String UNCHECKED_SIGN = "□";

  protected boolean isFlagged;
  protected boolean isOpened;

//   note: 설계를 잘하면 아래 메서드(turnOnLandMine, updateNearbyLandMineCount)가 필요 없을것이다.
//  public abstract void turnOnLandMine();
//  public abstract void updateNearbyLandMineCount(int count);

  public abstract boolean isLandMind();

  public abstract boolean hasLandMineCount();

  public abstract String getSign();

  public void flag() {
    this.isFlagged = true;
  }

  public void open() {
    this.isOpened = true;
  }

  public boolean isChecked() {
    return isFlagged || isOpened;
  }

  public boolean isOpened() {
    return isOpened;
  }

}
