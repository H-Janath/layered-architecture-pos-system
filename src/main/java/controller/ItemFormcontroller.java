package controller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.ItemDto;
import dto.tm.ItemTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemFormcontroller {
    public JFXTextField txtCode;
    public JFXTextField txtDescription;
    public JFXTextField txtunitprice;
    public JFXTextField txtqty;
    public JFXTreeTableView itemtbl;
    public TreeTableColumn colcode;
    public TreeTableColumn coldescription;
    public TreeTableColumn colunitprice;
    public TreeTableColumn colqty;
    public TreeTableColumn coloption;


    public void initialize(){
        colcode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        coldescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colunitprice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colqty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        coloption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadTable();
    }

    private void setData(ItemTm newvalue) {
        if(newvalue!=null){
            txtCode.setText(newvalue.getCode());
            txtDescription.setText(newvalue.getDescription());
            txtqty.setText(String.valueOf(newvalue.getQty()));
            txtunitprice.setText(String.valueOf(newvalue.getUnitPrice()));
        }
    }

    private void loadTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        String sql = "select * from item";
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()) {
                JFXButton btn = new JFXButton("Delete");
                ItemTm i1 = new ItemTm(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    try {
                        deleteItem(i1.getCode());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
                tmList.add(i1);
            }
            RecursiveTreeItem<ItemTm> treeItem =new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            itemtbl.setRoot(treeItem);
            itemtbl.setShowRoot(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String code) throws SQLException, ClassNotFoundException {
        String sql = "delete from item where code=?";
        PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
        pstm.setString(1,code);
        int result = pstm.executeUpdate();
        if(result>0){
            new Alert(Alert.AlertType.INFORMATION,"Delete successfull").show();
            loadTable();
        }

    }

    public void updatebtnOnAction(ActionEvent actionEvent) {

    }

    public void savebtnOnAction(ActionEvent actionEvent) {
        ItemDto i1 = new ItemDto(
                txtCode.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtunitprice.getText()),
                Integer.parseInt(txtqty.getText())
        );
        String sql="Insert into item values(?,?,?,?)";
        try {
            PreparedStatement prstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            prstm.setString(1,i1.getCode());
            prstm.setString(2,i1.getDescription());
            prstm.setDouble(3,i1.getUnitPrice());
            prstm.setInt(4,i1.getQty());
            int result = prstm.executeUpdate();
            if(result>0){
                new Alert(Alert.AlertType.INFORMATION,"Save successfully").show();
                loadTable();
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
