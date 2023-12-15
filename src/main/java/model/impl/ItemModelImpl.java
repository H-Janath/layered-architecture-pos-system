package model.impl;
import db.DBConnection;
import dto.ItemDto;
import model.ItemModel;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

public class ItemModelImpl implements ItemModel {
    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        List<ItemDto> dtos = new ArrayList<>();
        String sql = "select * from item";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet rst = pstm.executeQuery();
        while (rst.next()){
            dtos.add(new ItemDto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            ));
        }
        return dtos;
    }

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        String sql = "Insert into item values(?,?,?,?)";
        PreparedStatement prst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prst.setString(1, dto.getCode());
        prst.setString(2,dto.getDescription());
        prst.setInt(3,dto.getQty());
        prst.setDouble(4,dto.getUnitPrice());
        int result = prst.executeUpdate();
        if(result>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        String sql = "update from item set description=? unitPrice=? qtyOnHand=? where code=?";
        PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prstm.setString(1,dto.getDescription());;
        prstm.setDouble(2,dto.getUnitPrice());
        prstm.setInt(3,dto.getQty());
        prstm.setString(4,dto.getCode());
        int result= prstm.executeUpdate();
        if(result>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteeItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "delete from item where code=?";
        PreparedStatement prst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        prst.setString(1,code);
        int rst = prst.executeUpdate();
        if(rst>0){
            return true;
        }
        return false;
    }

    @Override
    public ItemDto searchItem(String code) {
        return null;
    }

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
}
