package manager.elevator.elevator;

import manager.elevator.door.Door;
import manager.elevator.floor.Floor;
import manager.elevator.system.DestinationRequest;
import manager.elevator.system.DestinationState;
import manager.elevator.system.ElevatorSystem;

import java.util.ArrayList;
import java.util.List;

public class ElevatorCab {
  private ElevatorSystem system;
  private ElevatorState state;
  private Door door;
  private Floor floor;
  private ArrayList<DestinationState> destinations = new ArrayList<>();
  private boolean twoDirectionCourse;

  public ElevatorState getState() {
    return state;
  }

  public Floor getFloor() {
    return floor;
  }

  public Door getDoor() {
    return door;
  }

  public ArrayList<DestinationState> getDestinations() {
    return destinations;
  }

  public void setDropoff(int floorID, boolean value) {
    destinations.get(floorID).setDropoff(value);
  }

  public void setPickup(int floorID, boolean value) {
    destinations.get(floorID).setPickup(value);
  }

  public DestinationState getFloorState() {
    return destinations.get(floor.getID());
  }

  public int getFloorID() {
    return floor.getID();
  }

  public ElevatorSystem getSystem() {
    return system;
  }

  public void openDoor() {
    door.open();
  }

  public void closeDoor() {
    door.close();
  }

  private void moveUp() {
    floor = system.getFloor(floor.getID() + 1);
  }

  private void moveDown() {
    floor = system.getFloor(floor.getID() - 1);
  }

  private boolean hasDestinations(List<DestinationState> destinations) {
    for (DestinationState destination : destinations) {
      if (destination.isDropoff() || destination.isPickup()) {
        return true;
      }
    }

    return false;
  }

  private boolean reachedTopFloor() {
    return floor.getID() == system.getNumberOfFloors() - 1;
  }

  private boolean reachedBottomFloor() {
    return floor.getID() == 0;
  }

  private boolean shouldMove() {
    return switch (state) {
      case IDLE ->
          hasDestinations(destinations);
      case UP ->
          !reachedTopFloor() && hasDestinations(destinations.subList(getFloorID() + 1, system.getNumberOfFloors() - 1));
      case DOWN ->
          !reachedBottomFloor() && hasDestinations(destinations.subList(0, getFloorID() - 1));
    };
  }

  public void makeStep() {
    if (destinations.get(floor.getID()).isPickup()) {
      if (door.isOpen()) {
        system.acquireRequest(
            new DestinationRequest(this, destinations.get(floor.getID()).getPickupDirection()));

        twoDirectionCourse = false;
        destinations.get(floor.getID()).setPickup(false);
        destinations.get(floor.getID()).setPickupDirection(null);
      } else {
        openDoor();
      }
    } else if (door.isOpen()) {
      closeDoor();
    } else if (shouldMove()) {
      switch (state) {
        case IDLE -> {
          for (int i = 0; i < destinations.size(); i++) {
            if (destinations.get(i).isPickup()) {
              state = i < floor.getID() ? ElevatorState.DOWN : ElevatorState.UP;

              twoDirectionCourse = !destinations.get(i).getPickupDirection().equals(state);
            }
          }
        }
        case UP -> moveUp();
        case DOWN -> moveDown();
      }
    }
  }
}

