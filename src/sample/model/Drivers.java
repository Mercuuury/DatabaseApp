package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DBConnection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.logging.Logger;

@XmlRootElement(name = "drivers")
public class Drivers {
    @XmlElement(name = "driver")
    ObservableList<Driver> list;

    private DBConnection dbConnection = null;
    private final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    public Drivers() { }
    public Drivers(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insert(Driver driver) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "INSERT INTO `drivers` (`surname`, `name`, `secondName`, `birthday`, `passSeries`, `passNumber`, `vehicle`) VALUES ('" +
                driver.getSurname() + "', '" + driver.getName() + "', '" + driver.getSecondName() + "', '" +
                driver.getBirthday() + "', '" + driver.getPassSeries() + "', '" + driver.getPassNumber() + "', '" + driver.getVehicle() + "')";
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

    public ObservableList<Driver> getAll() {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "SELECT `idDrivers`, `surname`, `name`, `secondName`, `birthday`, `passSeries`, `passNumber`, `vehicle` FROM `drivers`";
        Statement statement = null;
        list = FXCollections.observableArrayList();

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sqlQuery);
            while(res.next()){
                list.add(new Driver(res.getInt("idDrivers"),
                        res.getString("surname"),
                        res.getString("name"),
                        res.getString("secondName"),
                        res.getDate("birthday"),
                        res.getString("passSeries"),
                        res.getString("passNumber"),
                        res.getInt("vehicle")));
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

    public void update(Driver driver) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "UPDATE `drivers` SET `surname` = '" + driver.getSurname() +
                                            "', `name` = '" + driver.getName() +
                                            "', `secondName` = '" + driver.getSecondName() +
                                            "', `birthday` = '" + driver.getBirthday() +
                                            "', `passSeries` = '" + driver.getPassSeries() +
                                            "', `passNumber` = '" + driver.getPassNumber() +
                                            "', `vehicle` = '" + driver.getVehicle() +
                                        "' WHERE `idDrivers` = '" + driver.getIdDrivers() + "'";
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

    public void delete(Driver driver) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "DELETE FROM `drivers` WHERE `idDrivers` = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, driver.getIdDrivers());
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
