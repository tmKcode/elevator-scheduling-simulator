package manager.elevator.system;

public class DestinationState {
  // If both are false
  private boolean pickup;
  private CallDirection pickupDirection;
  private boolean dropoff;

  public DestinationState() {
    this.pickup = false;
    this.pickupDirection = null;
    this.dropoff = false;
  }


  public boolean isPickup() {
    return pickup;
  }

  public boolean isDropoff() {
    return dropoff;
  }

  public CallDirection getPickupDirection() {
    return pickupDirection;
  }

  public void setPickup(boolean pickup) {
    this.pickup = pickup;
  }

  public void setPickupDirection(CallDirection pickupDirection) {
    this.pickupDirection = pickupDirection;
  }

  public void setDropoff(boolean dropoff) {
    this.dropoff = dropoff;
  }
}
