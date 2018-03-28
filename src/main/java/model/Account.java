package model;

import java.sql.Date;

public class Account {

    private Long id;
    private String type;
    private double balance;
    private Date creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "type='" + type + '\'' +
                ", balance=" + balance +
                ", creationDate=" + creationDate +
                '}';
    }
}
