package model;

import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    List<ItemDto> allItems() throws SQLException, ClassNotFoundException;
    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteeItem(String code) throws SQLException, ClassNotFoundException;
    ItemDto searchItem(String code);
    ItemDto getItem(String code) throws SQLException, ClassNotFoundException;
}
