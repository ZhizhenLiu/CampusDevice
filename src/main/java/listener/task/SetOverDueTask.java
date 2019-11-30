package listener.task;

import dao.BorrowDao;
import dao.impl.BorrowDaoImpl;

import java.util.TimerTask;

/*
 * @Description: 需要定时执行的任务类:借用表中逾期时，设置借用状态b_state=-1
 */
public class SetOverDueTask extends TimerTask
{

    public void run()
    {
        BorrowDao borrowDao = new BorrowDaoImpl();
        int flag = borrowDao.setAllOverDueState();
        if (flag == 1)
        {
            System.out.println("自动设置：已设置借用逾期状态为-1");
        }
        else
        {
            System.out.println("自动设置：暂时没有逾期未还设备");
        }
    }
}
