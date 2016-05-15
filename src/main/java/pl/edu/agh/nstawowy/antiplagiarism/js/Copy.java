package pl.edu.agh.nstawowy.antiplagiarism.js;


public class Copy {
    CodePlacement aPlacement;
    CodePlacement bPlacement;
    String aBody;
    String bBody;

    public Copy(CodePlacement aPlacement, CodePlacement bPlacement, String aBody, String bBody) {
        this.aPlacement = aPlacement;
        this.bPlacement = bPlacement;
        this.aBody = aBody;
        this.bBody = bBody;
    }

    public Copy() {
    }

    public CodePlacement getaPlacement() {
        return aPlacement;
    }

    public void setaPlacement(CodePlacement aPlacement) {
        this.aPlacement = aPlacement;
    }

    public CodePlacement getbPlacement() {
        return bPlacement;
    }

    public void setbPlacement(CodePlacement bPlacement) {
        this.bPlacement = bPlacement;
    }

    public String getaBody() {
        return aBody;
    }

    public void setaBody(String aBody) {
        this.aBody = aBody;
    }

    public String getbBody() {
        return bBody;
    }

    public void setbBody(String bBody) {
        this.bBody = bBody;
    }
}
