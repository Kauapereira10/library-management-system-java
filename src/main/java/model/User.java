package model;

public class User {
	private Long id;
	private String fullName;
	private String nickName;
	private String email;
	private String passwordHash;
	
	public User() {
	
	}
	
	public User(String email, String passwordHash) {
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public User(String fullName, String nickName, String email, String passwordHash) {
		this.fullName = fullName;
		this.nickName = nickName;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public User(Long id, String fullName, String nickName, String email, String passwordHash) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.nickName = nickName;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		if(this.fullName == null || this.fullName.isEmpty()) {
			throw new IllegalArgumentException("O campo nome completo é obrigatório.");
		}
		this.fullName = fullName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(this.email == null || this.email.isEmpty()) {
			throw new IllegalArgumentException("O campo email é obrigatório.");
		}
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		if(this.passwordHash == null || this.passwordHash.isEmpty()) {
			throw new IllegalArgumentException("O campo senha é obrigatório.");
		}
		this.passwordHash = passwordHash;
	}	
	
}
