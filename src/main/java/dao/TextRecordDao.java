package dao;

import bean.TextRecord;

import java.util.List;

public interface TextRecordDao {

    //查看最近的所有短信记录
    List<TextRecord> getTextRecordByPage(int page,int count);

    //查询某个人在某个时间段的短信记录
    List<TextRecord> getTextRecordFromTimeByPage(String u_no,String startDate,String endDate);

    //将信息添加进text_record表中
    int addTextRecord(String u_no,String cr_content);
}
