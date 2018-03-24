package model.builder;

import model.Client;

public class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        this.client = new Client();
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIDNumber(String idNumber) {
        client.setIdNumber(idNumber);
        return this;
    }

    public ClientBuilder setCode(String code) {
        client.setCode(code);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public Client build() {
        return client;
    }

}
