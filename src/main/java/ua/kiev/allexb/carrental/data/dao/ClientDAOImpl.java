package ua.kiev.allexb.carrental.data.dao;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.dao.util.DateUtil;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.service.DataBaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 04.08.2016
 */
public class ClientDAOImpl implements ClientDAO {

    static final Logger logger = Logger.getLogger(ClientDAO.class);

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
    public ClientDomain getByDLNumber(int dlNumber) throws SQLException {
        String query = "SELECT * FROM client_tb WHERE dl_number=" + dlNumber;
        List<ClientDomain> clients = getItems(query, ONE);
        return clients.isEmpty() ? null : clients.get(0);
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

    private void dataChangeQuery(String query, ClientDomain client) throws SQLException {
        if (connection == null) throw new SQLException("No connection to database.");
        statement = connection.prepareStatement(query);
        try {
            ((PreparedStatement)statement).setString(1, client.getFirstName());
            ((PreparedStatement)statement).setString(2, client.getLastName());
            if (client.getBirthday() != null) ((PreparedStatement)statement).setDate(3, DateUtil.getSQLFormatDate(client.getBirthday()));
            else ((PreparedStatement)statement).setNull(3, Types.DATE);
            ((PreparedStatement)statement).setInt(4, client.getdLNumber());
            ((PreparedStatement)statement).setInt(5, client.getLengthOfDrivingExperience());
            int items = ((PreparedStatement)statement).executeUpdate();
            if (items == 0) logger.info("No entities changed.");
        } catch (Exception ex) {
            logger.info("Fail in data base changing.", ex);
            throw new SQLException(ex);
        }finally {
            DataBaseUtil.closeStatement(statement);
        }
        logger.info("Add or change data query occurred.");
    }

    @Override
    public void add(ClientDomain client) throws SQLException {
        String query = "INSERT INTO client_tb(first_name, last_name, birthday, dl_number, length_of_driving_experience) " +
                "VALUES (?, ?, ?, ?, ?)";
        this.dataChangeQuery(query, client);
    }

    @Override
    public void update(ClientDomain client) throws SQLException {
        String query = "UPDATE client_tb SET first_name=?, last_name=?, birthday=?, dl_number=?, " +
                "length_of_driving_experience=? WHERE id=" + client.getId();
        this.dataChangeQuery(query, client);
    }

    @Override
    public void remove(ClientDomain client) throws SQLException {
        String query = "DELETE FROM client_tb WHERE id=" + client.getId() + " AND first_name=? AND last_name=? " +
                "AND birthday=? IS NULL IS NOT NULL AND dl_number=? AND length_of_driving_experience=?";
        this.dataChangeQuery(query, client);
    }

}
