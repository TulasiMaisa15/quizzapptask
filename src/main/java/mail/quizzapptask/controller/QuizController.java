package mail.quizzapptask.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mail.quizzapptask.model.QuestionForm;
import mail.quizzapptask.model.Result;
import mail.quizzapptask.service.QuizService;


@Controller

public class QuizController {

	@Autowired
	Result result;

	@Autowired
	QuizService qService;

	@GetMapping("/")
	public String home() {
		return "index.html";
	}

	@ModelAttribute("result")
	public Result getResult() {
		return result;
	}

	@PostMapping("/quiz")
	public String quiz(@RequestParam String name,@RequestParam String email, Model m, RedirectAttributes ra) {

		Map<String, Object> model = new HashMap<>();
		model.put("name", name);
		model.put("email", email);
		qService.sendEmailWelcome(model,email);

		if (name.equals("")&&email.equals("")) {
			ra.addFlashAttribute("warning", "You must enter your Name");
			ra.addFlashAttribute("warning", "You must enter your Email");
			return "redirect:/";
		}
		result.setName(name);
		result.setEmail(email);
		QuestionForm qForm = qService.getQuestions();
		m.addAttribute("qForm", qForm);
		return "quiz.html";
	}

	@PostMapping("/submit")
	public String submit(@ModelAttribute QuestionForm qForm, Model m) {
		{
			result.setTotalCorrect(qService.getResult(qForm));
			qService.saveScore(result);

			Map<String, Object> model = new HashMap<>();
			model.put("name", result.getName());
			model.put("email", result.getEmail());
			model.put("correctAns", result.getTotalCorrect());
			model.put("wrongAns", 5-result.getTotalCorrect());

			qService.sendEmailScore(model, result.getEmail());

		}
		return "result.html";
	}

	@GetMapping("/score")
	public String score(Model m) {
		List<Result> sList = qService.getTopScore();
		m.addAttribute("sList", sList);
		return "scoreboard.html";
	}

}