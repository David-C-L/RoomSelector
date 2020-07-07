package room_selector;

public class Main {

  public static void main(String[] args) {
    RoomSelector selector = new RoomSelector(4);
    selector.addPersons("/home/david/Desktop/JavaPractices/roomdata.txt");
    System.out.println(selector.toString());
    System.out.println(selector.roomAllocs());
  }

}
