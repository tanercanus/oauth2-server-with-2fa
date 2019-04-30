package com.tanerus.security.authserver.htmlelement;

public class RadioElement {
    private String type = "radio";
    private String name;
    private String value;
    private String checked;
    private String text;
    private String id;

    public RadioElement(String name, String value, String checked, String text, String id) {
        this.name = name;
        this.value = value;
        this.checked = checked;
        this.text = text;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
