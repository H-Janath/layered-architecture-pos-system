package dao.Custom.impl;

import dao.CrudDao;
import dao.util.CrudUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dao.Custom.OrderDetailsDao;
import entity.OrderDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isDetailsSaved = true;
        for (OrderDetailsDto dto:list) {
            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
            return CrudUtil.execute(sql,dto.getOrderId(),dto.getItemCode(),dto.getQty(),dto.getUnitprice());
        }
        return isDetailsSaved;
    }
    public List<OrderDetails> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "select * from orderdetail where orderid=?";
        ObservableList<OrderDetails> dtoList = FXCollections.observableArrayList();
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,orderId);
        ResultSet result =prstm.executeQuery();
        while (result.next()){
            dtoList.add(
                    new OrderDetails(
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getDouble(4)
                    )
            );
        }
        return dtoList;

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
