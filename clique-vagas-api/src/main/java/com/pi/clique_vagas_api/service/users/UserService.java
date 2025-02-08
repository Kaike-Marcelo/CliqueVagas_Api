package com.pi.clique_vagas_api.service.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pi.clique_vagas_api.exceptions.EventNotFoundException;
import com.pi.clique_vagas_api.model.users.UserModel;
import com.pi.clique_vagas_api.repository.users.UserRepository;
import com.pi.clique_vagas_api.resources.dto.user.PostUserDto;
import com.pi.clique_vagas_api.utils.DateUtils;
import com.pi.clique_vagas_api.utils.FileUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final String PROFILE_DIR = "files/users/profile/";

    public UserModel createUser(PostUserDto data) {

        if (this.userRepository.findByEmail(data.email()) != null)
            throw new DataIntegrityViolationException("User already exists with email: " + data.email());

        if (userRepository.findByCpf(data.cpf()) != null)
            throw new DataIntegrityViolationException("User already exists with CPF: " + data.cpf());

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
                .orElseThrow(() -> new EventNotFoundException("User not found with ID: " +
                        userId));
    }

    public byte[] getPhotoProfileByFileName(String filename) {
        if (filename == null || filename.isEmpty())
            throw new IllegalArgumentException("Filename is required.");

        return FileUtils.loadFileFromPath(PROFILE_DIR + filename);
    }

    public byte[] getPhotoProfileByEmail(String email) {
        var user = findByEmail(email);

        if (user == null)
            throw new EventNotFoundException("User not found with email: " + email);

        String filename = user.getUrlImageProfile();
        if (filename == null || filename.isEmpty())
            throw new IllegalArgumentException("User does not have a profile photo.");

        return FileUtils.loadFileFromPath(filename);
    }

    public UserModel findByEmail(String email) {
        return (UserModel) userRepository.findByEmail(email);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel updateUserById(UserModel user, PostUserDto userDto) {

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
        if (!userRepository.existsById(id)) {
            throw new EventNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    public void deletePhotoProfile(String email) {
        UserModel user = findByEmail(email);

        String existingImageUrl = user.getUrlImageProfile();
        if (existingImageUrl != null && !existingImageUrl.isEmpty()) {
            FileUtils.deleteFile(existingImageUrl);
            user.setUrlImageProfile(null);
            userRepository.save(user);
        }
    }
}
