package luna.vinicius.rebeldesapi.service;

import lombok.RequiredArgsConstructor;
import luna.vinicius.rebeldesapi.repository.RebeldeRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final RebeldeRepository rebeldeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var rebeldequery = IterableUtils.toList(rebeldeRepository.filterByLogin(username).toIterable()).stream().findFirst();
        if (rebeldequery.isPresent()) {
            var rebelde = rebeldequery.get();
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(rebelde.getRole()));

            return new User(username, rebelde.getSenha(), authorities);
        } else {
            throw new UsernameNotFoundException("Rebelde não encontrado");
        }
    }
}
