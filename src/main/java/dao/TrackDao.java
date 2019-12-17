package dao;

import java.util.List;

public interface TrackDao
{
    /*
     * @Description: 判断是否正在跟踪设备
     * @Param u_no  d_no
     * @Return: boolean
     */
    boolean isTracking(String u_no, String d_no);

    /*
     * @Description: 用户跟踪设备
     * @Param u_no  d_no
     * @Return: int
     */
    int trackDevice(String u_no, String d_no);

    /*
     * @Description: 用户取消跟踪设备
     * @Param u_no  d_no
     * @Return: int
     */
    int cancelTrackDevice(String u_no, String d_no);

    /*
     * @Description: 获取该设备所有的正在跟踪者的编号
     * @Param d_no
     * @Return: java.util.List<java.lang.String>
     */
    List<String> getTrackingUserNoList(String d_no);
}
