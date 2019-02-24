package com.example.hateaos.server.model;

public class Person {
    private String id;
    private String email;
    private String firstName;
    private String lastName;

    public Person() {
    }

    public Person(String id, String email, String firstName, String lastName) {
        super();
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
        
    public void setEmail(final String email) {
        this.email = email;
    }
        
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
        
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
        
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
