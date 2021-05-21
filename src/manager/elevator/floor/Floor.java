package manager.elevator.floor;

import manager.elevator.elevator.ElevatorShaft;
import manager.elevator.system.CallDirection;
import manager.elevator.system.ElevatorSystem;
import manager.elevator.system.FloorCall;

import java.util.ArrayList;

import static manager.elevator.system.CallDirection.UP;

public class Floor {
  private final int ID;
  private final ElevatorSystem system;
  private boolean calledUp;
  private boolean calledDown;

  public Floor(int ID, ElevatorSystem system) {
    this.ID = ID;
    this.system = system;

    this.calledUp = false;
    this.calledDown = false;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("Calls: ");

    if (calledUp && calledDown) {
      result.append('↕');
    } else if (calledUp) {
      result.append('↑');
    } else if (calledDown) {
      result.append('↓');
    } else {
      result.append('-');
    }

    result.append(' ');

    for (ElevatorShaft shaft : system.getShafts()) {
      result.append(shaft.toString(this));
    }

    return result.toString();
  }

  public void call(CallDirection direction) {
    switch (direction) {
      case UP -> calledUp = true;
      case DOWN -> calledDown = true;
    }
  }

  public int getID() {
    return ID;
  }
}
