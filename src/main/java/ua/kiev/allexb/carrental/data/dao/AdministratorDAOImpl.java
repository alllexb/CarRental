package ua.kiev.allexb.carrental.data.dao;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.service.DataBaseUtil;
import ua.kiev.allexb.carrental.utils.ApplicationLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 05.08.2016
 */
public class AdministratorDAOImpl implements AdministratorDAO {

    static final Logger logger = ApplicationLogger.getLogger(AdministratorDAO.class);

    private Connection connection;
    private Statement statement;

    public AdministratorDAOImpl() {
    }

    public AdministratorDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private List<AdministratorDomain> getItems(String query, int amount) throws SQLException {
        ResultSet resultSet = null;
        List<AdministratorDomain> clients = new ArrayList<>();
        try {
            if (connection == null) throw new SQLException("No connection to database.");
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet != null) {
                for (int i = 0; i < amount & resultSet.next(); i++) {
                    AdministratorDomain client = new AdministratorDomain(resultSet.getLong("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("login"),
                            resultSet.getString("password"));
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
    public List<AdministratorDomain> getByFullName(String firsName, String lastName) throws SQLException {
        String query = "SELECT * FROM administrator_tb WHERE first_name='" + firsName + "' AND last_name='" + lastName + "'";
        List<AdministratorDomain> administrators = this.getItems(query, ALL);
        return administrators;
    }

    @Override
    public List<ClientDomain> getAllClients() {
        return null;
    }

    @Override
    public AdministratorDomain getByLoginAndPassword(String login, String password) throws SQLException {
        String query = "SELECT * FROM administrator_tb WHERE login='" + login + "' AND password='" + password + "'";
        List<AdministratorDomain> administrators = this.getItems(query, ONE);
        return administrators.isEmpty() ? null : administrators.get(0);
    }

    @Override
    public AdministratorDomain getByLogin(String login) throws SQLException {
        String query = "SELECT * FROM administrator_tb WHERE login='" + login + "'";
        List<AdministratorDomain> administrators = this.getItems(query, ONE);
        return administrators.isEmpty() ? null : administrators.get(0);
    }

    @Override
    public List<AdministratorDomain> getAll() throws SQLException {
        String query = "SELECT * FROM administrator_tb";
        List<AdministratorDomain> administrators = this.getItems(query, ALL);
        return administrators;
    }

    @Override
    public AdministratorDomain getById(Long id) throws SQLException {
        String query = "SELECT * FROM administrator_tb WHERE id=" + id;
        List<AdministratorDomain> administrators = this.getItems(query, ONE);
        return administrators.isEmpty() ? null : administrators.get(0);
    }

    private void dataChangeQuery(String query, AdministratorDomain administrator) throws SQLException {
        if (connection == null) throw new SQLException("No connection to database.");
        statement = connection.prepareStatement(query);
        try {
            ((PreparedStatement)statement).setString(1, administrator.getFirstName());
            ((PreparedStatement)statement).setString(2, administrator.getLastName());
            ((PreparedStatement)statement).setString(3, administrator.getEmail());
            ((PreparedStatement)statement).setString(4, administrator.getPassword());
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
    public void add(AdministratorDomain administrator) throws SQLException {
        String query = "INSERT INTO administrator_tb(first_name, last_name, email, login, password) " +
                "SELECT * FROM (SELECT ?, ?, ?, '" + administrator.getLogin() + "', ?) AS temp " +
                "WHERE NOT EXISTS(SELECT id FROM administrator_tb WHERE login='" + administrator.getLogin() + "') LIMIT 1";
        this.dataChangeQuery(query, administrator);
    }

    @Override
    public void update(AdministratorDomain administrator) throws SQLException {
        String query = "UPDATE administrator_tb SET " +
                "first_name=?, last_name=?, email=?, login='" + administrator.getLogin() + "', " +
                "password=? WHERE id=" + administrator.getId() + " AND login='" + administrator.getLogin() + "'";
        this.dataChangeQuery(query, administrator);
    }

    @Override
    public void remove(AdministratorDomain administrator) throws SQLException {
        String query = "DELETE FROM administrator_tb WHERE id=" + administrator.getId() + " AND " +
                "first_name=? AND last_name=? AND email=? AND login='" + administrator.getLogin() + "' AND " +
                "password=?";
        this.dataChangeQuery(query, administrator);
    }

}
