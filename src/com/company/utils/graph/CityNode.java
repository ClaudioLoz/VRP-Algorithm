package com.company.utils.graph;

public class CityNode {

  private static int counter =0;
  private final String name;
  //assign unique id to each node. safer than to rely on unique name
  private final int id = counter ++;

  public CityNode(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("City ");
    sb.append(name).append(" (id=").append(id).append(")");
    return sb.toString();
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {

    if(o == null || !(o instanceof CityNode)) return false;

    CityNode c = (CityNode) o;
    return c.getName().equalsIgnoreCase(name) && id == c.getId();
  }

  @Override
  public int hashCode() {
    int hash = 31 * 7 + id;
    return name == null ? hash : name.hashCode();
  }
}