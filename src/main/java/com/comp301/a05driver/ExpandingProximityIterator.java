package com.comp301.a05driver;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExpandingProximityIterator implements Iterator<Driver> {
  private static Position clientPos;
  private Iterable<Driver> driverP;
  private int expansion;
  private Iterator<Driver> drivers;
  private Driver nextDriver;
  private boolean flag;
  private int i;

  public ExpandingProximityIterator(
      Iterable<Driver> driverPool, Position clientPosition, int expansionStep) {
    clientPos = clientPosition;
    this.driverP = driverPool;
    this.expansion = expansionStep;
    this.nextDriver = null;
    this.flag = false;
    this.drivers = driverPool.iterator();
    this.i = 0;
  }

  @Override
  public boolean hasNext() {
    expandingProxy();
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
    } else {
      Driver nextD = this.nextDriver;
      this.nextDriver = null;
      return nextD;
    }
  }

  private void expandingProxy() {
    while (this.nextDriver == null) {
      if (!drivers.hasNext()) {
        if (flag) {
          i++;
          flag = false;
          drivers = driverP.iterator();
        } else {
          break;
        }
      } else {
        Driver maybe = drivers.next();
        int distance = clientPos.getManhattanDistanceTo(maybe.getVehicle().getPosition());
        if (distance > 1 + ((i - 1) * expansion)) {
          if (distance > 1 + (i * expansion)) {
            flag = true;
          } else {
            nextDriver = maybe;
          }
        }
      }
    }
  }
}
