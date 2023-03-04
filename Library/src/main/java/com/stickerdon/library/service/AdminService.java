package com.stickerdon.library.service;

import com.stickerdon.library.dto.AdminDto;
import com.stickerdon.library.model.Admin;
import com.stickerdon.library.model.Customer;

public interface AdminService {
    Admin findByUsername(String username);
    Admin save(AdminDto adminDto);
    Admin saveInfo(Admin admin);
}
