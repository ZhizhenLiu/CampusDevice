# CampusDevice

- 命名编码格式说明 
  --
  - 通用: 涉及到表的字段属性时为： 表名缩写 + 表字段，然后采用驼峰命名如
    - d_no：d(device缩写) + no(device编号)
    - r_feedBack: r(reservation缩写) + feedBack(采用驼峰命名)
  - test（测试文件夹）已被标注为 "Test Resources Root" ,内容均是调试，不在项目代码范围内部，自行无视忽略
  
  
最新修改：
--
- 添加了ExcelUtils，里面含有三个函数：ReadExcel、DbToExcel和ExcelToDb，意思分别是读Excel表、导出Excel和导入Excel
- 添加了TransformUtils，这个工具类主要是为了各种类型之间转换的方便
- 在Device表里面添加了getDevice和getDeviceByKeyword
- getDevice主要是获取当前除d_photo以外的其他的数据，
- getDeviceByKeyword是一个搜索功能，通过用户输入的关键字搜索需要的Device
- 暂时尚未有数据库对照片的处理

- 修改说明 
  --
  - bean层: 部分JavaBean添加附属属性
  - dao层:
    - 解耦合
    - 根据不同的表命名dao
    - 获取内容改为分页查询
  - service: service调用daoc进行逻辑处理判断
  - servlet: admin文件夹下新增：处理预约、预约详情
  - code: 重新梳理结构，修改代码，增加注释
- 新功能测试
  -
  - 测试完成
    - 用户
        - 注册
        - 浏览热门设备 
        - 查看设备信息         
        - 预约设备
    - 管理员
        - 获取预约设备
        - 同意设备预约请求
  - 待测试
