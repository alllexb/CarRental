package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.ClientDomain;

import java.util.List;

/**
 * @author allexb
 * @version 1.0 04.08.2016
 */
public interface ClientDAO extends AbstractDAO<ClientDomain> {

    List<ClientDomain> getByFullName(String firsName, String lastName);
}
