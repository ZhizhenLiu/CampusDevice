# CampusDevice

- 编码格式说明 
  --
  - bean层: JavaBean的属性命名为：m_ + 表名缩写 + 表字段，采用驼峰命名如：
    - Device（设备）: m_Dno（m_ + D(Device缩写) + no(device编号) ）
  - test文件夹（测试）已被标注为 "Test Resources Root" ,内容均是调试，不在项目代码范围内部，自行无视忽略
  
  
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
