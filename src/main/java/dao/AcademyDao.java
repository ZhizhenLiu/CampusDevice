package dao;

import bean.Academy;

import java.util.List;

public interface AcademyDao
{

    /*
     * @Description: 获取所有学院信息，不包括专业
     * @Param
     * @Return: java.util.List<bean.Academy>
     */
    public List<Academy> getAllAcademy();
}
