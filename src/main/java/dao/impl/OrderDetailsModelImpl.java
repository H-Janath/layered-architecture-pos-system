package dao.impl;

import db.DBConnection;
import dto.OrderDetailsDto;
import dao.OrderDetailsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModelImpl implements OrderDetailsModel {
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isDetailsSaved = true;
        for (OrderDetailsDto dto:list) {
            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQty());
            pstm.setDouble(4,dto.getUnitprice());

            if(!(pstm.executeUpdate()>0)){
                isDetailsSaved = false;
            }
        }
        return isDetailsSaved;
    }
    public List<OrderDetailsDto> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException {
        String sql = "select * from orderdetail where orderid=?";
        ObservableList<OrderDetailsDto> dtoList = FXCollections.observableArrayList();
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,orderId);
        ResultSet result =prstm.executeQuery();
        while (result.next()){
            dtoList.add(
                    new OrderDetailsDto(
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getDouble(4)
                    )
            );
        }
        return dtoList;

    }
}
