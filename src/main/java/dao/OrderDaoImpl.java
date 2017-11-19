package dao;

import exceptions.DatabaseException;
import org.apache.log4j.Logger;
import software.academy.orders.Order;
import software.academy.orders.OrderItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.sql.Timestamp;

public class OrderDaoImpl implements OrderDao {

    private Logger logger = Logger.getLogger(OrderDaoImpl.class);

    public static final String MYSQL_CONNECTION_STRING = "jdbc:mysql://localhost/orders?" +
            "user=root&password=root";

    @Override
    public void insert(Order order) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            logger.info("Trying to get connection...");
            connection = DriverManager.getConnection(MYSQL_CONNECTION_STRING);
            // transakcja nie jest commitowana przy kazdym statement.execute
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("INSERT INTO `orders`.`order`\n" +
                    "(`client_id`,\n" +
                    "`insert_date`,\n" +
                    "`update_date`,\n" +
                    "`status`,\n" +
                    "`total_cost`)\n" +
                    "VALUES (?,?,?,?,?)");

            statement.setInt(1, order.getClientId());
            statement.setTimestamp(2, new Timestamp(order.getInsertDate().getTime()));

            statement.setTimestamp(3, null);
            statement.setString(4, order.getStatus().name());
            statement.setBigDecimal(5, order.getTotalCost() );
            // transakcja jeszcze nie jest zacommitowana
            statement.execute();

            if (order.getItems()!=null) {
                // zapis pozycji zamowienia
                for (OrderItem orderItem : order.getItems()) {
                    statement = connection.prepareStatement("INSERT INTO `orders`.`order_item`\n" +
                            "(`order_id`,\n" +
                            "`product_id`,\n" +
                            "`quantity`)\n" +
                            " VALUES\n" +
                            "(last_insert_id(),\n" +
                            "?,\n" +
                            "?)");
                    statement.setInt(1, orderItem.getProductId());
                    statement.setInt(2, orderItem.getQuantity());
                    statement.execute();
                }
            }
            connection.commit();
            logger.info("Order was added");
        } catch (SQLException se) {
            logger.error("Problem during order insert", se);
            throw new DatabaseException("Problem during order insert", se);
        } finally {
            if (connection!=null) {
                try {
                    connection.close();
                } catch (SQLException se) {

                }

            }
        }
    }


}
