package utils;

import bean.Device;
import dao.BorrowDao;
import dao.DeviceDao;
import dao.impl.BorrowDaoImpl;
import dao.impl.DeviceDaoImpl;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class ExcelUtils
{

    private static Device device;

    //将excel变成Workbook
    public static Workbook excelToWorkbook(String filePath, String fileName)
    {
        try
        {
            //将excel加载到内存，获取excel的路径和名字
            File file = new File(filePath, fileName);
            InputStream inputStream = new FileInputStream(file);

            Workbook workbook = null;
            //判断excel是新版还是旧版
            if (fileName.endsWith("xlsx"))
            {
                workbook = new XSSFWorkbook(inputStream);  //新版
            }
            else if (fileName.endsWith("xls"))
            {
                workbook = new HSSFWorkbook(inputStream);  //旧版
            }
            return workbook;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    //读取Workbook内容存到list<String>中
    public static List<String> workbookToList(String filePath, String fileName)
    {
        Workbook workbook = excelToWorkbook(filePath, fileName);
        try
        {
            List<String> list = new ArrayList<>();
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
                //第i页表有rowEnd-rowStart行
                int rowStart = workSheet.getFirstRowNum();
                int rowEnd = workSheet.getLastRowNum();
                //第row行有columnEnd-columnStart列
                int columnStart = workSheet.getRow(rowStart).getFirstCellNum();
                int columnEnd = workSheet.getRow(rowStart).getLastCellNum();

                //循环获取j行第k列的内容
                //j=rowStart+1开头即去掉第1行的标题
                for (int j = rowStart + 1; j <= rowEnd; j++)
                {
                    for (int k = columnStart; k < columnEnd; k++)
                    {
                        Row workRow = workSheet.getRow(j);
                        //读取的这一行不为空
                        if (workRow != null)
                        {
                            Cell cell = workRow.getCell(k);
                            list.add(getCellValue(cell));
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

    //判断excel表标题是否合格(必须有标题)
    public static boolean isTitleValid(String filePath, String fileName)
    {
        Workbook workbook = excelToWorkbook(filePath, fileName);
        try
        {
            int flag = 1;
            //规定excel表的顺序
            String title[] = {"仪器标号", "仪器名称", "型号", "存放地", "出厂号", "状态", "入库日期"};
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
                //第i页表有rowEnd-rowStart行
                int rowStart = workSheet.getFirstRowNum();
                int rowEnd = workSheet.getLastRowNum();
                //第row行有columnEnd-columnStart列
                int columnStart = workSheet.getRow(rowStart).getFirstCellNum();
                int columnEnd = workSheet.getRow(rowStart).getLastCellNum();
                //检查标题是否有前后缺失
                for (int j = rowStart; j <= rowEnd; j++)
                {
                    if (workSheet.getRow(j).getLastCellNum() - workSheet.getRow(j).getFirstCellNum() > columnEnd - columnStart)
                    {
                        System.out.println(fileName + "的 第" + (i + 1) + "页 的标题缺失！");
                        System.out.println("请补充标题后输入");
                        flag = 0;
                        break;
                    }
                }
                //若标题没有前后缺失
                if (flag == 1)
                {
                    for (int j = columnStart; j < columnEnd; j++)
                    {
                        //检查标题是否中间部分有缺失
                        if (getCellValue(workSheet.getRow(0).getCell(j)).equals(""))
                        {
                            System.out.println(fileName + "的 第" + (i + 1) + "页 的标题缺失！");
                            System.out.println("请补充标题后输入");
                            flag = 0;
                            break;
                        }
                        //检查标题是否正确
                        if (!title[j].equals(getCellValue(workSheet.getRow(0).getCell(j))))
                        {
                            System.out.println("标题顺序错误！请按以下顺序调整excel表：");
                            for (int k = 0; k < title.length; k++)
                            {
                                System.out.print(title[k] + " ");
                            }
                            System.out.println();
                            flag = 0;
                            break;
                        }
                    }
                }
            }

            if (flag == 0)
            {
                return false;
            }
            return true;
        }
        catch (Exception e)
        {
            System.out.println("读取excel表出现问题");
            e.printStackTrace();
        }
        return false;
    }

    //判断excel表的内容是否有效，判断状态等部分
    public static boolean isStateValid(String filePath, String fileName)
    {
        Workbook workbook = excelToWorkbook(filePath, fileName);
        try
        {
            int flag = 1;
            //规定状态只能为：在库、外借、损坏、报废
            String state[] = {"在库", "外借", "损坏", "报废"};
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
                //第i页表有rowEnd-rowStart行
                int rowStart = workSheet.getFirstRowNum();
                int rowEnd = workSheet.getLastRowNum();
                //检查标题为状态栏的情况
                for (int j = rowStart + 1; j <= rowEnd; j++)
                {
                    String str = getCellValue(workSheet.getRow(j).getCell(5));
                    //检查标题是否中间部分有缺失
                    if (!str.equals("在库") && !str.equals("外借") && !str.equals("损坏") && !str.equals("报废"))
                    {
                        System.out.println(fileName+"的 第" + (i + 1) + "页 的状态错误！");
                        System.out.print("状态只能为：");
                        for (int k = 0; k < state.length - 1; k++)
                        {
                            System.out.print(state[k] + "、");
                        }
                        System.out.println(state[state.length - 1]);
                        System.out.println();
                        flag = 0;
                        break;
                    }
                }
            }

            if (flag == 0)
            {
                return false;
            }
            return true;
        }
        catch (Exception e)
        {
            System.out.println("读取excel表出现问题");
            e.printStackTrace();
        }
        return false;
    }

    //判断excel表的内容是否有效，判断编号是否重复
    public static boolean isDeviceNoValid(String filePath, String fileName)
    {
        Workbook workbook = excelToWorkbook(filePath, fileName);
        DeviceDao deviceDao = new DeviceDaoImpl();
        try
        {
            int flag = 1;
            //通过循环工作表Sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++)
            {
                //获取第i页
                Sheet workSheet = workbook.getSheetAt(i);
                if (workSheet == null)
                {
                    continue;
                }
                //第i页表有rowEnd-rowStart行
                int rowStart = workSheet.getFirstRowNum();
                int rowEnd = workSheet.getLastRowNum();
                //检查标题为状态栏的情况
                for (int j = rowStart + 1; j <= rowEnd; j++)
                {
                    String d_no = getCellValue(workSheet.getRow(j).getCell(0));
                    //判断是否有重复的设备仪器
                    if(deviceDao.getDeviceState(d_no).equals("在库") || deviceDao.getDeviceState(d_no).equals("外借"))
                    {
                        System.out.println(fileName + "的 第" + (i + 1) + "页 的编号为"+d_no+"的仪器已存在! 请勿重复导入");
                        flag = 0;
                    }
                    else if(deviceDao.getDeviceState(d_no).equals("损坏") || deviceDao.getDeviceState(d_no).equals("报废"))
                    {
                        System.out.println(fileName + "的 第" + (i + 1) + "页 的编号为"+d_no+"的仪器已存在! 请勿重复导入");
                        flag = 0;
                    }
                }
            }

            return flag != 0;
        }
        catch (Exception e)
        {
            System.out.println("读取excel表出现问题");
            e.printStackTrace();
        }
        return false;
    }

    //从数据库中导出excel，注意这里生成的文件是.xlsx格式的
    public static void dbToExcel(String filePath, String fileName, String a_no)
    {
        //创建HSSFWorkbook对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建Sheet对象
        XSSFSheet sheet = workbook.createSheet("未归还设备人员名单");
        //获取Device表中的内容
        BorrowDao borrowDao = new BorrowDaoImpl();
        List<String> list = borrowDao.getOverDueList(a_no);
        //设置格子宽高
        for (int i = 0; i < 8; i++)
        {
            sheet.setColumnWidth(i, 4000);
        }
        sheet.setColumnWidth(8, 7000);
        //获取行数和列数
        int row = list.size() / 9;
        int column = 9;
        //第0行为标题部分
        String title[] = {"逾期人学工号", "逾期人名", "设备编号", "设备名", "借用日期", "应当归还日期", "实际归还日期", "借用人信用分", "管理仪器的管理员的姓名"};
        XSSFRow firstRow = sheet.createRow(0);
        for (int i = 0; i < 9; i++)
        {
            firstRow.createCell(i).setCellValue(title[i]);
        }
        //赋值给i行j列
        for (int i = 1; i <= row; i++)
        {
            XSSFRow xssfRow = sheet.createRow(i);
            for (int j = 0; j < 9; j++)
            {
                xssfRow.createCell(j).setCellValue(list.get(j + 9 * (i - 1)));
            }
        }

        //输出Excel文件
        File file = new File(filePath, fileName);
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //将workbook的内容写入file
            workbook.write(fileOutputStream);
            workbook.close();
            System.out.println("导出excel表成功！");
        }
        catch (Exception e)
        {
            System.out.println("导出Excel出现问题！");
            e.printStackTrace();
        }
    }

    //导入excel，这里照片不通过excel导入数据库
    public static void excelToDb(String filePath, String fileName, String adminNo)
    {
        List<String> list = workbookToList(filePath, fileName);
        try
        {
            Connection conn = JDBCUtils.getConnection();
            //根据需要改sql
            String sql = "insert into device(a_no, d_no, d_name, d_model, d_save_site, d_factory_no, d_state, d_store_date) values";
            for (int i = 0; i < list.size() / 7 - 1; i++)
            {
                sql += "(?,?,?,?,?,?,?,?),";
            }
            sql += "(?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            //添加值
            int t = 1;
            for (int i = 0; i < list.size(); i++)
            {
                if (i % 7 == 0)
                {
                    ps.setString(t, adminNo);
                    t++;
                }
                ps.setString(t, list.get(i));
                t++;
            }

            //执行
            ps.executeUpdate();
            System.out.println("导入成功！");
            conn.close();

        }
        catch (Exception e)
        {
            System.out.println("导入失败！");
            e.printStackTrace();
        }
    }

    //自己写了个getCellValue方法，将其他类型变成String类型
    public static String getCellValue(Cell cell)
    {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellType())
        {
            case NUMERIC: // 数字
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell))
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
                }
                else
                {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case BLANK: // 空值
                cellValue = "";
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
