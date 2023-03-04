package com.stickerdon.library.service.impl;

import com.stickerdon.library.dto.AdminDto;
import com.stickerdon.library.model.Admin;
import com.stickerdon.library.model.Customer;
import com.stickerdon.library.repository.AdminRepository;
import com.stickerdon.library.repository.RoleRepository;
import com.stickerdon.library.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, RoleRepository roleRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public Admin save(AdminDto adminDto) {
        Admin admin = new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(roleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

    @Override
    public Admin saveInfo(Admin admin) {
        Admin adminSave = adminRepository.findByUsername(admin.getUsername());
        adminSave.setFirstName(admin.getFirstName());
        adminSave.setLastName(admin.getLastName());
        adminSave.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        return adminRepository.save(adminSave);
    }
}
