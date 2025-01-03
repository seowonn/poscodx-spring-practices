package fileupload.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

	@RequestMapping("/")
	public String form() {
		return "form";
	}

	@RequestMapping("/upload")
	public String updload(
		@RequestParam("email") String email,
		@RequestParam("file") MultipartFile file) {
		try {
			System.out.println(email);
			System.out.println(file.getOriginalFilename());
			System.out.println(file.getSize());
			System.out.println(file.getBytes().length);
			
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return "result";
	}

}