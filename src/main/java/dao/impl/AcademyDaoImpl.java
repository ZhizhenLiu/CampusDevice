package dao.impl;

import bean.Academy;
import bean.Admin;
import dao.AcademyDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcademyDaoImpl implements AcademyDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 获取所有学院信息，不包括专业
     * @Param
     * @Return: java.util.List<bean.Academy>
     */
    public List<Academy> getAllAcademy()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;
        List<Academy> academyList = new ArrayList<>();

        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT DISTINCT am_no ,am_name FROM academy ";
            pStmt = con.prepareStatement(sql);
            //替换参数，从1开始
            rs = pStmt.executeQuery();
            while (rs.next())
            {
                academyList.add(new Academy(rs.getInt("am_no"), rs.getString("am_name")));
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
        return academyList;
    }
}
