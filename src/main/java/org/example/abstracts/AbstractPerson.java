package org.example.abstracts;

import org.example.sequencers.PersonIdSequencer;

public abstract class AbstractPerson {
     private int id;
     private String name;
     private String email;
     private String address;

     public AbstractPerson() {}

     public AbstractPerson(String name, String email, String address) {
          this.id = PersonIdSequencer.getInstance().nextId();
          this.name = name;
          this.email = email;
          this.address = address;
     }

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
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

     public String getAddress() {
          return address;
     }

     public void setAddress(String address) {
          this.address = address;
     }
}
