package room_selector;

import java.util.List;

public class Person {

  private int weight;
  private String name;
  private List<Integer> ranks;

  public Person(int weight, String name) {
    this.weight = weight;
    this.name = name;
  }

  public void setRanks(List<Integer> ranks) {
    this.ranks = ranks;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getWeight() {
    return weight;
  }

  public String getName() {
    return name;
  }

  public int getRankIndex(int i) {
    return ranks.get(i);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getName());
    sb.append(" = [");
    for (int i : ranks) {
      sb.append(i).append(", ");
    }
    sb.append(weight).append("]");

    return sb.toString();
  }
}
