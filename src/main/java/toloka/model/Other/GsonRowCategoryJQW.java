package toloka.model.Other;

public class GsonRowCategoryJQW {
    private String id;
    private String label;
    private GsonRowCategoryJQW[] items;

    public GsonRowCategoryJQW(String id, String label, GsonRowCategoryJQW[] items) {
        this.id = id;
        this.label = label;
        this.items = items;
    }

    public GsonRowCategoryJQW() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public GsonRowCategoryJQW[] getItems() {
        return items;
    }

    public void setItems(GsonRowCategoryJQW[] items) {
        this.items = items;
    }
}
