import java.awt.Color;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FoodDB {
    ArrayList<String> arrayDrink = new ArrayList<String>();
	ArrayList<Integer> arrayDrinkCost = new ArrayList<Integer>();
	ArrayList<String> arrayDrinkUrl = new ArrayList<String>();
	ArrayList<Integer> arrayDrinkID = new ArrayList<Integer>();
	
	ArrayList<String> arrayFood = new ArrayList<String>();
	ArrayList<Integer> arrayFoodCost = new ArrayList<Integer>();
	ArrayList<String> arrayFoodUrl = new ArrayList<String>();
	ArrayList<Integer> arrayFoodID = new ArrayList<Integer>();
	
	ArrayList<String> arraySnack = new ArrayList<String>();
	ArrayList<Integer> arraySnackCost = new ArrayList<Integer>();
	ArrayList<String> arraySnackUrl = new ArrayList<String>();
	ArrayList<Integer> arraySnackID = new ArrayList<Integer>();
	
	
	public static Connection makeConnection() {
		String url="jdbc:mysql://localhost/sys?autoReconnect=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
		String id="root";
		String password="playtime3";
		Connection con=null;
				
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver loading success!");
			con=DriverManager.getConnection(url, id, password);
			System.out.println("database connecting success!");
		} catch(ClassNotFoundException e) {
			System.out.println("cannot find driver!");
		} catch(SQLException e) {
			System.out.println("seatdb connection failed!");
			System.out.println(url+"  "+id+"  "+password);
			
		}
		
		return con;
	}
	
	
	//음식 불러오기
	public void foodRead() {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		
		
		arrayDrink.clear();
		arrayDrinkCost.clear();
		arrayDrinkUrl.clear();
		arrayFood.clear();
		arrayFoodCost.clear();
		arrayFoodUrl.clear();
		arraySnack.clear();
		arraySnackCost.clear();
		arraySnackUrl.clear();
		
		try {	
			
			String selectDrink="SELECT type, name, cost, url, food_id FROM food Order by type";
			pstmt=con.prepareStatement(selectDrink);
			
			ResultSet rsT=pstmt.executeQuery();
			
			while(rsT.next())
			{
				int type = rsT.getInt(1);
				String name = rsT.getString(2);
				int cost = rsT.getInt(3);
				String url = rsT.getString(4);
				int id=rsT.getInt(5);
				
				
				if(type==100) {
					arrayDrink.add(name);
					arrayDrinkCost.add(cost);
					arrayDrinkUrl.add(url);
					arrayDrinkID.add(id);
				}
				else if(type==200) {
					arrayFood.add(name);
					arrayFoodCost.add(cost);
					arrayFoodUrl.add(url);
					arrayFoodID.add(id);
				}
				else if(type==300){
					arraySnack.add(name);
					arraySnackCost.add(cost);
					arraySnackUrl.add(url);
					arraySnackID.add(id);
				}
		
			}
			
			if(rsT != null) try{ rsT.close();} catch(SQLException e){};
			
		} catch(SQLException e ) {
			System.out.println("seatCheck connection failed! in main()");
		}
		catch(NullPointerException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
		
	}
	
	
	//음식 추가하기
	public void foodAdd(String name, int cost, String url, int type) {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		int num=0;
		
		try {	
			String select = "SELECT Max(food_id) FROM food";
			pstmt=con.prepareStatement(select);
			
			ResultSet rsT=pstmt.executeQuery();
			while(rsT.next())
			{
				num=rsT.getInt(1);
			}
			
			
			String insertStr="INSERT INTO food VALUES (?,?,?,?,?)";			
			pstmt=con.prepareStatement(insertStr);
			
			pstmt.setInt(1, num+1);//첫번째칼럼
			pstmt.setString(2, name);//두번째 칼럼
			pstmt.setInt(3, cost);//세번째 칼럼
			pstmt.setString(4, url);//네번째 칼럼
			pstmt.setInt(5, type);//다섯번째 칼럼*/
			
			int i=pstmt.executeUpdate();
			
			if(i==1) 
				System.out.println("record add success!!"); 
			else
				System.out.println("record add failed!!");
			
			if(rsT != null) try{ rsT.close();} catch(SQLException e){}; 
			
		} catch(SQLException e ) {
			System.out.println("seatCheck connection failed! in main()");
		}
		catch(NullPointerException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
	}
	
	
	
	public int foodNum() {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		int num=0;
		
		try {	
				String select = "SELECT Max(food_id) FROM food";
				pstmt=con.prepareStatement(select);
				
				ResultSet rsT=pstmt.executeQuery();
				while(rsT.next())
				{
					num =rsT.getInt(1);
				}
				
				if(rsT != null) try{ rsT.close();} catch(SQLException e){}; 
			
		} catch(SQLException e ) {
			System.out.println("seatCheck connection failed! in main()");
		}
		catch(NullPointerException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
        
        return num+1;
	}
	
	
	
	
	public static void main(String[]args) {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		int num=0;
		
		try {	
			
				String select = "SELECT Count(*) FROM food Where url=?";
				pstmt=con.prepareStatement(select);
				pstmt.setString(1, "images/coffee1.jpg");
				
				ResultSet rsT=pstmt.executeQuery();
				while(rsT.next())
				{
					num =rsT.getInt(1);
				}
				
				
				
				if(rsT != null) try{ rsT.close();} catch(SQLException e){}; 
			
		} catch(SQLException e ) {
			System.out.println("seatCheck connection failed! in main()");
		}
		catch(NullPointerException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
        System.out.println(num);
	}
}
