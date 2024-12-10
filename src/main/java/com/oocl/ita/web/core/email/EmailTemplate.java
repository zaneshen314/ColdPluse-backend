package com.oocl.ita.web.core.email;

public enum EmailTemplate {

    REGISTER("用户注册邮箱验证码", "<h1>您的验证码是：</h1><h2>{{verificationCode}}</h2><p>请在 10 分钟内完成验证。</p>");
    private final String subject;
    private final String bodyTemplate;

    EmailTemplate(String subject, String bodyTemplate) {
        this.subject = subject;
        this.bodyTemplate = bodyTemplate;
    }

    public String getSubject() {
        return subject;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }
}
