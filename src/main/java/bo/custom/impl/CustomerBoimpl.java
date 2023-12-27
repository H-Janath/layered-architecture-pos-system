package bo.custom.impl;

import bo.custom.CustomerBo;
import dao.Custom.CustomerDao;
import dao.Custom.impl.CustomerDaoImpl;
import dao.DaoFactory;
import dao.util.DaoType;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerBoimpl implements CustomerBo {
    private CustomerDao customerDao = DaoFactory.getInstence().getDao(DaoType.CUSTOMER);
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.save(new Customer(
                dto.getId(),
                dto.getName(),
                dto.getAddress(),
                dto.getSalary()
        ));
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return customerDao.update(
                new Customer(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary()
                        ));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDao.delete(id);
    }

    @Override
    public List<CustomerDto> allCustomer() throws SQLException, ClassNotFoundException {
        List<Customer> entityList = customerDao.getAll();
        List<CustomerDto> list = new ArrayList<>();
        for(Customer customer:entityList){
            list.add(new CustomerDto(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            ));
        }
        return list;
    }
}
