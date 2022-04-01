package luna.vinicius.rebeldesapi;

import luna.vinicius.rebeldesapi.model.Rebelde;
import luna.vinicius.rebeldesapi.service.RebeldeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class RebeldesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RebeldesApiApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(RebeldeService rebeldeService) {
        return args -> {
            var rebelde = new Rebelde();
            rebelde.setNome("admin");
            rebelde.setSenha("admin");
            rebelde.setGenero("humano");
            rebelde.setLogin("admin");
            rebeldeService.inserirAdmin(rebelde);

        };
    }

}
