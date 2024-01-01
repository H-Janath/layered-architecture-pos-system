package dao.Custom.impl;

import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dao.Custom.OrderDetailsDao;
import entity.Customer;
import entity.OrderDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isDetailsSaved = true;
        for (OrderDetailsDto dto:list) {
           Session session = HibernateUtil.getSession();
           Transaction transaction = session.getTransaction();
           session.save(dto);
           transaction.commit();
           session.clear();
        }
        return isDetailsSaved;
    }
    public List<OrderDetails> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM OrderDetails ");
        List<OrderDetails> list = query.list();
        return list;
    }
    @Override
    public boolean save(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
