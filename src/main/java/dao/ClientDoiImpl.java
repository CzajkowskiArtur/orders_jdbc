package dao;


import exceptions.DatabaseException;
import org.apache.log4j.Logger;
import software.academy.orders.Client;
import software.academy.orders.ClientType;

import java.sql.*;


public class ClientDoiImpl implements ClientDao {
    public static final String URL = "jdbc:mysql://localhost/orders?" + "user=root";
    private Logger logger = Logger.getLogger(ClientDoiImpl.class);

    public Client findById(Integer id) {
        Connection connection = null;
     PreparedStatement statement = null;
        Client client = null;
        try {
            logger.info("Trying to get connection...");
            //-adres do mojej bazy danych
            connection = DriverManager.getConnection(URL);

             statement = connection.prepareStatement(
                    "SELECT id, first_name, second_name, email, type FROM client WHERE id=? ");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer clientId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                String email = resultSet.getString("email");
                String clientType = resultSet.getString("type");
                resultSet.close();
                statement.close();
                connection.close();
                ClientType type = ClientType.valueOf(clientType);
                client =  new Client(clientId, firstName, secondName, email, type);
            }

        } catch (SQLException se) {
            logger.error("Problem with getting client",se);
        } finally {

        }
        return client;
    }

    @Override
    public void insert(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.info("Trying to get connection...");
            connection = DriverManager.getConnection(URL);
            statement = connection.prepareStatement("INSERT INTO client (first_name, second_name, email, type) " +
                    "VALUES (?,?,?,?)");
            statement.setString(1, client.getFirstName());
            statement.setString(2, client.getSecondName());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getType().name());
            statement.execute();
        } catch (SQLException se) {
            logger.error("Problem during client insert", se);
            throw new DatabaseException("Problem during client insert", se);


        }
    }

    public void update(Client client) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.info("Trying to get connection...");
            connection = DriverManager.getConnection(URL);
            statement = connection.prepareStatement("UPDATE client SET first_name=?, second_name=?, email=?, type=? " +
                    " WHERE id=?");

            int parameterIndex = 1;
            statement.setString(parameterIndex++, client.getFirstName());
            statement.setString(parameterIndex++, client.getSecondName());
            statement.setString(parameterIndex++, client.getEmail());
            statement.setString(parameterIndex++, client.getType().name());
            statement.setInt(parameterIndex++,client.getId());
            statement.execute();
        } catch (SQLException se) {
            logger.error("Problem during client update", se);
            throw new DatabaseException("Problem during client update", se);
        }
    }


    @Override
    public void delete(Integer id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.info("Trying to get connection....");
            connection = DriverManager.getConnection(URL);
            statement = connection.prepareStatement("DELETE FROM client WHERE id = ?");
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException se) {
            logger.error("Problem during client delete", se);
            throw new DatabaseException("Problem during client delete", se);
        }
    }
}

