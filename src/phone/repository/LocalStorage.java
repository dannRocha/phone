package phone.repository;

import phone.entity.Person;
import phone.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocalStorage {
  public static Set<Person> contacts = new HashSet<>() {{
    add(new Person("Daniel Rocha da Silva", "+55 98 9 8181-9393", "rochadaniel@email.com"));
  }};
  public static Set<Person> favorites = new HashSet<>();
  public static List<String> historic = new ArrayList<>();

  public static Set<User> users = new HashSet<>() {{
    add(new User("admin", "1234"));
  }};

}
