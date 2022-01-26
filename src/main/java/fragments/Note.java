package fragments;

public class Note {
	// a unique id is given to each note to identify each note
	private int id;
	private int colorIndex, category;
	private String title, text;
	private long time_modified;
	
	public Note(int id, int colorIndex, String title, String text, long time_modified, int category) {
		this.id = id;
		this.colorIndex = colorIndex;
		this.title = title;
		this.text = text;
		this.time_modified = time_modified;
		this.category = category;
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getColorIndex() {
		return colorIndex;
	}
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getTime_modified() {
		return this.time_modified;
	}
	public void setTime_modified(long time_modified) {
		this.time_modified = time_modified;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
