package mail.quizzapptask.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import mail.quizzapptask.model.Question;


public interface QuestionRepository extends JpaRepository<Question, Integer>{

	

}
