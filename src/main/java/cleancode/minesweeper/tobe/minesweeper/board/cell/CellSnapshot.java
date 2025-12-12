package cleancode.minesweeper.tobe.minesweeper.board.cell;

import java.util.Objects;

public class CellSnapshot {
  private final CellSnapshotStatus status;
  private final int nearbyLandMineCount;

  private CellSnapshot(CellSnapshotStatus status, int nearbyLandMineCount) {
    this.status = status;
    this.nearbyLandMineCount = nearbyLandMineCount;
  }

  public static CellSnapshot of(CellSnapshotStatus status, int nearByLandMineCount) {
    return new CellSnapshot(status, nearByLandMineCount);
  }

  public static CellSnapshot ofEmpty() {
    return of(CellSnapshotStatus.EMPTY, 0);
  }

  public static CellSnapshot ofFlag() {
    return of(CellSnapshotStatus.FLAG, 0);
  }

  public static CellSnapshot ofLandMine() {
    return of(CellSnapshotStatus.LAND_MINE, 0);
  }

  public static CellSnapshot ofNumber(int nearbyLandMineCount) {
    return of(CellSnapshotStatus.NUMBER, nearbyLandMineCount);
  }

  public static CellSnapshot ofUnChecked() {
    return of(CellSnapshotStatus.UNCHECKED, 0);
  }

  public boolean isSameStatus(CellSnapshotStatus cellSnapshotStatus) {
    return this.status == cellSnapshotStatus;
  }

  public CellSnapshotStatus getStatus() {
    return status;
  }

  public int getNearbyLandMineCount() {
    return nearbyLandMineCount;
  }

  // note: enum도 VO라고 볼 수 있다. ValueObject는 equals와 hashCode를 구현하는 습관을 기르면 좋다.
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CellSnapshot that = (CellSnapshot) o;
    return nearbyLandMineCount == that.nearbyLandMineCount && status == that.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, nearbyLandMineCount);
  }
}
