package com.comp301.a05driver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SnakeOrderAcrossPoolsIterator implements Iterator {
  private final List<Iterator<Driver>> driverIteratorList;
  private int ind;
  private Driver nextDriver;
  private int done;
  private int where;

  public SnakeOrderAcrossPoolsIterator(List<Iterable<Driver>> driverPools) {
    this.nextDriver = null;
    this.where = 1;
    this.done = 0;
    driverIteratorList = new ArrayList<>();
    for (int i = 0; i < driverPools.size(); i++) {
      driverIteratorList.add(driverPools.get(i).iterator());
      if (!driverIteratorList.get(i).hasNext()) {
        done++;
      }
    }
    ind = 0;
  }

  public boolean hasNext() {
    snakeOrderHelper();
    return this.nextDriver != null;
  }

  public Driver next() {
    if (this.hasNext()) {
      Driver temp = nextDriver;
      nextDriver = null;
      return temp;
    } else {
      throw new NoSuchElementException("Error occurred.");
    }
  }

  private void snakeOrderHelper() {
    if (nextDriver != null) return;
    while (done < driverIteratorList.size()) {
      while (!driverIteratorList.get(ind).hasNext()) {
        ind += where;
        if (ind == driverIteratorList.size()) {
          where = -1;
          ind = driverIteratorList.size() - 1;
        }
        if (ind == -1) {
          where = 1;
          ind = 0;
        }
      }
      nextDriver = driverIteratorList.get(ind).next();
      if (!driverIteratorList.get(ind).hasNext()) {
        done++;
      }
      ind += where;
      if (ind == driverIteratorList.size()) {
        where = -1;
        ind = driverIteratorList.size() - 1;
      }
      if (ind == -1) {
        where = 1;
        ind = 0;
      }
      break;
    }
  }
}
