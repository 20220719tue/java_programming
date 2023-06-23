import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Menu extends JFrame{
	private Color colorMain = new Color(0xDEEBF7);
	private Color colorBtn = new Color(0xFFE699);
	
	private ImageIcon imgCart = new ImageIcon("images/cart.png");
	
	private String userID;
	private String strFoodAdd[] = {"음료","식사류","간식류"};
	private String strAddUrl = "";
	
	
    private JScrollPane scrFood[]=new JScrollPane[3];//음식메뉴가 담기는 스크롤팬
    
    
	private RoundedButton btnLogin = new RoundedButton("로그인");//메인화면 로그인
	private RoundedButton btnRegister = new RoundedButton("회원가입");//메인화면 회원가입
	private RoundedButton btnLoginInput = new RoundedButton("로그인");//로그인화면 로그인 
	private RoundedButton btnSearchID = new RoundedButton("아이디찾기");//로그인화면 아이디찾기
	private RoundedButton btnSearchPIN = new RoundedButton("비밀번호찾기");//로그인화면 비밀번호찾기
	private RoundedButton btnLoginNo = new RoundedButton("처음으로");//로그인화면 처음으로
	private RoundedButton btnSearchIDOk = new RoundedButton("찾기");//아이디찾기화면 찾기버튼
	private RoundedButton btnSearchIDNo = new RoundedButton("취소");//아이디찾기화면 취소버튼
	private RoundedButton btnSearchPINOk = new RoundedButton("찾기");//비밀번호찾기화면 찾기버튼
	private RoundedButton btnSearchPINNo = new RoundedButton("취소");//비밀번호찾기화면 취소버튼
	private RoundedButton btnSignUpOk = new RoundedButton("확인");//회원가입화면 확인버튼
	private RoundedButton btnSignUpNo = new RoundedButton("취소");//회원가입화면 취소버튼
	public RectButton btnSeat[] = new RectButton[20];//좌석 배열 버튼
	private RoundedButton btnLogout[] = new RoundedButton[40];//로그아웃 배열 버튼
	private RectButton btnSeatMain[] = new RectButton[3];//좌석변경, 음식주문, 퇴실 버튼
	private RoundedButton btnseatChangeNo[] = new RoundedButton[2];//좌석변경 취소
	private RectButton btnOrderFood[] = new RectButton[3];//음료, 음식류, 간식류 버튼
	private RoundedButton btnFoodOk[] = new RoundedButton[5];//음식주문화면에서 주문을 한다
	private RoundedButton btnFoodNo[] = new RoundedButton[5];//음식주문화면에서 취소버튼->자리배정후 화면으로 되돌아감
	private RoundedButton btnDelete = new RoundedButton("삭제");
	private RectButton btnAdminMain[] = new RectButton[3];//좌석조회, 금액조회, 퇴실
	private RectButton btnSeatText[]=new RectButton[3];//이용가능한 좌석, 불가능한 좌석, 나의좌석에 대한 설명
	private RoundedButton btnAdminNo=new RoundedButton("이전");//금액조회(관리자) 이전
	private JButton cart = new JButton(imgCart);//카트버튼->음식주문목록화면으로 이동
	private OvalButton cartNum = new OvalButton();//주문한 음식 갯수
	private RectButton btnAddIcon = new RectButton("파일추가");//관리자에서 음식 이미지 파일 추가 버튼
	private JButton icon = new JButton();//관리자에서 음식 이미지 파일 추가 버튼
	private RoundedButton btnAdd[] = new RoundedButton[2];
	private RoundedButton btnOutSeat= new RoundedButton("전체퇴실");
	
	private JLabel la;
	private JLabel laSeat[]=new JLabel[3];
	private JLabel laSeatText[]=new JLabel[3];//이용가능한 좌석, 불가능한 좌석, 나의좌석에 대한 설명
	private JLabel laCost[]=new JLabel[3];
	
	public JTextField textID = new JTextField();
	public JTextField textPIN = new JTextField();
	public JTextField textIDName = new JTextField();
	public JTextField textIDEmail = new JTextField();
	public JTextField textPINID = new JTextField();
	public JTextField textPINName = new JTextField();
	public JTextField textPINEmail = new JTextField();
	public JTextField textSignName = new JTextField();
	public JTextField textSignID = new JTextField();
	public JTextField textSignPIN = new JTextField();
	public JTextField textSignPINCheck = new JTextField();
	public JTextField textSignEmail = new JTextField();
	public JTextField textSeatNum = new JTextField() {
		public void setBorder(Border border) {}
	};
	public JTextField textFoodAdd[] = new JTextField[4];
	
	
	public JComboBox comboFoodAdd = new JComboBox(strFoodAdd);
	
	private JPanel panelMain = new JPanel();//메인화면(로그인 및 회원가입)
	private JPanel panelLogin = new JPanel();//로그인 화면
	private JPanel panelSearchID = new JPanel();//아이디찾기 화면
	private JPanel panelSearchPIN = new JPanel();//비밀번호찾기 화면
	private JPanel panelSignUp = new JPanel();//회원가입 화면
	private JPanel panelSeatManage= new JPanel();//자리배정화면(사용자)
	private JPanel panelSeatMain = new JPanel();//자리배정후화면(사용자)
	private JPanel panelOrderFood = new JPanel();//음식주문 화면(사용자)
	private JPanel panelCart = new JPanel();//음식주문 장바구니 화면(사용자)
	private JPanel panelAdminMain = new JPanel();//관리자메인화면(관리자)
	private JPanel panelAdminCost = new JPanel();//금액조회화면(관리자)
	private JPanel panelAdminFoodAdd = new JPanel();//음식추가화면(관리자)
	private JPanel panelOrderMenu[] = new JPanel[5];//음식주문 스크롤 화면(음료, 음식류, 간식류)
	private JPanel panelOrderList = new JPanel();//장바구니 스크롤 화면
	
	private int seatNum = 0;
	private int laCostdata[]= {0,0,0};
	private int foodCheck = 0;//0일때는 이용자화면/1일때는 관리자화면
	private int count = 0;//클릭한 음식 갯수
	
	ArrayList <Integer>seat = new ArrayList<Integer>();
	ArrayList<Integer> id = new ArrayList<Integer>();//음식주문 클릭된 음식id
	ArrayList<Integer> amount = new ArrayList<Integer>();//음식주문 클릭된 음식id
	ArrayList<Integer> cost = new ArrayList<Integer>();//음식주문 클릭된 음식cost
	ArrayList<String> name = new ArrayList<String>();//음식주문 클릭된 음식name
	
	OvalButton btnMinus[]=new OvalButton[id.size()];
	OvalButton btnPlus[]=new OvalButton[id.size()];
	JLabel laName[] = new JLabel[id.size()];
	JLabel laAmount[]=new JLabel[id.size()];
	JLabel laTrue[]=new JLabel[id.size()];
	JLabel laFalse[]=new JLabel[id.size()];
	RectButton2[] btnList = new RectButton2[id.size()];
	boolean listCheck[] = new boolean[id.size()] ;
	
	Container c = getContentPane();

	
	public Menu(){
		setTitle("Study Cafe Program");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);//창 크기 고정
		
		//메인화면(로그인 및 회원가입)
		panelMain.setLayout(null);
		panelMain.setBackground(colorMain); 
		MyActionListener listener = new MyActionListener();
		
		la = new JLabel("스터디 카페");
		la.setSize(400,200);
		la.setLocation(140,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,60));
		panelMain.add(la);
		
		la = new JLabel("프로그램");
		la.setSize(400,300);
		la.setLocation(175,100);
		la.setFont(new Font("맑은고딕",Font.BOLD,60));
		panelMain.add(la);
		
		btnLogin.setSize(200,60);//로그인 버튼
		btnLogin.setLocation(190, 450);
		btnLogin.setBackground(colorBtn);
		btnLogin.setFont(new Font("맑은고딕",Font.BOLD,40));
		btnLogin.addActionListener(listener);
		panelMain.add(btnLogin);
		
		btnRegister.setSize(200,60);//회원가입 버튼
		btnRegister.setLocation(190, 550);
		btnRegister.setBackground(colorBtn);
		btnRegister.setFont(new Font("맑은고딕",Font.BOLD,40));
		btnRegister.addActionListener(listener);
		panelMain.add(btnRegister);
		
		//로그인 화면
		panelLogin.setLayout(null);
		panelLogin.setBackground(colorMain); 
		
		la = new JLabel("로그인");
		la.setSize(300,100);
		la.setLocation(220,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelLogin.add(la);
		
		la = new JLabel("아이디");
		la.setSize(300,100);
		la.setLocation(30,200);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelLogin.add(la);
		
		la = new JLabel("비밀번호");
		la.setSize(300,100);
		la.setLocation(30,360);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelLogin.add(la);
		
		textID.setSize(300,100);//아이디 입력 창
		textID.setLocation(220,210);
		textID.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelLogin.add(textID);
		
		textPIN.setSize(300,100);//비밀번호 입력 창
		textPIN.setLocation(220,360);
		textPIN.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelLogin.add(textPIN);
	
		btnLoginInput.setSize(150,50);//로그인 버튼
		btnLoginInput.setLocation(210, 510);
		btnLoginInput.setBackground(colorBtn);
		btnLoginInput.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnLoginInput.addActionListener(listener);
		panelLogin.add(btnLoginInput);
		
		btnSearchID.setSize(120,40);//아이디찾기 버튼
		btnSearchID.setLocation(220, 600);
		btnSearchID.setBackground(colorMain);
		btnSearchID.setFont(new Font("맑은고딕",Font.BOLD,15));
		btnSearchID.setBorderPainted(false);
		btnSearchID.addActionListener(listener);
		panelLogin.add(btnSearchID);
		
		btnSearchPIN.setSize(120,40);//비밀번호찾기 버튼
		btnSearchPIN.setLocation(360, 600);
		btnSearchPIN.setBackground(colorMain);
		btnSearchPIN.setFont(new Font("맑은고딕",Font.BOLD,15));
		btnSearchPIN.setBorderPainted(false);
		btnSearchPIN.addActionListener(listener);
		panelLogin.add(btnSearchPIN);
		
		btnLoginNo.setSize(120,40);//메인화면으로 돌아가기
		btnLoginNo.setLocation(80, 600);
		btnLoginNo.setBackground(null);
		btnLoginNo.setBorderPainted(false);
		btnLoginNo.setFont(new Font("맑은고딕",Font.BOLD,15));
		btnLoginNo.addActionListener(listener);
		panelLogin.add(btnLoginNo);
		
		
		
		//아이디 찾기
		panelSearchID.setLayout(null);
		panelSearchID.setBackground(colorMain); 
		
		la = new JLabel("아이디 찾기");
		la.setSize(300,100);
		la.setLocation(180,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchID.add(la);
		
		la = new JLabel("이름");
		la.setSize(300,100);
		la.setLocation(60,220);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchID.add(la);
		
		la = new JLabel("이메일");
		la.setSize(300,100);
		la.setLocation(60,370);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchID.add(la);
		
		textIDName.setSize(300,80);//아이디 입력 창
		textIDName.setLocation(220,230);
		textIDName.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchID.add(textIDName);
		
		textIDEmail.setSize(300,80);//아이디 입력 창
		textIDEmail.setLocation(220,380);
		textIDEmail.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchID.add(textIDEmail);
		
		btnSearchIDOk.setSize(150,50);//찾기버튼
		btnSearchIDOk.setLocation(300, 530);
		btnSearchIDOk.setBackground(colorBtn);
		btnSearchIDOk.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSearchIDOk.addActionListener(listener);
		panelSearchID.add(btnSearchIDOk);
	
		btnSearchIDNo.setSize(150,50);//취소버튼
		btnSearchIDNo.setLocation(110, 530);
		btnSearchIDNo.setBackground(colorBtn);
		btnSearchIDNo.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSearchIDNo.addActionListener(listener);
		panelSearchID.add(btnSearchIDNo);
		
		
		//비밀번호찾기
		panelSearchPIN.setLayout(null);
		panelSearchPIN.setBackground(colorMain); 
		
		la = new JLabel("비밀번호 찾기");
		la.setSize(300,100);
		la.setLocation(160,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchPIN.add(la);
		
		la = new JLabel("아이디");
		la.setSize(300,100);
		la.setLocation(60,220);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchPIN.add(la);
		
		la = new JLabel("이름");
		la.setSize(300,100);
		la.setLocation(60,330);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchPIN.add(la);
		
		la = new JLabel("이메일");
		la.setSize(300,100);
		la.setLocation(60,440);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchPIN.add(la);
		
		textPINID.setSize(300,80);//아이디 입력 창
		textPINID.setLocation(220,230);
		textPINID.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchPIN.add(textPINID);
		
		textPINName.setSize(300,80);//이름 입력 창
		textPINName.setLocation(220,340);
		textPINName.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchPIN.add(textPINName);
		
		textPINEmail.setSize(300,80);//이메일 입력 창
		textPINEmail.setLocation(220,450);
		textPINEmail.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSearchPIN.add(textPINEmail);
		
		btnSearchPINOk.setSize(150,50);//비밀번호 찾기버튼
		btnSearchPINOk.setLocation(300, 580);
		btnSearchPINOk.setBackground(colorBtn);
		btnSearchPINOk.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSearchPINOk.addActionListener(listener);
		panelSearchPIN.add(btnSearchPINOk);
		
		btnSearchPINNo.setSize(150,50);//비밀번호 취소버튼
		btnSearchPINNo.setLocation(110, 580);
		btnSearchPINNo.setBackground(colorBtn);
		btnSearchPINNo.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSearchPINNo.addActionListener(listener);
		panelSearchPIN.add(btnSearchPINNo);
		
		
		//회원가입 화면
		panelSignUp.setLayout(null);
		panelSignUp.setBackground(colorMain); 
		
		la = new JLabel("회원가입");
		la.setSize(300,100);
		la.setLocation(200,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(la);
		
		la = new JLabel("이름");
		la.setSize(300,100);
		la.setLocation(15,170);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(la);
		
		la = new JLabel("아이디");
		la.setSize(300,100);
		la.setLocation(15,270);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(la);
		
		la = new JLabel("비밀번호");
		la.setSize(300,100);
		la.setLocation(15,370);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(la);
		
		la = new JLabel("비밀번호확인");
		la.setSize(300,100);
		la.setLocation(15,470);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(la);
		
		la = new JLabel("이메일");
		la.setSize(300,100);
		la.setLocation(15,570);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(la);
		
		textSignName.setSize(300,80);//이름 입력 창
		textSignName.setLocation(270,180);
		textSignName.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(textSignName);
		
		textSignID.setSize(300,80);//아이디 입력 창
		textSignID.setLocation(270,280);
		textSignID.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(textSignID);
		
		textSignPIN.setSize(300,80);//비밀번호 입력 창
		textSignPIN.setLocation(270,380);
		textSignPIN.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(textSignPIN);
		
		textSignPINCheck.setSize(300,80);//비밀번호 확인 입력 창
		textSignPINCheck.setLocation(270,480);
		textSignPINCheck.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(textSignPINCheck);
		
		textSignEmail.setSize(300,80);//이메일 입력 창
		textSignEmail.setLocation(270,580);
		textSignEmail.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSignUp.add(textSignEmail);
		
		btnSignUpOk.setSize(150,50);//비밀번호 찾기버튼
		btnSignUpOk.setLocation(320, 720);
		btnSignUpOk.setBackground(colorBtn);
		btnSignUpOk.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSignUpOk.addActionListener(listener);
		panelSignUp.add(btnSignUpOk);
		
		btnSignUpNo.setSize(150,50);//비밀번호 취소버튼
		btnSignUpNo.setLocation(100, 720);
		btnSignUpNo.setBackground(colorBtn);
		btnSignUpNo.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSignUpNo.addActionListener(listener);
		panelSignUp.add(btnSignUpNo);
		

		//자리배정화면
		panelSeatManage.setLayout(null);
		panelSeatManage.setBackground(colorMain); 
		
		laSeat[0] = new JLabel("자리배정");
		laSeat[0].setSize(300,100);
		laSeat[0].setLocation(210,50);
		laSeat[0].setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSeatManage.add(laSeat[0]);
		
		laSeat[1] = new JLabel("자리변경");
		laSeat[1].setSize(300,100);
		laSeat[1].setLocation(210,50);
		laSeat[1].setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSeatManage.add(laSeat[1]);
		
		laSeat[2] = new JLabel("좌석조회");
		laSeat[2].setSize(300,100);
		laSeat[2].setLocation(210,50);
		laSeat[2].setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSeatManage.add(laSeat[2]);
		
		laSeat[0].setVisible(true);
		laSeat[1].setVisible(false);
		laSeat[2].setVisible(false);
		
		laSeatText[0] = new JLabel("이용가능");
		laSeatText[0].setSize(100,100);
		laSeatText[0].setLocation(480,-5);
		laSeatText[0].setFont(new Font("맑은고딕",Font.BOLD,10));
		panelSeatManage.add(laSeatText[0]);
		
		laSeatText[1] = new JLabel("이용중");
		laSeatText[1].setSize(100,100);
		laSeatText[1].setLocation(480,35);
		laSeatText[1].setFont(new Font("맑은고딕",Font.BOLD,10));
		panelSeatManage.add(laSeatText[1]);
		
		laSeatText[2] = new JLabel("나의 좌석");
		laSeatText[2].setSize(100,100);
		laSeatText[2].setLocation(480,75);
		laSeatText[2].setFont(new Font("맑은고딕",Font.BOLD,10));
		panelSeatManage.add(laSeatText[2]);
		
		btnSeatText[0]=new RectButton();
		btnSeatText[0].setSize(30,30);
		btnSeatText[0].setLocation(440, 30);
		btnSeatText[0].setBackground(colorBtn);
		btnSeatText[0].setEnabled(false);
		panelSeatManage.add(btnSeatText[0]);
		
		btnSeatText[1]=new RectButton();
		btnSeatText[1].setSize(30,30);
		btnSeatText[1].setLocation(440, 70);
		btnSeatText[1].setBackground(Color.red);
		btnSeatText[1].setEnabled(false);
		panelSeatManage.add(btnSeatText[1]);
		
		btnSeatText[2]=new RectButton();
		btnSeatText[2].setSize(30,30);
		btnSeatText[2].setLocation(440, 110);
		btnSeatText[2].setBackground(Color.green);
		btnSeatText[2].setEnabled(false);
		panelSeatManage.add(btnSeatText[2]);
		
		laSeatText[2].setVisible(false);
		btnSeatText[2].setVisible(false);
		
		for(int j=0; j<4; j++) {
			for(int i=0; i<5; i++) {
				btnSeat[i+(5*j)] = new RectButton(Integer.toString(i+1+(5*j)));
				btnSeat[i+(5*j)].setSize(70,70);
				if(j<2) {
					btnSeat[i+(5*j)].setLocation((100*j)+80, (120*i)+170);
				}
				else {
					btnSeat[i+(5*j)].setLocation((100*j)+130, (120*i)+170);
				}
				btnSeat[i+(5*j)].setBackground(colorBtn);
				btnSeat[i+(5*j)].setFont(new Font("맑은고딕",Font.BOLD,30));
				btnSeat[i+(5*j)].addActionListener(listener);
				panelSeatManage.add(btnSeat[i+(5*j)]);
			}
		}
		
		btnLogout[0] = new RoundedButton("로그아웃");
		btnLogout[0].setSize(100,40);//로그아웃 버튼
		btnLogout[0].setLocation(240, 800);
		btnLogout[0].setBackground(colorMain);
		btnLogout[0].setFont(new Font("맑은고딕",Font.BOLD,15));
		btnLogout[0].setBorderPainted(false);
		btnLogout[0].addActionListener(listener);
		panelSeatManage.add(btnLogout[0]);
		
		btnseatChangeNo[0]=new RoundedButton("취소");
		btnseatChangeNo[1]=new RoundedButton("이전");
		
		for(int i=0; i<=1; i++) {
		    btnseatChangeNo[i].setSize(150,50);
			btnseatChangeNo[i].setLocation(215, 780);
			btnseatChangeNo[i].setBackground(colorBtn);
			btnseatChangeNo[i].setFont(new Font("맑은고딕",Font.BOLD,30));
			btnseatChangeNo[i].addActionListener(listener);
			panelSeatManage.add(btnseatChangeNo[i]);
		}
		
		btnOutSeat.setSize(150,50);
		btnOutSeat.setLocation(320, 780);
		btnOutSeat.setBackground(colorBtn);
		btnOutSeat.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnOutSeat.addActionListener(listener);
		panelSeatManage.add(btnOutSeat);
		
		btnOutSeat.setVisible(false);
		btnLogout[0].setVisible(true);
		btnseatChangeNo[0].setVisible(false);
		btnseatChangeNo[1].setVisible(false);
		
		
		//자리배정후 화면
		panelSeatMain.setLayout(null);
		panelSeatMain.setBackground(colorMain); 
		
		la = new JLabel("현재좌석: ");
		la.setSize(300,100);
		la.setLocation(130,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSeatMain.add(la);
		
		textSeatNum.setSize(85,50);
		textSeatNum.setLocation(320,73);
		textSeatNum.setText("20번");
		textSeatNum.setBackground(colorMain);
		textSeatNum.setEditable(false);
		textSeatNum.setBorder(null);
		textSeatNum.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelSeatMain.add(textSeatNum);
		
		btnSeatMain[0] = new RectButton("좌석/변경");//좌석변경 버튼
		btnSeatMain[0].setSize(170,300);
		btnSeatMain[0].setLocation(15, 260);
		btnSeatMain[0].setBackground(colorBtn);
		btnSeatMain[0].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSeatMain[0].addActionListener(listener);
		panelSeatMain.add(btnSeatMain[0]);
		
		btnSeatMain[1] = new RectButton("음식/주문");//음식주문 버튼
		btnSeatMain[1].setSize(170,300);
		btnSeatMain[1].setLocation(207, 260);
		btnSeatMain[1].setBackground(colorBtn);
		btnSeatMain[1].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSeatMain[1].addActionListener(listener);
		panelSeatMain.add(btnSeatMain[1]);
		
		btnSeatMain[2] = new RectButton("퇴실");//퇴실버튼
		btnSeatMain[2].setSize(170,300);
		btnSeatMain[2].setLocation(400, 260);
		btnSeatMain[2].setBackground(colorBtn);
		btnSeatMain[2].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnSeatMain[2].addActionListener(listener);
		panelSeatMain.add(btnSeatMain[2]);
		
		btnLogout[1] = new RoundedButton("로그아웃");
		btnLogout[1].setSize(100,50);//로그아웃 버튼
		btnLogout[1].setLocation(240, 800);
		btnLogout[1].setBackground(colorMain);
		btnLogout[1].setFont(new Font("맑은고딕",Font.BOLD,15));
		btnLogout[1].setBorderPainted(false);
		btnLogout[1].addActionListener(listener);
		panelSeatMain.add(btnLogout[1]);
		
		
		//음식주문 화면
		panelOrderFood.setLayout(null);
		panelOrderFood.setBackground(colorMain); 
		
		la = new JLabel("음식주문");
		la.setSize(300,100);
		la.setLocation(200,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelOrderFood.add(la);
		
		btnOrderFood[0] = new RectButton("음료");//음식주문 버튼
		btnOrderFood[0].setSize(175,70);
		btnOrderFood[0].setLocation(30, 160);
		btnOrderFood[0].setBackground(colorBtn);
		btnOrderFood[0].setBorderPainted(false);
		btnOrderFood[0].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnOrderFood[0].addActionListener(listener);
		panelOrderFood.add(btnOrderFood[0]);
		
		btnOrderFood[1] = new RectButton("식사류");//음식주문 버튼
		btnOrderFood[1].setSize(180,70);
		btnOrderFood[1].setLocation(205, 160);
		btnOrderFood[1].setBackground(colorMain);
		btnOrderFood[1].setBorderPainted(false);
		btnOrderFood[1].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnOrderFood[1].addActionListener(listener);
		panelOrderFood.add(btnOrderFood[1]);
		
		btnOrderFood[2] = new RectButton("간식류");//음식주문 버튼
		btnOrderFood[2].setSize(175,70);
		btnOrderFood[2].setLocation(385, 160);
		btnOrderFood[2].setBackground(colorMain);
		btnOrderFood[2].setBorderPainted(false);
		btnOrderFood[2].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnOrderFood[2].addActionListener(listener);
		panelOrderFood.add(btnOrderFood[2]);
		
				
		for(int i=0; i<3; i++) {
			panelOrderMenu[i] = new JPanel();
			panelOrderMenu[i].setLayout(null);
			panelOrderMenu[i].setPreferredSize(new Dimension(500, 450));//이 숫자로 스크롤 길이를 조절
			panelOrderMenu[i].setBackground(colorBtn);
		}
		
		
		for(int i=0; i<3; i++) {//스크롤팬에 메뉴패널 붙이기
			scrFood[i] = new JScrollPane(panelOrderMenu[i], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrFood[i].setBounds(31, 230, 530, 450);
			panelOrderFood.add(scrFood[i]);
		}
		
		btnFoodOk[0]=new RoundedButton("주문");
		btnFoodOk[0].setSize(150,50);
		btnFoodOk[0].setLocation(320, 750);
		btnFoodOk[0].setBackground(colorBtn);
		btnFoodOk[0].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnFoodOk[0].addActionListener(listener);
		panelOrderFood.add(btnFoodOk[0]);
		
		btnFoodNo[0]=new RoundedButton("취소");
		btnFoodNo[0].setSize(150,50);
		btnFoodNo[0].setLocation(120, 750);
		btnFoodNo[0].setBackground(colorBtn);
		btnFoodNo[0].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnFoodNo[0].addActionListener(listener);
		panelOrderFood.add(btnFoodNo[0]);
		
		cart.setSize(70,70);
		cart.setLocation(440,50);
		cart.setFocusPainted(false);
		cart.setContentAreaFilled(false);
		cart.setBorderPainted(false);
		cart.addActionListener(listener);
		panelOrderFood.add(cart);
		
		cartNum.setSize(50,50);
		cartNum.setLocation(500,30);
		cartNum.setEnabled(false);
		cartNum.setFont(new Font("맑은고딕",Font.BOLD,30));
		cartNum.setText(Integer.toString(count));
		panelOrderFood.add(cartNum);
		

		//음식주문(장바구니)
		panelCart.setLayout(null);
		panelCart.setBackground(colorMain); 
				
		la = new JLabel("음식주문 목록");
		la.setSize(400,100);
		la.setLocation(160,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelCart.add(la);
				
		panelOrderList.setLayout(null);
		panelOrderList.setPreferredSize(new Dimension(500, 450));//이 숫자로 스크롤 길이를 조절
		panelOrderList.setBackground(colorBtn);
		
		JScrollPane scrOrder = new JScrollPane(panelOrderList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrOrder.setBounds(31, 160, 530, 550);
		panelCart.add(scrOrder);
				
		btnFoodOk[1]=new RoundedButton("주문");
		btnFoodOk[1].setSize(150,50);
		btnFoodOk[1].setLocation(390, 750);
		btnFoodOk[1].setBackground(colorBtn);
		btnFoodOk[1].setFont(new Font("맑은고딕",Font.BOLD,30));
	    btnFoodOk[1].addActionListener(listener);
		panelCart.add(btnFoodOk[1]);
		
		btnDelete.setSize(150,50);
		btnDelete.setLocation(215, 750);
		btnDelete.setBackground(colorBtn);
		btnDelete.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnDelete.addActionListener(listener);
		panelCart.add(btnDelete);
				
		btnFoodNo[1]=new RoundedButton("이전");
		btnFoodNo[1].setSize(150,50);
		btnFoodNo[1].setLocation(40,750);
		btnFoodNo[1].setBackground(colorBtn);
		btnFoodNo[1].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnFoodNo[1].addActionListener(listener);
		panelCart.add(btnFoodNo[1]);
				
				
				
		//관리자 메인 화면
		panelAdminMain.setLayout(null);
		panelAdminMain.setBackground(colorMain); 
		
		la = new JLabel("스터디 카페 관리");
		la.setSize(400,100);
		la.setLocation(140,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelAdminMain.add(la);
		
		btnAdminMain[0] = new RectButton("좌석/조회");//좌석변경 버튼
		btnAdminMain[0].setSize(170,300);
		btnAdminMain[0].setLocation(15, 260);
		btnAdminMain[0].setBackground(colorBtn);
		btnAdminMain[0].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnAdminMain[0].addActionListener(listener);
		panelAdminMain.add(btnAdminMain[0]);
		
		btnAdminMain[1] = new RectButton("금액/조회");//음식주문 버튼
		btnAdminMain[1].setSize(170,300);
		btnAdminMain[1].setLocation(205, 260);
		btnAdminMain[1].setBackground(colorBtn);
		btnAdminMain[1].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnAdminMain[1].addActionListener(listener);
		panelAdminMain.add(btnAdminMain[1]);
		
		btnAdminMain[2] = new RectButton("음식/추가");//퇴실버튼
		btnAdminMain[2].setSize(170,300);
		btnAdminMain[2].setLocation(400, 260);
		btnAdminMain[2].setBackground(colorBtn);
		btnAdminMain[2].setFont(new Font("맑은고딕",Font.BOLD,30));
		btnAdminMain[2].addActionListener(listener);
		panelAdminMain.add(btnAdminMain[2]);
		
		btnLogout[2] = new RoundedButton("로그아웃");
		btnLogout[2].setSize(100,50);//로그아웃 버튼
		btnLogout[2].setLocation(240, 800);
		btnLogout[2].setBackground(colorMain);
		btnLogout[2].setFont(new Font("맑은고딕",Font.BOLD,15));
		btnLogout[2].setBorderPainted(false);
		btnLogout[2].addActionListener(listener);
		panelAdminMain.add(btnLogout[2]);
		
		
		//금액조회(관리자용)
		panelAdminCost.setLayout(null);
		panelAdminCost.setBackground(colorMain); 
		
		la = new JLabel("금액조회");
		la.setSize(400,100);
		la.setLocation(210,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelAdminCost.add(la);
		
		
		
		btnAdminNo.setSize(150,50);//취소버튼
		btnAdminNo.setLocation(215, 780);
		btnAdminNo.setBackground(colorBtn);
		btnAdminNo.setFont(new Font("맑은고딕",Font.BOLD,30));
		btnAdminNo.addActionListener(listener);
		panelAdminCost.add(btnAdminNo);
		
		
		
		//음식추가(관리자)
		panelAdminFoodAdd.setLayout(null);
		panelAdminFoodAdd.setBackground(colorMain); 
		
		la = new JLabel("음식추가");
		la.setSize(400,100);
		la.setLocation(210,50);
		la.setFont(new Font("맑은고딕",Font.BOLD,40));
		panelAdminFoodAdd.add(la);
		
		la = new JLabel("구분");
		la.setSize(400,100);
		la.setLocation(60,200);
		la.setFont(new Font("맑은고딕",Font.BOLD,30));
		panelAdminFoodAdd.add(la);
		
		la = new JLabel("음식명");
		la.setSize(400,100);
		la.setLocation(60,300);
		la.setFont(new Font("맑은고딕",Font.BOLD,30));
		panelAdminFoodAdd.add(la);
		
		la = new JLabel("가격");
		la.setSize(400,100);
		la.setLocation(60,400);
		la.setFont(new Font("맑은고딕",Font.BOLD,30));
		panelAdminFoodAdd.add(la);
		
		la = new JLabel("사진추가");
		la.setSize(400,100);
		la.setLocation(60,500);
		la.setFont(new Font("맑은고딕",Font.BOLD,30));
		panelAdminFoodAdd.add(la);
		
	    
	    btnAdd[0] = new RoundedButton("취소");
	    btnAdd[1] = new RoundedButton("추가");
	   	
	    for(int i=0; i<2; i++) {
	    	btnAdd[i].setSize(150,50);
	    	btnAdd[i].setLocation(110+(i*220), 700);
	    	btnAdd[i].setBackground(colorBtn);
	    	btnAdd[i].setFont(new Font("맑은고딕",Font.BOLD,30));
	    	btnAdd[i].addActionListener(listener);
	    	panelAdminFoodAdd.add(btnAdd[i]);
	    }
		
		
		//패널 추가
		c.add(panelMain);
		//c.add(panelLogin);
		//c.add(panelSearchID);
		//c.add(panelSearchPIN);
		//c.add(panelSignUp);
		//c.add(panelSeatManage);
		//c.add(panelSeatMain);
		//c.add(panelOrderFood);
		//c.add(panelCart);
		//c.add(panelAdminMain);
		//c.add(panelAdminCost);
		//c.add(panelAdminFoodAdd);
		
		panelLogin.setVisible(true);
		panelMain.setVisible(true);
		//this.setVisible(true);
		
		
		setSize(600, 900);
		setVisible(true);
	}
	
	class MyActionListener implements ActionListener {

		UserDB userdb = new UserDB();//사용자 데이터베이스 
		FoodDB fooddb = new FoodDB();//음식 데이터베이스
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//메인화면버튼
			if(e.getSource()==btnLogin) {
				System.out.println("메인창에서 로그인");
				getContentPane().removeAll();
				c.add(panelLogin);
				textID.setText("");
				textPIN.setText("");
				revalidate();
	            repaint();
			}
			if(e.getSource()==btnRegister) {
				System.out.println("메인창에서 회원가입");
				getContentPane().removeAll();
				c.add(panelSignUp);
				
				textSignName.setText("");
				textSignID.setText("");
				textSignPIN.setText("");
				textSignPINCheck.setText("");
				textSignEmail.setText("");
				
				revalidate();
	            repaint();
			}
			
			//로그인화면버튼
			if(e.getSource()==btnLoginInput) {
				String id = textID.getText().replaceAll(" ", "");
				String pin = textPIN.getText().replaceAll(" ", "");
				if(id.length()==0) {
					//팝업->아이디를 입력하세요.
					JOptionPane.showMessageDialog(c,"아이디를 입력하세요.", "로그인",JOptionPane.WARNING_MESSAGE);
				}
				else if(pin.length()==0) {
					//팝업->비밀번호를 입력하세요.
					JOptionPane.showMessageDialog(c,"비밀번호를 입력하세요.", "로그인",JOptionPane.WARNING_MESSAGE);
				}
				else {
					if(id.equals("admin") && pin.equals("admin8")) {
						getContentPane().removeAll();
						c.add(panelAdminMain);
						revalidate();
			            repaint();
					}
					else {
						if(userdb.select(id, pin)==true) {//로그인 성공
							userID=id;
							System.out.println("유저아이디 "+userID);
							getContentPane().removeAll();
							int num = userdb.searchUser(id);
							if(num==0) {
								c.add(panelSeatManage);
								laSeat[0].setVisible(true);
								laSeat[1].setVisible(false);
								laSeat[2].setVisible(false);
								
								for(int i=1; i<=20; i++) {//전체좌석
									btnSeat[i-1].setBackground(colorBtn);
									btnSeat[i-1].setEnabled(true);
							     }
								
								userdb.seat();
								for(int i=0; i<userdb.seat.size(); i++) {//이용중인 자리
									  btnSeat[userdb.seat.get(i)-1].setBackground(Color.red);
									  btnSeat[userdb.seat.get(i)-1].setEnabled(false);
									  
							    }
								btnLogout[0].setVisible(true);
								btnseatChangeNo[0].setVisible(false);
								btnseatChangeNo[1].setVisible(false);
							}
							else {
								c.add(panelSeatMain);
								seatNum=num;
								textSeatNum.setText(seatNum+"번");
							}
							revalidate();
				            repaint();
						}
						else {//로그인 실패
							//실패했다는 팝업 띄우기
							JOptionPane.showMessageDialog(c," 로그인을 실패하였습니다.", "로그인",JOptionPane.WARNING_MESSAGE);
						}
					}
				}
				
				
			}
			
			if(e.getSource()==btnLoginNo) {
				textID.setText("");
				textPIN.setText("");
				
				getContentPane().removeAll();
				c.add(panelMain);
				revalidate();
	            repaint();
			}
			if(e.getSource()==btnSearchID) {//로그인창에서 아이디찾기
				getContentPane().removeAll();
				c.add(panelSearchID);
				textIDName.setText("");
				textIDEmail.setText("");
				revalidate();
	            repaint();
			}
			if(e.getSource()==btnSearchPIN) {//로그인창에서 비밀번호찾기
				getContentPane().removeAll();
				c.add(panelSearchPIN);
				revalidate();
	            repaint();
			}
			
			//아이디찾기화면
			if(e.getSource()==btnSearchIDOk) {
				String name = textIDName.getText().replaceAll(" ", "");
				String email = textIDEmail.getText().replaceAll(" ", "");
				if(name.length()==0) {
					//팝업->이름을 입력하세요.
					JOptionPane.showMessageDialog(c,"아이디를 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(email.length()==0) {
					//팝업->이메일을 입력하세요.
					JOptionPane.showMessageDialog(c,"이메일을 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")==false) {
					//팝업->이메일 형식이 올바르지 않습니다.
					JOptionPane.showMessageDialog(c,"이메일 형식이 올바르지 않습니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String id = userdb.searchID(name, email);
					if(id=="0") {
						JOptionPane.showMessageDialog(c," 아이디가 존재하지 않습니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(c,"아이디는 "+id+"입니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
						textIDName.setText("");
						textIDEmail.setText("");
						getContentPane().removeAll();
						c.add(panelLogin);
						revalidate();
			            repaint();
					}
				}
				
			}
			if(e.getSource()==btnSearchIDNo) {//아이디찾기에서 로그인화면으로
				textIDName.setText("");
				textIDEmail.setText("");
				
				textID.setText("");
				textPIN.setText("");
				
				getContentPane().removeAll();
				c.add(panelLogin);
				revalidate();
	            repaint();
			}
			
			//비밀번호찾기화면
			if(e.getSource()==btnSearchPINOk) {
				String id = textPINID.getText().replaceAll(" ", "");
				String name = textPINName.getText().replaceAll(" ", "");
				String email = textPINEmail.getText().replaceAll(" ", "");
				
				if(id.length()==0) {
					//팝업->이름을 입력하세요.
					JOptionPane.showMessageDialog(c,"  아이디를 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(name.length()==0) {
					//팝업->이름을 입력하세요.
					JOptionPane.showMessageDialog(c,"  이름을 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(email.length()==0) {
					//팝업->이메일을 입력하세요.
					JOptionPane.showMessageDialog(c,"  이메일을 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")==false) {
					//팝업->이메일 형식이 올바르지 않습니다.
					JOptionPane.showMessageDialog(c,"이메일 형식이 올바르지 않습니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String pin = userdb.searchPIN(id,name,email);
					if(pin=="0") {
						JOptionPane.showMessageDialog(c," 해당되는 비밀번호를 찾을 수 없습니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(c,"비밀번호는 "+pin+"입니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
						textPINID.setText("");
						textPINName.setText("");
						textPINEmail.setText("");
						getContentPane().removeAll();
						c.add(panelLogin);
						revalidate();
			            repaint();
					}
				}
				
			}
			if(e.getSource()==btnSearchPINNo) {//비밀번호찾기에서 로그인화면으로
				textPINID.setText("");
				textPINName.setText("");
				textPINEmail.setText("");
				
				textID.setText("");
				textPIN.setText("");
				
				getContentPane().removeAll();
				c.add(panelLogin);
				revalidate();
	            repaint();
			}
			
			//회원가입화면
			if(e.getSource()==btnSignUpOk) {
				String id = textSignID.getText().replaceAll(" ", "");
				String name = textSignName.getText().replaceAll(" ", "");
				String pin = textSignPIN.getText().replaceAll(" ", "");
				String pinCheck = textSignPINCheck.getText().replaceAll(" ", "");
				String email = textSignEmail.getText().replaceAll(" ", "");

				
				if(name.length()==0) {
					//팝업->아이디를 입력하세요.
					JOptionPane.showMessageDialog(c,"이름을 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(id.length()==0) {
					//팝업->이름을 입력하세요.
					JOptionPane.showMessageDialog(c,"아이디를 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(pin.length()==0) {
					//팝업->비밀번호를 입력하세요.
					JOptionPane.showMessageDialog(c,"비밀번호를 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(email.length()==0) {
					//팝업->이메일을 입력하세요.
					JOptionPane.showMessageDialog(c,"이메일을 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(pinCheck.length()==0) {
					//팝업->비밀번호를 입력하세요.
					JOptionPane.showMessageDialog(c,"비밀번호확인을 입력하세요.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(id.length()<5) {
					//팝업->아이디는 5글자 이상이어야 합니다.
					JOptionPane.showMessageDialog(c,"아이디는 5글자 이상이어야 합니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(id.length()>10) {
					//팝업->아이디는 10글자까지만 가능합니다.
					JOptionPane.showMessageDialog(c,"아이디는 10글자까지만 가능합니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(id.matches("^[a-zA-Z0-9]*$")==false){
					//팝업->아이디는 영어 또는 숫자로 이루어져야 합니다.
					JOptionPane.showMessageDialog(c,"아이디는 영어 또는 숫자로 이루어져야 합니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(pin.length()<5) {
					//팝업->비밀번호는 10글자까지만 가능합니다.
					JOptionPane.showMessageDialog(c,"비밀번호는 10글자까지만 가능합니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(pin.matches("^[a-zA-Z0-9]*$")==false){
					//팝업->비밀번호는 영어 또는 숫자로 이루어져야 합니다.
					JOptionPane.showMessageDialog(c,"비밀번호는 영어 또는 숫자로 이루어져야 합니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(pin.matches(pinCheck)==false) {
					//팝업->비밀번호가 일치하지 않습니다.
					JOptionPane.showMessageDialog(c,"비밀번호가 일치하지 않습니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")==false) {
					//팝업->이메일 형식이 올바르지 않습니다.
					JOptionPane.showMessageDialog(c,"이메일 형식이 올바르지 않습니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(userdb.checkID(id)==false) {
					//팝업->이미 존재하는 아이디입니다.
					JOptionPane.showMessageDialog(c,"이미 존재하는 아이디입니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else if(userdb.checkEmail(email)==false) {
					//팝업->이미 존재하는 이메일입니다.
					JOptionPane.showMessageDialog(c,"이미 존재하는 이메일입니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
				}
				else {
					userdb.input(id, name, pin, email);
					JOptionPane.showMessageDialog(c,"회원가입이 완료되었습니다.", "회원가입",JOptionPane.WARNING_MESSAGE);
					getContentPane().removeAll();
					c.add(panelLogin);
					revalidate();
		            repaint();
				}
		
			}
			if(e.getSource()==btnSignUpNo) {//로그인화면으로 돌아가기
				textSignName.setText("");
				textSignID.setText("");
				textSignPIN.setText("");
				textSignPINCheck.setText("");
				textSignEmail.setText("");
				
				getContentPane().removeAll();
				c.add(panelMain);
				revalidate();
	            repaint();
			}
			
			//자리배정화면
			for(int i=0; i<20; i++) {
				if(e.getSource()==btnSeat[i]) {
					int result=0;
					if(laSeat[0].isVisible()==true) {
						result=JOptionPane.showConfirmDialog(c,"  "+(i+1)+"번을 선택하시겠습니까?","좌석배정",JOptionPane.OK_CANCEL_OPTION);
					}
					else if(laSeat[1].isVisible()==true) {
						result=JOptionPane.showConfirmDialog(c,"  "+(i+1)+"번으로 변경하시겠습니까?","좌석변경",JOptionPane.OK_CANCEL_OPTION);
					}
					
	            	if(result==0) {
						seatNum = Integer.parseInt(btnSeat[i].getText());
						userdb.seatManage(userID, seatNum);
						
						getContentPane().removeAll();
						c.add(panelSeatMain);
						textSeatNum.setText(seatNum+"번");
						
						revalidate();
			            repaint();
	            	}
	            	else {
	            		
	            	}
					
					
				}
			}
			if(e.getSource()==btnLogout[0]) {
				getContentPane().removeAll();
				c.add(panelMain);
				revalidate();
	            repaint();
			}
			if(e.getSource()==btnseatChangeNo[0]){//좌석변경화면에서 취소버튼
				getContentPane().removeAll();
				c.add(panelSeatMain);
				revalidate();
	            repaint();
			}
			
			//자리배정후화면
			if(e.getSource()==btnSeatMain[0]) {//좌석변경
				getContentPane().removeAll();
				c.add(panelSeatManage);
				revalidate();
	            repaint();
				laSeat[0].setVisible(false);
				laSeat[1].setVisible(true);
				laSeat[2].setVisible(false);
				
				for(int i=1; i<=20; i++) {//전체좌석
						btnSeat[i-1].setBackground(colorBtn);
						btnSeat[i-1].setEnabled(true);
				}
				
				userdb.seat();
				for(int i=0; i<userdb.seat.size(); i++) {//이용중인 자리
					  btnSeat[userdb.seat.get(i)-1].setBackground(Color.red);
					  btnSeat[userdb.seat.get(i)-1].setEnabled(false);
			    }
				
				btnSeat[seatNum-1].setBackground(Color.GREEN);//나의 좌석
				
				btnLogout[0].setVisible(false);
				btnseatChangeNo[0].setVisible(true);//취소버튼(이용자)
				btnseatChangeNo[1].setVisible(false);//이전버튼(관리자)
				
				laSeatText[2].setVisible(true);//나의좌석 문구
				btnSeatText[2].setVisible(true);//나의좌석 아이콘
				
			}
			else if(e.getSource()==btnSeatMain[1]) {//음식주문
				getContentPane().removeAll();
				c.add(panelOrderFood);
				scrFood[0].setVisible(true);
				scrFood[1].setVisible(false);
				scrFood[2].setVisible(false);
				btnOrderFood[0].setBackground(colorBtn);
				btnOrderFood[1].setBackground(colorMain);
				btnOrderFood[2].setBackground(colorMain);
				
				count=0;
				id.clear();
				cost.clear();
				name.clear();
				amount.clear();
				cartNum.setText(Integer.toString(count));
				
				Order();
				
				revalidate();
	    		repaint();
			}
			
			else if(e.getSource()==btnSeatMain[2]) {//퇴실
				panelSeatManage.setVisible(false);
				userdb.total(userID);
				String time = "";
				
				if(userdb.Hours==0) {
					time=userdb.Minutes+"분 "+userdb.Seconds+"초";
				}
				else if(userdb.Hours==0&&userdb.Minutes==0) {
					time=userdb.Seconds+"초";
				}
				else {
					time=userdb.Hours+"시간 "+userdb.Minutes+"분 "+userdb.Seconds+"초";
				}
				
				int result=JOptionPane.showConfirmDialog(c, "<html><body><center> 총 이용시간은 "+time+"입니다.<br>"+
						"이용시간요금은 "+userdb.total+"원 입니다.<br> 퇴실하겠습니까?</center></body></html>","퇴실",JOptionPane.OK_CANCEL_OPTION);
				if(result==0) {
					userdb.calculate(userID);
					userdb.seatManage(userID,0);
					getContentPane().removeAll();
					panelSeatManage.setVisible(true);
					c.add(panelSeatManage);
					
					laSeat[0].setVisible(true);
					laSeat[1].setVisible(false);
					laSeat[2].setVisible(false);
					
					for(int i=1; i<=20; i++) {//전체좌석
						btnSeat[i-1].setBackground(colorBtn);
				    } 
				
					userdb.seat();
					for(int i=0; i<userdb.seat.size(); i++) {//이용중인 자리
						  btnSeat[userdb.seat.get(i)-1].setBackground(Color.red);
						  btnSeat[userdb.seat.get(i)-1].setEnabled(false);
				    }
					
				    btnLogout[0].setVisible(true);
					btnseatChangeNo[0].setVisible(false);
					btnseatChangeNo[1].setVisible(false);
				    laSeatText[2].setVisible(false);
					btnSeatText[2].setVisible(false);
					
					revalidate();
		            repaint();
				}
				else {}
				
			}
			
			if(e.getSource()==btnLogout[1]) {
				getContentPane().removeAll();
				c.add(panelMain);
				revalidate();
	            repaint();
			}
			
			//음식주문화면
			if(e.getSource()==btnOrderFood[0]) {
					scrFood[0].setVisible(true);
					scrFood[1].setVisible(false);
					scrFood[2].setVisible(false);
					btnOrderFood[0].setBackground(colorBtn);
					btnOrderFood[1].setBackground(colorMain);
					btnOrderFood[2].setBackground(colorMain);
			}
			else if(e.getSource()==btnOrderFood[1]) {
				scrFood[0].setVisible(false);
				scrFood[1].setVisible(true);
				scrFood[2].setVisible(false);
				btnOrderFood[0].setBackground(colorMain);
				btnOrderFood[1].setBackground(colorBtn);
				btnOrderFood[2].setBackground(colorMain);
			}
			else if(e.getSource()==btnOrderFood[2]) {
				scrFood[0].setVisible(false);
				scrFood[1].setVisible(false);
				scrFood[2].setVisible(true);
				btnOrderFood[0].setBackground(colorMain);
				btnOrderFood[1].setBackground(colorMain);
				btnOrderFood[2].setBackground(colorBtn);
			}
			
			
			if(e.getSource()==btnFoodOk[0]) {
				int sum=0;
				for(int i=0; i<id.size();i++) {
					sum+=cost.get(i)*amount.get(i);
				}
            	int result=JOptionPane.showConfirmDialog(c,"<html><body><center> 총 주문금액은 "+sum+"입니다.<br>"+" 주문하시겠습니까?</center></body></html>","주문",JOptionPane.OK_CANCEL_OPTION);
            	if(result==0) {
    	            
    				userdb.userOrder(userID,sum);
    				
    				id.clear();
     	            amount.clear();
     	            name.clear();
     	            cost.clear();
     	            count=0;
     	            
    				getContentPane().removeAll();
    				c.add(panelSeatMain);
    				cartNum.setText(Integer.toString(count));
    				revalidate();
    	            repaint();
            	}
            	else {
            		
            	}
			}
			
			if(e.getSource()==btnFoodNo[0]) {
				getContentPane().removeAll();
				c.add(panelSeatMain);
				revalidate();
	            repaint();
			}
			if(e.getSource()==cart) {//음식주문화면에서 장바구니 클릭
				getContentPane().removeAll();
				c.add(panelCart);
				panelOrderList.removeAll();
				Cart();

				revalidate();
	            repaint();
			}
			
			
			//장바구니
            if(e.getSource()==btnFoodNo[1]) {//장바구니에서 이전버튼
            	getContentPane().removeAll();
				c.add(panelOrderFood);
				cartNum.setText(Integer.toString(count));
				revalidate();
	            repaint();
			}
            
			if(e.getSource()==btnDelete) {//장바구니에서 삭제버튼
				for(int i=0; i<laTrue.length; i++) {
					if(laTrue[i].isVisible()==true) {
						count=count-amount.get(i);
						id.remove(i);
						cost.remove(i);
						name.remove(i);
						amount.remove(i);
					}
				}
				
				panelOrderList.removeAll();
				Cart();
				revalidate();
	            repaint();
			}
			
            if(e.getSource()==btnFoodOk[1]) {//장바구니에서 주문버튼
            	int sum=0;
				for(int i=0; i<id.size();i++) {
					sum+=cost.get(i)*amount.get(i);
				}
            	int result=JOptionPane.showConfirmDialog(c,"<html><body><center> 총 주문금액은 "+sum+"입니다.<br>"+" 주문하시겠습니까?</center></body></html>","주문",JOptionPane.OK_CANCEL_OPTION);
            	if(result==0) {
    	            
    				userdb.userOrder(userID,sum);
    				
    				id.clear();
     	            amount.clear();
     	            name.clear();
     	            cost.clear();
     	            count=0;
     	            
     	            getContentPane().removeAll();
				    c.add(panelSeatMain);
				    cartNum.setText(Integer.toString(count));
				    revalidate();
	                repaint();
	                System.out.println(result);
     	            
            	}
            	else {
            		
            	}
            	
            	  
            	
            	
			}
			
			
			//관리자 메인 화면
			if(e.getSource()==btnLogout[2]) {
				getContentPane().removeAll();
				c.add(panelMain);
				revalidate();
	            repaint();
			}
			if(e.getSource()==btnAdminMain[0]) {//좌석조회
				getContentPane().removeAll();
				c.add(panelSeatManage);
				laSeat[0].setVisible(false);
				laSeat[1].setVisible(false);
				laSeat[2].setVisible(true);
				
				 btnLogout[0].setVisible(false);
				 btnseatChangeNo[0].setVisible(false);
				 btnseatChangeNo[1].setVisible(true);
				 btnseatChangeNo[1].setLocation(110, 780);
				 laSeatText[2].setVisible(false);
			     btnSeatText[2].setVisible(false);
			     btnOutSeat.setVisible(true);
			     
			     for(int i=0; i<20; i++) {
						  btnSeat[i].setEnabled(false);
						  btnSeat[i].setBackground(colorBtn);
				 }
			 
			     
			     userdb.seat();
				 for(int i=0; i<userdb.seat.size(); i++) {//이용중인 자리
						  btnSeat[userdb.seat.get(i)-1].setBackground(Color.red);
						  btnSeat[userdb.seat.get(i)-1].setEnabled(false);
				 }
				 
				 int seatY=userdb.seat.size();
				 int seatN=20-seatY;
				 la=new JLabel("이용중인좌석: "+seatY+"    사용가능좌석: "+seatN);
				 la.setSize(500,50);
				 la.setLocation(160, 725);
				 la.setFont(new Font("맑은고딕",Font.BOLD,15));
				 panelSeatManage.add(la);
				 revalidate();
	             repaint();
			}
			else if(e.getSource()==btnAdminMain[1]) {//금액조회
				getContentPane().removeAll();
				c.add(panelAdminCost);
				userdb.admin_fee();
				
				laCost[0]=new JLabel("음식금액: "+userdb.food_fee+"원");
				laCost[0].setSize(570,100);
				laCost[0].setLocation(10,220);
				laCost[0].setHorizontalAlignment(JLabel.CENTER);
				laCost[0].setFont(new Font("맑은고딕",Font.BOLD,30));
				panelAdminCost.add(laCost[0]);
				
				laCost[1]=new JLabel("좌석금액: "+userdb.time_fee+"원");
				laCost[1].setSize(570,100);
				laCost[1].setLocation(10,370);
				laCost[1].setHorizontalAlignment(JLabel.CENTER);
				laCost[1].setFont(new Font("맑은고딕",Font.BOLD,30));
				panelAdminCost.add(laCost[1]);
				
				laCost[2]=new JLabel("총수익: "+userdb.total_fee+"원");
				laCost[2].setSize(570,100);
				laCost[2].setLocation(10,520);
				laCost[2].setHorizontalAlignment(JLabel.CENTER);
				laCost[2].setFont(new Font("맑은고딕",Font.BOLD,30));
				panelAdminCost.add(laCost[2]);
				
				revalidate();
	            repaint();
			}
            else if(e.getSource()==btnAdminMain[2]) {//음식추가
            	getContentPane().removeAll();
				c.add(panelAdminFoodAdd);
				
				comboFoodAdd.setSize(300,50);
				comboFoodAdd.setLocation(220, 225);
				comboFoodAdd.setFont(new Font("맑은고딕",Font.BOLD,30));
				comboFoodAdd.setBackground(Color.WHITE);
				panelAdminFoodAdd.add(comboFoodAdd);
				
				for(int i=0; i<2; i++) {
					textFoodAdd[i] = new JTextField();
					textFoodAdd[i].setSize(300,50);
					textFoodAdd[i].setLocation(220, 325+(i*100));
					textFoodAdd[i].setFont(new Font("맑은고딕",Font.BOLD,30));
					panelAdminFoodAdd.add(textFoodAdd[i]);
				}
				
				btnAddIcon.setSize(300,50);
				btnAddIcon.setLocation(220, 525);
				btnAddIcon.setFont(new Font("맑은고딕",Font.BOLD,25));
				btnAddIcon.setBackground(Color.LIGHT_GRAY);
				btnAddIcon.addActionListener(new OpenActionListener());
				panelAdminFoodAdd.add(btnAddIcon);
				
			    icon.setSize(120,120);
			    icon.setLocation(220, 525);
			    icon.addActionListener(new OpenActionListener());
			    panelAdminFoodAdd.add(icon);
			   
			    btnAddIcon.setVisible(true);
			    icon.setVisible(false);
				revalidate();
	    		repaint();
			}
			
			
			//관리자 좌석조회 화면
			if(e.getSource()==btnseatChangeNo[1]) {//이전
				getContentPane().removeAll();
				c.add(panelAdminMain);
				panelSeatManage.remove(la);
				btnOutSeat.setVisible(false);
				revalidate();
	            repaint();
			}
			else if(e.getSource()==btnOutSeat) {//전체퇴실
				userdb.reset();
			
				if(userdb.resetID.size()==0) {
					JOptionPane.showMessageDialog(c,"<html><center>사용자가 없습니다.</center></html>", "전체퇴실",JOptionPane.WARNING_MESSAGE);
				}
				else {
					String out = "<html>";
					for(int i=0; i<userdb.resetID.size(); i++) {
						out+=userdb.resetID.get(i)+"님을 "+String.valueOf(userdb.resetSeat.get(i))+"번에서 퇴실시켰습니다.<br>"
								+ "이용시간은 "+userdb.resetTime.get(i)+"입니다.<br>총 이용금액은 "+String.valueOf(userdb.resetCost.get(i))+"원입니다.<br><br>";
					}
					out+="</html>";
					System.out.println(out);
					JOptionPane.showMessageDialog(c,out, "전체퇴실",JOptionPane.WARNING_MESSAGE);
				}
	            
				for(int i=1; i<=20; i++) {//전체좌석
					btnSeat[i-1].setBackground(colorBtn);
			    }
				
				userdb.seat();
				for(int i=0; i<userdb.seat.size(); i++) {//이용중인 자리
					  btnSeat[userdb.seat.get(i)-1].setBackground(Color.red);
			    }
				panelSeatManage.remove(la);
				
				getContentPane().removeAll();
				c.add(panelSeatManage);
				
				int seatY=userdb.seat.size();
				int seatN=20-seatY;
				la=new JLabel("이용중인좌석: "+seatY+"    사용가능좌석: "+seatN);
			    la.setSize(500,50);
				la.setLocation(160, 725);
				la.setFont(new Font("맑은고딕",Font.BOLD,15));
				panelSeatManage.add(la);
				
				revalidate();
	            repaint();
			}
			
			
			//관리자 금액조회 화면
			if(e.getSource()==btnAdminNo) {
				getContentPane().removeAll();
				c.add(panelAdminMain);
				panelAdminCost.remove(laCost[0]);
				panelAdminCost.remove(laCost[1]);
				panelAdminCost.remove(laCost[2]);
				revalidate();
	            repaint();
			}
			
			//관리자 음식추가 화면
			if(e.getSource()==btnAdd[0]) {//취소버튼
				getContentPane().removeAll();
				c.add(panelAdminMain);
				panelAdminFoodAdd.remove(comboFoodAdd);
				panelAdminFoodAdd.remove(textFoodAdd[0]);
				panelAdminFoodAdd.remove(textFoodAdd[1]);
				panelAdminFoodAdd.remove(btnAddIcon);
				panelAdminFoodAdd.remove(icon);
				
				revalidate();
	            repaint();
			}
			
			if(e.getSource()==btnAdd[1]) {//추가버튼
				
				String name = textFoodAdd[0].getText().replaceAll(" ", "");
				String cost = textFoodAdd[1].getText().replaceAll(" ", "");
				String url = strAddUrl.replaceAll(" ", "");
				
				if(name.length()==0) {
					//팝업->음식명을 입력하세요.
					JOptionPane.showMessageDialog(c,"음식명을 입력하세요.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else if(cost.length()==0) {
					//팝업->가격을 입력하세요.
					JOptionPane.showMessageDialog(c,"가격을 입력하세요.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else if(url.length()==0) {
					//팝업->사진을 추가하세요.
					JOptionPane.showMessageDialog(c,"사진을 추가하세요.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else if(name.length()>7) {
					//팝업->음식명은 7글자이상 입력할 수 없습니다.
					JOptionPane.showMessageDialog(c,"음식명은 7글자이상 입력할 수 없습니다.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else if(cost.matches("^[0-9]*$")==false){
					//팝업->가격은 숫자만으로 입력해야 합니다.
					JOptionPane.showMessageDialog(c,"가격은 숫자만으로 입력해야 합니다.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else if(cost.length()>7) {
					JOptionPane.showMessageDialog(c,"가격은 100만원이상 입력할 수 없습니다.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else if(Integer.parseInt(cost)<=0) {
					//팝업->가격은 100만원이상 입력할 수 없습니다.
					JOptionPane.showMessageDialog(c,"가격은 0원이상 입력해야 합니다.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else if(Integer.parseInt(cost)>999999) {
					//팝업->가격은 100만원이상 입력할 수 없습니다.
					JOptionPane.showMessageDialog(c,"가격은 100만원이상 입력할 수 없습니다.", "음식추가",JOptionPane.WARNING_MESSAGE);
				}
				else {
					int cost1 = Integer.parseInt(cost);
					
					if(strAddUrl.indexOf("eclipse-workspace")!=-1&&strAddUrl.indexOf("StudyCafeProgram")!=-1) {//스터디프로그램내부에 저장된 사진
						    strAddUrl = strAddUrl.replace("\\", "/");
				    		int num=strAddUrl.indexOf("eclipse-workspace")+18;
				    		strAddUrl=strAddUrl.substring(num);
							num=strAddUrl.indexOf("/");
							strAddUrl=strAddUrl.substring(num+1);
					}
				    else {//다른 경로에 있는 사진
				    	String fileName="copy";
				    	int num = fooddb.foodNum();
				    	try {
							put(strAddUrl,"images/copy"+num+".png");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    	strAddUrl="images/copy"+num+".png";
				    }
					
					
					if(comboFoodAdd.getSelectedIndex()==0) {
						fooddb.foodAdd(name, cost1, strAddUrl, 100);
					}
					else if(comboFoodAdd.getSelectedIndex()==1) {
						fooddb.foodAdd(name, cost1, strAddUrl, 200);
					}
					else {
						fooddb.foodAdd(name, cost1, strAddUrl, 300);
					}
					
					
					
					JOptionPane.showMessageDialog(c,"     추가되었습니다.", "음식추가",JOptionPane.WARNING_MESSAGE);
					getContentPane().removeAll();
					c.add(panelAdminMain);
					panelAdminFoodAdd.remove(comboFoodAdd);
					panelAdminFoodAdd.remove(textFoodAdd[0]);
					panelAdminFoodAdd.remove(textFoodAdd[1]);
					panelAdminFoodAdd.remove(btnAddIcon);
					panelAdminFoodAdd.remove(icon);
					
					revalidate();
		            repaint();
				}
			}
		}
		
	}
	
	//버튼 클래스
	public class OvalButton extends JButton {
	      public OvalButton() { super(); decorate(); } 
	      public OvalButton(String text) { super(text); decorate(); } 
	      public OvalButton(Action action) { super(action); decorate(); } 
	      public OvalButton(Icon icon) { super(icon); decorate(); } 
	      public OvalButton(String text, Icon icon) { super(text, icon); decorate(); } 
	      protected void decorate() { setBorderPainted(false); setOpaque(false); }
	      @Override 
	      protected void paintComponent(Graphics g) {
	         Color c=new Color(0xFFE699); //배경색 결정
	         Color o=new Color(0x000000); //글자색 결정
	         int width = 50; 
	         int height = 50; 
	         Graphics2D graphics = (Graphics2D) g; 
	         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
	         if (getModel().isArmed()) { graphics.setColor(getBackground().darker()); } 
	         else { graphics.setColor(getBackground()); } 
	         graphics.fillOval(0, 0, width, height); 
	         FontMetrics fontMetrics = graphics.getFontMetrics(); 
	         Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
	         int textX = (width - stringBounds.width) / 2; 
	         int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	         graphics.setColor(o); 
	         graphics.setFont(getFont()); 
	         graphics.drawString(getText(), textX, textY); 
	         graphics.dispose(); 
	         super.paintComponent(g); 
	         }
	      }
	
	public class RoundedButton extends JButton {
	      public RoundedButton() { super(); decorate(); } 
	      public RoundedButton(String text) { super(text); decorate(); } 
	      public RoundedButton(Action action) { super(action); decorate(); } 
	      public RoundedButton(Icon icon) { super(icon); decorate(); } 
	      public RoundedButton(String text, ImageIcon icon) { super(text, icon); decorate(); } 
	      protected void decorate() { setBorderPainted(false); setOpaque(false); }
	      @Override 
	      protected void paintComponent(Graphics g) {
	         Color o=new Color(0x000000); //글자색 결정
	         Color s=new Color(0xFFD966); //버튼 눌렸을 때 색상
	         
	         Graphics2D graphics = (Graphics2D) g; 
	         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
	         if (getModel().isArmed()) { graphics.setColor(getBackground().darker()); } //버튼이 눌렸을때
	         else { graphics.setColor(getBackground()); } //버튼기본색상
	         
	         graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 500); 
	         FontMetrics fontMetrics = graphics.getFontMetrics(); 
	         Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
	         int textX = (getWidth() - stringBounds.width) / 2; 
	         int textY = (getHeight() - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	         graphics.setColor(o); 
	         graphics.setFont(getFont()); 
	         graphics.drawString(getText(), textX, textY); 
	         graphics.dispose(); 
	         super.paintComponent(g); 
	         }
	      }
	
	public class RectButton extends JButton {
	      public RectButton() { super(); decorate(); } 
	      public RectButton(String text) { super(text); decorate(); } 
	      public RectButton(Action action) { super(action); decorate(); } 
	      public RectButton(Icon icon) { super(icon); decorate(); } 
	      public RectButton(String text, ImageIcon icon) { super(text, icon); decorate(); }
	      protected void decorate() { setBorderPainted(false); setOpaque(false); }
	      @Override 
	      protected void paintComponent(Graphics g) {
	         Color o=new Color(0x000000); //글자색 결정
	         Color s=new Color(0xFFD966); //버튼 눌렸을 때 색상
	         
	         Graphics2D graphics = (Graphics2D) g; 
	         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
	         if (getModel().isArmed()) { graphics.setColor(getBackground().darker()); } //버튼이 눌렸을때
	         else { graphics.setColor(getBackground()); } //버튼기본색상
	         
	         graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); 
	         FontMetrics fontMetrics = graphics.getFontMetrics(); 
	         Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
	         int textX = (getWidth() - stringBounds.width) / 2; 
	         int textY = (getHeight() - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	         graphics.setColor(o); 
	         graphics.setFont(getFont()); 
	         
             int icon = 0;
             int iconMinus = 0;
             String str ="";
	         String strImg = "";
	         
	         for(int i=0; i<getText().length(); i++) {
		 			if(getText().charAt(i)=='*') {
		 				icon=i;
		 				break;
		 			}
		 		}
	         
	         
	         if(icon!=0) {
	        	 str=getText().substring(0,icon);
		         strImg=getText().substring(icon+1,getText().length());
	         }
	         else if(icon==0) {
	        	 str = getText();
	         }
	         
	         
	         if(icon!=0) {
	        	 ImageIcon img = new ImageIcon(strImg);
	        	 Image before = img.getImage();
			     Image changeImg = before.getScaledInstance(120,120,Image.SCALE_SMOOTH);
			     ImageIcon Change=new ImageIcon(changeImg);
		         graphics.drawImage(Change.getImage(), 15, 12, this);
		         iconMinus=67;
	         }
	         
	         
	        
	         
	         int num = 0;
	         
	         for(int i=0; i<str.length(); i++) {
		 			if(getText().charAt(i)=='/') {
		 				num=i;
		 				break;
		 			}
		 		}
	         
	         
	         if(num==0) {graphics.drawString(str, textX, textY); }
	         else {
	        
	        	 String a = getText().substring(0,num);
	        	 String b = getText().substring(num+1,str.length());
	        	 
	        	 stringBounds = fontMetrics.getStringBounds(a, graphics).getBounds(); 
	        	 textX = (getWidth() - stringBounds.width) / 2; 
		         textY = (getHeight() - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	        	 graphics.drawString(a, textX, textY-17+iconMinus);
	        	 
	        	 stringBounds = fontMetrics.getStringBounds(b, graphics).getBounds(); 
	        	 textX = (getWidth() - stringBounds.width) / 2; 
		         textY = (getHeight() - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	        	 graphics.drawString(b, textX, textY+17+iconMinus);
	         }
	         
	         graphics.dispose(); 
	         super.paintComponent(g); 
	         }
	      }
	
	
	public class RectButton2 extends JButton {
	      public RectButton2() { super(); decorate(); } 
	      public RectButton2(String text) { super(text); decorate(); } 
	      public RectButton2(Action action) { super(action); decorate(); } 
	      public RectButton2(Icon icon) { super(icon); decorate(); } 
	      public RectButton2(String text, ImageIcon icon) { super(text, icon); decorate(); }
	      protected void decorate() { setBorderPainted(false); setOpaque(false); }
	      @Override 
	      protected void paintComponent(Graphics g) {
	         Color o=new Color(0x000000); //글자색 결정
	         Color s=new Color(0xFFD966); //버튼 눌렸을 때 색상
	         
	         Graphics2D graphics = (Graphics2D) g; 
	         graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
	         if (getModel().isArmed()) { graphics.setColor(getBackground()); } //버튼이 눌렸을때
	         else { graphics.setColor(getBackground()); } //버튼기본색상
	         
	         graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); 
	         FontMetrics fontMetrics = graphics.getFontMetrics(); 
	         Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); 
	         int textX = (getWidth() - stringBounds.width) / 2; 
	         int textY = (getHeight() - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	         graphics.setColor(o); 
	         graphics.setFont(getFont()); 
	         
           int icon = 0;
           int iconMinus = 0;
           String str ="";
	         String strImg = "";
	         
	         for(int i=0; i<getText().length(); i++) {
		 			if(getText().charAt(i)=='*') {
		 				icon=i;
		 				break;
		 			}
		 		}
	         
	         
	         if(icon!=0) {
	        	 str=getText().substring(0,icon);
		         strImg=getText().substring(icon+1,getText().length());
	         }
	         else if(icon==0) {
	        	 str = getText();
	         }
	         
	         
	         if(icon!=0) {
	        	 ImageIcon img = new ImageIcon(strImg);
	        	 Image before = img.getImage();
			     Image changeImg = before.getScaledInstance(120,120,Image.SCALE_SMOOTH);
			     ImageIcon Change=new ImageIcon(changeImg);
		         graphics.drawImage(Change.getImage(), 15, 12, this);
		         iconMinus=67;
	         }
	         
	         
	        
	         
	         int num = 0;
	         
	         for(int i=0; i<str.length(); i++) {
		 			if(getText().charAt(i)=='/') {
		 				num=i;
		 				break;
		 			}
		 		}
	         
	         
	         if(num==0) {graphics.drawString(str, textX, textY); }
	         else {
	        
	        	 String a = getText().substring(0,num);
	        	 String b = getText().substring(num+1,str.length());
	        	 
	        	 stringBounds = fontMetrics.getStringBounds(a, graphics).getBounds(); 
	        	 textX = (getWidth() - stringBounds.width) / 2; 
		         textY = (getHeight() - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	        	 graphics.drawString(a, textX, textY-17+iconMinus);
	        	 
	        	 stringBounds = fontMetrics.getStringBounds(b, graphics).getBounds(); 
	        	 textX = (getWidth() - stringBounds.width) / 2; 
		         textY = (getHeight() - stringBounds.height) / 2 + fontMetrics.getAscent(); 
	        	 graphics.drawString(b, textX, textY+17+iconMinus);
	         }
	         
	         graphics.dispose(); 
	         super.paintComponent(g); 
	         }
	      }
	
		class OpenActionListener implements ActionListener{//파일추가
			private JFileChooser chooser;
			
			public OpenActionListener() {
				chooser=new JFileChooser();
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Images", "jpg", "png");
				chooser.setFileFilter(filter);
				
				
				int ret = chooser.showOpenDialog(c);
				
				if(ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(c, "파일을 선택하지 않았습니다", "파일추가", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				strAddUrl = chooser.getSelectedFile().getPath();
				if(strAddUrl.replaceAll(" ", "")!="") {
					btnAddIcon.setVisible(false);
					ImageIcon img = new ImageIcon(strAddUrl);
		        	Image before = img.getImage();
				    Image changeImg = before.getScaledInstance(120,120,Image.SCALE_SMOOTH);
				    ImageIcon Change=new ImageIcon(changeImg);
				    icon.setIcon(Change);
				    icon.setVisible(true);
				}
			}
		}
		
		
		public void put(String url, String name) throws IOException {
			
			  File file1 = new File(url);
			  File file2 = new File(name);
			  
			  long fsize1 = file1.length(); // 원본 파일 크기 변환
			  
			  FileInputStream fis = new FileInputStream(file1);
			  FileOutputStream fos = new FileOutputStream(file2);
			  
			  int input=0, count=0;
			  
			  byte[] data = new byte[1024];
			  
			  while((input=fis.read(data))!=-1){
			   // 배열을 할 때는 0부터 끝까지 넣어야함
			   fos.write(data, 0, input);
			   count+=input;
			   
			   // (읽은 바이트 수 / 전체 파일 크기) * 100
			   float per = ((float)count/fsize1) * 100;
			  }

		 }
		 
		
		
		public void Order() {
			FoodDB fooddb = new FoodDB();
			fooddb.foodRead();
			
			RectButton btnDrink[] = new RectButton[fooddb.arrayDrink.size()];//음료 메뉴 리스트 버튼배열
			RectButton btnFood[] = new RectButton[fooddb.arrayFood.size()];//음식류 메뉴 리스트 버튼배열
			RectButton btnSnack[] = new RectButton[fooddb.arraySnack.size()];//간식류 메뉴 리스트 버튼배열
			
			if(fooddb.arrayDrink.size()>6) {//음료 패널 사이즈 설정
				int size=(fooddb.arrayDrink.size()-7)/3+1; 
				panelOrderMenu[0].setPreferredSize(new Dimension(500, 450+(225*size)));
			}
			else {
				panelOrderMenu[0].setPreferredSize(new Dimension(500, 450));
			}
			if(fooddb.arrayFood.size()>6) {//음식류 패널 사이즈 설정
				int size=(fooddb.arrayFood.size()-7)/3+1; 
				panelOrderMenu[1].setPreferredSize(new Dimension(500, 450+(225*size)));
			}
			else {
				panelOrderMenu[1].setPreferredSize(new Dimension(500, 450));
			}
			if(fooddb.arraySnack.size()>6) {//간식류 패널 사이즈 설정
				int size=(fooddb.arraySnack.size()-7)/3+1; 
				panelOrderMenu[2].setPreferredSize(new Dimension(500, 450+(225*size)));
			}
			else {
				panelOrderMenu[2].setPreferredSize(new Dimension(500, 450));
			}
			
			for(int i=0;i<fooddb.arrayDrink.size();i++) {//음료버튼추가	
			btnDrink[i] = new RectButton(fooddb.arrayDrink.get(i)+"/"+fooddb.arrayDrinkCost.get(i)+"원*"+fooddb.arrayDrinkUrl.get(i));
			btnDrink[i].setSize(150,200);
			
            if(i%3==0) {
            	btnDrink[i].setLocation(13, 10+((i/3)*225));
			}
            else if(i%3==1) {
            	btnDrink[i].setLocation(183, 10+((i/3)*225));
            }
            else if(i%3==2) {
            	btnDrink[i].setLocation(353, 10+((i/3)*225));
            }
			btnDrink[i].setBackground(colorMain);
			btnDrink[i].setFont(new Font("맑은고딕",Font.PLAIN,20));
			btnDrink[i].setVerticalTextPosition(JButton.BOTTOM);
			btnDrink[i].setHorizontalTextPosition(JButton.CENTER);
			btnDrink[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					for(int i=0; i<fooddb.arrayDrink.size(); i++) {
						if(e.getSource()==btnDrink[i]) {
							int getid=fooddb.arrayDrinkID.get(i);
							
							if(!id.contains(getid)) {
								id.add(getid);
								amount.add(1);
								cost.add(fooddb.arrayDrinkCost.get(i));
								name.add(fooddb.arrayDrink.get(i));
								count++;
							}
							else {
								int num = id.indexOf(getid);
								if(amount.get(num)==10) {
									
								}
								else {
									amount.set(num, amount.get(num)+1);
									count++;
								}
								
								
							}
							
							cartNum.setText(Integer.toString(count));
						}
					}
				}
				
			});
			panelOrderMenu[0].add(btnDrink[i]);
		}
	 
			for(int i=0;i<fooddb.arrayFood.size();i++) {//음식류버튼추가
				btnFood[i] = new RectButton(fooddb.arrayFood.get(i)+"/"+fooddb.arrayFoodCost.get(i)+"원*"+fooddb.arrayFoodUrl.get(i));
				btnFood[i].setSize(150,200);
				
				
	            if(i%3==0) {
	            	btnFood[i].setLocation(13, 10+((i/3)*225));
				}
	            else if(i%3==1) {
	            	btnFood[i].setLocation(183, 10+((i/3)*225));
	            }
	            else if(i%3==2) {
	            	btnFood[i].setLocation(353, 10+((i/3)*225));
	            }
	            btnFood[i].setBackground(colorMain);
	            btnFood[i].setFont(new Font("맑은고딕",Font.PLAIN,20));
	            btnFood[i].setVerticalTextPosition(JButton.BOTTOM);
	            btnFood[i].setHorizontalTextPosition(JButton.CENTER);
	            btnFood[i].setContentAreaFilled(false);
	            btnFood[i].setFocusPainted(false);
	            btnFood[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						for(int i=0; i<fooddb.arrayFood.size(); i++) {
							if(e.getSource()==btnFood[i]) {
								int getid=fooddb.arrayFoodID.get(i);
								
								if(!id.contains(getid)) {
									id.add(getid);
									amount.add(1);
									cost.add(fooddb.arrayFoodCost.get(i));
									name.add(fooddb.arrayFood.get(i));
									count++;
								}
								else {
									int num = id.indexOf(getid);
									if(amount.get(num)==10) {
										
									}
									else {
										amount.set(num, amount.get(num)+1);
										count++;
									}
								}
								
								cartNum.setText(Integer.toString(count));
							}
						}
					}
					
				});
				panelOrderMenu[1].add(btnFood[i]);
			}
			
			for(int i=0;i<fooddb.arraySnack.size();i++) {//간식류버튼추가
				btnSnack[i] = new RectButton(fooddb.arraySnack.get(i)+"/"+fooddb.arraySnackCost.get(i)+"원*"+fooddb.arraySnackUrl.get(i));
				btnSnack[i].setSize(150,200);
				
				
	            if(i%3==0) {
	            	btnSnack[i].setLocation(13, 10+((i/3)*225));
				}
	            else if(i%3==1) {
	            	btnSnack[i].setLocation(183, 10+((i/3)*225));
	            }
	            else if(i%3==2) {
	            	btnSnack[i].setLocation(353, 10+((i/3)*225));
	            }
	            btnSnack[i].setBackground(colorMain);
	            btnSnack[i].setFont(new Font("맑은고딕",Font.PLAIN,20));
	            btnSnack[i].setVerticalTextPosition(JButton.BOTTOM);
	            btnSnack[i].setHorizontalTextPosition(JButton.CENTER);
	            btnSnack[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						for(int i=0; i<fooddb.arraySnack.size(); i++) {
							if(e.getSource()==btnSnack[i]) {
								int getid=fooddb.arraySnackID.get(i);
								
								if(!id.contains(getid)) {
									id.add(getid);
									amount.add(1);
									cost.add(fooddb.arraySnackCost.get(i));
									name.add(fooddb.arraySnack.get(i));
									count++;
								}
								else {
									int num = id.indexOf(getid);
									if(amount.get(num)==10) {
										
									}
									else {
										amount.set(num, amount.get(num)+1);
										count++;
									}
								}
								
								cartNum.setText(Integer.toString(count));
								
							}
						}
					}
					
				});
				panelOrderMenu[2].add(btnSnack[i]);
			}
			
		}
		
		public void Cart() {
			
			btnMinus = new OvalButton[id.size()];
		    btnPlus = new OvalButton[id.size()];
			laName = new JLabel[id.size()];
			laAmount = new JLabel[id.size()];
			laTrue = new JLabel[id.size()];
			laFalse = new JLabel[id.size()];
			btnList = new RectButton2[id.size()];
			listCheck = new boolean[id.size()] ;
			
			if(id.size()<=4) {
				panelOrderList.setPreferredSize(new Dimension(500, 550));
			}
			else {
				panelOrderList.setPreferredSize(new Dimension(500, 550+((id.size()-4)*135)));
			}
			
			
			for(int i=0; i<id.size(); i++) {
				int j=i;
				listCheck[i]=false;
				
				btnMinus[i]=new OvalButton("−");
				btnMinus[i].setSize(50,50);
				btnMinus[i].setLocation(355,45+(i*135));
				btnMinus[i].setBackground(colorMain);
				btnMinus[i].setFont(new Font("맑은고딕",Font.BOLD,20));
				
				btnMinus[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
                         if(amount.get(j)==0) {}
						else {
							amount.set(j, amount.get(j)-1);
							count--;
							laAmount[j].setText(Integer.toString(amount.get(j)));
						}
					}
					
				});
				panelOrderList.add(btnMinus[i]);
				
				btnPlus[i] = new OvalButton("+");
				btnPlus[i].setSize(50,50);
				btnPlus[i].setLocation(450,45+(i*135));
				btnPlus[i].setBackground(colorMain);
				btnPlus[i].setFont(new Font("맑은고딕",Font.BOLD,20));
				btnPlus[i].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
                         if(amount.get(j)==10) {}
						else {
							amount.set(j, amount.get(j)+1);
							count++;
							laAmount[j].setText(Integer.toString(amount.get(j)));
						}
					}
					
				});
				panelOrderList.add(btnPlus[i]);
				
				laName[i] = new JLabel(name.get(i)+"     "+cost.get(i)+"원");
				laName[i].setSize(280,100);
				laName[i].setLocation(70,20+(i*135));
				laName[i].setOpaque(true);
				laName[i].setBackground(Color.white);
				laName[i].setVerticalAlignment(JLabel.CENTER);
				laName[i].setHorizontalAlignment(JLabel.LEFT);
				laName[i].setFont(new Font("맑은고딕",Font.BOLD,20));
				panelOrderList.add(laName[i]);
				
				laAmount[i] = new JLabel(Integer.toString(amount.get(i)));
				laAmount[i].setSize(45,100);
				laAmount[i].setLocation(405,20+(i*135));
				laAmount[i].setOpaque(true);
				laAmount[i].setBackground(Color.white);
				laAmount[i].setVerticalAlignment(JLabel.CENTER);
				laAmount[i].setHorizontalAlignment(JLabel.CENTER);
				laAmount[i].setFont(new Font("맑은고딕",Font.BOLD,20));
				panelOrderList.add(laAmount[i]);
				
				laFalse[i] = new JLabel("□");
				laFalse[i].setSize(45,50);
				laFalse[i].setLocation(20,45+(i*135));
				laFalse[i].setOpaque(true);
				laFalse[i].setBackground(Color.white);
				laFalse[i].setVerticalAlignment(JLabel.CENTER);
				laFalse[i].setHorizontalAlignment(JLabel.CENTER);
				laFalse[i].setFont(new Font("맑은고딕",Font.BOLD,50));
				laFalse[i].setVisible(true);
				panelOrderList.add(laFalse[i]);
				
				laTrue[i] = new JLabel("☑");
				laTrue[i].setSize(45,50);
				laTrue[i].setLocation(20,45+(i*135));
				laTrue[i].setOpaque(true);
				laTrue[i].setBackground(Color.white);
				laTrue[i].setVerticalAlignment(JLabel.CENTER);
				laTrue[i].setHorizontalAlignment(JLabel.CENTER);
				laTrue[i].setFont(new Font("맑은고딕",Font.BOLD,50));
				laTrue[i].setVisible(false);
				panelOrderList.add(laTrue[i]);
				
				btnList[i] = new RectButton2("");
				btnList[i].setSize(495,100);
				btnList[i].setLocation(10,20+(i*135));
				btnList[i].setBackground(Color.white);
				btnList[i].setFont(new Font("맑은고딕",Font.BOLD,20));
				btnList[i].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(listCheck[j]==false) {
							laTrue[j].setVisible(true);
							laFalse[j].setVisible(false);
							listCheck[j]=true;
						}
						else {
							laTrue[j].setVisible(false);
							laFalse[j].setVisible(true);
							listCheck[j]=false;
						}
								
					} 
					
				});
				panelOrderList.add(btnList[i]);
				
				
		}
		
		}
		
		

	
	public static void main(String[] args) {
		new Menu();
	}
}