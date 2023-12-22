package dao.Custom;

import dao.CrudDao;
import entity.Orders;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Orders> {
    Orders lastOrder() throws SQLException, ClassNotFoundException;
}
