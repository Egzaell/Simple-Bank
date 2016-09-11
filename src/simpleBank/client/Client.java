package simpleBank.client;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {

	private String login;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthDate;

	public Client(ClientBuilder clientBuilder) {
		this.login = clientBuilder.login;
		this.password = clientBuilder.password;
		this.firstName = clientBuilder.firstName;
		this.lastName = clientBuilder.lastName;
		this.birthDate = clientBuilder.birthDate;
	}
	
	@Override
	public boolean equals(Object aClient) {
		Client testClient = (Client) aClient;
		String testLogin = testClient.getLogin();
		return login.equals(testLogin);
	}
	
	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBirthDate() {
		return ClientBuilder.formatter.format(birthDate);
	}
	
	public static class ClientBuilder {
		
		public static DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		private static final String WRONG_FORMAT = "ZLY FORMAT WEJSCIOWY";
		private String login;
		private String password;
		private String firstName;
		private String lastName;
		private Date birthDate;
		
		public Client build() {
			return new Client(this);
		}
		
		public ClientBuilder login(String login) {
			this.login = login;
			return this;
		}
		
		public ClientBuilder password(String password) {
			this.password = password;
			return this;
		}
		
		public ClientBuilder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public ClientBuilder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public ClientBuilder birthDate(String birthDate) {
			try {
				this.birthDate = formatter.parse(birthDate);
			} catch (ParseException e) {
				System.out.println(WRONG_FORMAT);
				e.printStackTrace();
			}
			return this;
		}
	}
}
