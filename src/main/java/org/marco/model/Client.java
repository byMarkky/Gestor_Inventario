package org.marco.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String email;
    private int purchases;
    private LocalDateTime createDate;
    private LocalDateTime udpateDate;

    public Client(int id, String name, String surname, String email, int purchases, LocalDateTime createDate, LocalDateTime udpateDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.purchases = purchases;
        this.createDate = createDate;
        this.udpateDate = udpateDate;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPurchases() {
        return purchases;
    }

    public void setPurchases(int purchases) {
        this.purchases = purchases;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return udpateDate;
    }

    public void setUdpateDate(LocalDateTime udpateDate) {
        this.udpateDate = udpateDate;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", purchases=" + purchases +
                ", createDate=" + createDate +
                ", udpateDate=" + udpateDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && purchases == client.purchases && Objects.equals(name, client.name) && Objects.equals(surname, client.surname) && Objects.equals(email, client.email) && Objects.equals(createDate, client.createDate) && Objects.equals(udpateDate, client.udpateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, purchases, createDate, udpateDate);
    }
}
