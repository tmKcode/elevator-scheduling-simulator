package manager.elevator.system;

import manager.elevator.elevator.ElevatorCab;
import manager.elevator.elevator.ElevatorShaft;
import manager.elevator.floor.Floor;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Scanner;

import static manager.elevator.system.CallDirection.UP;

public class ElevatorSystem {
  ArrayList<ElevatorShaft> shafts = new ArrayList<>();
  ArrayList<ElevatorCab> cabs = new ArrayList<>();
  ArrayList<Floor> floors = new ArrayList<>();
  Queue<DestinationRequest> requests = new Queue<DestinationRequest>();

  public ArrayList<ElevatorShaft> getShafts() {
    return shafts;
  }

  public int getNumberOfFloors() {
    return floors.size();
  }

  public Floor getFloor(int floorID) {
    return  floors.get(floorID);
  }

  private void stepElevatorCab(ElevatorCab cab) {
    if (cab.getDoor().isOpen()) {
      cab.closeDoor();

      if (cab.getFloorState().isPickup()) {
        int newDestinationFloor = scanFloorID(cab);
        cab.getDestinations().get(newDestinationFloor).setDropoff(true);
      }

    }
    if (cab.getFloorState().isDropoff()) {
      cab.openDoor();

      cab.getFloorState().setDropoff(false);
    }
    if (cab.getFloorState().isPickup()) {
      cab.openDoor();

      cab.getFloorState().setPickup(false);
      cab.getFloorState().setPickupDirection(null);
    }
  }

  public void printState() {
    StringBuilder line = new StringBuilder();

    line.append("Shafts:  ");

    for (int i = 0; i < shafts.size(); i++) {
      line.append("  ").append(i).append("  ");
    }

    System.out.println(line);

    for (Floor floor : floors) {
      System.out.println(floor.toString());
    }
  }

  public void acquireRequest(DestinationRequest request) {
    requests.add(request);
  }
}
