package cleancode.minesweeper.tobe.cell;

public interface Cell {

  boolean isLandMind();

  boolean hasLandMineCount();

  CellSnapshot getSnapshot();

  void flag();

  void open();

  boolean isChecked();

  boolean isOpened();

}
