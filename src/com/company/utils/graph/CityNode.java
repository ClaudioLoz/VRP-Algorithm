package com.company.utils.graph;

import com.company.Node;

import java.util.Objects;

import static com.company.utils.Constants.*;

public class CityNode extends Node {
  String ubigeo;
  String dep;
  String region;
  private static int counter =0;
  private final String name;
  //assign unique id to each node. safer than to rely on unique name
  private final int id = counter ++;
  private  double maximumTime;

  public CityNode(double latitude, double longitude, String ubigeo, String dep, String region, String name) {
    super(latitude, longitude);
    this.ubigeo = ubigeo;
    this.dep = dep;
    this.region = region;
    this.name = name;
    if (Objects.equals(this.region, SELVA)) {
      this.maximumTime = 72;
    }
    if (Objects.equals(this.region, SIERRA)) {
      this.maximumTime = 48;
    }
    if (Objects.equals(this.region, COSTA)) {
      this.maximumTime = 24;
    }
  }

  public CityNode(String name, double maximumTime) {
    this.name = name;
    this.maximumTime= maximumTime;
  }

  public CityNode(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }

  public String getUbigeo() {
    return ubigeo;
  }

  public void setUbigeo(String ubigeo) {
    this.ubigeo = ubigeo;
  }

  public String getDep() {
    return dep;
  }

  public void setDep(String dep) {
    this.dep = dep;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public double getMaximumTime() {
    return maximumTime;
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