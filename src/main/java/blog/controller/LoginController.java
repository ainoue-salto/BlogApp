package blog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	/**
	 * ログイン画面へアクセスする
	 * @param mav 画面
	 * @param error エラー内容
	 * @return ログイン画面
	 */
	@RequestMapping("/login")
	@PreAuthorize("permitAll")
	public String login() {
		return "/login";
	}
	
}
