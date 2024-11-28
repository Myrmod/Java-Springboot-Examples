package com.example.angular_spring_boot.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "email_id")
  private String emailID;
  @Column(name = "password")
  private String password;

  // we need this since hibernate creates empty objects and fills them later
  public Employee() {
  }

  public Employee(long id, String firstName, String lastName, String emailID) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.emailID = emailID;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailID() {
    return this.emailID;
  }

  public void setEmailID(String emailID) {
    this.emailID = emailID;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
