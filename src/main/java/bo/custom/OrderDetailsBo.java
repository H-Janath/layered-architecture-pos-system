package bo.custom;

import bo.SuperBo;
import dto.OrderDetailsDto;
import dto.OrderDto2;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsBo extends SuperBo {
    public List<OrderDetailsDto> getOrderDetails(String id) throws SQLException, ClassNotFoundException;
    public List<OrderDto2> allOrders() throws SQLException, ClassNotFoundException;

}
