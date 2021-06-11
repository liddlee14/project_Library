package com.group.bookloan.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.group.bookloan.main.CustomButton;


public class RentForm extends JFrame{
	DetailMain dm;
	
	JPanel p_north;
	JPanel p_center;
	JPanel p_center1;
	JPanel p_center2;
	JPanel p_south;
	JTextField greeting;
	//Header header;
	JTable rent_table;
	JTable member_table;
	JScrollPane scroll1;
	JScrollPane scroll2;
	JButton goback;
	JButton rent;
	CustomButton bt_del;
	JTextField t_howmany;
	
	Calendar today;		//현재 날짜 정보를 가진 객체
	String rentDate = "";
	String returnDate = "";
//	JTextField t_date;
	
	//책 DB 관련
	String book[] = {"데스노트", "상남2인조", "삐따기", "나루토" , "블리치", "드래곤볼", /*"원피스", "슬램덩크", "바른연애길잡이", "유리가면"*/};
	String bookimages[] = {"데스노트.jpg", "상남2인조.jpg", "삐따기.jpg", "나루토.jpg" , "블리치.jpg", "드래곤볼.jpg"};
	String author[] = {"author0","author1","author2","author3","author4","author5"};
	String pub[] = {"pub0","pub1","pub2","pub3","pub4","pub5"};
	JButton bt_image;
	
	//정보 테이블
	String[][] r = {
			{book[0], author[0], pub[0], rentDate, returnDate},
			{book[1], author[1], pub[1], rentDate, returnDate},
			{book[2], author[2], pub[2], rentDate, returnDate}			
	};
	String[] c= {"책 제목", "저자", "출판사", "대여일자", "반납일자"};
	String[][] row = {
			{"민진호", "010-1234-5678","zino@naver.com", 1+"권 대여중"},
			{}
	};
	String[] col = {"고객명", "전화번호", "이메일주소", "현재 대여수"};
	
	DefaultTableModel model;
	DefaultTableModel model_member;
	
	public RentForm(String title, DetailMain dm) {
		this.dm = dm;
		/************** 생성 **************/
		p_north = new JPanel(new BorderLayout());
		p_center = new JPanel();
		p_center1 = new JPanel();
		p_center2 = new JPanel();
		p_south = new JPanel(new FlowLayout());
		//header = new Header();
		greeting = new JTextField("??님 안녕하세요");
		greeting.setFocusable(false);
		goback = new JButton("뒤로가기");
		rent = new JButton("대여하기");
		model_member = new DefaultTableModel(row, col);
		model = new DefaultTableModel(r,c);
		member_table = new JTable(model_member	);
		rent_table = new JTable(model);
		scroll1 = new JScrollPane(member_table);
		scroll2 = new JScrollPane(rent_table);		
		bt_del = new CustomButton("선택한 도서 삭제");
		t_howmany = new JTextField("대여할 총 도서는 "+r.length+"권입니다");
		
		
		
		bt_del.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(rent_table.getSelectedRow()==-1) {
					return;
				}else {
					model.removeRow(rent_table.getSelectedRow());
				}
			}
		});
		
		
		rent.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "이대로 대여할까요?", "대여 확인", JOptionPane.YES_NO_OPTION);
				
			}
		});

		goback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dm.setVisible(true);
				setVisible(false);
				
				
			}
		});
		
		/****** 디자인 *******/
		greeting.setPreferredSize(new Dimension(500, 50));
		greeting.setForeground(Color.GRAY);
		greeting.setFont(new Font("돋움", Font.BOLD, 40));
		greeting.setBackground(null);
		greeting.setBorder(null);
		p_center.setBackground(Color.LIGHT_GRAY);
		p_center1.setBackground(Color.RED);
		p_center2.setBackground(Color.ORANGE);
		goback.setBorder(new LineBorder(Color.blue, 3, true));
		goback.setPreferredSize(new Dimension(200, 30));
		goback.setBackground(new Color(183, 214, 237));
		rent.setBorder(new LineBorder(Color.blue, 3, true));
		rent.setBackground(new Color(83, 167, 230));
		rent.setPreferredSize(new Dimension(200, 30));
		scroll1.setPreferredSize(new Dimension(550, 300));
		scroll2.setPreferredSize(new Dimension(550, 300));
		bt_del.setPreferredSize(new Dimension(300, 70));
		t_howmany.setFont(new Font("궁서", Font.BOLD, 25));
		t_howmany.setBackground(null);
		t_howmany.setEditable(false);
		member_table.setFont(new Font("돋움", Font.BOLD, 20));
		rent_table.setFont(new Font("굴림", Font.ITALIC, 15));
		bt_del.setFont(new Font("궁서", Font.PLAIN, 25));

		
		
		getToday();
		setRentDate();
		setReturnDate();
		
		for(int i=0; i<r.length; i++) {
			r[i][3] = setRentDate(); //<<  수정된 부분!  3, 4번셀의 값을 넣고 테이블을 생성하시면 될거같아요~
			r[i][4] = setReturnDate();
		}
		
		
		member_table.setRowHeight(40);
		rent_table.setRowHeight(30);

		
		/****** 부착 ******/
//		p_center2.setLayout(new BoxLayout(p_center2, BoxLayout.Y_AXIS));
		//p_north.add(header);
		p_center1.add(greeting, BorderLayout.NORTH);
		p_center1.add(scroll1);
		p_center2.add(scroll2);
		p_center2.add(bt_del);
		p_center2.add(t_howmany);		
		p_center.setLayout(new GridLayout(1,1));
		p_center.add(p_center1);
		p_center.add(p_center2);
		p_south.add(goback);
		p_south.add(rent);
		
		setLayout(new BorderLayout());
		add(p_north, BorderLayout.NORTH);
		add(p_center, BorderLayout.CENTER);
		add(p_south, BorderLayout.SOUTH);
		
		
		
		/****** 보여주기 ******/
		pack();
		setVisible(true);
		setBounds(60,10,1200,670);
		
	}


	//현재 날짜 구하기 (프로그램 가동과 동시에 사용될 디폴트 날짜 객체)
	public Calendar getToday() {
		today = Calendar.getInstance();
		return today;
	}
	
	//대여 날짜, 즉 출력할 날짜 구하기
	public String setRentDate() {
		int mm = today.get(Calendar.MONTH);
		int dd = today.get(Calendar.DATE);		
		rentDate = ((mm+1)+"월 "+(dd)+"일");		//'대여날짜'에 출력
		return rentDate;
	}
	
	public String setReturnDate() {
		int mm = today.get(Calendar.MONTH);
		int dd = today.get(Calendar.DATE);		
		returnDate = ((mm+1)+"월 "+(dd+6)+"일");		//'반납날짜'에 출력
		return returnDate;
	}
	
	
}
