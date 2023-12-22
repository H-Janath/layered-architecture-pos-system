package dao.Custom;

import dao.CrudDao;
import dto.OrderDetailsDto;
import entity.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao extends CrudDao<OrderDetails> {
    List<OrderDetails> getOrderDetails(String orderId) throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException;
}
