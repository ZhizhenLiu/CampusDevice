package bean;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Academy {

  private int am_no;
  private String am_name;
  private List<Specialty> specialtyList;

  public Academy(int am_no, String am_name)
  {
    this.am_no = am_no;
    this.am_name = am_name;
    this.specialtyList = new ArrayList<>();
  }

  public Academy()
  {
  }

  public int getAm_no()
  {
    return am_no;
  }

  public void setAm_no(int am_no)
  {
    this.am_no = am_no;
  }

  public String getAm_name()
  {
    return am_name;
  }

  public void setAm_name(String am_name)
  {
    this.am_name = am_name;
  }

  public List<Specialty> getSpecialtyList()
  {
    return specialtyList;
  }

  public void setSpecialtyList(List<Specialty> specialtyList)
  {
    this.specialtyList = specialtyList;
  }

  @Override
  public String toString()
  {
    return "Academy{" +
            "am_no=" + am_no +
            ", am_name='" + am_name + '\'' +
            ", specialtyList=" + specialtyList +
            '}';
  }
}
