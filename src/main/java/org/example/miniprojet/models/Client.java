package org.example.miniprojet.models;

public class Client {
    private int id;
    private String firstName, lastName, email;
    private boolean editable = false;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getEditable() {
        return editable;
    }


    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Client() {
    }

    public Client(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Client && this.id == ((Client)obj).getId();
    }
}
