package org.example.nckh.service;

import org.example.nckh.model.User;
import org.example.nckh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Đăng ký người dùng
    public User registerUser(User user) throws Exception {
        // Kiểm tra nếu email đã tồn tại
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("Email đã được sử dụng.");
        }

        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Tạo mã xác nhận
        user.setVerificationCode(generateVerificationCode());
        user.setEnabled(false); // Chưa kích hoạt
        return userRepository.save(user);
    }

    // Gửi email xác nhận
    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Xác nhận tài khoản");
        message.setText("Xin chào " + user.getUsername() + ",\n\n" +
                "Cảm ơn bạn đã đăng ký. Vui lòng sử dụng mã xác nhận sau để kích hoạt tài khoản của bạn:\n\n" +
                user.getVerificationCode() + "\n\n" +
                "Cảm ơn!");
        mailSender.send(message);
    }

    // Xác thực người dùng
    public boolean verifyUser(String code) {
        User user = userRepository.findByVerificationCode(code);
        if (user != null) {
            user.setEnabled(true);
            user.setVerificationCode(null); // Xóa mã sau khi xác thực
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // Tạo mã xác nhận ngẫu nhiên 6 chữ số
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}