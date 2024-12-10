package com.oocl.ita.web.core.email;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static com.oocl.ita.web.common.constant.EmailConstants.*;

public class EmailUtil {
    /**
     * 发送邮件并填充模板内容。
     *
     * @param template 邮件模板，表示邮件的主题和正文格式。
     *
     * @param email   收件人的电子邮件地址。
     *
     * @param params  邮件模板中占位符所需要的动态数据。
     */
    public static void sendEmailWithTemplate(EmailTemplate template, String email, Map<String, String> params) {
        // 生成邮件主题
        String subject = template.getSubject();

        // 填充邮件内容
        String body = fillTemplate(template.getBodyTemplate(), params);

        // 发送邮件
        sendEmail(email, subject, body, true);
    }
//    解析占位符方法
    private static String fillTemplate(String template, Map<String, String> params) {
        // 用传入的参数填充模板中的占位符
        for (Map.Entry<String, String> entry : params.entrySet()) {
            template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return template;
    }

        /**
     * 发送邮件
     * @param to          收件人邮箱
     * @param subject     邮件主题
     * @param content     邮件内容
     * @param isHtml      是否是 HTML 格式的邮件
     */
    public static void sendEmail(String to, String subject, String content, boolean isHtml) {
        Properties properties = new Properties();
        properties.put(MAIL_SMTP_HOST,SMTP_HOST); // SMTP 主机
        properties.put(MAIL_SMTP_PORT, SMTP_PORT); // SMTP 端口
        properties.put(MAIL_SMTP_AUTH, SMTP_AUTH); // 启用认证
        properties.put(MAIL_SMTP_SSL_ENABLE, SMTP_STARTTLS); // 启用 SSL 加密

        // 获取会话对象
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SEND_EMAIL, CODE); // 使用授权码代替密码
            }
        });

        try {
            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SEND_EMAIL)); // 发件人邮箱
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); // 收件人邮箱
            message.setSubject(subject); // 邮件主题

            // 设置邮件内容
            if (isHtml) {
                message.setContent(content, ContentType); // HTML 格式邮件
            } else {
                message.setText(content); // 普通文本邮件
            }

            // 发送邮件
            Transport.send(message);
            System.out.println("邮件发送成功!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("邮件发送失败!");
        }
    }

    // 测试邮件发送
    public static void main(String[] args) {

        String to = "we1dongwu@163.com";  // 收件人邮箱
        String subject = "测试邮件";
        String content = "<h1>这是一个测试邮件</h1><p>发送邮件测试通过。</p>";

        sendEmail(to, subject, content, true);  // 发送 HTML 格式邮件
    }

    // 生成 4 位随机验证码
    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // 生成 1000-9999 的随机数
        return String.valueOf(code);
    }
    // 邮箱格式正则表达式
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // 邮箱格式验证
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false; // 空字符串不合法
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
