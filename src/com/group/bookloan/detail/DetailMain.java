package com.group.bookloan.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.group.bookloan.main.CustomButton;
import com.group.bookloan.main.MyCanvas;
import com.group.bookloan.main.Mypanel;
import com.group.bookloan.main.Page;
import com.group.bookloan.main.Sessions;
import com.group.bookloan.user.MainPage;
import com.group.bookloan.user.NewestPage;
import com.group.bookloan.user.PopularPage;
import com.group.bookloan.user.RecommendPage;

public class DetailMain extends JFrame implements MouseListener{
	Sessions sess;
	Calendar cal = Calendar.getInstance();
	Calendar current;
	Mypanel m_panel;
	Mypanel p_center;
	JPanel p_left;
	JPanel p_left1;
	JPanel p_left2;
	JPanel p_right;
	JPanel p_right1;
	JPanel p_right2;
	ArrayList<String> filenames;
	ArrayList<String> titles;
	ArrayList<Boolean> canloans;
	
	Page rec;
	Page newest;
	Page pop;
	

	JTextArea t_detail;
	JTextField t_title;
	JTextField t_author;
	JTextField t_days;
	String subcategory;
	String topcategory;
	JTextField t_recommend;
	
	CustomButton bt_rent;
	MyCanvas bt_image;
//	CustomButton t_recommend;
	MainPage mainPage;
	//책 DB관련
	String book[] = {"데스노트", "상남2인조", "삐따기", "나루토" , "블리치", "드래곤볼", /*"원피스", "슬램덩크", "바른연애길잡이", "유리가면"*/};
	String rentable[] = {"대여 가능", "대여 불가"};
	String bookimages[] = {"데스노트.jpg", "상남2인조.jpg", "삐따기.jpg", "나루토.jpg" , "블리치.jpg", "드래곤볼.jpg"};
	
	ImageIcon[] bt_book = new ImageIcon[book.length];	
	JTextArea[] t_book = new JTextArea[book.length];
	Image image;
	RecommendBox[] boxArray = new RecommendBox[book.length];
	int product_id;
	int checkBox = 0;
	int hit;
	String title;
	String filename;
	SimpleDateFormat format;
	
