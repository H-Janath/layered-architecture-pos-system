package dao.Custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dao.Custom.OrderDetailsDao;
import dao.Custom.OrderDao;
import dto.OrderDto2;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();

    @Override
    public Orders lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "select * from orders order by id desc limit 1";
        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()){
            return new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
        }
        return null;
    }
    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into orders values(?,?,?)";
            return CrudUtil.execute(sql,entity.getId(),entity.getDate(),entity.getCustomerId());
        } catch (SQLException | ClassNotFoundException ex) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        List<Orders> dtolist = new ArrayList<>();
        String sql = "select * from orders";
        ResultSet result= CrudUtil.execute(sql);
        while (result.next()){
            dtolist.add(
                    new Orders(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3)
                    )
            );
        }
        return dtolist;
    }
}
