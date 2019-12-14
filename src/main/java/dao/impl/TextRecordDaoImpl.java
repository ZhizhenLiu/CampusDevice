package dao.impl;

import bean.TextRecord;
import dao.TextRecordDao;
import utils.JDBCUtils;
import utils.TransformUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TextRecordDaoImpl implements TextRecordDao {

    //查看最近的所有短信记录
    @Override
    public List<TextRecord> getTextRecordByPage(int page, int count) {
        List<TextRecord> list = new ArrayList<>();
        try{
            Connection con = JDBCUtils.getConnection();
            String sql ="select * from text_record order by tr_no desc limit ?,?";
            PreparedStatement pSmt = con.prepareStatement(sql);

            pSmt.setInt(1,(page-1)*count);
            pSmt.setInt(2,count);
            ResultSet rs =pSmt.executeQuery();
            while (rs.next())
            {
                list.add(new TextRecord(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4)));
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //查询某个人在某个时间段的短信记录
    @Override
    public List<TextRecord> getTextRecordFromTimeByPage(String u_no, String startDate, String endDate) {
        List<TextRecord> list = new ArrayList<>();
        Date sqlStartDate = TransformUtils.StringTransSQLDate(startDate);

        try{
            Connection con = JDBCUtils.getConnection();
            String sql ="select * from text_record where tr_date >= startDate and tr_date <= endDate order by tr_no desc limit ?,?";
            PreparedStatement pSmt = con.prepareStatement(sql);

            ResultSet rs =pSmt.executeQuery();
            while (rs.next())
            {
                list.add(new TextRecord(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4)));
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //将信息添加进text_record表中
    @Override
    public int addTextRecord(String u_no, String cr_content) {

        try{
            Connection con = JDBCUtils.getConnection();
            String sql ="insert into text_record(u_no,tr_content,tr_date) values(?,?,CURRENT_DATE)";
            PreparedStatement pSmt = con.prepareStatement(sql);

            pSmt.setString(1,u_no);
            pSmt.setString(2,cr_content);
            int flag = pSmt.executeUpdate();
            con.close();
            return flag;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
