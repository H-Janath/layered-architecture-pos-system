package dao.Custom.impl;

import dao.Custom.CustomerDao;
import dao.util.CrudUtil;
import entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {


    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        String sql = "insert into customer value(?,?,?,?)";
        boolean result = CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
        return result;
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
