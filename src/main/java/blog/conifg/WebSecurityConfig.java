package blog.conifg;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //Spring Securityの有効化を行う
public class WebSecurityConfig {

	private DataSource dataSource;

	public WebSecurityConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests //リクエスト時に認証が必要かを定義
				.requestMatchers("/login").permitAll()
				.requestMatchers("/js/**").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/path/**").permitAll()
				.anyRequest().authenticated());
		http.formLogin(formLogin -> //フォーム認証であることを定義
		formLogin
				.loginPage("/login") //ログインページURL // ユーザー名・パスワードの送信先URL???
				.usernameParameter("username") //ユーザー名のパラメータ名を指定
				.passwordParameter("password") //パスワードのパラメータ名を指定

		);

		http.logout((logout) -> logout.logoutSuccessUrl("/login"));

		http.formLogin((form) -> {
			form
					.defaultSuccessUrl("/blog", true)
					.loginPage("/login");
		});

		return http.build();

	}

	//パスワードハッシュ化する
	@Bean //同アプリケーション内の他のコンポーネントで使用するためBeanとして登録
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		return bcpe;
	}
}

