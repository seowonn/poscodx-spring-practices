package locale.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LocaleController {
	
	private LocaleResolver localeResolver;
	
	public LocaleController(LocaleResolver localeResolver) {
		this.localeResolver = localeResolver;
	} 
	
	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model) {
		String lang = localeResolver.resolveLocale(request).getLanguage();
//		출력하려면 쿠기에 값을 넣어줘야 함.
		System.out.println("Language Code: " + lang);
		
		model.addAttribute("lang", lang);
		return "index";
	}

}
