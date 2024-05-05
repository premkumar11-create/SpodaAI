package ai.spoda.pojo;

import java.util.ArrayList;

public class Widgets {
	public double credit_used;
	public String widget_type;
	public ArrayList<WidgetDatum> widget_data;

	public double getCredit_used() {
		return credit_used;
	}

	public void setCredit_used(double credit_used) {
		this.credit_used = credit_used;
	}

	public String getWidget_type() {
		return widget_type;
	}

	public void setWidget_type(String widget_type) {
		this.widget_type = widget_type;
	}

	public ArrayList<WidgetDatum> getWidget_data() {
		return widget_data;
	}

	public void setWidget_data(ArrayList<WidgetDatum> widget_data) {
		this.widget_data = widget_data;
	}

	public Widgets() {
		// TODO Auto-generated constructor stub
	}

	public Widgets(double credit_used, String widget_type, ArrayList<WidgetDatum> widget_data) {
		super();
		this.credit_used = credit_used;
		this.widget_type = widget_type;
		this.widget_data = widget_data;
	}

}
