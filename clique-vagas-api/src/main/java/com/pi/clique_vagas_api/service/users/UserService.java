package com.pi.clique_vagas_api.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.repository.UserRepository;
import com.pi.clique_vagas_api.resources.dto.user.UserDto;
import com.pi.clique_vagas_api.utils.DateUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel createUser(UserDto data) {

        if (this.userRepository.findByEmail(data.email()) != null)
            throw new EventNotFoundException("User already exists with email: " + data.email());

        if (userRepository.findByCpf(data.cpf()) != null)
            throw new EventNotFoundException("User already exists with CPF: " + data.cpf());

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        var user = new UserModel(
                null,
                data.firstName(),
                data.lastName(),
                data.role(),
                data.cpf(),
                data.phone(),
                data.email(),
                encryptedPassword,
                DateUtils.nowInZone(),
                null);
        return userRepository.save(user);// .getUserId();

        // var user = new UserModel(
        // null,
        // userDto.firstName(),
        // userDto.lastName(),
        // userDto.role(),
        // userDto.cpf(),
        // userDto.phone(),
        // userDto.email(),
        // userDto.password(),
        // DateUtils.nowInZone(),
        // null);
        // var userSaved = userRepository.save(user);
        // return userSaved.getUserId();
    }

    public UserModel getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EventNotFoundException("User not found with ID: " + userId));
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserById(Long userId, UserDto userDto) {
        var userModel = userRepository.findById(userId);

        if (userModel.isPresent()) {
            var user = userModel.get();

            if (userDto.firstName() != null)
                user.setFirstName(userDto.firstName());
            if (userDto.lastName() != null)
                user.setLastName(userDto.lastName());
            if (userDto.cpf() != null)
                user.setCpf(userDto.cpf());
            if (userDto.phone() != null)
                user.setPhone(userDto.phone());
            if (userDto.email() != null)
                user.setEmail(userDto.email());
            if (userDto.password() != null)
                user.setPassword(userDto.password());

            user.setUpdatedAt(DateUtils.nowInZone());

            userRepository.save(user);
        }

    }

    public void deleteById(Long id) {
        var userExists = userRepository.existsById(id);
        if (userExists) {
            userRepository.deleteById(id);
        }
    }
}
