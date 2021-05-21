package manager.elevator.elevator;

import manager.elevator.door.Door;
import manager.elevator.floor.Floor;
import manager.elevator.system.*;

import java.util.ArrayList;
import java.util.List;

public class ElevatorCab {
  private final ElevatorSystem system;
  private final int ID;
  private ElevatorState state;
  private Door door;
  private Floor floor;
  private final ArrayList<DestinationState> destinations;
  private boolean twoDirectionCourse;

  public ElevatorCab(ElevatorSystem system, int ID, Floor floor) {
    this.system = system;
    this.ID = ID;
    this.floor = floor;

    this.state = ElevatorState.IDLE;
    this.door = door = new Door();
    this.twoDirectionCourse = false;

    this.destinations = new ArrayList<>();
    for(int i = 0; i < system.getNumberOfFloors(); i++) {
      destinations.add(new DestinationState());
    }

  }

  public ElevatorSystem getSystem() {
    return system;
  }

  public int getID() {
    return ID;
  }

  public ElevatorState getState() {
    return state;
  }

  public Door getDoor() {
    return door;
  }

  public Floor getFloor() {
    return floor;
  }

  public int getFloorID() {
    return floor.getID();
  }

  public ArrayList<DestinationState> getDestinations() {
    return destinations;
  }

  public DestinationState getDestination(Floor floor) {
    return destinations.get(floor.getID());
  }

  public DestinationState getFloorState() {
    return destinations.get(floor.getID());
  }

  public boolean isTwoDirectionCourse() {
    return twoDirectionCourse;
  }

  public void removePickup(int floorID) {
    destinations.get(floorID).setPickup(false);
    destinations.get(floorID).setPickupDirection(null);
  }

  public void setPickup(FloorCall call) {
    destinations.get(call.getFloor().getID()).setPickup(true);
    destinations.get(call.getFloor().getID()).setPickupDirection(call.getDirection());
  }

  public void setDropoff(int floorID, boolean value) {
    destinations.get(floorID).setDropoff(value);
  }

  public void openDoor() {
    door.open();
  }

  public void closeDoor() {
    door.close();
  }

  private boolean isCalledUp(FloorCall call) {
    return call.getFloor().getID() > floor.getID();
  }

  private boolean isCalledDown(FloorCall call) {
    return call.getFloor().getID() < floor.getID();
  }

  public boolean isGoingUp() {
    for(int i = 0; i < destinations.size(); i++) {
      if (destinations.get(i).isPickup() || destinations.get(i).isDropoff()) {
        return i > floor.getID();
      }
    }

    return false;
  }

  public boolean isGoingDown() {
    for(int i = 0; i < destinations.size(); i++) {
      if (destinations.get(i).isPickup() || destinations.get(i).isDropoff()) {
        return i < floor.getID();
      }
    }

    return false;
  }

  private boolean callIsTwoDirection(FloorCall call) {
    return (isCalledDown(call) && call.getDirection().isUp()) || (isCalledUp(call) && call.getDirection().isDown());
  }

  public void acceptCall(FloorCall call) {
    setPickup(call);

    if(state.isIdle() && callIsTwoDirection(call)) {
      twoDirectionCourse = true;
    }
  }

  private void moveUp() {
    floor = system.getFloor(floor.getID() + 1);
  }

  private void moveDown() {
    floor = system.getFloor(floor.getID() - 1);
  }

  public boolean hasDestinations(List<DestinationState> destinations) {
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
          !reachedTopFloor() && hasDestinations(destinations.subList(getFloorID() + 1, system.getNumberOfFloors()));
      case DOWN ->
          !reachedBottomFloor() && hasDestinations(destinations.subList(0, getFloorID()));
    };
  }

  public void makeStep() {
    if (destinations.get(floor.getID()).isPickup()) {
      if (door.isOpen()) {
        destinations.get(floor.getID()).setDropoff(false);

        system.takeRequest(
            new DestinationRequest(this, destinations.get(floor.getID()).getPickupDirection()));

        twoDirectionCourse = false;
        floor.setCalled(destinations.get(floor.getID()).getPickupDirection(), false);
        removePickup(floor.getID());
      } else {
        state = ElevatorState.IDLE;

        openDoor();

        if (destinations.get(floor.getID()).isDropoff()) {
          destinations.get(floor.getID()).setDropoff(false);
        }
      }
    } else if (destinations.get(floor.getID()).isDropoff()) {
      state = ElevatorState.IDLE;

      openDoor();

      destinations.get(floor.getID()).setDropoff(false);
    } else if (door.isOpen()) {
      closeDoor();
    } else if (shouldMove()) {
      switch (state) {
        case IDLE -> {
          for (int i = 0; i < destinations.size(); i++) {
            if (destinations.get(i).isPickup()) {
              state = i < floor.getID() ? ElevatorState.DOWN : ElevatorState.UP;

              twoDirectionCourse = !destinations.get(i).getPickupDirection().equals(state);
            } else if (destinations.get(i).isDropoff()) {
              state = i < floor.getID() ? ElevatorState.DOWN : ElevatorState.UP;
            }
          }
        }
        case UP -> moveUp();
        case DOWN -> moveDown();
      }
    } else {
      state = ElevatorState.IDLE;
    }
  }
}

