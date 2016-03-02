package br.com.restautomation.assembly;

public class Mark {

	private String param;

	private String value;

	private int pos = 0;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static Mark value(String word) {

		Mark mark = new Mark();
		mark.setValue(word);
		return mark;
	}

	public static Mark param(String word) {

		Mark mark = new Mark();
		mark.setParam(word);
		return mark;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

}
