package bo.custom.impl;

import bo.custom.OrderDetailsBo;
import dao.Custom.OrderDao;
import dao.Custom.OrderDetailsDao;
import dao.Custom.impl.OrderDaoImpl;
import dao.Custom.impl.OrderDetailsDaoImpl;
import dto.OrderDetailsDto;
import dto.OrderDto2;
import dto.OrderDto2;
import entity.OrderDetails;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsBoimpl implements OrderDetailsBo {
    OrderDao orderDao = new OrderDaoImpl();
    OrderDetailsDao orderDetailsDo = new OrderDetailsDaoImpl();
    public List<OrderDetailsDto>  getOrderDetails(String id) throws SQLException, ClassNotFoundException {
        List<OrderDetails> orderDetails= orderDetailsDo.getOrderDetails(id);
        List<OrderDetailsDto> dtoList = new ArrayList<>();
        for (OrderDetails OD:orderDetails){
            dtoList.add(
                    new OrderDetailsDto(
                           OD.getOrderId(),
                           OD.getItemCode(),
                            OD.getQty(),
                            OD.getUnitPrice()
                    )
            );
        }
        return dtoList;
    }
    public List<OrderDto2> allOrders() throws SQLException, ClassNotFoundException {
        List<Orders> orderslist=orderDao.getAll();
        List<OrderDto2> orderDetailsDtoslist = new ArrayList<>();
        for (Orders orders:orderslist){
            orderDetailsDtoslist.add(
                    new OrderDto2(
                            orders.getId(),
                            orders.getDate(),
                            orders.getCustomerId()
                    )
            );
        }
        return orderDetailsDtoslist;
    }
}
