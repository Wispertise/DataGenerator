package com.cgl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cgl.config.MyFileOutPutSetting;
import com.cgl.mapper.ColsMapper;
import com.cgl.mapper.DataTypeMapper;
import com.cgl.mapper.SqlDataMapper;
import com.cgl.mapper.TableDataMapper;
import com.cgl.model.ResponseResult;
import com.cgl.model.dto.AddWordsDTO;
import com.cgl.model.dto.Col;
import com.cgl.model.dto.SqlTable;
import com.cgl.model.dto.UserCols;
import com.cgl.model.entity.User;
import com.cgl.model.po.DataType;
import com.cgl.model.po.SqlData;
import com.cgl.model.po.TableData;
import com.cgl.model.vo.DataTypeVo;
import com.cgl.model.vo.SqlRes;
import com.cgl.model.vo.WordsVO;
import com.cgl.service.SqlGenService;
import com.cgl.utils.JwtUtil;
import com.cgl.utils.RandomDataCreateUtil;
import com.cgl.utils.TxtWriterUtil;
import org.apache.logging.log4j.util.StringBuilders;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @description:sql生成服务实现类
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
@Service
public class SqlGenServiceImpl implements SqlGenService {

    private static final String PEOPLE_NAME = "人名";
    private static final String LOCATION = "地名";
    private static final String UNIVERSITY = "大学";
    private static final String EMAIL = "邮箱";
    private static final String PHONE_NUM = "手机号";
    private static final String DATE_DATA = "日期";
    private static final String SEX_DATA = "性别";
    private static final String NUM_DATA = "数字";
    private static final String STRING_DATA = "字符串";
    private static final String RANDON_DATA = "随机";

    @Autowired
    SqlDataMapper sqlDataMapper;
    @Autowired
    RandomDataCreateUtil randomDataCreateUtil;
    @Autowired
    DataTypeMapper dataTypeMapper;

    @Autowired
    TableDataMapper tableDataMapper;
    @Autowired
    ColsMapper colsMapper;

    @Value("${info.Excelpath}")
    String path;

