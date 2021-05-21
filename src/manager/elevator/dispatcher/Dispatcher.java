package manager.elevator.dispatcher;

import manager.elevator.elevator.ElevatorCab;
import manager.elevator.elevator.ElevatorState;
import manager.elevator.floor.Floor;
import manager.elevator.system.ElevatorSystem;
import manager.elevator.system.FloorCall;

public class Dispatcher {
  private final ElevatorSystem system;

  public Dispatcher(ElevatorSystem system) {
    this.system = system;
  }

  private int distance(Floor callFloor, ElevatorCab cab) {
    return Math.abs(callFloor.getID() - cab.getFloorID());
  }

  private boolean cabIsAvailable(ElevatorCab cab, FloorCall call) {
    return cab.getState() == ElevatorState.IDLE || (call.getDirection().equals(cab.getState())
  }

  public void handleFloorCall(FloorCall call) throws CallNotHandledException {
    ElevatorCab calledCab = null;
    int minDistance = system.getNumberOfFloors() + 1;

    for (ElevatorCab cab : system.getCabs()) {
        if (cab.getState() == ElevatorState.IDLE || cab.getState())
    }
  }
}
