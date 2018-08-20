package io.hsjang.markers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import io.hsjang.markers.config.security.MarkerAuthenticationWebFilter;
import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class MarkersApplication implements WebFluxConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(MarkersApplication.class, args);
	}
	
	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
		return http
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				/*.authorizeExchange().anyExchange().permitAll().and()*/
				.csrf().disable()
				.headers().disable()
				.logout().disable()
				.requestCache().disable()
				.exceptionHandling().authenticationEntryPoint( (exchange,e) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))).and()
				.authenticationManager(auth->Mono.just(auth))
				.addFilterAt(new MarkerAuthenticationWebFilter(null), SecurityWebFiltersOrder.AUTHENTICATION)
				.build();
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload-image/**")
            .addResourceLocations("file:///data/upload/");
            //.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }
	
}