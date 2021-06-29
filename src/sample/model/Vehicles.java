package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DBConnection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.logging.Logger;

@XmlRootElement(name = "vehicles")
public class Vehicles {
    @XmlElement(name = "vehicle")
    ObservableList<Vehicle> list;

    private DBConnection dbConnection = null;
    private final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    public Vehicles(){ }
    public Vehicles(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insert(Vehicle vehicle) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "INSERT INTO `vehicles` (`vehicleNum`, `brand`, `type`, `year`, `color`, `vehicleClass`) VALUES ('" +
                vehicle.getVehicleNum() + "', '" + vehicle.getBrand() + "', '" + vehicle.getType() + "', '" +
                vehicle.getYear().getYear() + "', '" + vehicle.getColor() + "', '" + vehicle.getVehicleClass() + "')";
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

    public ObservableList<Vehicle> getAll() {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "SELECT `idVehicles`, `vehicleNum`, `brand`, `type`, `year`, `color`, `vehicleClass` FROM `vehicles`";
        Statement statement = null;
        list = FXCollections.observableArrayList();

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sqlQuery);
            while(res.next()){
                list.add(new Vehicle(res.getInt("idVehicles"),
                        res.getString("vehicleNum"),
                        res.getString("brand"),
                        res.getString("type"),
                        res.getDate("year"),
                        res.getString("color"),
                        res.getString("vehicleClass")));
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

    public void update(Vehicle vehicle) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "UPDATE `vehicles` SET `vehicleNum` = '" + vehicle.getVehicleNum() +
                                        "', `brand` = '" + vehicle.getBrand() +
                                        "', `type` = '" + vehicle.getType() +
                                        "', `year` = '" + vehicle.getYear().getYear() +
                                        "', `color` = '" + vehicle.getColor() +
                                        "', `vehicleClass` = '" + vehicle.getVehicleClass() +
                                    "' WHERE `idVehicles` = '" + vehicle.getIdVehicles() + "'";
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

    public void delete(Vehicle vehicle) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "DELETE FROM `vehicles` WHERE `idVehicles` = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, vehicle.getIdVehicles());
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