    @Override
    public SqlRes sqlGenerate(SqlTable sqlTable, String userId) {
        //构造数据库表生成语句
        String template = "CREATE TABLE ";
        String tableName = sqlTable.getTable_name();
        String tableComment = sqlTable.getTable_comment();
        ArrayList<Col> cols = sqlTable.getCols();
        template += tableName + " ( ";
        for (Col col : cols) {
            //当生成的数据类型包含char并且规则为字符串时,能够指定长度,否则按照255处理
            String subvarchar;

            if (col.getCol_type().contains("char") && col.getVal_rule().equals(STRING_DATA) && Strings.isNotEmpty(col.getVal_start())) {
                subvarchar = col.getCol_type() + "(" + col.getVal_start() + ")";
            } else if (col.getCol_type().contains("char")) {
                subvarchar = col.getCol_type() + "(255)";
            } else {
                subvarchar = col.getCol_type();
            }
            template += "\n\t\t\t\t" + col.getCol_name() + " " + subvarchar;
            template += col.getIs_key().equals("true") ? " PRIMARY KEY" : "";
            template += col.getIs_notNull().equals("true") ? " NOT NULL" : "";
            template += col.getAuto_increase().equals("true") ? " AUTO_INCREMENT" : "";
            if (Strings.isNotEmpty(col.getDefault_val())) {
                template += " DEFAULT ";
                template += !col.getCol_type().contains("varchar") ? "'" + col.getDefault_val() + "'" : col.getDefault_val();
            }
            template += Strings.isNotEmpty(col.getCol_comment()) ? " COMMENT " + "'" + col.getCol_comment() + "'" : "";
            template += ",";
        }
        template = template.substring(0, template.length() - 1) + " \n  ) ";
        //构造插入语句
        int n = sqlTable.getRow_num();
        //数据生成
        ArrayList<ArrayList<String>> datalist = new ArrayList<>();
        int key = -1;
        boolean isRandomNum = false;//数字数据是否随机生成
        boolean isRandomDate = false;//日期是否随机生成
        List<DataType> dataTypes = dataTypeMapper.getDataType(userId);
//        System.out.println("dataTypes" + dataTypes);
        for (int index = 0; index < cols.size(); index++) {
            for (DataType dataType : dataTypes) {
                //生成的数据为自动生成的情况
                if (dataType.getTypeName().equals(cols.get(index).getVal_rule())) {
                    if (dataType.getIsRandomCrea() == 1) {
                        switch (cols.get(index).getVal_rule()) {
                            case EMAIL:
                                datalist.add((ArrayList<String>) randomDataCreateUtil.FakeEmail(n));
                                break;
                            case PHONE_NUM:
                                datalist.add((ArrayList<String>) randomDataCreateUtil.FakeTel(n));
                                break;
                            case DATE_DATA:
                                isRandomDate = cols.get(index).getVal_type().equals(RANDON_DATA) ? true : false;
                                datalist.add((ArrayList<String>) RandomDataCreateUtil.FakeDay("2001-1-1", n, isRandomDate));
                                break;
                            case SEX_DATA:
                                datalist.add((ArrayList<String>) randomDataCreateUtil.FalkeSex(n));
                                break;
                            case NUM_DATA:
                                isRandomNum = cols.get(index).getVal_type().equals(RANDON_DATA) ? true : false;
                                datalist.add((ArrayList<String>) randomDataCreateUtil.FakeNum(Integer.valueOf(cols.get(index).getVal_start()), n, isRandomNum));
                                break;
                            case STRING_DATA:
                                datalist.add((ArrayList<String>) randomDataCreateUtil.FakeString(Integer.valueOf(cols.get(index).getVal_start()), n));
                                break;
                        }
                        break;
                    } else { //生成数据为数据库中的数据时
                        List<String> strings = sqlDataMapper.RandomGet(dataType.getTypeName(), n);
                        int le = strings.size();
                        while (le < n) {
                            strings.addAll(sqlDataMapper.RandomGet(dataType.getTypeName(), n - le));
                            le = strings.size();
                        }
                        datalist.add((ArrayList<String>) strings);
                    }
                }
            }
        }
        //生成sql
//        System.out.println("datalistSize" + datalist.get(0).size());
        String insert_sql = "";
        for (int i = 0; i < n; i++) {
            insert_sql += "INSERT INTO " + sqlTable.getTable_name() + " VALUES(";
            for (int j = 0; j < datalist.size(); j++) {
//                System.out.println("index i = " + i + "index j = " + j);
                insert_sql += cols.get(j).getVal_rule().equals(NUM_DATA) ? datalist.get(j).get(i).toString() + "," : " '" + datalist.get(j).get(i).toString() + "',";
            }
            insert_sql = insert_sql.substring(0, insert_sql.length() - 1) + ");\n";
        }

        //表头
        List<String> headers = new ArrayList();
        for (Col col : cols) {
            headers.add(col.getCol_name());
        }
//        System.out.println("headers"+headers);
        //生成json
        StringBuilder jsonsb = new StringBuilder();
        jsonsb.append("[");
        StringBuilder subJsonsb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            subJsonsb.append("{");
            for (int j = 0; j < headers.size(); j++) {
                if (cols.get(j).getVal_rule().equals(NUM_DATA)) {
                    subJsonsb.append("\"" + headers.get(j) + "\"" + ":" + datalist.get(j).get(i));
                } else {
//                    System.out.println("i= "+i+" j= "+j);
                    subJsonsb.append("\"" + headers.get(j) + "\"" + ":\"" + datalist.get(j).get(i) + "\"");
                }
                if (j != headers.size() - 1) {
                    subJsonsb.append(",\n");
                }
            }
            if (i != n - 1) {
                subJsonsb.append("},\n");
            } else {
                subJsonsb.append("}]");
            }
            jsonsb.append(subJsonsb);
            //清空
            subJsonsb.setLength(0);
        }
        System.out.println(jsonsb);
//        生成excel文件
//        创建 Excel 工作簿和工作表
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i));
        }
        // 写入数据到 Excel 文件
        int rowNum = 1;
        for (int i = 0; i < n; i++) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (int j = 0; j < datalist.size(); j++) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(datalist.get(j).get(i).toString());
            }
        }
        // 保存 Excel 文件
        try {
            String excelpath = path + userId + "\\";
            isChartPathExist(excelpath);
            FileOutputStream outputStream = new FileOutputStream(path + userId + "\\" + userId + ".xlsx");
            FileOutputStream outputStream1 = new FileOutputStream(path + userId + "\\" + userId + ".csv");
            workbook.write(outputStream);
            workbook.write(outputStream1);
            workbook.close();
            outputStream.close();
            outputStream1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //生成txt
        // 指定保存文件的路径和文件名
        String filePath = path + userId + "\\";
        TxtWriterUtil.writeIntoTxt(filePath, datalist, headers, n, userId);
//        System.out.println(list);
        System.out.println(template);
        return new SqlRes(template, insert_sql, jsonsb.toString());

    }

    private static void isChartPathExist(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @return List<AddWordsDTO>
     * @author SunnyDeer
     * @description查询所有公共词库
     * @date 2023/3/19 20:23
     */
    @Override
    public List<WordsVO> ShowAllPublicWords() {
        List<WordsVO> lists = new ArrayList<>();
        AddWordsDTO addWordsDTO = new AddWordsDTO();
        WordsVO wordsVO = new WordsVO();
        QueryWrapper dataTypeswrapper = new QueryWrapper();
        dataTypeswrapper.eq("limits", "public");
        List<DataType> dataTypes = dataTypeMapper.selectList(dataTypeswrapper);
        for (DataType dataType : dataTypes) {
            if (dataType.getIsRandomCrea() == 0) {
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("data_limits", "public");
                wrapper.eq("data_type", dataType.getTypeName());
                List<SqlData> list = sqlDataMapper.selectList(wrapper);
                if (list.size() == 0) {
                    continue;
                }
                String value = "";
                for (int i = 0; i < list.size(); i++) {
                    value += list.get(i).getValue() + "、";
                }
                wordsVO.setLimits("public");
                wordsVO.setTable_name(list.get(0).getDataType());
                wordsVO.setUser_id(list.get(0).getUserId());
                wordsVO.setValue(value);
                lists.add(wordsVO);
                wordsVO = new WordsVO();
            }
        }
        return lists;
    }

    /**
     * @param userid: 查询用户的id
     * @return List<AddWordsDTO>
     * @author SunnyDeer
     * @description 查询个人所有词库
     * @date 2023/3/19 20:23
     */

    @Override
    public List<WordsVO> ShowAllPersonalWords(String userid) {

        List<WordsVO> lists = new ArrayList<>();
        WordsVO wordsVO = new WordsVO();
        QueryWrapper dataTypeswrapper = new QueryWrapper();
        dataTypeswrapper.eq("user_id", userid);
        dataTypeswrapper.eq("is_delete", 0);
        List<DataType> dataTypes = dataTypeMapper.selectList(dataTypeswrapper);
        for (DataType dataType : dataTypes) {
            if (dataType.getIsRandomCrea() == 0) {
                QueryWrapper wrapper = new QueryWrapper();
//                wrapper.eq("data_limits", "private");
                wrapper.eq("user_id", userid);
                wrapper.eq("data_type", dataType.getTypeName());
                List<SqlData> list = sqlDataMapper.selectList(wrapper);
                String value = "";
                if (list.size() == 0) {
                    continue;
                }
                for (int i = 0; i < list.size(); i++) {
                    value += list.get(i).getValue() + "、";
                }
                wordsVO.setLimits(list.get(0).getDataLimits());
                wordsVO.setTable_name(list.get(0).getDataType());
                wordsVO.setUser_id(list.get(0).getUserId());
                wordsVO.setValue(value);
                lists.add(wordsVO);
                wordsVO = new WordsVO();
            }
        }
        return lists;
    }

    /**
     * @param addWordsDTO:
     * @return int
     * @author SunnyDeer
     * @description 添加新的词库
     * @date 2023/3/19 20:23
     */
    @Override
    public int AddNewWords(AddWordsDTO addWordsDTO, String userid) {

        List<DataType> dataTypes = dataTypeMapper.selectList(null);
        boolean isexist = true;
        for (DataType dataType : dataTypes) {
            if (dataType.getTypeName().equals(addWordsDTO.getTable_name())) {
                isexist = false;
            }
        }
        if (isexist) {
            DataType dataType = new DataType();
            dataType.setTypeName(addWordsDTO.getTable_name());
            dataType.setIsRandomCrea(0);
            dataType.setLimits(addWordsDTO.getLimits());
            dataType.setUserId(userid);
            dataTypeMapper.insert(dataType);
        }
        int flag = 1;
        String strs[] = addWordsDTO.getValue().split("、");
        System.out.println(strs);
        for (String str : strs) {
            SqlData sqlData = new SqlData();
            sqlData.setValue(str);
            sqlData.setDataType(addWordsDTO.getTable_name());
            sqlData.setDataLimits(addWordsDTO.getLimits());
            sqlData.setUserId(userid);
            sqlData.setIsDelete(0);
            System.out.println(sqlData);
            if (sqlDataMapper.insert(sqlData) < 0) {
                flag = 1;
            }
        }
        return flag;
    }

    /**
     * @param tableData:
     * @return int
     * @author SunnyDeer
     * @description写入生成sql的历史记录
     * @date 2023/3/19 20:23
     */
    //已测试
    @Override
    public int WriteTableHistory(TableData tableData) {

        return tableDataMapper.insert(tableData);
    }

    /**
     * @param :
     * @return List<TableData>
     * @author SunnyDeer
     * @description 获取公共tabledata
     * @date 2023/3/19 20:22
     */
    //已测试
    @Override
    public List<TableData> getTableData() {

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("limits", "public");
        wrapper.eq("is_delete", 0);
        List<TableData> tableData = tableDataMapper.selectList(wrapper);
        return tableData;
    }

    /**
     * @param userid:
     * @return List<TableData>
     * @author SunnyDeer
     * @description 获取个人tabledata
     * @date 2023/3/19 20:22
     */
    //已测试
    @Override
    public List<TableData> getPrivateTableData(String userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userid);
        wrapper.eq("is_delete", 0);
        List<TableData> tableData = tableDataMapper.selectList(wrapper);
        return tableData;
    }

    /**
     * @param UserId:
     * @param TableName:
     * @return int
     * @author SunnyDeer
     * @description 将私有tabeldata变为公有
     * @date 2023/3/19 20:22
     */
    //已测试
    @Override
    public int publishTabelData(String UserId, String TableName) {
        int i = tableDataMapper.setPrivateToPublic(UserId, TableName);
        return i;
    }

    /**
     * @param UserId:
     * @param TableName:
     * @return int
     * @author SunnyDeer
     * @description 将公有tableData设为私有
     * @date 2023/3/20 12:51
     */
    //已测试
    @Override
    public int privateTabelData(String UserId, String TableName) {
        int i = tableDataMapper.setPublicToPrivate(UserId, TableName);
        return i;
    }

    /**
     * @param UserId:用户信息
     * @param DataType:词库名称
     * @return int
     * @author SunnyDeer
     * @description 将私有词库变为公有
     * @date 2023/3/19 20:22
     */
    //已测试
    @Override
    public int publishSqlData(String UserId, String DataType) {
        int i = sqlDataMapper.setPrivateToPublic(UserId, DataType);
        int j = dataTypeMapper.setPrivateToPublic(UserId, DataType);
        return i * j;
    }

    /**
     * @param UserId:
     * @param DataType:
     * @return int
     * @author SunnyDeer
     * @description 将公有sqlData变为私有
     * @date 2023/3/20 8:15
     */
