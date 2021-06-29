package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DBConnection;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.*;
import java.util.logging.Logger;

@XmlRootElement(name = "tariffs")
public class Tariffs {
    @XmlElement(name = "tariff")
    ObservableList<Tariff> list;

    private DBConnection dbConnection = null;
    private final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    public Tariffs() { }
    public Tariffs(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insert(Tariff tariff) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "INSERT INTO `tariffs` (`name`, `timeOfDay`, `distanceFromCityCenter`, `pricePerKM`) VALUES ('" +
                tariff.getName() + "', '" + tariff.getTimeOfDay() + "', '" +
                tariff.getDistanceFromCityCenter() + "', '" + tariff.getPricePerKM() + "')";
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

    public ObservableList<Tariff> getAll() {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "SELECT `idTariffs`, `name`, `timeOfDay`, `distanceFromCityCenter`, `pricePerKM` FROM `tariffs`";
        Statement statement = null;
        list = FXCollections.observableArrayList();

        try {
            statement = connection.createStatement();
            ResultSet res = statement.executeQuery(sqlQuery);
            while(res.next()){
                list.add(new Tariff(res.getInt("idTariffs"),
                        res.getString("name"),
                        res.getString("timeOfDay"),
                        res.getInt("distanceFromCityCenter"),
                        res.getInt("pricePerKM")));
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

    public void update(Tariff tariff) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "UPDATE `tariffs` SET `name` = '" + tariff.getName() +
                                            "', `timeOfDay` = '" + tariff.getTimeOfDay() +
                                            "', `distanceFromCityCenter` = '" + tariff.getDistanceFromCityCenter() +
                                            "', `pricePerKM` = '" + tariff.getPricePerKM() +
                                        "' WHERE `idTariffs` = '" + tariff.getIdTariffs() + "'";
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

    public void delete(Tariff tariff) {
        Connection connection = dbConnection.getConnection();

        String sqlQuery = "DELETE FROM `tariffs` WHERE `idTariffs` = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sqlQuery);
            ps.setInt(1, tariff.getIdTariffs());
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
