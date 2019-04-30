package com.rr.security.authserver.bean;

import com.rr.security.authserver.htmlelement.RadioElement;

import java.util.List;

public class ApprovalElement {

    private String text;
    private List<RadioElement> radioElements;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<RadioElement> getRadioElements() {
        return radioElements;
    }

    public void setRadioElements(List<RadioElement> radioElements) {
        this.radioElements = radioElements;
    }
}
