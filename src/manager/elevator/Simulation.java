package manager.elevator;

import manager.elevator.system.ElevatorSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
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

  public static void main(String[] args) throws FileNotFoundException {
    Simulation.printFile("./messages/welcome.txt");

    int floors =
        Simulation.scanInt("floors");

    int elevators =
        Simulation.scanInt("elevators");

    ElevatorSystem system = new ElevatorSystem(floors, elevators);
  }
}
