package model.builder;

import model.Account;

import java.sql.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(double balance) {
        account.setBalance(balance);
        return this;
    }

    public AccountBuilder setCreationDate(Date date) {
        account.setCreationDate(date);
        return this;
    }

    public Account build() {
        return account;
    }

}
