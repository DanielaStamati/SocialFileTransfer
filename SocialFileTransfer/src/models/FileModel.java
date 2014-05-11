package models;


public class FileModel {
    private String name;
    private User from;
    private User to;
    private float progress;
    private String status;

    
    public FileModel(String name, User from, User to){
    	this.name = name;
    	this.from = from;
    	this.to = to;
    	//TODO update
    	this.status = StringConstants.sending;
    	this.progress = 0.f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return name;
    }        
}
