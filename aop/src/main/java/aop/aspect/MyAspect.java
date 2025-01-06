package aop.aspect;

import java.io.ObjectInputValidation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * applicationContext의 component-scan에 Aspect 빈을 보도록 설정하지 않았기 때문에 기존 등록된
 * @Component를 달아서 Bean으로 자동 등록할 수 있도록 설정해준다.
 */
@Component
@Aspect
public class MyAspect {
	
	@Before("execution(public aop.domain.Product aop.service.ProductService.find(String))")
	public void adviceBefore() {
		// 행단 관심
		System.out.println("-- Before Advice --");
	}
	
	@After("execution(* aop.service.ProductService.find(..))")
	public void adviceAfter() {
		System.out.println("-- After Advice --");
	}
	
	@AfterReturning("execution(* aop.service.ProductService.find(..))")
	public void adviceAfterReturning() {
		System.out.println("-- AfterReturning Advice --");
	}
	
	@AfterThrowing(value = "execution(* *..*.ProductService.find(..))", throwing = "ex")
	public void adviceAfterThrowing(Throwable ex) {
		System.out.println("-- AfterThrowing[" + ex.getMessage() + "] Advice --");
	}
	
	@Around("execution(* *..*.*.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("-- Around Advice : Before --");

		Object[] params = {"PC"};
		Object result = pjp.proceed(params); // 파라미터를 넘겨 관련 처리를 진행 가능. 파라미터 없어도 됨.
		
		System.out.println("-- Around Advice : After --");
		
		return result;
	}
	
}
