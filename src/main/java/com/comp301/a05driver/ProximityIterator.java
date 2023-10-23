package com.comp301.a05driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ProximityIterator implements Iterator<Driver> {

  private static Position clientPos;
  private Iterator<Driver> drivers;
  private Driver nextDriver;
  private int range;

  public ProximityIterator(Iterable<Driver> driverPool, Position clientPosition, int proximityRange) {
    if (clientPosition == null) {throw new IllegalArgumentException();}
    if (driverPool == null) {throw new NoSuchElementException();}
    this.clientPos = clientPosition;
    this.drivers = driverPool.iterator();
    this.range = proximityRange;
    this.nextDriver = null;
  }

  @Override
  public boolean hasNext() {
    findNextDriver();
    if (this.nextDriver != null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Driver next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    Driver nextD = this.nextDriver;
    this.nextDriver = null;
    return nextD;
  }

  private Driver findNextDriver() {
    if (this.nextDriver != null) {
    } else {
      int i = 0;
      while (drivers.hasNext() && i == 0) {
        Driver next = drivers.next();
        if (next.getVehicle().getPosition().getManhattanDistanceTo(clientPos) <= this.range) {
          this.nextDriver = next;
          i += 1;
        }
      }
    }
    return this.nextDriver;
  }
}
