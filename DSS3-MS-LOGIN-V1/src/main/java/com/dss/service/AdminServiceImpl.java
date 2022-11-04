package com.dss.service;

import com.dss.entity.Admin;
import com.dss.exception.DuplicateAdminException;
import com.dss.exception.EmailAlreadyBeenUsedException;
import com.dss.exception.LoginAuthenticationException;
import com.dss.model.AdminRequest;
import com.dss.repository.AdminRepository;
import com.dss.util.DSSUtil;
import com.dss.util.ResponseMsgConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public String addAdmin(AdminRequest adminRequest) {
        Admin addedAdmin;
        Optional<Admin> adminOptional = adminRepository.findById(adminRequest.getAdminId());
        if (adminOptional.isPresent()) {
            throw new DuplicateAdminException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG);
        }
        Optional<Admin> adminEmailOptional = adminRepository.findAdminByEmail(adminRequest.getEmail());
        if (adminEmailOptional.isPresent()) {
            throw new EmailAlreadyBeenUsedException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG);
        }

        Admin admin = Admin.builder()
                .firstName(adminRequest.getFirstName())
                .lastName(adminRequest.getLastName())
                .email(adminRequest.getEmail())
                .password(DSSUtil.passwordEncryption(adminRequest.getPassword()))
                .mobileNumber(adminRequest.getMobileNumber())
                .build();
        addedAdmin = adminRepository.save(admin);

        return ResponseMsgConstant.SUCCESSFULLY_REGISTER_ADMIN_MSG.concat(" (id: " + addedAdmin.getAdminId()
                + "\t Name: " + addedAdmin.getFirstName() + " "
                + addedAdmin.getLastName() + ")");
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public String doLogin(String email, String password) {
        Optional<Admin> admin = adminRepository.findAdminByEmailAndPassword(email, DSSUtil.passwordEncryption(password));
        if (!admin.isPresent()) {
            throw new LoginAuthenticationException(ResponseMsgConstant.LOGIN_AUTHENTICATION_FAILED_MSG);
        }

        return ResponseMsgConstant.LOGIN_AUTHENTICATION_SUCCESS_MSG;
    }
}
