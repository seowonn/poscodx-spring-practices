package controller;

import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import vo.UserVo;

@Controller
@RequestMapping("/ex02")
public class Ex02 {
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@GetMapping("/01")
	public String _01(HttpSession session, Model model) {
		/**
		 * Request Scope(Model Attributes)
		 * Model에서 addAttribute를 통해 RequestScope에 데이터를 저장할 수 있다.
		 * HTTP 요청 동안에만 유효하다. (요청 단위로 관리)
		 */
		UserVo vo1 = new UserVo(1L, "둘리1");
		model.addAttribute("vo", vo1);
		
		/**
		 * Session Scope
		 * 클라이언트의 세션이 유지되는 동안 유효하다. 즉, 동일 클라이언트에서의 요청 간에 데이터가 유지된다.
		 */
		UserVo vo2 = new UserVo(2L, "둘리2");
		session.setAttribute("vo", vo2);

		/**
		 * Application Scope1(ServletContext)
		 * 모든 사용자와 요청 간에 데이터를 공유한다. 
		 * 서버가 실행되고 종료될 때까지 데이터를 유지한다. 
		 */
		UserVo vo3 = new UserVo(3L, "둘리3");
		servletContext.setAttribute("vo", vo3);
		
		// The 'request','session','servletContext' and 'response' expression utility objects are no longer available by default for template expressions and their use is not recommended.
		// In cases where they are really needed, they should be manually added as context variables.
		// 권장하지 않는 이유: Spring의 철학과 맞지 않음. 서블릿 객체와의 결합도가 높아져 테스트와 확장성이 저하될 가능성이 있다.
		model.addAttribute("servletContext", servletContext);

		//
		// Application Scope2(Spring의 ApplicationContext)
		//
		UserVo vo4 = new UserVo(4L, "둘리4");
		
		MutablePropertyValues propertyValues = new MutablePropertyValues(new ObjectMapper().convertValue(vo4, new TypeReference<Map<String, Object>>(){}));
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(UserVo.class);
		beanDefinition.setPropertyValues(propertyValues);
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition("vo", beanDefinition);
		
		return "ex02/01";
	}
}
