import model.Client;
import model.builder.ClientBuilder;

public class Boot {

    public static void main(String args[]) {
        Client client = new ClientBuilder()
                .setName("Tester")
                .setIDNumber("185")
                .setAddress("Cluj")
                .setCode("1996")
                .build();

        System.out.println(client.toString());
    }
}
