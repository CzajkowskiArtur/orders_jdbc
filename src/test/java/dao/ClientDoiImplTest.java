package dao;

import org.junit.Assert;
import org.junit.Test;
import software.academy.orders.Client;
import software.academy.orders.ClientType;


public class ClientDoiImplTest {
    @Test
    public void delete() throws Exception {
        ClientDao clientDao = new ClientDoiImpl();
        clientDao.delete(4);

    }

    @org.junit.Test//ten test nie przejdzie
    public void shouldFindById() throws Exception {

        ClientDao clientDao = new ClientDoiImpl();
        Client clientFromDatabase = clientDao.findById(1);
        Client expectedClient = new Client(5, "john", "bravo",
                "john@bravo.com", ClientType.REGULAR);
        Assert.assertEquals("Clients should be the same", expectedClient, clientFromDatabase);

    }


    @Test
    public void shouldInsertClient() {
        ClientDao clientDao = new ClientDoiImpl();
        Client newClient = new Client(6, "jan", "kowalski", "jan@kowalski.com", ClientType.REGULAR);

        clientDao.insert(newClient);
    }

    @Test
    public void shouldUpdateClient() {
        ClientDao clientDao = new ClientDoiImpl();
        Client newClient = new Client("jan", "kowalski",
                "jan@mailinator.com", ClientType.REGULAR);
        clientDao.insert(newClient);
    }
}

