package cn.tutu.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 较为易懂
 *
 * 发送邮件的工具类
 * @anthor tutu
 *
 */

public class SimpleMailUtils {
	public static void sendMail(String to, String code) throws Exception {
		Properties props = new Properties();
		props.setProperty("mail.smpt", "localhost");   // 设置邮箱服务器
		
		// session对象.连接（与邮箱服务器连接）
		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("service@shop.com", "123");
			}
			
		});
	
		// 构建邮件内容
		Message message = new MimeMessage(session);
		// 发件人
		message.setFrom(new InternetAddress("service@shop.com"));
		// 收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		// 设置标题
		message.setSubject("来自SHOP的激活邮件");
		// 设置正文
		message.setContent("<h1>来自SHOP_TWO官网的激活邮件，点击以下链接完成激活</h1><h3><a href='http://localhost:8080/ShopTwo/ActiveServlet?code="+code+"'>http://localhost:8080/ShopTwo/ActiveServlet?code="+code+"</a></h3>", "text/html;charset=UTF-8");
		
		// 发送的对象
		Transport.send(message);
		
	}
}
