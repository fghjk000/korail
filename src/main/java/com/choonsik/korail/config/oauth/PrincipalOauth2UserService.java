package com.choonsik.korail.config.oauth;

import com.choonsik.korail.config.auth.PrincipalDetails;
import com.choonsik.korail.entity.Enum;
import com.choonsik.korail.entity.User;
import com.choonsik.korail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration:"+userRequest.getClientRegistration());
        System.out.println("getAttributes:"+super.loadUser(userRequest));

        OAuth2User oauth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getClientId();
        String providerId =oauth2User.getAttribute("sub");
        String username = provider+"_"+providerId;
        String password = "password";
        String email = oauth2User.getAttribute("email");
        Enum.UserRole role = Enum.UserRole.USER;  // 기본값을 USER로 설정

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }
        return new PrincipalDetails(userEntity, oauth2User.getAttributes());
    }
}