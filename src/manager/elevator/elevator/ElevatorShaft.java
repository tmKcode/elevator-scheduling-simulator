package manager.elevator.elevator;

import manager.elevator.floor.Floor;

public class ElevatorShaft {
  private final ElevatorCab cab;

  public ElevatorShaft(ElevatorCab cab) {
    this.cab = cab;
  }

  public String toString(Floor floor) {
    if (cab.getFloor() == floor) {
      return cab.getDoor().toString()
          + switch (cab.getState()) {
            case IDLE -> " ▯ ";
            case UP -> " △ ";
            case DOWN -> "  ▽  ";
          }
          + cab.getDoor().toString();
    }
    else {
      return "|   |";
    }
  }
}
