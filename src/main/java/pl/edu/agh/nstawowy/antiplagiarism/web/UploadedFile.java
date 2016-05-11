package pl.edu.agh.nstawowy.antiplagiarism.web;


public class UploadedFile {
    private String filename;
    private String content;

    public UploadedFile(String filename, String content) {
        this.filename = filename;
        this.content = content;
    }

    public UploadedFile() {

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
