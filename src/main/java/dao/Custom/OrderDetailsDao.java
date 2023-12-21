package dao.Custom;

import dto.OrderDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao {
    public List<OrderDetailsDto> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException;
}
