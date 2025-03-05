package com.choonsik.korail.controller;

import com.choonsik.korail.config.auth.PrincipalDetails;
import com.choonsik.korail.dto.UserDto;
import com.choonsik.korail.entity.Reservation;
import com.choonsik.korail.entity.User;
import com.choonsik.korail.repository.UserRepository;
import com.choonsik.korail.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/test/login")
    public @ResponseBody String testOAuthLogin(
            Authentication authentication,
            @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("/test/login =============");

        // OAuth2User 정보 출력
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User oAuth2User) {
            System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());
        } else {
            System.out.println("Unknown principal type: " + principal);
        }

        // 일반 로그인 유저 정보 출력
        if (userDetails != null) {
            System.out.println("UserDetails: " + userDetails.getUsername());
            // 필요한 다른 정보도 출력할 수 있습니다.
            System.out.println("User role(s): " + userDetails.getAuthorities());
        } else {
            System.out.println("No user details available");
        }

        return "세션 정보 확인하기";
    }



    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "joinForm";
        }
        userService.UserCreate(userDto);

        return "redirect:/loginForm";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        // SecurityContext에서 인증된 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails ) {
            String username = userDetails.getUsername(); // 현재 로그인한 사용자의 username

            User user = userRepository.findByUsername(username); // DB에서 사용자 정보 가져오기

            if (user == null) {
                return "redirect:/loginForm"; // 유저가 DB에 없을 경우 리다이렉트
            }

            model.addAttribute("user", user);

            List<Reservation> reservations = userService.getUserReservations(user.getUserId());

            if (reservations.isEmpty()) {
                model.addAttribute("message", "예약 내역이 없습니다.");
            } else {
                model.addAttribute("reservations", reservations);
            }

            return "user"; // user.html로 반환
        } else {
            return "redirect:/loginForm"; // 로그인되지 않은 경우 리다이렉트
        }

    }

    @PutMapping("/user/{userId}")
    public String updateUser(@PathVariable Long userId, @ModelAttribute UserDto userDto, Model model) {
        userService.updateUser(userId, userDto);
        model.addAttribute("userId", userId);
        return "redirect:/user";
    }

}

