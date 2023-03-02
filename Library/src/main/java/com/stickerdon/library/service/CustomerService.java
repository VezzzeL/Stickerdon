package com.stickerdon.library.service;

import com.stickerdon.library.dto.CustomerDto;
import com.stickerdon.library.model.Customer;

public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);
    Customer findByUsername(String username);
    Customer saveInfo(Customer customer);
}
