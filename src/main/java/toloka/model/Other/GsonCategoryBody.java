package toloka.model.Other;

public class GsonCategoryBody {

    // body - json который мы отправляем на сервер
    // action - действие соторое мы выполняем create, edit, delete, move
    // create, edit, delete - один аргумент
    // move - переносим елемент firstid под secondid
    // firstid - id первого елемента
    // value - строковое значение. Используется при переименовании и добавлении категории
    // secondid - id второго елемента
    private String action;
    private String firstid;
    private String value;
    private String secondid;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFirstid() {
        return firstid;
    }

    public void setFirstid(String firstid) {
        this.firstid = firstid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSecondid() {
        return secondid;
    }

    public void setSecondid(String secondid) {
        this.secondid = secondid;
    }

}
