package controller;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ItemModel;
import model.OrderModel;
import model.impl.CustomerModelImpl;
import model.impl.ItemModelImpl;
import model.impl.OrderModelImpl;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderFormController {
    public JFXComboBox cmbCustId;
    public JFXComboBox cmbItemCode;
    public JFXTextField txtCustName;
    public JFXTextField txtDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTreeTableView<OrderTm> orderTble;
    public TreeTableColumn colDescription;
    public TreeTableColumn colQty;
    public TreeTableColumn colAmount;
    public TreeTableColumn colOption;
    public TreeTableColumn colCode;
    public Label lblTotal;

    public Label lblOrderId;
    public BorderPane pane;
    private List<CustomerDto> customers;
    private List<ItemDto> items;
    double total=0;
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();

    private CustomerModel customer = new CustomerModelImpl();
    private ItemModel item = new ItemModelImpl();
    private OrderModel orderModel = new OrderModelImpl();
    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        genertateID();
        loadCustomerID();
        loadItemCodes();

        cmbCustId.getSelectionModel().selectedItemProperty().addListener((observable,oldvalue,id)->{
                for (CustomerDto dto : customers) {
                    if (dto.getId().equals(id)) {
                        txtCustName.setText(dto.getName());
                    }
                }

        } );

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, code) -> {
            for(ItemDto dto:items){
                if(dto.getCode().equals(code)){
                    txtDescription.setText(dto.getDescription());
                    txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        }));
    }

    private void loadItemCodes() {
        try {
            items = item.allItems();
            ObservableList<String> list = FXCollections.observableArrayList();
            for(ItemDto dto : items){
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerID() {
        try {
            customers = customer.allCustomers();
            ObservableList<String> list = FXCollections.observableArrayList();
            for(CustomerDto dto : customers){
                list.add(dto.getId());
            }
            cmbCustId.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void backBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cmbCustId.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addtoCartSetOnAction(ActionEvent actionEvent) {
        Object customer = cmbCustId.getValue();
            if(customer!=null){
                try {
                    double amount = item.getItem(cmbItemCode.getValue().toString()).getUnitPrice()*Integer.parseInt(txtQty.getText());
                    JFXButton btn = new JFXButton("Delete");
                    OrderTm tm = new OrderTm(
                            cmbItemCode.getValue().toString(),
                            txtDescription.getText(),
                            Integer.parseInt(txtQty.getText()),
                            amount,
                            btn
                    );
                    btn.setOnAction(ActionEvent->{
                        tmList.remove(tm);
                        total-=tm.getAmount();
                        orderTble.refresh();
                        lblTotal.setText(String.format("%.2f",total));

                    });
                    boolean isExist = false;
                    for (OrderTm ordertm:tmList){
                        if(ordertm.getCode().equals(tm.getCode())){
                            ordertm.setQty(ordertm.getQty()+tm.getQty());
                            ordertm.setAmount(ordertm.getAmount()+tm.getAmount());
                            isExist = true;
                            total+=tm.getAmount();
                        }
                    }
                    if(!isExist){
                        tmList.add(tm);
                        total+=tm.getAmount();
                    }
                    RecursiveTreeItem<OrderTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
                    orderTble.setRoot(treeItem);
                    orderTble.setShowRoot(false);
                    lblTotal.setText(String.format("%.2f",total));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Please select customer").show();
            }
    }

    public void genertateID(){
        try {
            OrderDto dto = orderModel.lastOrder();
            if (dto!=null){
                String id = dto.getOrderid();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                lblOrderId.setText(String.format("D%03d",num));
            }else{
                lblOrderId.setText("D001");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    public void placeOrdersetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (!isEmpty()) {
            List<OrderDetailsDto> list = new ArrayList<>();
            for (OrderTm tm : tmList) {
                list.add(new OrderDetailsDto(
                        lblOrderId.getText(),
                        tm.getCode(),
                        tm.getQty(),
                        tm.getAmount() / tm.getQty()
                ));
            }
            if (!tmList.isEmpty()) {
                boolean isSaved = orderModel.saveOrder(new OrderDto(
                        lblOrderId.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")).toString(),
                        cmbCustId.getValue().toString(),
                        list
                ));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Order Saved!").show();
                }
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"Empty field found").show();
        }

    }
    public boolean isEmpty(){
        if(txtQty.getText().isEmpty()||txtDescription.getText().isEmpty()||txtUnitPrice.getText().isEmpty()||txtCustName.getText().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
