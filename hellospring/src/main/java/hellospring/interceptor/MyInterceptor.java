package hellospring.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *	호출 시점: 백엔드 Controller 들어가기 전에 호출됨.
 * 
 */
public class MyInterceptor implements HandlerInterceptor {

	/**
	 * 여기 handler 매개변수는 url의 handler?
	 * Object 타입인 이유: default Servlet Handler와 ~해서 2개의 타입?이 넘어올 수 있기 때문에
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("MyInterceptor.preHandle() Called");
		return true;	// 여기를 true로 설정해 줘야 이후 동작 실행됨! : postHandle 및 백엔드 Controller 진입
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("MyInterceptor.postHandle() Called");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("MyInterceptor.afterCompletion() Called");
	}

}
