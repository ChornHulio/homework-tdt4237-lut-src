package core;

public class UserSession {
	private String username;
    private String password;
    private String question;
    private String answer;
    private boolean adminRole = false;
    private boolean loggedIn = false;
	
    public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isAdminRole() {
		return adminRole;
	}
	public void setAdminRole(boolean adminRole) {
		this.adminRole = adminRole;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public void setUsername(String username) {
        this.username = username;
 	}
    public String getUsername() {
        return username;
    }
    public void setPassword(String password) {
        this.password = password;
 	}
    public String getPassword() {
       return password;
	}    	
}
