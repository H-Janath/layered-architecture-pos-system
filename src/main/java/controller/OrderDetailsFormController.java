package controller;

import bo.BoFactory;
import bo.custom.OrderDetailsBo;
import bo.custom.impl.OrderDetailsBoimpl;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.util.BoType;
import dto.OrderDetailsDto;
import dto.tm.OrderDetailsTm;
import dto.OrderDto;
import dto.OrderDto2;
import dto.tm.OrderTm2;
import entity.Orders;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import dao.Custom.OrderDetailsDao;
import dao.Custom.OrderDao;
import dao.Custom.impl.OrderDetailsDaoImpl;
import dao.Custom.impl.OrderDaoImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

public class OrderDetailsFormController {
    public JFXTextField txtsearch;
    public JFXTreeTableView<OrderTm2> orderTbl;
    public TreeTableColumn colorderid;
    public TreeTableColumn colcustomerId;
    public TreeTableColumn colordersdate;

    public JFXTreeTableView<OrderDetailsTm> orderdetailstble;
    public TreeTableColumn colorderdetailsid;
    public TreeTableColumn colorderdetailsitemcode;
    public TreeTableColumn colorderdetailsqty;
    public TreeTableColumn colOrderdetailsUnitPrice;
    OrderDetailsBo orderDetailsBo = BoFactory.getInstance().getBo(BoType.ORDER_DETAIL);
    OrderDao orderDao = new OrderDaoImpl();
    public void initialize(){
        colorderid.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colcustomerId.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        colcustomerId.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerId"));

        //Order details table column
        colorderdetailsid.setCellValueFactory(new TreeItemPropertyValueFactory<>("OrderId"));
        colorderdetailsitemcode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colorderdetailsqty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOrderdetailsUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitpice"));


        loadOrderTable();

        orderTbl.setOnMouseClicked(event -> {
            if(event.getClickCount()==1&&(!orderTbl.getSelectionModel().isEmpty())){
                TreeItem<OrderTm2> item = orderTbl.getSelectionModel().getSelectedItem();
                try {
                    ObservableList<OrderDetailsTm> tmList = FXCollections.observableArrayList();
                    List<OrderDetailsDto> dtoList = orderDetailsBo.getOrderDetails(item.getValue().getId());
                    for(OrderDetailsDto dto:dtoList){
                        tmList.add(
                                new OrderDetailsTm(
                                        dto.getOrderId(),
                                        dto.getItemCode(),
                                        dto.getQty(),
                                        dto.getUnitprice()
                                )
                        );
                    }
                    TreeItem<OrderDetailsTm> treeItem = new RecursiveTreeItem<>(tmList,RecursiveTreeObject::getChildren);
                    orderdetailstble.setRoot(treeItem);
                    orderdetailstble.setShowRoot(false);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch(ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        txtsearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                orderTbl.setPredicate(new Predicate<TreeItem<OrderTm2>>() {
                    @Override
                    public boolean test(TreeItem<OrderTm2> treeItem) {
                        return treeItem.getValue().getId().contains(newValue);
                    }
                });
            }
        });

    }

    private void loadOrderTable() {
        ObservableList<OrderTm2> tmList = FXCollections.observableArrayList();
        try {
            List<OrderDto2> dtolist = orderDetailsBo.allOrders();
            for(OrderDto2 order:dtolist){
                tmList.add(
                        new OrderTm2(
                                order.getId(),
                                order.getDate(),
                                order.getCustomerId()
                        )
                );
            }
            TreeItem<OrderTm2> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            orderTbl.setRoot(treeItem);
            orderTbl.setShowRoot(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void reloadButtonOnAction(ActionEvent actionEvent) {
    }

    public void backBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) txtsearch.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
