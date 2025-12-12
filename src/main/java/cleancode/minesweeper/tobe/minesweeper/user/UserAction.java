package cleancode.minesweeper.tobe.minesweeper.user;

public enum UserAction {
  // note: 값만 있어서는 후대 사람들이 알기 어렵다. 그래서 description필드를 꼭 넣어주는 편이다.

  OPEN("셀 열기"),
  FLAG("깃발 꽂기"),
  UNKNOWN("알 수 없음");

  private final String description;

  UserAction(String description) {
    this.description = description;
  }
}
