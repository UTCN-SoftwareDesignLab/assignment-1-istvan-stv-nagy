package model.builder;

import model.Bill;

public class BillBuilder {

    private Bill bill;

    public BillBuilder() {
        this.bill = new Bill();
    }

    public BillBuilder setId(Long id) {
        bill.setId(id);
        return this;
    }

    public BillBuilder setAmount(double amount) {
        bill.setAmount(amount);
        return this;
    }

    public BillBuilder setDescription(String description) {
        bill.setDescription(description);
        return this;
    }

    public Bill build() {
        return bill;
    }

}
