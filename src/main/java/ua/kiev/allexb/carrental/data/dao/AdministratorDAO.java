package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.AdministratorDomain;
import ua.kiev.allexb.carrental.data.domain.ClientDomain;

import java.sql.SQLException;
import java.util.List;

/**
 * @author allexb
 * @version 1.0 05.08.2016
 */
public interface AdministratorDAO extends AbstractDAO<AdministratorDomain>{

    List<AdministratorDomain> getByFullName(String firsName, String lastName) throws SQLException;

    List<ClientDomain> getAllClients() throws SQLException;

    AdministratorDomain getByLoginAndPassword(String login, String password) throws SQLException;

    AdministratorDomain getByLogin(String login) throws SQLException;

}
