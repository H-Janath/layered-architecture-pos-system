package dao.Custom.impl;

import db.DBConnection;
import dto.OrderDto;
import dao.Custom.OrderDetailsDao;
import dao.Custom.OrderDao;
import dto.tm.OrderDto2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();
    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into orders values(?,?,?)";
            PreparedStatement prstm = connection.prepareStatement(sql);
            prstm.setString(1,dto.getOrderid());
            prstm.setString(2,dto.getDate());
            prstm.setString(3,dto.getCustId());
            if(prstm.executeUpdate()>0){
                boolean isDetailSaved = orderDetailsDao.saveOrderDetails(dto.getDto());
                if(isDetailSaved){
                    connection.commit();
                    return true;
                }
            }
        } catch (SQLException | ClassNotFoundException ex){
            connection.rollback();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "select * from orders order by id desc limit 1";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }
    public List<OrderDto2> allOrders() throws SQLException, ClassNotFoundException {
        List<OrderDto2> dtolist = new ArrayList<>();
        String sql = "select * from orders";
        PreparedStatement prstm  = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet result = prstm.executeQuery();
        while (result.next()){
            dtolist.add(
                    new OrderDto2(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3)
                    )
            );
        }
        return dtolist;
    }
}
