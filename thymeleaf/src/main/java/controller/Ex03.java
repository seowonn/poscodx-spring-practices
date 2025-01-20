package controller;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vo.UserVo;

@Controller
@RequestMapping("/ex03")
public class Ex03 {
	@GetMapping("/01")
	public String _01(Model model) {
		model.addAttribute("list", Arrays.asList(new UserVo(1L, "둘리1"), new UserVo(2L, "둘리2"), new UserVo(3L, "둘리3")));
		return "ex03/01";
	}

	@GetMapping("/02")
	public String _02(
		@RequestParam(value="r", defaultValue="0") Integer row,
		@RequestParam(value="c", defaultValue="0") Integer col,
		Model model) {
		model.addAttribute("row", row);
		model.addAttribute("col", col);
		return "ex03/02";
	}
}