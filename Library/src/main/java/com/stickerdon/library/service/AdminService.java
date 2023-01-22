package com.stickerdon.library.service;

import com.stickerdon.library.dto.AdminDto;
import com.stickerdon.library.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
