package manager.elevator.floor;

import manager.elevator.elevator.ElevatorShaft;
import manager.elevator.system.ElevatorSystem;
import manager.elevator.system.FloorCall;

import java.util.ArrayList;

import static manager.elevator.system.CallDirection.UP;

public class Floor {
  private int ID;

  private ArrayList<FloorCall> calls;

  private ElevatorSystem system;

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("Calls: ");

    if (calls.size() == 2) {
      result.append('↕');
    } else if (calls.size() == 1) {
      if (calls.get(0).getDirection() == UP) {
        result.append('↑');
      } else {
        result.append('↓');
      }
    } else {
      result.append('-');
    }

    result.append(' ');

    for (ElevatorShaft shaft : system.getShafts()) {
      result.append(shaft.toString(this));
    }

    return result.toString();
  }

  public int getID() {
    return ID;
  }
}
