package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        this.account = new Account();
    }

    public AccountBuilder setId(int id) {
        account.setId(id);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setBalance(float balance) {
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
