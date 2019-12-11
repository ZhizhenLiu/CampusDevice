package service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public interface SystemService
{

    //对逾期未归还的人扣除其信用积分，逾期一个星期扣1分，超过一个星期小于等于一个月再扣2分，大于一个月再扣2分，之后每个月扣5分
    int deductCreditFromUser();

    /*
     * @Description: 用户登陆校验
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    JSONObject isUserExist(String wechatID);

}
