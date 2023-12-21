package bo.custom.impl;

import bo.custom.ItemBo;
import dao.Custom.ItemDao;
import dao.Custom.impl.ItemDaoImpl;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoimpl implements ItemBo<ItemDto> {
    ItemDao itemDao = new ItemDaoImpl();
    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(
                new Item(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQty()
                )
        );
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.update(
                new Item(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQty()
                )
        );
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDao.delete(code);
    }

    @Override
    public List<ItemDto> allItem() throws SQLException, ClassNotFoundException {
       List<Item> entitylist = itemDao.getAll();
       List<ItemDto> dtolist =new ArrayList<>();
       for(Item item:entitylist){
           dtolist.add(
                   new ItemDto(
                           item.getCode(),
                           item.getDescription(),
                           item.getUnitPrice(),
                           item.getQtyOnHand()
                   )
           );
       }
       return dtolist;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        return itemDao.getItem(code);
    }
}
