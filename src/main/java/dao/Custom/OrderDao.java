package dao.Custom;

import dto.OrderDto;
import dto.tm.OrderDto2;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException;
    OrderDto  lastOrder() throws SQLException, ClassNotFoundException;


    public List<OrderDto2> allOrders() throws SQLException, ClassNotFoundException;
}
