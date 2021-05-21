package manager.elevator.system;

import manager.elevator.floor.Floor;

public class FloorCall {
  private final CallDirection direction;
  private final Floor floor;

  public FloorCall(CallDirection direction, Floor floor) {
    this.direction = direction;
    this.floor = floor;
  }

  public CallDirection getDirection() {
    return direction;
  }

  public Floor getFloor() {
    return floor;
  }
}
