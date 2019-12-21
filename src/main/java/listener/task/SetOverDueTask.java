package listener.task;

import dao.BorrowDao;
import dao.impl.BorrowDaoImpl;
import service.SystemService;
import service.impl.SystemServiceImpl;

import java.util.TimerTask;

/*
 * @Description: 需要定时执行的任务类:借用表中逾期时，设置借用状态b_state=-1
 */
public class SetOverDueTask extends TimerTask
{

    public void run()
    {
        System.out.println("定时任务运行");
        BorrowDao borrowDao = new BorrowDaoImpl();
        int flag = borrowDao.setAllOverDueState();
        if (flag != 0)
        {
            System.out.println("自动设置：已设置借用逾期状态为-1");
        }
        else
        {
            System.out.println("自动设置：暂时没有逾期未还设备");
        }
        SystemService systemService = new SystemServiceImpl();
        flag = systemService.deductCreditFromUser();
        if (flag == 1)
        {
            System.out.println("自动设置：已向信用积分记录表里面添加信息");
        }
        else if(flag == 0)
        {
            System.out.println("自动设置：暂时没有逾期未还的人或者逾期未还的人已经接受了惩罚");
        }
        else
        {
            System.out.println("自动设置：插入数组错误");
        }
    }
}
