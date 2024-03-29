package listener.timer;

import listener.DailyListener;
import listener.task.SetOverDueTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/*
 * @Description: 管理程序开跑的任务类、时间以及频率
 */
public class TimerManager
{
    //时间间隔
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;  //24小时执行一次

    public TimerManager()
    {

        Calendar calendar = Calendar.getInstance();
        /*** 定制每日24:00执行方法 ***/

        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        System.out.println(date);


        //如果第一次执行定时任务的时间 小于 当前的时间
        //此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。循环执行的周期则以当前时间为准
        if (date.before(new Date()))
        {
            date = this.addDay(date, 1);
            System.out.println(date);
        }

        Timer timer = new Timer();
        SetOverDueTask setOverDueTask = new SetOverDueTask();

        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(setOverDueTask, date, PERIOD_DAY);
    }


    //增加几天
    public Date addDay(Date date, int num)
    {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);
        startDate.add(Calendar.DAY_OF_MONTH, num);
        return startDate.getTime();
    }

}
