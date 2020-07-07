package room_selector;

public enum Rooms {

  GROUND(0),
  LARGE_MIDDLE(1),
  EN_SUITE_MIDDLE(2),
  TOP(3);

  private int index;

  Rooms(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }
}
