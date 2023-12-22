package bo.custom;

import dto.OrderDto;
import dto.OrderDto2;

import java.sql.SQLException;

public interface OrdersBo {
    public boolean saveOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException;
    public OrderDto2 lastOrder() throws SQLException, ClassNotFoundException;
}
