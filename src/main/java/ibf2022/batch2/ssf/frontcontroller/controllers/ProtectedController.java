package ibf2022.batch2.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProtectedController {

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected

	@GetMapping("/protected/**")

	public String protectedWeb(){
		return "redirect:/";

	}
}
