package dao.impl;

import bean.Admin;
import bean.Specialty;
import dao.SpecialtyDao;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyDaoImpl implements SpecialtyDao
{
    private Connection con;
    private PreparedStatement pStmt;
    private ResultSet rs;
    private String sql;

    /*
     * @Description: 获取所有专业信息
     * @Param
     * @Return: java.util.List<bean.Specialty>
     */
    public List<Specialty> getAllSpecialty()
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Specialty> specialtyList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM specialty s, academy am WHERE s.am_no = am.am_no";
            pStmt = con.prepareStatement(sql);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Specialty specialty = new Specialty();
                specialty.setS_no(rs.getInt("s_no"));
                specialty.setS_name(rs.getString("s_name"));
                specialty.setAm_no(rs.getInt("am_no"));
                specialty.setAm_name(rs.getString("am_name"));
                specialtyList.add(specialty);
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
        return specialtyList;
    }

    /*
     * @Description: 根据学院编号获取专业信息
     * @Param am_no
     * @Return: java.util.List<bean.Specialty>
     */
    public List<Specialty> getSpecialtyByAcademyNo(int am_no)
    {
        //初始化
        con = null;
        pStmt = null;
        rs = null;

        List<Specialty> specialtyList = new ArrayList<>();
        try
        {
            con = JDBCUtils.getConnection();
            sql = "SELECT * FROM specialty s WHERE am_no = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, am_no);
            rs = pStmt.executeQuery();

            while (rs.next())
            {
                Specialty specialty = new Specialty();
                specialty.setS_no(rs.getInt("s_no"));
                specialty.setS_name(rs.getString("s_name"));
                specialty.setAm_no(rs.getInt("am_no"));
                specialtyList.add(specialty);
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
        return specialtyList;
    }
}
