package mail.quizzapptask.model;

import java.util.List;

import org.springframework.stereotype.Component;


@Component
public class QuestionForm {

	private List<Question> questions;
	List<Question> findAll() {
		return questions;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
