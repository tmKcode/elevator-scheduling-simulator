package manager.elevator.dispatcher;

import manager.elevator.elevator.ElevatorCab;
import manager.elevator.floor.Floor;
import manager.elevator.system.ElevatorSystem;
import manager.elevator.system.FloorCall;

import java.util.LinkedList;
import java.util.Queue;

public class Dispatcher {
  private final ElevatorSystem system;
  private Queue<FloorCall> unhandledFloorCalls;

  public Dispatcher(ElevatorSystem system) {
    this.system = system;

    this.unhandledFloorCalls = new LinkedList<>();
  }

  private int distance(Floor callFloor, ElevatorCab cab) {
    return Math.abs(callFloor.getID() - cab.getFloorID());
  }

  private boolean cabIsAvailable(ElevatorCab cab, FloorCall call) {
    return (cab.getState().isIdle() && !cab.hasDestinations(cab.getDestinations()))
        || (cab.getState().isIdle()
            && !cab.getDestination(call.getFloor()).isPickup()
            && (call.getDirection().isUp() ? cab.isGoingUp() : cab.isGoingDown()))
        || (call.getDirection().equals(cab.getState()) && !cab.isTwoDirectionCourse())
        || (call.getFloor().isTop()
            && (cab.getDestination(call.getFloor()).isDropoff()
                || cab.getDestination(call.getFloor()).isPickup()))
        || (call.getFloor().isBottom()
            && (cab.getDestination(call.getFloor()).isDropoff()
                || cab.getDestination(call.getFloor()).isPickup()));
  }

  private void handleFloorCall(FloorCall call) throws CallNotHandledException {
    ElevatorCab calledCab = null;
    int minDistance = system.getNumberOfFloors();

    for (ElevatorCab cab : system.getCabs()) {
        if (cabIsAvailable(cab, call)) {
          if (distance(call.getFloor(), cab) < minDistance) {
            minDistance = distance(call.getFloor(), cab);
            calledCab = cab;
          }
        }
    }

    if (calledCab == null) {
      throw new CallNotHandledException();
    } else {
      calledCab.acceptCall(call);
    }
  }

  public void takeFloorCall (FloorCall call) {
    unhandledFloorCalls.add(call);
  }

  public void handleCalls() {
    while (!unhandledFloorCalls.isEmpty()) {
      FloorCall call = unhandledFloorCalls.peek();

      try {
        handleFloorCall(call);
      } catch (CallNotHandledException ex) {
        return;
      }

      unhandledFloorCalls.remove();
    }
  }
}
