package room_selector;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RoomSelector {

  private static int BASE_WEIGHT = 2;
  private static int GEN_ITERATIONS = 100;
  private static int RANDOM_SEED = 10;

  private int roomCount;
  private List<Person> persons;
  private HashMap<Rooms, Person> allocs;


  public RoomSelector(int roomCount) {
    this.roomCount = roomCount;
    this.persons = new ArrayList<>();
    this.allocs = new HashMap<>();
  }

  public void addPersons(String filePath) {

    try {
      File file = new File(filePath);
      Scanner scanner = new Scanner(file);

      while (scanner.hasNextLine()) {
        String[] data = parser(scanner.nextLine());
        Person next = new Person(parseWeight(data[2]), parseName(data[0]));
        next.setRanks(parseRanks(data[1]));
        persons.add(next);
      }

    } catch (FileNotFoundException e) {
      System.out.println("File Opening Error: ");
      e.printStackTrace();
    }
  }

  private String[] parser(String line) {
    return line.split("\\.");
  }

  private String parseName(String data) {
    return data.strip();
  }

  private List<Integer> parseRanks(String data) {
    String[] ranks = data.split(",");

    return Arrays.stream(ranks)
        .map(String::strip)
        .map(Integer::parseInt)
        .collect(Collectors.toList());
  }

  private int parseWeight(String data) {
    return Integer.parseInt(data.strip());
  }

  public String roomAllocs() {
    Random gen = new Random(RANDOM_SEED);
    int seed = gen.nextInt();
    for (int i = 0; i < GEN_ITERATIONS; i++) {
      seed = gen.nextInt();
    }
    Random finalGen = new Random(seed);

    for (int i = 0; i < roomCount; i++) {
      List<Person> pref_one = new ArrayList<>();
      List<Person> pref_two = new ArrayList<>();
      List<Person> pref_three = new ArrayList<>();
      List<Person> pref_four = new ArrayList<>();
      List<Person>[] lists = new List[4];
      lists[0] = pref_one;
      lists[1] = pref_two;
      lists[2] = pref_three;
      lists[3] = pref_four;
      for (Person p : persons) {
        lists[p.getRankIndex(i) - 1].add(p);
      }
      Person person = null;
      for (List<Person> preference_list : lists) {
        if (person == null && !preference_list.isEmpty()) {
          int lowestWeight = preference_list.stream()
              .map(Person::getWeight)
              .reduce(Integer::min)
              .get();
          List<Person> lowestWeightPeople = preference_list.stream()
              .filter(p -> p.getWeight() == lowestWeight)
              .collect(Collectors.toList());
          person = lowestWeightPeople.get(Math.abs(finalGen.nextInt()) % lowestWeightPeople.size());
        }
      }
      allocs.put(Rooms.values()[i], person);
      persons.remove(person);
    }
    return allocsToString();
  }


  private String allocsToString() {
    StringBuilder sb = new StringBuilder();
    sb.append("####################################################");
    sb.append("\n");
    for (int i = 0; i < roomCount; i++) {
      sb.append(Rooms.values()[i]).append(": ")
          .append(allocs.get(Rooms.values()[i]).getName());
      sb.append("\n");
    }
    sb.append("####################################################");

    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[ ");
    sb.append(Rooms.values()[0]);
    for (int i = 1; i < roomCount; i++) {
      sb.append(", ");
      sb.append(Rooms.values()[i]);
    }
    sb.append(", WEIGHT");
    sb.append(" ]");
    for (Person p : persons) {
      sb.append("\n").append(p.toString());
    }

    return sb.toString();
  }
}






