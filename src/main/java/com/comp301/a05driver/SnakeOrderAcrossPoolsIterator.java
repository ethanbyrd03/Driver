package com.comp301.a05driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SnakeOrderAcrossPoolsIterator implements Iterator<Driver> {

  private final List<Iterator<Driver>> driverList;
  private Driver nextDriver;
  private int ind;
  private int current;
  private int finish;

  public SnakeOrderAcrossPoolsIterator(List<Iterable<Driver>> driverPools) {
    this.nextDriver = null;
    this.finish = 0;
    this.current = 1;
    this.driverList = new ArrayList<>();
    for (int i = 0; i < driverPools.size(); i++) {
      driverList.add(driverPools.get(i).iterator());
      if (!driverList.get(i).hasNext()) {
        finish++;
      }
    }
    ind = 0;
  }

  @Override
  public boolean hasNext() {
    snakeOrder();
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

  private void snakeOrder() {
    if (nextDriver != null) {
      return;
    }
    while (finish < driverList.size()) {
      while (!driverList.get(ind).hasNext()) {
        ind += current;
        if (ind == driverList.size()) {
          current = -1;
          ind = driverList.size() - 1;
        }
        if (ind == -1) {
          ind = 0;
          current = 1;
        }
      }
      nextDriver = driverList.get(ind).next();
      if (!driverList.get(ind).hasNext()) {
        current++;
      }
      ind += current;
      if (ind == driverList.size()) {
        current = -1;
        ind = driverList.size() - 1;
      }
      if (ind == -1) {
        current = 1;
        ind = 0;
      }
      break;
    }
  }
}
