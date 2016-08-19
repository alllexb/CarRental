package ua.kiev.allexb.carrental.data.dao;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.util.DateUtil;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.service.ConnectionFactory;
import ua.kiev.allexb.carrental.data.service.DataBaseUtil;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 04.08.2016
 */
public class ClientDAOImpl implements ClientDAO {

    static final Logger logger = ApplicationLogger.getLogger(ClientDAO.class);

    private Connection connection;
    private Statement statement;

    public ClientDAOImpl() {
    }

    public ClientDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private List<ClientDomain> getItems(String query, int amount) throws SQLException {
        ResultSet resultSet = null;
        List<ClientDomain> clients = new ArrayList<>();
        try {
            if (connection == null) throw new SQLException("No connection to database.");
            connection = ConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                for (int i = 0; i < amount & resultSet.next(); i++) {
                    ClientDomain client = new ClientDomain(resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getDate("birthday"),
                            resultSet.getInt("dl_number"),
                            resultSet.getInt("length_of_driving_experience"));
                    clients.add(client);
                }
            }
        } finally {
            DataBaseUtil.closeResultSet(resultSet);
            DataBaseUtil.closeStatement(statement);
            DataBaseUtil.closeConnection(connection);
        }
        logger.info("Get data query occurred.");
        return clients;
    }

    @Override
    public List<ClientDomain> getByFullName(String firsName, String lastName) throws SQLException {
        String query = "SELECT * FROM client_tb WHERE first_name='" + firsName + "' AND last_name='" + lastName + "'";
        List<ClientDomain> clients = this.getItems(query, ALL);
        return clients;
    }

    @Override
    public List<ClientDomain> getAll() throws SQLException {
        String query = "SELECT * FROM client_tb";
        List<ClientDomain> clients = this.getItems(query, ALL);
        return clients;
    }

    @Override
    public ClientDomain getById(Long id) throws SQLException {
        String query = "SELECT * FROM client_tb WHERE id=" + id;
        List<ClientDomain> clients = getItems(query, ONE);
        return clients.isEmpty() ? null : clients.get(0);
    }

    private void dataChangeQuery(String query) throws SQLException {
        try {
            if (connection == null) throw new SQLException("No connection to database.");
            connection = ConnectionFactory.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } finally {
            DataBaseUtil.closeStatement(statement);
            DataBaseUtil.closeConnection(connection);
        }
        logger.info("Add or change data query occurred.");
    }

    @Override
    public void add(ClientDomain client) throws SQLException {
        String query = "INSERT INTO client_tb(first_name, last_name, birthday, dl_number, length_of_driving_experience) " +
                "VALUES ('" + client.getFirstName() + "', '" + client.getLastName() + "', '" +
                DateUtil.getSQLFormatDate(client.getBirthday()) + "', " + client.getdLNumber() + ", " +
                client.getLengthOfDrivingExperience() + ")";
        this.dataChangeQuery(query);
    }

    @Override
    public void update(ClientDomain client) throws SQLException {
        String query = "UPDATE client_tb SET " +
                "first_name='" + client.getFirstName() + "', " +
                "last_name='" + client.getLastName() + "', " +
                "birthday='" + DateUtil.getSQLFormatDate(client.getBirthday()) + "', " +
                "dl_number=" + client.getdLNumber() + ", " +
                "length_of_driving_experience=" + client.getLengthOfDrivingExperience() + " WHERE id=" + client.getId();
        this.dataChangeQuery(query);
    }

    @Override
    public void remove(ClientDomain client) throws SQLException {
        String query = "DELETE FROM client_tb WHERE id=" + client.getId() + " AND " +
                "first_name='" + client.getFirstName() + "' AND " +
                "last_name='" + client.getLastName() + "' AND " +
                "birthday='" + DateUtil.getSQLFormatDate(client.getBirthday()) + "' AND " +
                "dl_number=" + client.getdLNumber() + " AND " +
                "length_of_driving_experience=" + client.getLengthOfDrivingExperience();
        this.dataChangeQuery(query);
    }

}
