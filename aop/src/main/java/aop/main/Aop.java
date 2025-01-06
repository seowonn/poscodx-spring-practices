package aop.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import aop.domain.Product;
import aop.service.ProductService;

public class Aop {

	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("config/applicationContext.xml");
		ProductService ps = ac.getBean(ProductService.class);
		Product p = ps.find("TV");
		System.out.println(p);
	}

}
