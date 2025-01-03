package jp.kopher.customloginpage.security

import jp.kopher.customloginpage.repository.ApplicationUserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.JdbcUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import javax.sql.DataSource

@Configuration
class SecurityConfiguration(
    private val customAccessDeniedHandler: AccessDeniedHandler,
    private val applicationUserRepository: ApplicationUserRepository
) {

    @Bean
    fun userDetailsService(): UserDetailsService {
        return CustomUserDetailsService(applicationUserRepository)
    }

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authz ->
            authz.requestMatchers("/login").permitAll()
                .requestMatchers("/delete/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        }
        http.exceptionHandling { exception ->
            exception.accessDeniedHandler(customAccessDeniedHandler)
        }
        http.formLogin { form ->
            form.loginPage("/login")
        }
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers("/webjars/**", "/images/**", "/css/**", "/h2-console/**")
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

}
