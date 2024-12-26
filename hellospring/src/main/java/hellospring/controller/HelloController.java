package hellospring.controller;

import java.io.IOException;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello() {
		return "/WEB-INF/views/hello.jsp";
	}
	
	@RequestMapping("/hello2")
	public String hello(@RequestParam("name") String name) {
		System.out.println("name: " + name);
		return "/WEB-INF/views/hello.jsp";
	}
	
	@RequestMapping("/hello3")
	public ModelAndView hello3(@RequestParam("name") String name) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/WEB-INF/views/hello3.jsp");
		mav.addObject("name", name);
		return mav;
	}
	
	@RequestMapping("/hello4")
	public String notUsingModelAndView(@RequestParam("name") String name, Model model) {
		model.addAttribute("name", name);
		return "/WEB-INF/views/hello3.jsp";
	}
	
	@ResponseBody
	@RequestMapping("/hello5")
	public String usingResponseBody() {
		return "<h1>Hello Spring</h1>";
	}
	
	@RequestMapping("/hello6")
	public String redirect() {
		return "redirect:/hello";
	}
	
//	이건 bad code~
	@RequestMapping("/hello7")
	public void hello7(HttpServletRequest req, HttpServletResponse res, Writer out) throws IOException {
		String name = req.getParameter("name");
		System.out.println("name: " + name);
//		res.getWriter().print("<h1>Hello World</h1>");
		out.write("<h1>Hello World</h1>");
	}
	
}
