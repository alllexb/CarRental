package ua.kiev.allexb.carrental.data.dao;

import ua.kiev.allexb.carrental.data.domain.CarDomain;

/**
 * @author allexb
 * @version 1.0 29.07.2016
 */
public interface CarDAO extends AbstractDAO<CarDomain> {

    CarDomain getByModel(String model);

}