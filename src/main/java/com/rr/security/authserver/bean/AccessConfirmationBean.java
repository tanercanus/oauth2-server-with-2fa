package com.rr.security.authserver.bean;

import com.rr.security.authserver.htmlelement.InputElement;

import java.util.List;

public class AccessConfirmationBean {

    private Boolean isUserOauthApproval;
    private InputElement csrfInput;
    private List<ApprovalElement> approvalElements;

    public Boolean getUserOauthApproval() {
        return isUserOauthApproval;
    }

    public void setUserOauthApproval(Boolean userOauthApproval) {
        isUserOauthApproval = userOauthApproval;
    }

    public InputElement getCsrfInput() {
        return csrfInput;
    }

    public void setCsrfInput(InputElement csrfInput) {
        this.csrfInput = csrfInput;
    }

    public List<ApprovalElement> getApprovalElements() {
        return approvalElements;
    }

    public void setApprovalElements(List<ApprovalElement> approvalElements) {
        this.approvalElements = approvalElements;
    }
}
