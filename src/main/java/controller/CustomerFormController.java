package controller;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.sql.*;


public class CustomerFormController{

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_salary;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_address;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TableColumn colid;

    @FXML
    private TableColumn colname;

    @FXML
    private TableColumn coladdress;

    @FXML
    private TableColumn colsalary;

    @FXML
    private TableColumn coloption;
    private CustomerModel customermodel = new CustomerModelImpl();

    public void initialize() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colsalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        coloption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldvalue, newvalue) ->{
               setData(newvalue);
        });
    }

    private void setData(CustomerTm newvalue) {
        if(newvalue!=null) {
            txt_id.setEditable(false);
            txt_id.setText(newvalue.getId());
            txt_address.setText(newvalue.getAddress());
            txt_name.setText(newvalue.getName());
            txt_salary.setText(String.valueOf(newvalue.getSalary()));
        }
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        String sql = "select * from customer";
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()) {
                Button btn = new Button("Delete");
                CustomerTm c1 = new CustomerTm(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(4),
                        btn
                );
                btn.setOnAction(actionEvent -> {
                    deleteCustomer(c1.getId());
                });
                tmList.add(c1);
            }
            tblCustomer.setItems(tmList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCustomer(String code) {
        String sql = "delete from customer where id=?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,code);
            int result = pstm.executeUpdate();
            if(result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer deleted").show();
                loadCustomerTable();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void reloadButtonOnAction(ActionEvent event) {
        loadCustomerTable();
        tblCustomer.refresh();
        clearFields();
    }

    private void clearFields() {
        tblCustomer.refresh();
        txt_salary.clear();
        txt_name.clear();
        txt_id.clear();
        txt_address.clear();
        txt_id.setEditable(true);
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        if(txt_id.getText()!=null&txt_address.getText()!=null&txt_name.getText()!=null&txt_salary.getText()!=null){
        CustomerDto c =  new CustomerDto(txt_id.getText(),
                txt_name.getText(),
                txt_address.getText(),
                Double.parseDouble(txt_salary.getText())
        );

        String sql = "insert into customer value('"+c.getId()+"','"+c.getName()+"','"+c.getAddress()+"',"+c.getSalary()+")";
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if(result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved").show();
                loadCustomerTable();
                clearFields();
            }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entity").show();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        }}

    public void tableOnaction(MouseEvent mouseEvent) {
    }

    public void updateButtonOnAction(ActionEvent actionEvent) {
        CustomerDto c =  new CustomerDto(txt_id.getText(),
                txt_name.getText(),
                txt_address.getText(),
                Double.parseDouble(txt_salary.getText())
        );
        String sql = "update customer set name='"+c.getName()+"',address='"+c.getAddress()+"',salary="+c.getSalary()+"where id='"+c.getId()+"'";
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            int result = stm.executeUpdate(sql);
            if(result>0){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved").show();
                loadCustomerTable();
                clearFields();
            }
        }catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entity").show();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void backBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)tblCustomer.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }
}
