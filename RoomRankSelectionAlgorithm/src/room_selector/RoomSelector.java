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
    Random gen = new Random();

    for (int i = 0; i < roomCount; i++) {
      int finalI = i;
      int lowestRank = persons.stream().map(p -> p.getRankIndex(finalI)).reduce(Integer::min).get();
      List<Person> competitorsRank = persons.stream().filter(p -> p.getRankIndex(finalI) == lowestRank)
          .collect(Collectors.toList());
      int lowestWeight = competitorsRank.stream().map(Person::getWeight).reduce(Integer::min).get();
      List<Person> competitorsWeight = competitorsRank.stream().filter(p -> p.getWeight() == lowestWeight)
          .collect(Collectors.toList());
      allocs.put(Rooms.values()[i], competitorsWeight.get(gen.nextInt(competitorsWeight.size())));
      persons.remove(allocs.get(Rooms.values()[i]));
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






