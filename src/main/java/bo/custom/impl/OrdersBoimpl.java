package bo.custom.impl;

import bo.custom.OrdersBo;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.Custom.OrderDao;
import dao.Custom.OrderDetailsDao;
import dao.Custom.impl.OrderDaoImpl;
import dao.Custom.impl.OrderDetailsDaoImpl;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.OrderDto2;
import entity.Orders;

import java.sql.SQLException;

public class OrdersBoimpl implements OrdersBo {
    OrderDao orderDao = new OrderDaoImpl();
    OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();

    @Override
    public boolean saveOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        boolean isSaveorder=orderDao.save(
            new Orders(
                    orderDto.getOrderid(),
                    orderDto.getDate(),
                    orderDto.getCustId()
            )
        );
        if(isSaveorder) {
            boolean isSavedDetails=orderDetailsDao.saveOrderDetails(orderDto.getDto());
            if(isSavedDetails){
                return true;
            }
        }
        return false;
    }

    @Override
    public OrderDto2 lastOrder() throws SQLException, ClassNotFoundException {
        Orders order = orderDao.lastOrder();
        return new OrderDto2(
                order.getId(),
                order.getDate(),
                order.getCustomerId()
        );
    }
}
