package dao.Custom.impl;

import dao.Custom.CustomerDao;
import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
       Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.clear();
        return true;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.find(Customer.class, entity.getId());
        customer.setName(entity.getName());
        customer.setAddress(entity.getAddress());
        customer.setSalary(entity.getSalary());
        session.save(customer);
        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Customer.class,value));
        transaction.commit();
        return true;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.list();
        return list;
    }
}
