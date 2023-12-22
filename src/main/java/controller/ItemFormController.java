package controller;

import bo.custom.impl.ItemBoimpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.ItemDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dto.tm.ItemTm;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {

    @FXML
    private TreeTableColumn colCode;

    @FXML
    private TreeTableColumn colDesc;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colUnitPrice;

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTreeTableView<ItemTm> tblItem;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtDesc;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTextField txtUnitPrice;
    ItemBoimpl itemBo = new ItemBoimpl();

    public void initialize() throws SQLException, ClassNotFoundException {
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItemTable();

        tblItem.setOnMouseClicked(event -> {
            if(event.getClickCount()==1&& (!tblItem.getSelectionModel().isEmpty())){
                TreeItem<ItemTm> item = tblItem.getSelectionModel().getSelectedItem();
                txtCode.setText(item.getValue().getCode());
                txtCode.setEditable(false);
                txtDesc.setText(item.getValue().getDescription());
                txtUnitPrice.setText(String.valueOf(item.getValue().getUnitPrice()));
                txtQty.setText(String.valueOf(item.getValue().getQty()));
            }
        });
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().contains(newValue) ||
                                treeItem.getValue().getDescription().contains(newValue);
                    }
                });
            }
        });
    }

    private void loadItemTable() throws SQLException, ClassNotFoundException {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        List<ItemDto> itemDtos = itemBo.allItem();
        for(ItemDto dto:itemDtos){
            JFXButton btn = new JFXButton("Delete");
            tmList.add(
                    new ItemTm(
                            dto.getCode(),
                            dto.getDescription(),
                            dto.getUnitPrice(),
                            dto.getQty(),
                            btn
                    )
            );
            btn.setOnAction(ActionEvent->{
                deleteItem(dto.getCode());
            });
        }
        TreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
        tblItem.setRoot(treeItem);
        tblItem.setShowRoot(false);
    }

    private void deleteItem(String code) {
        boolean isDelete = false;
        try {
            isDelete = itemBo.deleteItem(code);
            if(isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Delete successfully").show();
                loadItemTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Delete unsuccessfully").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        if(!isEmpty()){
        ItemDto dto = new ItemDto(txtCode.getText(),
                txtDesc.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        try {
             boolean isSave = itemBo.saveItem(dto);
             if(isSave){
                 new Alert(Alert.AlertType.INFORMATION,"Saved").show();
             }else {
                 new Alert(Alert.AlertType.ERROR,"Saved unsuccessfully").show();
             }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entity").show();
        } catch (SQLException e) {
             throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Empty field found").show();
        }
    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        if(!isEmpty()) {
            ItemDto itemDto = new ItemDto(txtCode.getText(), txtDesc.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQty.getText()));
            boolean isUpdated = false;
            try {
                isUpdated = itemBo.updateItem(itemDto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Update Success").show();
                    loadItemTable();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Update unsuccessfully").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Empty field found").show();
        }
    }
    private boolean isEmpty(){
          if(txtQty.getText().isEmpty()||txtDesc.getText().isEmpty()||txtCode.getText().isEmpty()||txtUnitPrice.getText().isEmpty()){
              return true;
          }else {
              return false;
          }
    }

}
