package com.example.fakefy.service.Implementation;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.repositories.MusicUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private MusicUserRepository musicUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MusicUser userfix = musicUserRepository.findByUsername(username);
        if(userfix == null){
            String role = "AnnonymousUser";
        }
            String role = userfix.getRole().toString();
        Optional<MusicUser> user = musicUserRepository.findUserByUsername(username);
        if (user.isPresent()) {
            var UserObj = user.get();
            return User.builder()
                    .username(UserObj.getUsername())
                    .password(UserObj.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority(role)))// ДОДАДЕНИ РОЛИ!
                    .build();
        }else{
            throw new UsernameNotFoundException(username);
        }
    }
}
