package com.lewei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lewei.db.DBUtil;
import com.lewei.model.Email;
import com.lewei.model.Message;
import com.lewei.model.Product;
import com.lewei.model.TPLProd;
import com.lewei.model.TPLine;
import com.lewei.model.TPPlan;

public class LineControlDao {

	private PreparedStatement pstmt;

	public void Query() throws Exception {
		Connection conn = DBUtil.getConn();
		// 3.通过数据库的连接操作数据库（增删改查）
		Statement statement = conn.createStatement();
		// 4.通过查询返回结果
		ResultSet rs = statement.executeQuery("select * from tpplantable");
		// 5.循环去除 rs 中的结果
		while (rs.next()) {
			System.out.println(rs.getObject(3));
		}
		rs.close();
		statement.close();
		conn.close();
	}

	/**
	 * 插入数据到表 tpplan ，并返回主键值。
	 * 
	 * @param tpplan
	 * @return
	 * @throws Exception
	 */
	public int insertToTpplan(TPPlan tpplan) throws Exception {
		// 获得连接
		Connection conn = DBUtil.getConn();
		// ‘？’ 为预执行所用的占位符
		String sql = "insert into tpplan(TotalNum,TPLineName,Ranger,CreateTime,CreatePeople,Status) values(?,?,?,now(),?,1)";
		// 预执行sql
		pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// RETURN_GENERATED_KEYS：返回插入的主键
		// 给占位符赋值
		pstmt.setInt(1, tpplan.getTotalNum());
		pstmt.setString(2, tpplan.getTPLineName());
		pstmt.setInt(3, tpplan.getRanger());
		pstmt.setString(4, tpplan.getCreatePeople());
		// 执行
		pstmt.executeUpdate();
		// 获取返回主键
		ResultSet rs = pstmt.getGeneratedKeys();
		int autoInckey = -1;
		if (rs.next()) {
			// 取得主键ID
			autoInckey = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return autoInckey;
	}

	/**
	 * 插入数据到表 tpline ，并返回主键值。
	 * 
	 * @param tpline
	 * @return
	 * @throws SQLException
	 */
	public int insertToTpline(TPLine tpline) throws SQLException {
		// 获得连接
		Connection conn = DBUtil.getConn();
		// ‘？’ 为预执行所用的占位符
		String sql = "INSERT INTO tpline (TPPlanID,TPLineName,Ranger,RestTime,StartTime,EndTime,Status) VALUES(?,?,?,?,?,?,1)";
		// 预执行sql
		pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// RETURN_GENERATED_KEYS：返回插入的主键
		// 给占位符赋值
		pstmt.setInt(1, tpline.getTPPlanID());
		pstmt.setString(2, tpline.getTPLineName());
		pstmt.setInt(3, tpline.getRanger());
		pstmt.setString(4, tpline.getRestTime());
		pstmt.setString(5, tpline.getStartTime());
		pstmt.setString(6, tpline.getEndTime());
		// 执行
		int a = pstmt.executeUpdate();
		// 获取返回主键
		ResultSet rs = pstmt.getGeneratedKeys();
		int autoInckey = -1;
		if (rs.next()) {
			// 取得主键ID
			autoInckey = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return autoInckey;
	}

	/**
	 * 插入数据到表 tplprod ，并返回主键值。
	 * 
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public int insertToTplprod(TPLProd p) throws SQLException {

		// 获得连接
		Connection conn = DBUtil.getConn();
		// ‘？’ 为预执行所用的占位符
		String sql = "insert into tplprod(TPLineID,ProdName,Takt,Num) values(?,?,?,?)";
		// 预执行sql
		pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// RETURN_GENERATED_KEYS：返回插入的主键
		// 给占位符赋值
		pstmt.setInt(1, p.getTPLineID());
		pstmt.setString(2, p.getProdName());
		pstmt.setDouble(3, p.getTakt());
		pstmt.setInt(4, p.getNum());
		// 执行
		pstmt.executeUpdate();
		// 获取返回主键
		ResultSet rs = pstmt.getGeneratedKeys();
		int autoInckey = -1;
		if (rs.next()) {
			// 取得主键ID
			autoInckey = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return autoInckey;
	}

	public List<String> getProductNames() throws SQLException {
		List<String> list = new ArrayList<String>();
		// 获得链接
		Connection conn = DBUtil.getConn();
		// 通过数据库的连接操作数据库
		Statement statement = conn.createStatement();
		// 通过查询返回结果
		ResultSet rs = statement.executeQuery("select ProName from product");
		// 5.循环取出 rs 中的结果
		while (rs.next()) {
			// 通过字段名取出
			list.add(rs.getString("ProName")); // 序号取出rs.getString(1);
		}
		rs.close();
		statement.close();
		conn.close();
		return list;
	}

	/**
	 * 通过名称获取产线ID。
	 * 
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public int getTplineIDByName(String name) throws SQLException {
		// 获得连接
		Connection conn = DBUtil.getConn();
		// ‘？’ 为预执行所用的占位符
		String sql = "select TPLineID from tpline where TPLineName = ?";
		// 预执行sql
		pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// RETURN_GENERATED_KEYS：返回插入的主键
		// 给占位符赋值
		pstmt.setString(1, name);
		// 执行
		ResultSet rs = pstmt.executeQuery();
		int autoInckey = -1;
		if (rs.next()) {
			// 取得主键ID
			autoInckey = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();
		return autoInckey;
	}

	/**
	 * 通过产线索引获得产品列表。
	 * 
	 * @param selectedIndex
	 * @return
	 * @throws SQLException
	 */
	public List<Product> getProWithLineIndex(int selectedIndex)
			throws SQLException {
		List<Product> proList = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT ProName,Takt,Num FROM product WHERE LineID = "
						+ selectedIndex);
		while (rs.next()) {
			Product p = new Product();
			p.setProName(rs.getString(1));
			p.setTakt(rs.getInt(2));
			p.setNum(rs.getInt(3));
			proList.add(p);
		}
		rs.close();
		statement.close();
		conn.close();
		return proList;
	}

	/**
	 * 获取所有产线的列表。
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String[] getLineList() throws SQLException {
		String[] lineList = null;
		int i = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(ID) FROM line");
		while (rs.next()) {
			lineList = new String[rs.getInt(1)];
		}
		rs = statement.executeQuery("SELECT LineName FROM line ");
		while (rs.next()) {
			lineList[i] = rs.getString(1);
			i++;
		}
		rs.close();
		statement.close();
		conn.close();
		return lineList;
	}

	/**
	 * 获取所有产线的列表，并在最前面加上‘’全部‘’选项。
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String[] getLineList2() throws SQLException {
		String[] lineList = null;
		int i = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery("SELECT COUNT(ID) FROM line");
		while (rs.next()) {
			lineList = new String[rs.getInt(1) + 1];
		}
		lineList[i] = "全部";
		rs = statement.executeQuery("SELECT LineName FROM line");
		while (rs.next()) {
			i++;
			lineList[i] = rs.getString(1);
		}
		rs.close();
		statement.close();
		conn.close();
		return lineList;
	}

	/**
	 * 根据产线ID索引删除产品。
	 * 
	 * @param selectedIndex
	 * @return
	 * @throws SQLException
	 */
	public int deleteProWithLineIndex(int selectedIndex) throws SQLException {
		int r = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		r = statement.executeUpdate("DELETE FROM product WHERE LineID = "
				+ selectedIndex);
		statement.close();
		conn.close();
		return r;
	}

	public int insertToProduct(Product p) throws SQLException {
		int r = 0;
		Connection conn = DBUtil.getConn();
		String sql = "INSERT INTO product (ProName,LineID,Takt,Num,Status) VALUES (?,?,?,?,1)";
		// 预执行sql
		pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// RETURN_GENERATED_KEYS：返回插入的主键
		// 给占位符赋值(?)
		pstmt.setString(1, p.getProName());
		pstmt.setInt(2, p.getLineID());
		pstmt.setInt(3, p.getTakt());
		pstmt.setInt(4, p.getNum());
		// 执行
		r = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		return r;

	}

	/**
	 * 添加新的号码。
	 * 
	 * @param phoneNumber
	 * @return
	 * @throws SQLException
	 */
	public int addPhoneNumber(String phoneNumber, int lineID, String remarks)
			throws SQLException {
		int row = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		row = statement
				.executeUpdate("INSERT INTO message (MesNumber, LineID, MesRemark) VALUES ('"
						+ phoneNumber + "', " + lineID + ", '" + remarks + "')");
		statement.close();
		conn.close();
		return row;
	}

	/**
	 * 查询号码是否已存在。
	 * 
	 * @param s
	 * @return
	 * @throws SQLException
	 */
	public boolean hasNumber(String s) throws SQLException {
		int row = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT COUNT(MesID) FROM message WHERE Status = 1 AND MesNumber = '"
						+ s + "'");
		while (rs.next()) {
			row = rs.getInt(1);
		}
		rs.close();
		statement.close();
		conn.close();
		return row > 0 ? true : false;
	}

