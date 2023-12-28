package dao;

import bo.custom.impl.ItemBoimpl;
import bo.custom.impl.OrderDetailsBoimpl;
import bo.custom.impl.OrdersBoimpl;
import dao.Custom.ItemDao;
import dao.Custom.OrderDetailsDao;
import dao.Custom.impl.CustomerDaoImpl;
import dao.Custom.impl.ItemDaoImpl;
import dao.Custom.impl.OrderDaoImpl;
import dao.Custom.impl.OrderDetailsDaoImpl;
import dao.util.DaoType;

import java.lang.reflect.Type;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){

    }
    public static DaoFactory getInstence(){
        return daoFactory!=null? daoFactory:(daoFactory = new DaoFactory());
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case CUSTOMER:return (T)new CustomerDaoImpl();
            case ITEM:return (T)new ItemDaoImpl();
            case ORDERDETAILS:return (T)new OrderDetailsDaoImpl();
            case ORDER:return (T)new OrderDaoImpl();
        }
        return null;
    }
}
