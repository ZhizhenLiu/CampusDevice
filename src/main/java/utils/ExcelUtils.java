package utils;

import bean.Device;
import dao.DeviceDao;
import dao.impl.DeviceDaoImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils
{

    private static Device device;

    //读取excel的内容
    public static List<String> ReadExcel(String filePath)
    {
        try
        {
            List<String> list = new ArrayList<>();
            //将excel加载到内存
            File file = new File(filePath);
            String filename = file.getName();
            InputStream is = new FileInputStream(file);

            Workbook workbook = null;
            //判断excel是2003版还是2007版
            if (filename.endsWith("xlsx"))
            {
                workbook = new XSSFWorkbook(is);  //Excel 2007
            }
            else if (filename.endsWith("xls"))
            {
                workbook = new HSSFWorkbook(is);  //Excel 2003
            }

            //将导入的仪器放在device表中
            //通过循环工作表Sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++)
            {
                //获取第i页
                Sheet workSheet = workbook.getSheetAt(i);
                if (workSheet == null)
                {
                    continue;
                }
                //第i页表有column列
                int column = workSheet.getDefaultColumnWidth();
                //第i页表有row行
                int row = workSheet.getLastRowNum();

                //循环获取j行第k列的内容
                //j=1开头即去掉第1行的标题
                for (int j = 1; j <= row; j++)
                {
                    for (int k = 1; k < column; k++)  // k<=column则表示全部读取，由于不需要读取照片所以这里的k<column
                    {
                        Row workRow = workSheet.getRow(j);
                        //读取的这一行不为空
                        if (workRow != null)
                        {
                            //之前有值的getCell（）可以返回值，之前没有值会返回null
                            if (workRow.getCell(k) == null)
                            {
                                Cell cell = workRow.createCell(k);
                                cell.setCellType(CellType.STRING);
                                list.add(cell.getStringCellValue());
                            }
                            else
                            {
                                Cell cell = workRow.getCell(k);
                                //将excel表的内容转成字符串读取
                                cell.setCellType(CellType.STRING);
                                list.add(cell.getStringCellValue());
                            }
                        }
                    }
                }
            }
            return list;

        }
        catch (Exception e)
        {
            System.out.println("读取excel表出现问题");
            e.printStackTrace();
        }
        return null;
    }

    //从数据库中导出excel，注意这里生成的文件是.xlsx格式的
    public static void DbToExcel(String filePath)
    {

        //创建HSSFWorkbook对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建Sheet对象
        XSSFSheet sheet = workbook.createSheet("设备表");
        //获取Device表中的内容
        DeviceDao dd = new DeviceDaoImpl();
        List<String> list = dd.getDevice();
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i));
        }
        //获取行数和列数
        int row = list.size() / 8;
        int column = 8;
        //赋值给i行j列
        for (int i = 0; i < row; i++)
        {
            XSSFRow xssfRow = sheet.createRow(i);
            for (int j = 0; j < 8; j++)
            {
                xssfRow.createCell(j).setCellValue(list.get(8 * i + j));
            }
        }

        //输出Excel文件
        File file = new File(filePath);
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            //将workbook的内容写入file
            workbook.write(fos);
            workbook.close();

        }
        catch (Exception e)
        {
            System.out.println("导出Excel出现问题！");
            e.printStackTrace();
        }
    }

    //导入excel，这里照片不通过excel导入数据库
    public static void ExcelToDb(String filePath)
    {
        List<String> list = new ArrayList<>();
        list = ReadExcel(filePath);

        TransformUtils t = new TransformUtils();
        try
        {
            Connection conn = JDBCUtils.getConnection();
            //根据需要改sql
            String sql = "insert into device(a_no, d_state, d_borrowed_times, d_name, d_important_param, d_main_use, d_save_site) values(?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            //假设有9个属性，这里照片和编号不通过excel导入数据库
            for (int i = 0; i < list.size(); i += 7)
            {
                for (int j = 0; j < 7; j++)
                {
                    if (j == 2)
                    {
                        ps.setInt(j + 1, t.StringTransInt(list.get(i + j)));
                    }
                    else
                    {
                        ps.setString(j + 1, list.get(i + j));
                    }
                }
                ps.executeUpdate();
            }

            conn.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
