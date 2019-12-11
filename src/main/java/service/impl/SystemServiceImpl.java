package service.impl;

import bean.Academy;
import bean.Specialty;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.*;
import dao.impl.*;
import service.SystemService;

import java.util.List;

public class SystemServiceImpl implements SystemService
{
    private AdminDao adminDao = new AdminDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private BorrowDao borrowDao = new BorrowDaoImpl();
    private CreditRecordDao creditRecordDao = new CreditRecordDaoImpl();
    private SpecialtyDao specialtyDao = new SpecialtyDaoImpl();
    private AcademyDao academyDao = new AcademyDaoImpl();

    //对逾期未归还的人扣除其信用积分，逾期一个星期扣1分，超过一个星期小于等于一个月再扣2分，大于一个月再扣2分，之后每个月扣5分
    public int deductCreditFromUser()
    {
        List<Integer> list = borrowDao.getOverDueBorrowAndDays();
        if (list.isEmpty())
        {
            System.out.println("当前没有逾期未还的人");
            return 0;
        }
        else if (list.size() % 2 != 0)
        {
            System.out.println("插入数组错误");
            return -1;
        }
        else
        {
            for (int i = 0; i < list.size(); i += 2)
            {
                int flag = creditRecordDao.addRecord(list.get(i), list.get(i + 1));
                if (flag == -1)
                {
                    System.out.println("添加信用积分变动记录失败，错误编号：" + list.get(i));
                }
            }
            return 1;
        }
    }

    /*
     * @Description: 用户登陆校验
     * @Param wechatID
     * @Return: com.alibaba.fastjson.JSONObject
     */
    public JSONObject isUserExist(String wechatID)
    {
        JSONObject info = new JSONObject();
        JSONArray errMsg = new JSONArray();
        
        /*
        1、查询对应openid的用户是否存在
        2、返回查询：flag为-1：不存在用户。
                     flag为0：用户身份为普通用户。
                     flag为1：用户身份为管理员。
         */
        int flag = -1;
        if (adminDao.getAdminByWechatID(wechatID) != null)
        {
            flag = 1;
        }
        else if (userDao.getUserByWechatID(wechatID) != null)
        {
            flag = 0;
        }
        else
        {
            flag = -1;

            //用户不存在，获取注册所需信息：学院、专业
            List<Academy> academyList = academyDao.getAllAcademy();
            System.out.println(academyList);
            for (Academy academy : academyList)
            {
                List<Specialty> specialtyList = specialtyDao.getSpecialtyByAcademyNo(academy.getAm_no());
                if (!specialtyList.isEmpty())
                {
                    for (Specialty specialty: specialtyList)
                    {
                        academy.getSpecialtyList().add(specialty);
                    }
                }
            }
            info.put("academyList", JSONArray.parseArray(JSON.toJSONString(academyList)));
        }
        info.put("flag", "1");
        info.put("identity", flag);

        return info;
    }
}
