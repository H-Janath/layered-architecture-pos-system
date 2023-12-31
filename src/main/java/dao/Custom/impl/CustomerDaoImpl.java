package dao.Custom.impl;

import dao.Custom.CustomerDao;
import dao.util.CrudUtil;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        Configuration configuration=new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class);

       SessionFactory sessionFactory= configuration.buildSessionFactory();
       Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.clear();
        return true;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "update customer set name=?, address=?, salary=? where id=?";
        boolean result = CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());
        return result;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "delete from customer where id=?";
        boolean result = CrudUtil.execute(sql,value);
        return result;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        List<Customer> customerentity = new ArrayList<>();
        String sql = "select * from customer";
        ResultSet rst = CrudUtil.execute(sql);
        while(rst.next()){
            customerentity.add(new Customer(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4)
                    )
            );
        }
        return customerentity;
    }
}
