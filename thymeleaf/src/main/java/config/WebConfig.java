package config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan({ "controller" })
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * 전체 흐름
	 * Controller: "home" (ViewName 반환)
		   ↓
		ThymeleafViewResolver
		   ↳ SpringTemplateEngine
		       ↳ SpringResourceTemplateResolver
		           ↳ 파일 로드 (classpath:/templates/home.html)
		       ↳ 템플릿 렌더링
		   ↓
		클라이언트로 HTML 출력
	 */
	
	/**
	 * Resouce Handler
	 * ResourceHandlerRegistry: 애플리케이션의 정적 리소스 요청에 대해 핸들러를 등록할 수 있다.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/assets/**")
			.addResourceLocations("classpath:assets/");
	}

	// Message Source
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages/message");
		messageSource.setDefaultEncoding("utf-8");

		return messageSource;
	}

	/**
	 * Thymeleaf01: Template Resolver
	 * SpringResourceTemplateResolver: Thymeleaf 템플릿 엔진에서 ViewName을 해석하고 요구사항에 따라 리소스를 로드하는 역할
	 * 	DispatcherServlet이 컨트롤러에서 반환된 ViewName을 (설정헤 따라) Thymeleaf View Resolver 또는 Html View Resolver에 넘겨주면
	 * 	View Resolver는 SpringResourceTemplateResolver를 사용하여 지정된 위치에서 HTML 템플릿 파일을 찾는다.
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver(
		ApplicationContext applicationContext) {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

		// Spring의 ApplicationContext를 설정하여 리소스 로딩 가능
		templateResolver.setApplicationContext(applicationContext);

		// 템플릿 파일의 기본 경로 설정 (classpath는 src/main.resources를 의미한다.)
		templateResolver.setPrefix("classpath:templates/");

		// 템플릿 파일의 확장자 설정
		templateResolver.setSuffix(".html");

		// 템플릿 모드 설정 (HTML 템플릿으로 처리)
		templateResolver.setTemplateMode(TemplateMode.HTML);

		// 템플릿 파일의 캐싱 비활성화 (개발 환경에서 실시간 변경 반영하기 위함)
		templateResolver.setCacheable(false);

		// 템플릿 파일의 인코딩 설정
		templateResolver.setCharacterEncoding("utf-8");

		return templateResolver;
	}

	/**
	 * Thymeleaf02: Template Engine
	 * SpringResourceTemplateResolver를 사용해 템플릿을 로드, 
	 * 이후 템플릿 파일을 해석하고 렌더링(HTML 생성)하는 역할을 담당
	 */
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();

		// 템플릿 해석기 설정 (SpringResourceTemplateResolver 등)
		templateEngine.setTemplateResolver(templateResolver);
		
		// Spring EL (SpEL) 컴파일러 활성화 (동적 표현식 지원)
		templateEngine.setEnableSpringELCompiler(true);
		
		// 메시지 소스 설정 (국제화 지원)
		templateEngine.setTemplateEngineMessageSource(messageSource());

		return templateEngine;
	}
	
	/**
	 * MultiView: View Resolver를 2개이상 설정하는 경우
	 * 	setOrder 설정으로 우선순위 지정
	 * 	setViewNames를 다르게 설정하여 View Resolver의 중복 참조 방지한다.
	 */
	
	// Thymeleaf03: 여러 View Resolver 중 Thymeleaf View Resolver를 설정하는 메서드
	@Bean
	public ViewResolver thymeleafViewResolver(ISpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setOrder(1);

		return viewResolver;
	}
	
	// JSP 파일 반환을 위한 View Resolver 설정
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setViewNames("views/*");
		viewResolver.setPrefix("/WEB-INF/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(0);

		return viewResolver;
	}
	
}
