package dao;

import bean.Specialty;

import java.util.List;

public interface SpecialtyDao
{
    /*
     * @Description: 获取所有专业信息
     * @Param
     * @Return: java.util.List<bean.Specialty>
     */
    List<Specialty> getAllSpecialty();

    /*
     * @Description: 根据学院编号获取专业信息
     * @Param am_no
     * @Return: java.util.List<bean.Specialty>
     */
    List<Specialty> getSpecialtyByAcademyNo(int am_no);
}
