package phone.entity;

import java.util.Objects;

public class Person {
  private String name = "";
  private String email = "";
  private String phoneNumber = "";
  private String company = "";
  private String title = "";

  public Person() {}

  public Person(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }

  public Person(String name, String phoneNumber, String email) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public Person(String name,  String phoneNumber, String email, String company, String title) {
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.company = company;
    this.title = title;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return name.equals(person.name) && phoneNumber.equals(person.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, phoneNumber);
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", company='" + company + '\'' +
        ", title='" + title + '\'' +
        '}';
  }
}
