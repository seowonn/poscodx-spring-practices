package fileupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fileupload.service.FileUploadService;

@Controller
public class FileUploadController {
	
	private FileUploadService fileUploadService;
	
	public FileUploadController(FileUploadService fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	@RequestMapping("/")
	public String form() {
		return "form";
	}

	@RequestMapping("/upload")
	public String updload(
		@RequestParam("email") String email,
		@RequestParam("file") MultipartFile file,
		Model model) {
		
		String url = fileUploadService.restore(file);
		model.addAttribute("url", url);
		return "result";
	}

}