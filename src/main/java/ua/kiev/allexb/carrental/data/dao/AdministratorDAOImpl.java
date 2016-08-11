package ua.kiev.allexb.carrental.data.dao;

import org.apache.log4j.Logger;
import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;
import ua.kiev.allexb.carrental.data.service.ConnectionFactory;
import ua.kiev.allexb.carrental.data.service.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 05.08.2016
 */
public class AdministratorDAOImpl implements AdministratorDAO {

    static final Logger logger = Logger.getLogger(AdministratorDAO.class);

    private static final int ONE = 1;
    private static final int ALL = Integer.MAX_VALUE;

    private Connection connection;
    private Statement statement;

    private List<AdministratorDomain> getItems(String query, int amount) {
        ResultSet resultSet = null;
        List<AdministratorDomain> clients = new ArrayList<>();
        try {
            try {
                connection = ConnectionFactory.getInstance().getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                if (resultSet!= null) {
                    for(int i = 0; i < amount & resultSet.next(); i++) {
                        AdministratorDomain client = new AdministratorDomain(resultSet.getLong("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("login"),
                                resultSet.getString("password"));
                        clients.add(client);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DbUtil.close(resultSet);
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        logger.info("Get data query occurred.");
        return clients;
    }

    @Override
    public List<AdministratorDomain> getByFullName(String firsName, String lastName) {
        String query = "SELECT * FROM administrator_tb WHERE first_name='" + firsName + "' AND last_name='" + lastName + "'";
        List<AdministratorDomain> administrators = this.getItems(query, ALL);
        return administrators;
    }

    @Override
    public List<ClientDomain> getAllClients() {
        return null;
    }

    @Override
    public AdministratorDomain getByLoginAndPassword(String login, String password) {
        String query = "SELECT * FROM administrator_tb WHERE login='" + login + "' AND password='" + password + "'";
        List<AdministratorDomain> administrators = this.getItems(query, ONE);
        return administrators.isEmpty() ? null : administrators.get(0);
    }

    @Override
    public List<AdministratorDomain> getAll() {
        String query = "SELECT * FROM administrator_tb";
        List<AdministratorDomain> administrators = this.getItems(query, ALL);
        return administrators;
    }

    @Override
    public AdministratorDomain getById(Long id) {
        String query = "SELECT * FROM administrator_tb WHERE id=" + id;
        List<AdministratorDomain> administrators = this.getItems(query, ONE);
        return administrators.isEmpty() ? null : administrators.get(0);
    }

    private void dataChangeQuery(String query) {
        try {
            try {
                connection = ConnectionFactory.getInstance().getConnection();
                statement = connection.createStatement();
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            DbUtil.close(statement);
            DbUtil.close(connection);
        }
        logger.info("Add or change data query occurred.");
    }
    @Override
    public void add(AdministratorDomain administrator) {
        String query = "INSERT INTO administrator_tb(first_name, last_name, email, login, password) " +
                "SELECT * FROM (SELECT '" + administrator.getFirstName() + "', '" + administrator.getLastName() + "', '" +
                administrator.getEmail() + "', '" + administrator.getLogin() + "', '" +
                administrator.getPassword() + "') AS temp " +
                "WHERE NOT EXISTS(SELECT id FROM administrator_tb WHERE " +
                "login='" + administrator.getLogin() + "') LIMIT 1";
        this.dataChangeQuery(query);
    }

    @Override
    public void update(AdministratorDomain administrator) {
        String query = "UPDATE administrator_tb SET " +
                "first_name='" + administrator.getFirstName() + "', " +
                "last_name='" + administrator.getLastName() + "', " +
                "email='" + administrator.getEmail() + "', " +
                "login='" + administrator.getLogin() + "', " +
                "password='" + administrator.getPassword() + "' WHERE id=" + administrator.getId() + " AND login='" +
                administrator.getLogin() + "'";
        this.dataChangeQuery(query);
    }

    @Override
    public void remove(AdministratorDomain administrator) {
        String query = "DELETE FROM administrator_tb WHERE id=" + administrator.getId() + " AND " +
                "first_name='" + administrator.getFirstName() + "' AND " +
                "last_name='" + administrator.getLastName() + "' AND " +
                "email='" + administrator.getEmail() + "' AND " +
                "login='" + administrator.getLogin() + "' AND " +
                "password='" + administrator.getPassword() + "'";
        this.dataChangeQuery(query);
    }

}
