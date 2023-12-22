package bo.custom;

import dto.OrderDetailsDto;
import dto.OrderDto2;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsBo {
    public List<OrderDetailsDto> getOrderDetails(String id) throws SQLException, ClassNotFoundException;
    public List<OrderDto2> allOrders() throws SQLException, ClassNotFoundException;

}
