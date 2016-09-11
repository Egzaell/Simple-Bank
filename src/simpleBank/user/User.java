package simpleBank.user;

public class User {

	public static User INSTANCE = null;
	private String login;

	private User(String login) {
		this.login = login;
	}

	public static User getInstance(String login) {
		if (INSTANCE == null) {
			INSTANCE = new User(login);
		}
		return INSTANCE;
	}

	public String getLogin() {
		return login;
	}
}
