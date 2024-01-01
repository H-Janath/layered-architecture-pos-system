package dao.Custom.impl;
import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.ItemDto;
import dao.Custom.ItemDao;
import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Item itemDto = session.find(Item.class,code);
        return new ItemDto(
                itemDto.getCode(),
                itemDto.getDescription(),
                itemDto.getUnitPrice(),
                itemDto.getQtyOnHand()
        );

    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
       Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.clear();
        return true;
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.find(Item.class,entity.getCode());
        item.setCode(entity.getCode());
        item.setDescription(entity.getDescription());
        item.setUnitPrice(entity.getUnitPrice());
        item.setQtyOnHand(entity.getQtyOnHand());
        session.save(item);
        transaction.commit();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        session.delete(session.find(Item.class,value));
        transaction.commit();
        return true;
    }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Item");
        List<Item> itemEntity = query.list();
        return itemEntity;
    }
}
