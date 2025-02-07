package com.personal.foodcost.services;

import com.personal.foodcost.entities.MyUser;
import com.personal.foodcost.exceptions.UserException;
import com.personal.foodcost.mappers.UserMapper;
import com.personal.foodcost.models.DTOs.request_dto.UserRequestDTO;
import com.personal.foodcost.models.DTOs.response_dto.UserResponseDTO;
import com.personal.foodcost.repositories.UserRepository;
import com.personal.foodcost.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserMapper userMapper;

    public List<UserResponseDTO> getAll() {
        List<MyUser> myUserList = userRepository.findAll();

        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        for(MyUser myUser : myUserList) {
            userResponseDTOList.add(userMapper.entityToDto(myUser));
        }

        return userResponseDTOList;
    }

    public UserResponseDTO getById(Long id) throws UserException {
        MyUser myUser = userRepository.findById(id).orElseThrow(() -> new UserException("no user found with this id", 404));

        return userMapper.entityToDto(myUser);
    }

    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) throws UserException {
        try {
            userValidator.isUserValid(userRequestDTO);
            MyUser myUser = userMapper.dtoToEntity(userRequestDTO);
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            MyUser saved = userRepository.saveAndFlush(myUser);

            return userMapper.entityToDto(myUser);

        } catch (UserException e) {
            throw new UserException(e.getMessage(), e.getStatusCode());
        }
    }

    public UserResponseDTO updateById(Long id, UserRequestDTO userRequestDTO) throws UserException {
        try {
            userValidator.isUserValid(userRequestDTO);
            MyUser myUser = userRepository.findById(id).orElseThrow(() -> new UserException("User not found with this id", 404));
            myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
            MyUser saved = userRepository.saveAndFlush(myUser);

            return userMapper.entityToDto(saved);
        } catch (UserException e) {
            throw new UserException(e.getMessage(), e.getStatusCode());
        }
    }

    public UserResponseDTO deleteById(Long id) throws UserException {
        MyUser myUser = userRepository.findById(id).orElseThrow(() -> new UserException("User not found with this id", 404));
        userRepository.deleteById(id);

        return userMapper.entityToDto(myUser);
    }
}
