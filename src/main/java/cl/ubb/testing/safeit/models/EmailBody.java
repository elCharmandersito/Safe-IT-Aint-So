package cl.ubb.testing.safeit.models;

public class EmailBody {
	
	private String email;
	private String content;
	private String subject;
	
	public EmailBody(String email, String content, String subject) {
		this.email = email;
		this.content = content;
		this.subject = subject;
	}
	public EmailBody() {
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
