package com.choonsik.korail.service;

import com.choonsik.korail.dto.UserDto;
import com.choonsik.korail.entity.Reservation;
import com.choonsik.korail.entity.User;
import com.choonsik.korail.repository.ReservationRepository;
import com.choonsik.korail.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public void UserCreate(UserDto userDto) {
        User user = new User();
        String rawPassword = userDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setUsername(userDto.getUsername());
        user.setPassword(encPassword);
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
    }

    public List<Reservation> getUserReservations(Long user_id) {
        return reservationRepository.findByUser_UserIdOrderByReservationDateDesc(user_id);
    }


    @Transactional
    public void updateUser(Long userId, UserDto userDto) {
        String rawPassword = userDto.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.update(userDto.getUsername(), encPassword ,userDto.getEmail(),userDto.getPhoneNumber());
    }

}
