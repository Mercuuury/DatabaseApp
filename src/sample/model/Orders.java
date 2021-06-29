package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DBConnection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.logging.Logger;

@XmlRootElement(name = "orders")
public class Orders {
    @XmlElement(name = "order")
    ObservableList<Order> list;

    private DBConnection dbConnection = null;
    private final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    public Orders() { }
    public Orders(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insert(Order order) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "INSERT INTO `orders` (`idDrivers`, `length`, `passengerCount`, `addressSrc`, `addressDst`, `date`, `time`, `status`) VALUES ('" +
                order.getIdDrivers() + "', '" + order.getLength() + "', '" + order.getPassengerCount() + "', '" + order.getAddressSrc() +
                "', '" + order.getAddressDst() + "', '" + order.getDate() + "', '" + order.getTime() + "', '" + order.getStatus() + "')";
        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
        }
        catch (SQLException e) {
            LOGGER.info("SQL Error : " + e.getMessage());
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    LOGGER.info("Statement Error : " + ex.getMessage());
                }
            }
        }
    }

    public ObservableList<Order> getAll() {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "SELECT `idOrders`, `idDrivers`, `length`, `passengerCount`, `addressSrc`, `addressDst`, `date`, `time`, `status` FROM `orders`";
        Statement statement = null;
        list = FXCollections.observableArrayList();

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sqlQuery);
            while(res.next()){
                list.add(new Order(res.getInt("idOrders"),
                        res.getInt("idDrivers"),
                        res.getFloat("length"),
                        res.getInt("passengerCount"),
                        res.getString("addressSrc"),
                        res.getString("addressDst"),
                        res.getDate("date"),
                        res.getTime("time"),
                        res.getString("status")));
            }
        }
        catch (SQLException e) {
            LOGGER.info("SQL Error : " + e.getMessage());
        }
        finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    LOGGER.info("Statement Error : " + ex.getMessage());
                }
            }
        }

        return list;
    }

    public void update(Order order) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "UPDATE `orders` SET `idDrivers` = '" + order.getIdDrivers() +
                "', `length` = '" + order.getLength() +
                "', `passengerCount` = '" + order.getPassengerCount() +
                "', `addressSrc` = '" + order.getAddressSrc() +
                "', `addressDst` = '" + order.getAddressDst() +
                "', `date` = '" + order.getDate() +
                "', `time` = '" + order.getTime() +
                "', `status` = '" + order.getStatus() +
                "' WHERE `idOrders` = '" + order.getIdOrders() + "'";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlQuery);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            LOGGER.info("SQL Error : " + e.getMessage());
        }
        finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LOGGER.info("Statement Error : " + ex.getMessage());
                }
            }
        }
    }

    public void delete(Order order) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "DELETE FROM `orders` WHERE `idOrders` = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, order.getIdOrders());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("SQL Error : " + e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    LOGGER.info("Statement Error : " + ex.getMessage());
                }
            }
        }
    }
}
