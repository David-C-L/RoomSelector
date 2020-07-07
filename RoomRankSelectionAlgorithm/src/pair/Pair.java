package pair;

public interface Pair<K,V> {

  K getFirst();

  V getSecond();

  void setFirst(K item);

  void setSecond(V item);

}