//已测试
    @Override
    public int privateSqlData(String UserId, String DataType) {
        int i = sqlDataMapper.setPublicToPrivate(UserId, DataType);
        int j = dataTypeMapper.setPublicToPrivate(UserId, DataType);
        return i * j;
    }

    /**
     * @param UserId:
     * @param TableName:
     * @return int
     * @author SunnyDeer
     * @description 删除tabledata
     * @date 2023/3/19 20:22
     */
//已测试
    @Override
    public int deleteTableData(String UserId, String TableName) {
        int i = tableDataMapper.fakeDeleteData(UserId, TableName);
        return i;
    }

    /**
     * @param UserId:
     * @param DataType:
     * @return int
     * @author SunnyDeer
     * @description 删除用户词库
     * @date 2023/3/19 20:42
     */
    //已测试
    @Override
    public int deletesqlData(String UserId, String DataType) {
        //删除sqldata
        int i = sqlDataMapper.fakeDelete(UserId, DataType);
        //删除数据type
        int j = dataTypeMapper.fakeDelete(UserId, DataType);
        return i * j;
    }


    @Override
    public int AddNewCol(UserCols userCols) {
        int i = colsMapper.insert(userCols);
        return i;
    }

    @Override
    public List<UserCols> getAllPublicCols() {
        QueryWrapper<UserCols> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("limits", "public");
        queryWrapper.eq("is_delete", 0);
        List<UserCols> userCols = colsMapper.selectList(queryWrapper);
        return userCols;
    }

    @Override
    public List<UserCols> getPersonalCols(String userId) {
        QueryWrapper<UserCols> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("is_delete", 0);
        List<UserCols> userCols = colsMapper.selectList(queryWrapper);
        return userCols;
    }

    @Override
    public int publishCols(String userId, int colId) {

        int i = colsMapper.setPrivateToPublic(userId, colId);
        return i;
    }

    @Override
    public int privateCols(String userId, int colId) {
        int i = colsMapper.setPublicToPrivate(userId, colId);
        return i;
    }

    @Override
    public int deleteCols(String userId, int colId) {
        int i = colsMapper.fakeDelete(userId, colId);
        return i;
    }

    @Override
    public String getIdByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            String userid = JwtUtil.parseJWT(token).getSubject();
            return userid;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DataTypeVo> getDataType(String userId) {
        List<DataType> dataTypes = dataTypeMapper.getDataType(userId);
        List<DataTypeVo> dataTypeVos = new ArrayList<>();
        for (DataType type : dataTypes) {
            DataTypeVo dataTypeVo = new DataTypeVo();
            BeanUtils.copyProperties(type, dataTypeVo);
            dataTypeVos.add(dataTypeVo);
        }
        return dataTypeVos;
    }

    @Override
    public int getTableDataNumByDate(String date, String userid) {
        return tableDataMapper.getNumByDate(date, userid);
    }

    @Override
    public int getDataTypeNumById(String userId) {
        return dataTypeMapper.getNumById(userId);
    }

    @Override
    public int getColsNumById(String userId) {
        return colsMapper.getNumById(userId);
    }
    @Override
    public ResponseResult checkExist(String type, String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        List list = new ArrayList();
        if (type.equals("tabledata")) {
            queryWrapper.eq("table_name", name);
            list = tableDataMapper.selectList(queryWrapper);
        }else if (type.equals("col")){
            queryWrapper.eq("col_name", name);
            list = colsMapper.selectList(queryWrapper);
        }else if(type.equals("sqldata")){
            queryWrapper.eq("type_name", name);
            list = dataTypeMapper.selectList(queryWrapper);
        }else{
            return ResponseResult.okResult(301,"错误的Url参数！");
        }
        if(list.size() == 0 ){
            return ResponseResult.okResult(200,"该名称可用");
        }else{
            return ResponseResult.okResult(300,"该名称已存在");
        }
    }
}
