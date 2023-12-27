package bo;

import bo.custom.impl.CustomerBoimpl;
import bo.custom.impl.ItemBoimpl;
import bo.custom.impl.OrderDetailsBoimpl;
import bo.custom.impl.OrdersBoimpl;
import dao.SuperDao;
import dao.util.BoType;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){

    }

    public  static BoFactory getInstance(){
        return boFactory!=null? boFactory:(boFactory = new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerBoimpl();
            case ITEM: return (T)new ItemBoimpl();
            case ORDER_DETAIL: return (T)new OrderDetailsBoimpl();
            case ORDER:return (T)new OrdersBoimpl();
        }
        return null;
    }

}
