package dao.impl;

import dao.TrackDao;
import utils.JDBCUtils;

import java.security.spec.PSSParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackDaoImpl implements TrackDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 判断是否正在跟踪设备
     * @Param u_no  d_no
     * @Return: boolean
     */
    public boolean isTracking(String u_no, String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        boolean flag = false;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM track WHERE u_no = ? AND d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, u_no);
            pStmt.setString(2, d_no);

            rs = pStmt.executeQuery();
            if (rs.next())
            {
                flag = true;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(rs, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 用户跟踪设备
     * @Param u_no  d_no
     * @Return: int
     */
    public int trackDevice(String u_no, String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int flag = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "INSERT INTO track (U_NO, D_NO) VALUES ( ?, ?)";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, u_no);
            pStmt.setString(2, d_no);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 用户取消跟踪设备
     * @Param u_no  d_no
     * @Return: int
     */
    public int cancelTrackDevice(String u_no, String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        int flag = 0;

        try
        {
            con = JDBCUtils.getConnection();
            sql = "DELETE FROM track WHERE u_no = ? AND d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, u_no);
            pStmt.setString(2, d_no);

            flag = pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return flag;
    }

    /*
     * @Description: 获取该设备所有的正在跟踪者的编号
     * @Param d_no
     * @Return: java.util.List<java.lang.String>
     */
    public List<String> getTrackingUserNoList(String d_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<String> userNoList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT u_no FROM track WHERE d_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, d_no);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                userNoList.add(rs.getString("u_no"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            JDBCUtils.closeAll(null, pStmt, con);
        }
        return userNoList;
    }
}
