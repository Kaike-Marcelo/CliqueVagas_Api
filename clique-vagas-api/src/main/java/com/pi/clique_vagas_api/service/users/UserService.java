package com.pi.clique_vagas_api.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.repository.users.UserRepository;
import com.pi.clique_vagas_api.resources.dto.user.UserDto;
import com.pi.clique_vagas_api.utils.DateUtils;
import com.pi.clique_vagas_api.utils.FileUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String PROFILE_DIR = "files/users/profile/";

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
                null,
                data.role(),
                data.cpf(),
                data.phone(),
                data.email(),
                encryptedPassword,
                DateUtils.nowInZone(),
                null);
        return userRepository.save(user);

    }

    public UserModel savePhotoProfile(MultipartFile file, Long userId) {

        UserModel user = getUserById(userId);

        String existingImageUrl = user.getUrlImageProfile();
        if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
            FileUtils.deleteFile(existingImageUrl);
        }

        if (!FileUtils.isValidContentTypeImage(file.getContentType()))
            throw new IllegalArgumentException("File must be a image.");

        var url = FileUtils.saveFileInDirectory(file, userId, PROFILE_DIR, "profile");

        user.setUrlImageProfile(url);

        return userRepository.save(user);
    }

    public UserModel getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EventNotFoundException("User not found with ID: " + userId));
    }

    public UserModel findByEmail(String email) {
        return (UserModel) userRepository.findByEmail(email);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel updateUserById(UserModel user, UserDto userDto) {

        if (user == null)
            throw new EventNotFoundException("User not found with ID: ");

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

        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        var userExists = userRepository.existsById(id);
        if (userExists) {
            userRepository.deleteById(id);
        }
    }

    public void deletePhotoProfile(Long userId) {
        UserModel user = getUserById(userId);

        String existingImageUrl = user.getUrlImageProfile();
        if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
            FileUtils.deleteFile(existingImageUrl);
            user.setUrlImageProfile(null);
            userRepository.save(user);
        }
    }
}
