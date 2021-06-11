package com.group.bookloan.admin;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MailSendApp extends JFrame{
	JLabel l_receiver;
	JTextField t_receiver;
	JLabel l_sender;
	JTextField t_sender;
	JLabel l_title;
	JTextField t_title;
	JLabel l_area;
	JTextArea area;
	JButton bt;
	Properties props;
	
	public MailSendApp(String address, String name) {
		l_receiver = new JLabel("받는사람");
		t_receiver = new JTextField(address);
		l_sender = new JLabel("보내는사람");
		t_sender = new JTextField("duddnjsyh@gmail.com");
		l_title = new JLabel("제목");
		t_title = new JTextField(name+" 고객님!");
		l_area = new JLabel("내용");
		area = new JTextArea("좋은말로 할 때 반납해라");
		bt = new JButton("메일발송");
		
		setLayout(new FlowLayout());
		area.setPreferredSize(new Dimension(250, 230));
		Font f = new Font("바탕", Font.BOLD, 25);
		Dimension d = new Dimension(260, 30);
		l_receiver.setFont(f);
		t_receiver.setPreferredSize(d);
		l_sender.setFont(f);
		t_sender.setPreferredSize(d);
		l_title.setFont(f);
		t_title.setPreferredSize(d);
		l_area.setFont(f);
		
		add(l_receiver);
		add(t_receiver);
		add(l_sender);
		add(t_sender);
		add(l_title);
		add(t_title);
		add(l_area);
		add(area);
		add(bt);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMail();
			}
		});
		
		setSize(300,600);
		setVisible(true);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void sendMail() {
		props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("duddnjsyh@gmail.com", "");
			}
		});
		
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(t_sender.getText())); //발신자
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(t_receiver.getText()));//수신자
			message.setSubject(t_title.getText()); //메일 주소
			message.setContent(area.getText(), "text/html;charset=utf8"); //메일 내용
			Transport.send(message);
			JOptionPane.showMessageDialog(this, "메일 발송 성공");
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
