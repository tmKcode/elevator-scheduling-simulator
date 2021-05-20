package manager.elevator.system;

import manager.elevator.elevator.ElevatorState;

public enum CallDirection {
  UP,
  DOWN;

  public boolean equals(ElevatorState state) {
    return (state == ElevatorState.DOWN && this == DOWN) || (state == ElevatorState.UP && this == UP);
  }
}
