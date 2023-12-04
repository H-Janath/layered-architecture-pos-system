package model;

import dto.CustomerDto;

import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(CustomerDto dto);
    boolean updateCustomer(CustomerDto dto);
    boolean deleteCustomer(String id);
    List<CustomerDto> allCustomers();
    CustomerDto searchCustomer(String id);

}
