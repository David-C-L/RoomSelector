package pair;

public class GenericPair<K,V> implements Pair<K,V> {

  private K first;
  private V second;

  public GenericPair(K first, V second) {
    this.first = first;
    this.second = second;
  }

  @Override
  public K getFirst() {
    return first;
  }

  @Override
  public V getSecond() {
    return second;
  }

  @Override
  public void setFirst(K first) {
    this.first = first;
  }

  @Override
  public void setSecond(V second) {
    this.second = second;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("( ");
    sb.append(first.toString());
    sb.append(", ");
    sb.append(second.toString());
    sb.append(")");

    return sb.toString();
  }
}
