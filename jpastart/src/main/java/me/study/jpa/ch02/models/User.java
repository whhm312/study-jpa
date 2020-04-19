package me.study.jpa.ch02.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
public class User {

	@Id
	private String email;
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date update_date;

	protected User() {
	}

	public User(String email, String name, Date createDate) {
		this.email = email;
		this.name = name;
		this.createDate = createDate;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getUpdateDate() {
		return update_date;
	}

	public void changeName(String newName) {
		this.name = newName;
		this.update_date = new Date();
	}

}
