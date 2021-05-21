package manager.elevator.elevator;

public enum ElevatorState {
  IDLE,
  UP,
  DOWN;

  public boolean isIdle() {
    return this == IDLE;
  }

  public boolean isUp() {
    return this == UP;
  }

  public boolean isDown() {
    return this == DOWN;
  }
}
