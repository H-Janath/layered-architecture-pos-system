package bo.custom;

import bo.SuperBo;
import dto.OrderDto;
import dto.OrderDto2;

import java.sql.SQLException;

public interface OrdersBo extends SuperBo {
    public boolean saveOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException;
    public OrderDto2 lastOrder() throws SQLException, ClassNotFoundException;
}
