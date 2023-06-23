import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserDB {
	int mileSeconds=0;
	int Hours=0; 
	int Minutes=0;         
	int Seconds=0;
	int total=0;//총시간요금
	
	int food_fee=0;
	int time_fee=0;
	int total_fee=food_fee+time_fee;
	
	//아이디, 좌석번호, 이용시간, 이용금액
	ArrayList <String> resetID = new ArrayList <String> ();
	ArrayList <Integer> resetSeat = new ArrayList <Integer> ();
	ArrayList <String> resetTime = new ArrayList <String> ();
	ArrayList <Integer> resetCost = new ArrayList <Integer> ();
	
	//사용중인 좌석
	ArrayList <Integer> seat = new ArrayList<Integer>();
	
		
	public static Connection makeConnection() {
		String url="jdbc:mysql://localhost/sys?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
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
			System.out.println("userdb connection failed!");
		}
		
		return con;
	}
	
	//회원가입
	public void input(String id, String name, String pin, String email) {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		
		try {					
			String insert="INSERT INTO user (user_id, name, pin, email, time_fee, food_fee)";
			insert+="VALUES ('"+ id +"', '"+ name +"', '"+ pin +"', '"+ email +"',0,0)";  
					
			pstmt=con.prepareStatement(insert);
			
			int i=pstmt.executeUpdate();
			
			if(i==1) 
				System.out.println("record add success!!"); 
			else
				System.out.println("record add failed!!");
			
			
		} catch(SQLException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
	}
	
	
	
	//회원가입 중복 체크
	//아이디체크
	public Boolean checkID (String id) {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		
		try {					

			String selectOneRow="SELECT COUNT(*) FROM user where user_id=?";
			pstmt=con.prepareStatement(selectOneRow);
			pstmt.setString(1, id);
			
			ResultSet rsT=pstmt.executeQuery();
			while(rsT.next())
			{
				int num=rsT.getInt(1);
				if(num>0) {
					return false;
				}
				else if(num==0){
					return true;
				}
			}
			       
			if(rsT != null) try{ rsT.close();} catch(SQLException e){};
			
		} catch(SQLException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
		return false;
	}
		
	//이메일체크
	public Boolean checkEmail (String email) {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		
		try {					

			String selectOneRow="SELECT COUNT(*) FROM user where email=?";
			pstmt=con.prepareStatement(selectOneRow);
			pstmt.setString(1, email);
			
			ResultSet rsT=pstmt.executeQuery();
			while(rsT.next())
			{
				int num=rsT.getInt(1);
				if(num>0) {
					return false;
				}
				else if (num==0){
					return true;
				}
			}
			       
			if(rsT != null) try{ rsT.close();} catch(SQLException e){};
			
		} catch(SQLException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
		return false;
	}
		
	
	//로그인
	public Boolean select(String id, String pin) {
		Connection con=makeConnection();
		PreparedStatement pstmt=null;
		
		try {					
			String selectOneRow="SELECT COUNT(*) FROM user where user_id=? AND pin=?";
			pstmt=con.prepareStatement(selectOneRow);
			pstmt.setString(1, id);
			pstmt.setString(2, pin);
			
			ResultSet rsT=pstmt.executeQuery();
			while(rsT.next())
			{
				int num=rsT.getInt(1);
				if(num==1) {
					return true;
				}
				else {
					return false;
				}
			}
			       
			if(rsT != null) try{ rsT.close();} catch(SQLException e){};
			
		} catch(SQLException e ) {
			System.out.println("connection failed! in main()");
		}
		        
		if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
        if(con != null) try{ con.close();} catch(SQLException e){};
		return false;
	}
	
	
	    //아이디 찾기
		public String searchID(String name, String email) {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			try {					
				String selectOneRow="SELECT COUNT(*), user_id FROM user where name=? AND email=?";
				pstmt=con.prepareStatement(selectOneRow);
				pstmt.setString(1, name);
				pstmt.setString(2, email);
				
				ResultSet rsT=pstmt.executeQuery();
				while(rsT.next())
				{
					int num=rsT.getInt(1);
					if(num==1) {
						return rsT.getString(2);
					}
					else {
						return "0";
					}
				}
				       
				if(rsT != null) try{ rsT.close();} catch(SQLException e){};
				
			} catch(SQLException e ) {
				System.out.println("connection failed! in main()");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
			return "";
		}
		
		
		//비밀번호 찾기
		public String searchPIN(String id, String name, String email) {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			try {					
				String selectOneRow="SELECT COUNT(*), pin FROM user where user_id=? AND name=? AND email=?";
				pstmt=con.prepareStatement(selectOneRow);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				pstmt.setString(3, email);
				
				ResultSet rsT=pstmt.executeQuery();
				while(rsT.next())
				{
					int num=rsT.getInt(1);
					if(num==1) {
						return rsT.getString(2);
					}
					else {
						return "0";
					}
				}
				       
				if(rsT != null) try{ rsT.close();} catch(SQLException e){};
				
			} catch(SQLException e ) {
				System.out.println("connection failed! in main()");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
			return "";
		}
		
		//좌석배정
		public boolean seatManage(String id, int num) {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			boolean check = false;
			
			try {	
				
						String update="UPDATE user SET seat=? WHERE user_id=?";
						pstmt=con.prepareStatement(update);
						pstmt.setInt(1, num);
						pstmt.setString(2, id);
						
						int t=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
						
						if(t==1) 
							//업데이트된 사항이 존재할 때
							System.out.println("record update success!!");
						else
							//업데이트된 사항이 존재하지 않을 때
							System.out.println("record update failed!!");
						
						
						//시간추가
						String update2="UPDATE user SET in_time=? WHERE user_id=?";
						pstmt=con.prepareStatement(update2);
						pstmt.setLong(1, System.currentTimeMillis());
						pstmt.setString(2, id);
						
						int k=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
						
						if(k==1) 
							//업데이트된 사항이 존재할 때
							System.out.println("djlfkjdlfjl record update success!!");
						else
							//업데이트된 사항이 존재하지 않을 때
							System.out.println("record update failed!!");
						
						return true;
				
			} catch(SQLException e ) {
				System.out.println("seatManage connection failed! in main()");
			}
			catch(NullPointerException e ) {
				System.out.println("nullpointer");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
	        return false;
		}
		
		//좌석배정여부확인
		public int searchUser(String id) {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			try {					
				String selectOneRow="SELECT seat FROM user where user_id=?";
				pstmt=con.prepareStatement(selectOneRow);
				pstmt.setString(1, id);
				
				ResultSet rsT=pstmt.executeQuery();
				while(rsT.next())
				{
					int num=rsT.getInt(1);
					return num;
				}
				       
				if(rsT != null) try{ rsT.close();} catch(SQLException e){};
				
			} catch(SQLException e ) {
				System.out.println("connection failed! in main()");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
			return 0;
		}
		
		
		//음식주문정산
		public void userOrder(String id, int fee) {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			try {	
				String update="UPDATE user SET food_fee=food_fee+? WHERE user_id=?";
				pstmt=con.prepareStatement(update);
				pstmt.setInt(1, fee);
				pstmt.setString(2, id);
				
				int t=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
				
				if(t==1) 
					//업데이트된 사항이 존재할 때
					System.out.println("record update success!!");
				else
					//업데이트된 사항이 존재하지 않을 때
					System.out.println("record update failed!!");
				
			} catch(SQLException e ) {
				System.out.println("seatOut connection failed! in main()");
			}
			catch(NullPointerException e ) {
				System.out.println("nullpointer");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
		}
		
		
		//시간요금측정
		public void total(String id) {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			try {					
				String update1="UPDATE user SET out_time=? WHERE user_id=?";
				pstmt=con.prepareStatement(update1);
				pstmt.setLong(1, System.currentTimeMillis());
				pstmt.setString(2, id);
				
				int k=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
				
				if(k==1) 
					//업데이트된 사항이 존재할 때
					System.out.println("record update success!!");
				else
					//업데이트된 사항이 존재하지 않을 때
					System.out.println("record update failed!!");
				
				String selectOneRow="SELECT out_time-in_time FROM user where user_id=?";
				pstmt=con.prepareStatement(selectOneRow);
				pstmt.setString(1, id);
				
				ResultSet rsT=pstmt.executeQuery();
				int time=0;
				
				while(rsT.next())
				{
					time=rsT.getInt(1);
					System.out.println(time);
				}
				       
				//mileSeconds=time/1000;
				Hours = (time/(1000*60*60))%24; 
				Minutes = (time/(1000*60))%60;         
				Seconds = (time/1000)%60; 
		
				System.out.println(Seconds);
				System.out.println(Minutes);
				if(rsT != null) try{ rsT.close();} catch(SQLException e){};
				
				total=Hours*5000+Minutes*100+Seconds*1;
				
				String update2="UPDATE user SET time_fee=? WHERE user_id=?";
				pstmt=con.prepareStatement(update2);
				pstmt.setInt(1, total);
				pstmt.setString(2, id);
				
				int j=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
				
				if(j==1) 
					//업데이트된 사항이 존재할 때
					System.out.println("record update success!!");
				else
					//업데이트된 사항이 존재하지 않을 때
					System.out.println("record update failed!!");
				
			} catch(SQLException e ) {
				System.out.println("connection failed! in main()");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
		}
		
		
		//시간요금정산
		public void calculate(String id) {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			try {					
				String update2="UPDATE user SET time_fee=? WHERE user_id=?";
				pstmt=con.prepareStatement(update2);
				pstmt.setInt(1, total);
				pstmt.setString(2, id);
				
				int j=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
				
				if(j==1) 
					//업데이트된 사항이 존재할 때
					System.out.println("record update success!!");
				else
					//업데이트된 사항이 존재하지 않을 때
					System.out.println("record update failed!!");
				
			} catch(SQLException e ) {
				System.out.println("connection failed! in main()");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
		}
		
		
		//관리자 금액 조회
		public void admin_fee() {
			Connection con=makeConnection();
			PreparedStatement pstmt=null;
			
			try {					
			    String selectOneRow="SELECT Sum(food_fee),Sum(time_fee) FROM user";
				pstmt=con.prepareStatement(selectOneRow);
		
				ResultSet rsT=pstmt.executeQuery();
				
				
				while(rsT.next())
				{
					int food=rsT.getInt(1);
					int time=rsT.getInt(2);
					food_fee=food;
					time_fee=time;
					total_fee=food_fee+time_fee;
				}
			
				if(rsT != null) try{ rsT.close();} catch(SQLException e){};
			
			} catch(SQLException e ) {
				System.out.println("connection failed! in main()");
			}
			        
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
	        if(con != null) try{ con.close();} catch(SQLException e){};
		}
		
		
		
		
		
		//전체퇴실
				public void reset() {
					Connection con=makeConnection();
					PreparedStatement pstmt=null;
					resetID.clear();
					resetSeat.clear();
					resetTime.clear();
					resetCost.clear();
					seat.clear();
					
					try {	
						String select1="SELECT user_id, seat FROM user Where seat!=0 AND seat is not null";
						pstmt=con.prepareStatement(select1);
				
						ResultSet rsT=pstmt.executeQuery();
						
						
						while(rsT.next())
						{
							String id=rsT.getString(1);
							int seat=rsT.getInt(2);
							
							resetID.add(id);
							resetSeat.add(seat);
						}
					
						if(rsT != null) try{ rsT.close();} catch(SQLException e){};
						
					
						
						
						for(int i=0; i<resetID.size(); i++) {
							String update1="UPDATE user SET out_time=? WHERE user_id=?";
							pstmt=con.prepareStatement(update1);
							pstmt.setLong(1, System.currentTimeMillis());
							pstmt.setString(2,resetID.get(i));
							
							int k=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
							
							if(k==1) 
								//업데이트된 사항이 존재할 때
								System.out.println("record update success!!");
							else
								//업데이트된 사항이 존재하지 않을 때
								System.out.println("record update failed!!");
						}
						
						
						
						for(int i=0; i<resetID.size(); i++) {
							String select2="SELECT out_time-in_time FROM user where user_id=?";
							pstmt=con.prepareStatement(select2);
							pstmt.setString(1, resetID.get(i));
							
							ResultSet rs=pstmt.executeQuery();
							int time=0;
							
							while(rs.next())
							{
								time=rs.getInt(1);
							}
							       
							Hours = (time/(1000*60*60))%24; 
							Minutes = (time/(1000*60))%60;         
							Seconds = (time/1000)%60;
					
							if(Hours==0&&Seconds==0) {
								resetTime.add(String.valueOf(Seconds)+"초");
							}
							else if(Hours==0&&Minutes!=0) {
								resetTime.add(String.valueOf(Minutes)+"분 "+String.valueOf(Seconds)+"초");
							}
							else {
								resetTime.add(String.valueOf(Hours)+"시간 "+String.valueOf(Minutes)+"분 "+String.valueOf(Seconds)+"초");
							}
							
							
							if(rs != null) try{ rs.close();} catch(SQLException e){};
							
							total=Hours*5000+Minutes*100+Seconds*1;
							resetCost.add(total);
						}
						
						
						
						for(int i=0; i<resetID.size(); i++) {
							String update2="UPDATE user SET time_fee=time_fee+? WHERE user_id=?";
							pstmt=con.prepareStatement(update2);
							
							pstmt.setInt(1, resetCost.get(i));
							pstmt.setString(2, resetID.get(i));
							
							int j=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
							
							if(j==1) 
								//업데이트된 사항이 존재할 때
								System.out.println("record update success!!");
							else
								//업데이트된 사항이 존재하지 않을 때
								System.out.println("record update failed!!");
							
							
							String update3="UPDATE user SET seat=0 WHERE user_id=?";
							pstmt=con.prepareStatement(update3);
						
							pstmt.setString(1, resetID.get(i));
							
							int k=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
							
							if(k==1) 
								//업데이트된 사항이 존재할 때
								System.out.println("record update success!!");
							else
								//업데이트된 사항이 존재하지 않을 때
								System.out.println("record update failed!!");
						}
						
						
					} catch(SQLException e ) {
						System.out.println("connection failed! in main()");
					}
					        
					if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
			        if(con != null) try{ con.close();} catch(SQLException e){};
				}
				
				
			//좌석조회
				public void seat() {
					Connection con=makeConnection();
					PreparedStatement pstmt=null;
					
					seat.clear();
					
					try {					
					    String selectOneRow="SELECT seat FROM user Where seat!=0 And seat is not null";
						pstmt=con.prepareStatement(selectOneRow);
				
						ResultSet rsT=pstmt.executeQuery();
						
						
						while(rsT.next())
						{
							int num=rsT.getInt(1);
							seat.add(num);
						}
					
						if(rsT != null) try{ rsT.close();} catch(SQLException e){};
					
					} catch(SQLException e ) {
						System.out.println("connection failed! in main()");
					}
					        
					if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
			        if(con != null) try{ con.close();} catch(SQLException e){};
				}
				
		public static void main(String[] args) {
				Connection con=makeConnection();
				PreparedStatement pstmt=null;
				
				try {	
					String update="UPDATE user SET food_fee=food_fee+? WHERE user_id=?";
					pstmt=con.prepareStatement(update);
					pstmt.setInt(1, 5000);
					pstmt.setString(2, "test20");
					
					int t=pstmt.executeUpdate();//업데이트 된 수, 1을 반환
					
					if(t==1) 
						//업데이트된 사항이 존재할 때
						System.out.println("record update success!!");
					else
						//업데이트된 사항이 존재하지 않을 때
						System.out.println("record update failed!!");
					
				} catch(SQLException e ) {
					System.out.println("seatOut connection failed! in main()");
				}
				catch(NullPointerException e ) {
					System.out.println("nullpointer");
				}
				        
				if(pstmt != null) try{ pstmt.close();} catch(SQLException e){};                   
		        if(con != null) try{ con.close();} catch(SQLException e){};
		}
}
