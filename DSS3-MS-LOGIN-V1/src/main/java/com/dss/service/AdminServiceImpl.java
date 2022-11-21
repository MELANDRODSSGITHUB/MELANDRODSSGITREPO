package com.dss.service;

import com.dss.entity.Role;
import com.dss.entity.User;
import com.dss.exception.DuplicateAdminException;
import com.dss.exception.EmailAlreadyBeenUsedException;
import com.dss.model.UserRequest;
import com.dss.repository.AdminRepository;
import com.dss.util.ResponseMsgConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public String addAdmin(UserRequest userRequest) {
        User resultUser;
        Optional<User> adminOptional = adminRepository.findById(userRequest.getAdminId());
        if (adminOptional.isPresent()) {
            throw new DuplicateAdminException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG);
        }
        Optional<User> adminEmailOptional = adminRepository.findAdminByEmail(userRequest.getEmail());
        if (adminEmailOptional.isPresent()) {
            throw new EmailAlreadyBeenUsedException(ResponseMsgConstant.FAILED_TO_REGISTER_ADMIN_MSG);
        }
        Role role = new Role();
        role.setDescription(userRequest.getRole());
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .mobileNumber(userRequest.getMobileNumber())
                .roles(roleList)
                .build();
        resultUser = adminRepository.save(user);

        return ResponseMsgConstant.SUCCESSFULLY_REGISTER_ADMIN_MSG.concat(" (id: " + resultUser.getUserId()
                + "\t Name: " + resultUser.getFirstName() + " "
                + resultUser.getLastName() + ")");
    }

    @Override
    public List<User> getAllAdmins() {
        return adminRepository.findAll();
    }

/*    @Override
    public String doLogin(String email, String password) {
        Optional<Admin> admin = adminRepository.findAdminByEmailAndPassword(email, DSSUtil.passwordEncryption(password));
        if (!admin.isPresent()) {
            throw new LoginAuthenticationException(ResponseMsgConstant.LOGIN_AUTHENTICATION_FAILED_MSG);
        }

        return ResponseMsgConstant.LOGIN_AUTHENTICATION_SUCCESS_MSG;
    }*/
}