	/**
	 * 删除一个号码。
	 * 
	 * @param s
	 * @return
	 * @throws SQLException
	 */
	public int deletePhoneNumber(String s) throws SQLException {
		int row = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		row = statement
				.executeUpdate("UPDATE message SET Status = 0 WHERE MesNumber = '"
						+ s + "'");
		statement.close();
		conn.close();
		return row;

	}

	/**
	 * 查询邮箱是否已存在。
	 * 
	 * @param s
	 * @return
	 * @throws SQLException
	 */
	public boolean hasEmail(String s) throws SQLException {

		int row = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT COUNT(EmailID) FROM email WHERE Status = 1 AND Email = '"
						+ s + "'");
		while (rs.next()) {
			row = rs.getInt(1);
		}
		rs.close();
		statement.close();
		conn.close();
		return row > 0 ? true : false;
	}

	/**
	 * 添加新的邮箱。
	 * 
	 * @param s
	 * @return
	 * @throws SQLException
	 */
	public int addEmail(String email, int lineID, String remarks)
			throws SQLException {
		int row = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		row = statement
				.executeUpdate("INSERT INTO email (Email, LineID, EmailRemark) VALUES ('"
						+ email + "', " + lineID + ", '" + remarks + "')");
		statement.close();
		conn.close();
		return row;
	}

	/**
	 * 删除一个邮箱。
	 * 
	 * @param s
	 * @return
	 * @throws SQLException
	 */
	public int deleteEmail(String s) throws SQLException {
		int row = 0;
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		row = statement
				.executeUpdate("UPDATE email SET Status = 0 WHERE Email = '"
						+ s + "'");
		statement.close();
		conn.close();
		return row;
	}

	/**
	 * 根据关键字、开始时间、结束时间查询产线列表。
	 * 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 * @throws SQLException
	 */
	public List<TPLine> getLineList(String key, String begin, String end)
			throws SQLException {
		List<TPLine> list = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement statement = conn.createStatement();
		ResultSet rs = statement
				.executeQuery("SELECT a.TPLineName,a.Ranger,a.PlanNum,a.RealNum,a.LostTime,a.StartTime,a.EndTime,a.WorkStations,b.CreateTime FROM tpline a,tpplan b WHERE a.TPLineName LIKE '"
						+ key
						+ "' AND a.Status = 2 AND a.TPPlanID IN (SELECT TPPlanID FROM tpplan WHERE CreateTime BETWEEN '"
						+ begin
						+ "' AND '"
						+ end
						+ "') AND a.TPLineID = b.TPPlanID");
		while (rs.next()) {
			TPLine l = new TPLine();
			l.setTPLineName(rs.getString(1));
			l.setRanger(rs.getInt(2));
			l.setPlanNum(rs.getInt(3));
			l.setRealNum(rs.getInt(4));
			l.setLostTime(rs.getString(5));
			l.setStartTime(rs.getString(6));
			l.setEndTime(rs.getString(7));
			l.setWorkStations(rs.getString(8));
			l.setChangeTime(rs.getString(9));// 这里借用换班时间保存 创建时间
			list.add(l);
		}
		rs.close();
		statement.close();
		conn.close();
		return list;
	}

	/**
	 * 获取未删除的短信记录。
	 * 
	 * @return List<Message>
	 * @throws SQLException
	 */
	public List<Message> getMesRecord() throws SQLException {
		List<Message> mesList = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement state = conn.createStatement();
		ResultSet rs = null;
		rs = state
				.executeQuery("SELECT MesID, MesNumber, MesRemark, LineID, Status FROM message WHERE Status = 1");
		while (rs.next()) {
			// 存入模型
			Message mes = new Message();
			mes.setMesID(rs.getInt(1));
			mes.setMesNumber(rs.getString(2));
			mes.setMesRemark(rs.getString(3));
			mes.setLineID(rs.getInt(4));
			mes.setStatus(rs.getInt(5));
			// 添加进入list
			mesList.add(mes);
		}
		rs.close();
		state.close();
		conn.close();
		return mesList;
	}
	
	/**
	 * 获取未删除的Email记录。
	 * 
	 * @return List<Email>
	 * @throws SQLException
	 */
	public List<Email> getEmailRecord() throws SQLException {
		List<Email> emailList = new ArrayList<>();
		Connection conn = DBUtil.getConn();
		Statement state = conn.createStatement();
		ResultSet rs = null;
		rs = state
				.executeQuery("SELECT EmailID, Email, EmailRemark, LineID, Status FROM email WHERE Status = 1");
		while (rs.next()) {
			// 存入模型
			Email email = new Email();
			email.setEmailID(rs.getInt(1));
			email.setEmail(rs.getString(2));
			email.setEmailRemark(rs.getString(3));
			email.setLineID(rs.getInt(4));
			email.setStatus(rs.getInt(5));
			// 添加进入list
			emailList.add(email);
		}
		rs.close();
		state.close();
		conn.close();
		return emailList;
	}

}