	boolean canloan;
	public DetailMain(String title, String filename, MainPage mainPage) {
		//super(mainPage);
		//setTitle(title);
		setTitle(title+" 상세페이지");
		this.title = title;
		this.filename = filename;
		this.mainPage = mainPage;
		/************** 생성 **************/
		newest = mainPage.usePage(0);
		pop = mainPage.usePage(1);
		rec = mainPage.usePage(2);
		m_panel = new Mypanel();
		p_center = new Mypanel();
		p_left = new JPanel();
		p_left1 = new JPanel();
		p_left2 = new JPanel();
		p_right = new JPanel();
		p_right1 = new JPanel();
		p_right2 = new JPanel();
		format = new SimpleDateFormat("yyyy-MM-dd");
		
		bt_image = new MyCanvas(400, 600);
		
		t_author = new JTextField("작가", 28);
		t_author.setEditable(false);
		t_title = new JTextField("책 제목", 28);
		t_title.setEditable(false);
		t_detail = new JTextArea("이 책의 작가는~~");
		t_detail.setFocusable(false);
		t_days = new JTextField("대여기간 : 3일", 20);
		bt_rent = new CustomButton("RENT");
		bt_rent.setFont(new Font("STENCIL", Font.BOLD, 35));
		
		t_recommend = new JTextField("추천 도서");
		t_recommend.setFont(new Font("돋움", Font.BOLD, 20));
		
		
		/****** 디자인 *******/
		t_days.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		t_detail.setPreferredSize(new Dimension(420,400));
		t_detail.setLineWrap(true);
		bt_image.setPreferredSize(new Dimension(400, 600));
//		bt_image.setBorder(BorderFactory.createEmptyBorder());
//		bt_image.setBackground(Color.cyan);
		bt_rent.setPreferredSize(new Dimension(300, 80));
		t_days.setBorder(BorderFactory.createEmptyBorder());
		t_days.setHorizontalAlignment(JTextField.CENTER);
		t_title.setBackground((new Color(0,0,0,30)));
		t_author.setBackground(new Color(230, 230, 230));
		t_detail.setBackground((new Color(0,0,0,30)));
		t_title.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		t_title.setBorder(BorderFactory.createEmptyBorder());
		t_author.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		t_author.setBorder(BorderFactory.createEmptyBorder());
		t_detail.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 15));
		
		//t_title, t_author 추가했습니다! 다른 것도 new Color 하고 폰트 바꿔서 여기 디자인들은 다 가져가시는게...!

		
		//p_right1 영역
		t_recommend.setBorder(BorderFactory.createEmptyBorder());
		t_recommend.setFocusable(false);
		t_recommend.setOpaque(true);
		t_recommend.setBackground((new Color(255,0,0,0)));
		t_recommend.setHorizontalAlignment(JTextField.CENTER);
		t_recommend.setPreferredSize(new Dimension(230, 40));
		
		
		setLayout(new BorderLayout());
		p_center.setLayout(new BorderLayout());
		p_left.setLayout(new BorderLayout());
		p_left2.setLayout(new FlowLayout());
		p_right.setLayout(new BorderLayout());
		
		
		p_center.setPreferredSize(new Dimension(1000, 600));
		p_center.setOpaque(true);
		p_center.setBackground((new Color(255,0,0,0)));
		
		p_left.setPreferredSize(new Dimension(850, 600));
		p_left.setOpaque(true);
		p_left.setBackground((new Color(255,0,0,0)));
		
		p_left1.setPreferredSize(new Dimension(400, 600));
		p_left1.setOpaque(true);
		p_left1.setBackground((new Color(255,0,0,0)));
		
		p_left2.setPreferredSize(new Dimension(440, 600));
		p_left2.setBackground((new Color(255,0,0,0)));
		
		p_right.setPreferredSize(new Dimension(340, 600));
		p_right.setOpaque(true);
		p_right.setBackground((new Color(255,0,0,0)));
		p_right1.setBackground((new Color(0,0,0,30)));
		p_right2.setBackground((new Color(0,0,0,30)));
		
		p_right1.setPreferredSize(new Dimension(340, 45));
		p_right2.setPreferredSize(new Dimension(340, 520));

		bt_image.setPreferredSize(new Dimension(400, 600));
		//bt_image.setBorder(BorderFactory.createEmptyBorder());
		t_days.setBorder(BorderFactory.createEmptyBorder());
		t_days.setHorizontalAlignment(JTextField.CENTER);
		bt_rent.setPreferredSize(new Dimension(300, 80));
		t_recommend.setPreferredSize(new Dimension(230, 40));
		
		//bt_image.setBackground(Color.cyan);
		t_detail.setLineWrap(true);
		
		/****** 부착 ******/
		p_left1.add(bt_image);
		
		p_left2.add(t_title);
		p_left2.add(t_author);
		p_left2.add(t_detail);
		p_left2.add(t_days);
		p_left2.add(bt_rent);
		
		p_left.add(p_left1, BorderLayout.WEST);
		p_left.add(p_left2, BorderLayout.EAST);
		
		p_right1.add(t_recommend, BorderLayout.NORTH);	
		p_right.add(p_right1, BorderLayout.NORTH);
		p_right.add(p_right2);
		
		p_center.add(p_left, BorderLayout.WEST);
		p_center.add(p_right, BorderLayout.EAST);
		
		add(p_center);
		
		bt_rent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(canloan) {
					loginCheck();
				}else {
					JOptionPane.showMessageDialog(DetailMain.this, "이미 대여중인 서적입니다.");
				}
			}
		});
		

		setDetail();
		checktop();
		createRecomm();
		createBox();
		//printImage();
		createImage();
		
		String format_time1 = format.format(cal.getTime());
		//System.out.println(format_time1);

		/****** 보여주기 ******/

		setBounds(60,10,1200,670);
		setVisible(true);

	}
	
	public void createImage() {
		Image image = bt_image.createImage(".//res//"+filename);
		bt_image.setImage(image);
	}
	public void setDetail() {
		String sql = "select * from product where filename = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = mainPage.getAppMain().getCon().prepareStatement(sql);
			pstmt.setString(1, filename);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				t_title.setText(rs.getString("product_name"));
				t_author.setText(rs.getString("author"));
				t_detail.setText(rs.getString("detail"));
				canloan = rs.getBoolean("canloan");
				if(canloan) {
					t_days.setText("대여기간 : 대여일로부터 "+rs.getInt("deadline")+"일");
					t_days.setForeground(new Color(82, 165, 255));					
				}else {
					t_days.setText("대여불가능");
					t_days.setForeground(new Color(247, 74, 47));					
				}
				subcategory = rs.getString("subcategory_id");
				product_id = rs.getInt("product_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void checktop() {
		String sql = "select t.topcategory_id from subcategory as s left outer join topcategory as t on t.topcategory_id = s.topcategory_id where s.subcategory_id="+subcategory;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = mainPage.getAppMain().getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				topcategory = rs.getString("topcategory_id");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void createRecomm() {
		titles = new ArrayList<String>();
		filenames = new ArrayList<String>();
		canloans = new ArrayList<Boolean>();
		String sql = "select * from product as p left outer join subcategory as s on p.subcategory_id = s.subcategory_id left outer join topcategory as t on t.topcategory_id = s.topcategory_id where s.topcategory_id="+topcategory;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = mainPage.getAppMain().getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				filenames.add(rs.getString("filename"));
				titles.add(rs.getString("product_name"));
				hit = (rs.getInt("hit"));
				canloans.add(rs.getBoolean("canloan"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	public void createBox() {
		int n = 0;
		if(filenames.size()<6) {
			n = filenames.size();
		}else {
			n = 6;
		}
		for(int i=0; i<n; i++) {
			int m = 0;
			if(canloans.get(i)) {m = 0;}else {m=1;}
			RecommendBox recommendBox = new RecommendBox(150, 185, titles.get(i), rentable[m],(new Color(255,0,0,0)));
			p_right2.add(recommendBox);
			boxArray[i] = recommendBox;		//나중에 책 이미지 넣을때 필요
			image = boxArray[i].createImage("./res//"+filenames.get(i));
			boxArray[i].setImage(image);
			boxArray[i].addMouseListener(new MouseAdapter() {

				public void mouseReleased(MouseEvent e) {
					//showRecommendDetails();
				}				
			});
		}
	}
	
//	public void showRecommendDetails() {
//		new RentForm("",this);		
	
//	}
	public void insertdata() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String day1 = null;
		day1 = format.format(cal.getTime());
		cal.add(Calendar.DATE, 6);
		String day2 =format.format(cal.getTime());
		String sql ="insert into loanedbook(product_id,day_start,day_end) values(?,?,?)";
		try {
			pstmt=mainPage.getAppMain().getCon().prepareStatement(sql);
			pstmt.setInt(1, product_id);
			pstmt.setString(2,day1);
			pstmt.setString(3,day2);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				
			}else {
				JOptionPane.showMessageDialog(this,"에러발생!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainPage.getAppMain().release(pstmt, rs);
		}
	}
	public void loanbook() {
		sess = mainPage.getAppMain().getSess();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = ("insert into userbooks(user_id, product_id) values(?,?)");
		try {
			pstmt = mainPage.getAppMain().getCon().prepareStatement(sql);
			pstmt.setInt(1, sess.getUser_id());
			pstmt.setInt(2, product_id);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				JOptionPane.showMessageDialog(this, "도서 대여가 성공적으로 이루어졌습니다.");
				setVisible(false);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainPage.getAppMain().release(pstmt, rs);
		}
		
	}
	public void getNumber(int no) {
		this.checkBox = no;
	}
	public void loginCheck() {
		sess = mainPage.getAppMain().getSess();
		if(sess.getUser_id()<1) {
			JOptionPane.showMessageDialog(this, "로그인이 필요한 기능입니다.");
		}else if(checkOverdue()==true){
			JOptionPane.showMessageDialog(this, "연체중인 도서가 있으면 사용할 수 없습니다.");
		}else{
			int result = JOptionPane.showConfirmDialog(null, "이책을 대여하시겠습니까?", "대여 확인", JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION) {
				insertdata();
				loanbook();
				updateLoan();
				addHit();
				RecommendPage rec2 = (RecommendPage)rec;
				NewestPage newest2 = (NewestPage)newest;
				PopularPage pop2 = (PopularPage)pop;
				rec2.removePanel();
				newest2.removePanel();
				pop2.removePanel();
			}else{
				JOptionPane.showMessageDialog(this, "도서 대여를 취소하셨습니다.");
			}
		}
	}
	public void updateLoan() {
		String sql = "update product set canloan = false, user_id = "+sess.getUser_id()+" where product_id = "+product_id;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = mainPage.getAppMain().getCon().prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				
			}else {
				JOptionPane.showMessageDialog(mainPage.getAppMain(),"에러 발생!!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			mainPage.getAppMain().release(pstmt);
		}
	}
	// 이 메서드를 호출하면, 적절한 이미지를 반환해주게 처리!
	public Image getIcon(String filename) {
		URL url = this.getClass().getClassLoader().getResource(filename);		//매개변수 filename이라 저렇게 써야함		//이렇게 로컬url 받아오기로 올릴 수 있게
		ImageIcon icon = new ImageIcon(url);		//메모리에 올라옴
		return icon.getImage();		//우리에게 필요한 건 ImageIcon이 아니라 Image이므로 이미지로 변환해서 반환!
	}
	public Calendar currentTime() {
		current = Calendar.getInstance();
		return current;
	}
	
	public boolean checkOverdue() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean overdue = false;
		
		String sql = "select overdue from userdata where user_id = "+sess.getUser_id();
		try {
			pstmt = mainPage.getAppMain().getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				overdue = rs.getBoolean("overdue");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainPage.getAppMain().release(pstmt,rs);
		}
		return overdue;
	}

	public void addHit() {
		hit++;
		PreparedStatement pstmt =null;
		
		String sql = "update product set hit =? where product_id = "+product_id;
		
		try {
			pstmt = mainPage.getAppMain().getCon().prepareStatement(sql);
			pstmt.setInt(1,hit);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				
			}else {
				JOptionPane.showMessageDialog(mainPage.getAppMain(), "에러발생!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
