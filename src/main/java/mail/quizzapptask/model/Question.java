package mail.quizzapptask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="questions")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="optionA")
	private String optionA;
	
	@Column(name="optionB")
	private String optionB;
	
	@Column(name="optionc")
	private String optionc;
	
	@Column(name="ans")
	private int ans;
	
	@Column(name="chose")
	private int chose;
	
	public Question(int id, String title, String optionA, String optionB, String optionc, int ans, int chose) {	
		this.id = id;
		this.title = title;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionc = optionc;
		this.ans = ans;
		this.chose = chose;
		
	}
	public Question() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionc() {
		return optionc;
	}
	public void setOptionc(String optionc) {
		this.optionc = optionc;
	}
	public int getAns() {
		return ans;
	}
	public void setAns(int ans) {
		this.ans = ans;
	}
	public int getChose() {
		return chose;
	}
	public void setChose(int chose) {
		this.chose = chose;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", optionA=" + optionA + ", optionB=" + optionB
				+ ", optionc=" + optionc + ", ans=" + ans + ", chose=" + chose + "]";
	}
	
}

