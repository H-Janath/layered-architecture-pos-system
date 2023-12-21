package dao.Custom;

import dao.CrudDao;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao extends CrudDao<Item> {
    public ItemDto getItem(String code)throws SQLException, ClassNotFoundException;
}
