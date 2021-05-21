package manager.elevator.system;

import manager.elevator.elevator.ElevatorCab;

import java.util.InputMismatchException;
import java.util.Scanner;


public class DestinationRequest {
  private final ElevatorCab cab;
  private final CallDirection direction;

  public DestinationRequest(ElevatorCab cab, CallDirection direction) {
    this.cab = cab;
    this.direction = direction;
  }

  private boolean scannedFloorIDIsValid(int scannedFloorID) {
    return scannedFloorID >= 0 && scannedFloorID < cab.getSystem().getNumberOfFloors() && switch(direction) {
      case UP -> scannedFloorID > cab.getFloorID();
      case DOWN -> scannedFloorID < cab.getFloorID();
        };
  }

  private int scanFloorID() {
    Scanner scanner = new Scanner(System.in);
    boolean continueInput = true;

    System.out.println("Elevator "
        + cab.getID()
        + " picks up passengers at floor "
        + cab.getFloorID()
        + " to go "
        + direction
        + ".");
    System.out.println("Enter destination (floor number):");

    int scannedFloorID = -1;

    do {
      try {
        scannedFloorID = scanner.nextInt();

        if (!scannedFloorIDIsValid(scannedFloorID)) {
          throw (new InputMismatchException());
        }

        continueInput = false;
      } catch (InputMismatchException ex) {
        System.out.println("Wrong floor number, try again!");

        scanner.nextLine();
      }
    } while (continueInput);

    return scannedFloorID;
  }

  public void execute() {
    cab.setDropoff(scanFloorID(), true);
  }
}
