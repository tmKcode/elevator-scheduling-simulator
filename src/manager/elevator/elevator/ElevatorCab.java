package manager.elevator.elevator;

import manager.elevator.floor.Floor;

public class ElevatorCab {
  private ElevatorState state;

  private Floor floor;

  public ElevatorState getState() {
    return state;
  }

  public Floor getFloor() {
    return floor;
  }
}
