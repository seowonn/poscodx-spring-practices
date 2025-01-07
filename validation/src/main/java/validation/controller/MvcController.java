package validation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import validation.domain.User;

@Controller
public class MvcController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/result")
	public String result() {
		return "result";
	}
	
	@GetMapping("/ex01")
	public String ex01() {
		return "form/ex01";
	}
	
	/**
	 * BindingResult result: - Spring MVC에서 유효성 검사의 결과를 담아준다. @Valid와 함께! 사용된다. 
	 * 				  - Spring Framework가 자동으로 생성하고 Controller 메서드에 직접 주입해준다.
	 * 				  - 요청 데이터를 객체(user)로 바인딩하는 과정에서 발생하는 
	 * 				    에러 정보(@Valid 유효성 검사 결과)를 저장한다. 
	 */
	@PostMapping("/ex01")
	public String ex01(@ModelAttribute @Valid User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
//			List<ObjectError> errors = result.getAllErrors();
//			for(ObjectError error :  errors) {
//				System.out.println(error);
//			}
			
			Map<String, Object> map = result.getModel();
			model.addAllAttributes(map);
			return "form/ex01";
		}
		return "redirect:/result";
	}
	
	@GetMapping("/ex02")
	public String ex02() {
		return "form/ex02";
	}
	
	@PostMapping("/ex02")
	public String ex02(@ModelAttribute @Valid User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "form/ex02";
		}
		return "redirect:/result";
	}
	
	@GetMapping("/ex03")
	public String ex03(@ModelAttribute User user) {
		return "form/ex03";
	}
	
	@PostMapping("/ex03")
	public String ex03(@ModelAttribute @Valid User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "form/ex02";
		}
		return "redirect:/result";
	}

}
