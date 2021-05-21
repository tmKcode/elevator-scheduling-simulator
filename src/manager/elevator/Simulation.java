package manager.elevator;

import manager.elevator.system.CallDirection;
import manager.elevator.system.ElevatorSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Simulation {
  public static void printFile(String path) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(path));

    while (scanner.hasNextLine()) {
      System.out.println(scanner.nextLine());
    }
  }

  private static int scanInt(String name) {
    Scanner scanner = new Scanner(System.in);
    boolean continueInput = true;

    System.out.println("Enter the number of " + name + ":");

    int scannedInt = -1;

    do {
      try {
        scannedInt = scanner.nextInt();

        if (scannedInt <= 0) {
          throw (new InputMismatchException());
        }

        continueInput = false;
      } catch (InputMismatchException ex) {
        System.out.println("Wrong number of " + name + "! Try again:");

        scanner.nextLine();
      }
    } while (continueInput);

    return scannedInt;
  }

  private static void processCommand(ElevatorSystem system) throws FileNotFoundException {
    Scanner scanner = new Scanner(System.in);
    boolean continueInput = true;

    System.out.println("Enter command:");

    do {
      try {
          switch (scanner.next()) {
          case "h" -> Simulation.printFile("messages/help_default.txt");
          case "s" -> {
            system.makeStep();

            system.printState();
          }
          case "q" -> System.exit(0);
          case "c" -> {
            int floorID = scanner.nextInt();

            if(!system.floorIDIsValid(floorID)) {
              throw new InputMismatchException();
            }

            CallDirection direction = CallDirection.valueOf(scanner.next());

            if (floorID == system.getNumberOfFloors() - 1 && direction.isUp()) {
              System.out.println("You can't go UP from top floor!");
              throw new InputMismatchException();
            } else if (floorID == 0 && direction.isDown()) {
              System.out.println("You can't go DOWN from bottom floor!");
              throw new InputMismatchException();
            }

            system.takeFloorCall(floorID, direction);

            system.printState();
          }
            default -> throw new InputMismatchException();
        }

        continueInput = false;
      } catch (InputMismatchException | IllegalArgumentException ex) {
        System.out.println("Wrong command! Use 'h' for help.");
        System.out.println("Enter command:");

        scanner.nextLine();
      }
    } while (continueInput);
  }


  public static void main(String[] args) throws FileNotFoundException {
    Simulation.printFile("messages/welcome.txt");

    int floors =
        Simulation.scanInt("floors");

    int elevators =
        Simulation.scanInt("elevators");

    ElevatorSystem elevatorSystem = new ElevatorSystem(floors, elevators);

    elevatorSystem.printState();

    Simulation.printFile("messages/help_default.txt");
    
    while(true) {
      processCommand(elevatorSystem);
    }
  }
}
