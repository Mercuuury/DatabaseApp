package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DBConnection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.logging.Logger;

@XmlRootElement(name = "paymentInfo")
public class PaymentInfo {
    @XmlElement(name = "payment")
    ObservableList<Payment> list;

    private DBConnection dbConnection = null;
    private final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    public PaymentInfo() { }
    public PaymentInfo(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insert(Payment payment) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "INSERT INTO `paymentInfo` (`idOrders`, `idTariffs`, `mileage`, `price`) VALUES ('" +
                payment.getIdOrders() + "', '" + payment.getIdTariffs() + "', '" +
                payment.getMileage() + "', '" + payment.getPrice() + "')";
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

    public ObservableList<Payment> getAll() {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "SELECT `idOrders`, `idTariffs`, `mileage`, `price` FROM `paymentInfo`";
        Statement statement = null;
        list = FXCollections.observableArrayList();

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sqlQuery);
            while(res.next()){
                list.add(new Payment(res.getInt("idOrders"),
                        res.getInt("idTariffs"),
                        res.getFloat("mileage"),
                        res.getFloat("price")));
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

    public void update(Payment payment) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "UPDATE `paymentInfo` SET `mileage` = '" + payment.getMileage() +
                                                "', `price` = '" + payment.getPrice() +
                                            "' WHERE `idOrders` = '" + payment.getIdOrders() +
                                            "' AND `idTariffs` = '" + payment.getIdTariffs() + "'";
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

    public void delete(Payment payment) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "DELETE FROM `paymentInfo` WHERE `idOrders` = ? AND `idTariffs` = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, payment.getIdOrders());
            ps.setInt(2, payment.getIdTariffs());
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
