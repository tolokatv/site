package toloka.model.Other;

public class GsonRowCategory {
    private Long id;
    private String text;
    private GsonRowCategory[] children;

    public GsonRowCategory(Long id, String text, GsonRowCategory[] children) {
        this.id = id;
        this.text = text;
        this.children = children;
    }

    public GsonRowCategory() {
        this.id = null;
        this.text = null;
        this.children = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GsonRowCategory[] getChildren() {
        return children;
    }

    public void setChildren(GsonRowCategory[] children) {
        this.children = children;
    }
}
