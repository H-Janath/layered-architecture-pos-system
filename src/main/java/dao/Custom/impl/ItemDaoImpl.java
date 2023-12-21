package dao.Custom.impl;
import db.DBConnection;
import dto.ItemDto;
import dao.Custom.ItemDao;
import entity.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "select * from item where code=?";
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,code);
        ResultSet result = prstm.executeQuery();
        if(result.next()){
            return new ItemDto(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return null;
    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "Insert into item values(?,?,?,?)";
        PreparedStatement prst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prst.setString(1, entity.getCode());
        prst.setString(2,entity.getDescription());
        prst.setDouble(3,entity.getUnitPrice());
        prst.setInt(4,entity.getQtyOnHand());
        int result = prst.executeUpdate();
        if(result>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        String sql = "update item set description=?, unitPrice=?, qtyOnHand=? where code=?";
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1, entity.getDescription());
        prstm.setDouble(2, entity.getUnitPrice());
        prstm.setInt(3, entity.getQtyOnHand());
        prstm.setString(4, entity.getCode());
        int result = prstm.executeUpdate();
        return result > 0;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "delete from item where code=?";
        PreparedStatement prst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prst.setString(1,value);
        int rst = prst.executeUpdate();
        if(rst>0){
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        List<Item> itemEntity = new ArrayList<>();
        String sql = "select * from item";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            itemEntity.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            ));
        }
        return itemEntity;
    }
}
