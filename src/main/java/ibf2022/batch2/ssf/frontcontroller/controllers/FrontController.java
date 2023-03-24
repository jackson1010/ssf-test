package ibf2022.batch2.ssf.frontcontroller.controllers;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

import ch.qos.logback.core.util.SystemInfo;
import ibf2022.batch2.ssf.frontcontroller.Model.User;
import ibf2022.batch2.ssf.frontcontroller.services.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping
public class FrontController {

	// TODO: Task 2, Task 3, Task 4, Task 5

	@Autowired
	private AuthenticationService auSvc;

	@GetMapping(path = { "/", "/view0.html" })
	public String showLandingPage(Model model, HttpSession sess) {
		User user = (User) sess.getAttribute("user");

		if (user == null) {
			user = new User();
			sess.setAttribute("user", user);
		}
		model.addAttribute("user", user);
		sess.setAttribute("count", 0);
		return "view0";
	}

	// return "redirect:/api/authenticate";

	@PostMapping(path = "/login")
	public String getAuthenticate(@RequestParam String username, @RequestParam String password,
			Model model, HttpSession sess, @Valid User user, BindingResult binding) throws Exception {

		int count_tries = (int) sess.getAttribute("count");
		user.setCount(count_tries);
		System.out.println(user.isTimeout());

		if (user.getCount() < 2 && !user.isTimeout()) {

			if (binding.hasErrors()) {
				return "view0";
			}

			if (user.getAnswer() != user.getCorrectAnswer()) {
				FieldError error = new FieldError("user", "answer", "Captcha answer is incorrect");
				binding.addError(error);
				user.getRandomNumbers();
				count_tries++;
				System.out.println(user.getCorrectAnswer());
				user.setCount(count_tries);
				System.out.println("no. of tries" + user.getCount());
				sess.setAttribute("count", user.getCount());
				return "view0";
			}

			try {
				auSvc.authenticate(user.getUsername(), user.getPassword());
			} catch (HttpClientErrorException e) {
				if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
					System.out.println("Incorrect username and/or password");
					user.setAuthenticated(false);
					user.getRandomNumbers();
					System.out.println("correct" +user.getCorrectAnswer());
					count_tries++;
					user.setCount(count_tries);
					sess.setAttribute("count", user.getCount());
					return "view0";
				} else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
					System.out.println("Bad Request");
					// Handle the 400 Bad Request response here
					user.setAuthenticated(false);
					user.getRandomNumbers();
					count_tries++;
					user.setCount(count_tries);
					System.out.println(user.getCount());
					sess.setAttribute("count", user.getCount());
					return "view0";
				} else {
					throw e;
				}
			}
			return "view1";

		}
		count_tries = 0;
		user.setCount(count_tries);
		sess.setAttribute("count", user.getCount());
		auSvc.disableUser(username);
		user.setTimeout(true);
		return "view2";

	}

	private FieldError FieldError(String string, String string2, String string3) {
		return null;
	}

}
