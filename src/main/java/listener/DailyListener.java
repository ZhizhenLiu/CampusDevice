package listener;

import listener.timer.TimerManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//为了能让项目初始化的时候，就去将此定时程序跑起来，可以使用监听器
@WebListener()
public class DailyListener implements ServletContextListener
{

    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("listener init");
        new TimerManager();
    }

    public void contextDestroyed(ServletContextEvent sce)
    {
    }
}
