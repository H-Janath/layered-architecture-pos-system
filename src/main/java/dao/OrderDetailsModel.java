package dao;

import dto.OrderDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsModel {
    public List<OrderDetailsDto> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException;
}
