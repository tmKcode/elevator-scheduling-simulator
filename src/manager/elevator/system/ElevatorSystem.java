package manager.elevator.system;

import manager.elevator.dispatcher.Dispatcher;
import manager.elevator.elevator.ElevatorCab;
import manager.elevator.elevator.ElevatorShaft;
import manager.elevator.floor.Floor;

import java.util.ArrayList;

public class ElevatorSystem {
  private final ArrayList<ElevatorShaft> shafts;
  private final ArrayList<ElevatorCab> cabs;
  private final ArrayList<Floor> floors;
  private final Dispatcher dispatcher;
  private ArrayList<DestinationRequest> requests;

  public ElevatorSystem(int floors, int elevators) {
    this.floors = new ArrayList<>();
    for (int i = 0; i <floors; i++) {
      this.floors.add(new Floor(i, this));
    }

    float ratio = (float) floors / (float) elevators;

    this.shafts = new ArrayList<>();
    this.cabs = new ArrayList<>();
    for (int i = 0; i <elevators; i++) {
      ElevatorCab newCab = new ElevatorCab(this, i, this.floors.get((int) (i * ratio)));

      shafts.add(new ElevatorShaft(newCab));
      cabs.add(newCab);
    }

    this.requests = new ArrayList<>();
    this.dispatcher = new Dispatcher(this);
  }

  public ArrayList<ElevatorShaft> getShafts() {
    return shafts;
  }

  public ArrayList<ElevatorCab> getCabs() {
    return cabs;
  }

  public int getNumberOfFloors() {
    return floors.size();
  }

  public Floor getFloor(int floorID) {
    return floors.get(floorID);
  }

  public boolean floorIDIsValid(int floorID) {
    return floorID >= 0 && floorID < getNumberOfFloors();
  }

  public void printState() {
    StringBuilder line = new StringBuilder();

    line.append("Shafts:  ");

    for (int i = 0; i < shafts.size(); i++) {
      line.append("  ").append(i).append("  ");
    }

    System.out.println(line);

    for (int i = floors.size() - 1; i >=0; i--) {
      System.out.println(floors.get(i).toString());
    }
  }

  public void takeRequest(DestinationRequest request) {
    requests.add(request);
  }

  private void executeRequests() {
    while (!requests.isEmpty()) {
      DestinationRequest request = requests.remove(requests.size() - 1);

      request.execute();
    }
  }

  public void makeStep() {
    dispatcher.handleCalls();

    for (ElevatorCab cab : cabs) {
      cab.makeStep();
    }

    executeRequests();
  }

  public void takeFloorCall(int floorID, CallDirection direction) {
    FloorCall call = new FloorCall(direction, floors.get(floorID));

    floors.get(call.getFloor().getID()).setCalled(call.getDirection(), true);

    dispatcher.takeFloorCall(call);
    dispatcher.handleCalls();
  }
}
