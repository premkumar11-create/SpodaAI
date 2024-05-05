package ai.spoda.pojo;

import java.util.Date;

public class SpodaResponse {

	public User user;
	public String question;
	public String type;
	public Widgets widgets;
	public String id;
	public Date createdAt;
	public Date updatedAt;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Widgets getWidgets() {
		return widgets;
	}

	public void setWidgets(Widgets widgets) {
		this.widgets = widgets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public SpodaResponse() {
		// TODO Auto-generated constructor stub
	}

	public SpodaResponse(User user, String question, String type, Widgets widgets, String id, Date createdAt,
			Date updatedAt) {
		super();
		this.user = user;
		this.question = question;
		this.type = type;
		this.widgets = widgets;
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
