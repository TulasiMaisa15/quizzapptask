package mail.quizzapptask.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import mail.quizzapptask.dao.QuestionRepository;
import mail.quizzapptask.dao.ResultRepository;
import mail.quizzapptask.model.Question;
import mail.quizzapptask.model.QuestionForm;
import mail.quizzapptask.model.Result;

@Service
public class QuizService {

	@Autowired
	QuestionForm qForm;

	@Autowired
	QuestionRepository qRepo;

	@Autowired
	Result result;

	@Autowired
	ResultRepository rRepo;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Configuration config;

	@Value("${spring.mail.username}")
	private String fromEmail;

	private String personalMessage = "TULASI";

	public QuestionForm getQuestions() {
		List<Question> allQues = qRepo.findAll();
		List<Question> qList = new ArrayList<Question>();

		Random random = new Random();

		for (int i = 0; i < 5; i++) {

			int rand = random.nextInt(allQues.size());
			qList.add(allQues.get(rand));
			allQues.remove(rand);
		}

		qForm.setQuestions(qList);

		return qForm;
	}

	public int getResult(QuestionForm qForm) {
		int correct = 0;
		for (Question q : qForm.getQuestions())
			if (q.getAns() == q.getChose())
				correct++;

		return correct;
	}

	public void saveScore(Result result) {
		Result saveResult = new Result();
		saveResult.setName(result.getName());
		saveResult.setEmail(result.getEmail());
		saveResult.setTotalCorrect(result.getTotalCorrect());
		rRepo.save(saveResult);
	}

	public List<Result> getTopScore() {
		List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));

		return sList;
	}

	private String createTemplate(String htmlFile, Object model) {
		String html = "";
		try {
			Optional<String> value = Optional.of(htmlFile);
			if (value.isPresent()) {
				Template t = config.getTemplate(htmlFile);
				html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
			}
		} catch (IOException | TemplateException e) {
		}
		return html;
	}

	public void sendEmail(Map<String, Object> model, String email, String htmlFile, String strSubject) {

		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			helper.setTo(email);
			helper.setText(createTemplate(htmlFile, model), true);
			helper.setSubject(strSubject);
			helper.setFrom(fromEmail, personalMessage);
			mailSender.send(message);

		} catch (MessagingException | UnsupportedEncodingException e) {
		}
	}

	public void sendEmailWelcome(Map<String, Object> model,  String email) {
		sendEmail(model, email, "register.html", "Welcome to Quizz  - tulasi");
	}

	public void sendEmailScore(Map<String, Object> model,  String email) {
		sendEmail(model, email, "score.html", "Quizz Score - tulasi");
	}
}
