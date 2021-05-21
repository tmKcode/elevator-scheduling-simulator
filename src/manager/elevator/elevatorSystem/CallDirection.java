package manager.elevator.system;

import manager.elevator.elevator.ElevatorState;

public enum CallDirection {
  UP,
  DOWN;

  public boolean equals(ElevatorState state) {
    return (state.isDown() && this.isDown()) || (state.isUp() && this.isUp());
  }

  public boolean isUp() {
    return this == UP;
  }

  public boolean isDown() {
    return this == DOWN;
  }
}
