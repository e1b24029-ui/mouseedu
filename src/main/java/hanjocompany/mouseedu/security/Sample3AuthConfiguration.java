package hanjocompany.mouseedu.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Sample3AuthConfiguration {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login
        .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")) // ログアウト後に / にリダイレクト
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/sample3/**").authenticated() // /sample3/以下は認証済みであること
            .anyRequest().permitAll()); // 上記以外は全員アクセス可能
    return http.build();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    UserDetails user1 = User.withUsername("榎恕洲逗彌")
        .password("{bcrypt}$2y$10$cInqypTK0yMSXlzT1tCVcOegBEbyB31rxX.ygE0Zf1goSbVIor.U6").roles("USER").build();
    UserDetails admin = User.withUsername("takamama")
        .password("{bcrypt}$2y$10$zeRremppYn1DFzmbuMpP4ulMRIX95lansHZMpWUY/4YJiQmeUcYeu").roles("ADMIN").build();

    return new InMemoryUserDetailsManager(user1, admin);
  }
}
