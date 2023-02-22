package com.stickerdon.library.service.impl;

import com.stickerdon.library.dto.CustomerDto;
import com.stickerdon.library.model.Customer;
import com.stickerdon.library.model.ShoppingCart;
import com.stickerdon.library.repository.CustomerRepository;
import com.stickerdon.library.repository.RoleRepository;
import com.stickerdon.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {
    private RoleRepository repository;
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(RoleRepository repository, CustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRoles(Arrays.asList(repository.findByName("CUSTOMER")));

        Customer customerSave = customerRepository.save(customer);
        return mapperDTO(customerSave);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer saveInfor(Customer customer) {
        Customer customer1 = customerRepository.findByUsername(customer.getUsername());
        customer1.setAddress(customer.getAddress());
        customer1.setCity(customer.getCity());
        customer1.setCountry(customer.getCountry());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        return customerRepository.save(customer1);
    }

    private CustomerDto mapperDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPassword(customer.getPassword());
        customerDto.setUsername(customer.getUsername());
        return customerDto;
    }
}
