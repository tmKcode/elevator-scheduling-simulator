package manager.elevator.door;

public class Door {
  private boolean open;

  public boolean isOpen() {
    return open;
  }

  public void open() {
    open = true;
  }

  public void close() {
    open = false;
  }

  @Override
  public String toString() {
    return open ? " " : "|";
  }
}
