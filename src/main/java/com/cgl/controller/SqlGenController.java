package com.cgl.controller;

import com.cgl.model.ResponseResult;
import com.cgl.model.dto.AddWordsDTO;
import com.cgl.model.dto.SqlTable;
import com.cgl.model.dto.UserCols;
import com.cgl.model.po.DataType;
import com.cgl.model.po.TableData;
import com.cgl.model.vo.DataTypeVo;
import com.cgl.model.vo.SqlRes;
import com.cgl.model.vo.WordsVO;
import com.cgl.service.SqlGenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;
import java.sql.Timestamp;

/**
 * @description:控制器
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
@RestController
@RequestMapping("/sqlGen")
public class SqlGenController {

    @Autowired
    SqlGenService sqlGenService;

    /**
     * @param sqlTable: 封装字段信息
     * @param request:
     * @return SqlRes
     * @author SunnyDeer
     * @description 接受建表字段信息，生成sql
     * @date 2023/3/19 19:46
     */
    @PostMapping
    public SqlRes SqlReceive(@RequestBody SqlTable sqlTable, HttpServletRequest request) throws JsonProcessingException {
        String userid = sqlGenService.getIdByToken(request);
        long d = System.currentTimeMillis();
//        Date date = new Date(d);
        ObjectMapper mapper = new ObjectMapper();
        if(sqlTable.getRow_num() <= 0 || sqlTable.getRow_num() > 2000){
            SqlRes sqlRes = new SqlRes();
            sqlRes.setCreate_sql("请输入正确的生成条数,范围为0~2000");
            return sqlRes;
        }
        SqlRes sqlRes = sqlGenService.sqlGenerate(sqlTable, userid);

        //创建对象
        SqlTable sqlTable1 = new SqlTable();
        //将java对象转换为json字符串
        String create_sql = mapper.writeValueAsString(sqlTable);
//        System.out.println("str---" + create_sql);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TableData tableData = new TableData(null, sqlTable.getTable_name(), create_sql,userid,sqlTable.getLimits() , timestamp, sqlRes.getInsert_sql(), 0);
//        System.out.println(tableData);
        sqlGenService.WriteTableHistory(tableData);
        return sqlRes;
    }
    /**
     * @param :
     * @return List<AddWordsDTO>
     * @author SunnyDeer
     * @description 显示公共新词库
     * @date 2023/3/19 20:07
     */
    @GetMapping
    public List<WordsVO> ShowPublic() {
        return sqlGenService.ShowAllPublicWords();
    }

    /**
     * @return List<TableData>
     * @author SunnyDeer
     * @description 获取公共TableData
     * @description 获取公共TableData
     * @date 2023/3/19 20:09
     */
    @GetMapping("/getsql")
    public List<TableData> showTableData() {
        List<TableData> tableData = sqlGenService.getTableData();
        return tableData;
    }

    /**
     * @param request:
     * @return List<AddWordsDTO>
     * @author SunnyDeer
     * @description 查询私人词库
     * @date 2023/3/19 20:08
     */
    @GetMapping("/PersonalSqlData")
    public List<WordsVO> ShowPrivate(HttpServletRequest request) {
        String userid = sqlGenService.getIdByToken(request);
        return sqlGenService.ShowAllPersonalWords(userid);
    }

    /**
     * @param request:
     * @return List<TableData>
     * @author SunnyDeer
     * @description 获取私人tableData
     * @date 2023/3/19 20:12
     */
    @GetMapping("/getsql/PersonalTableData")
    public List<TableData> showPrivateTableData(HttpServletRequest request) {
        String userid = sqlGenService.getIdByToken(request);
        List<TableData> tableData = sqlGenService.getPrivateTableData(userid);
        return tableData;
    }

    /**
     * @param addWordsDTO:
     * @author SunnyDeer
     * @description 添加新词库
     * @date 2023/3/19 20:08
     */
    @PostMapping("/add")
    public int AddWords(@RequestBody AddWordsDTO addWordsDTO,HttpServletRequest request) {
        String userid = sqlGenService.getIdByToken(request);
        return sqlGenService.AddNewWords(addWordsDTO,userid);
    }


    /**
     * @param request:
     * @param TableName:
     * @return int
     * @author SunnyDeer
     * @description删除TableData
     * @date 2023/3/20 14:37
     */
    @DeleteMapping("/tabledata/{TableName}")
    public int deleteTableData(@PathVariable String TableName,HttpServletRequest request) {
        String userid = sqlGenService.getIdByToken(request);
        int i = sqlGenService.deleteTableData(userid, TableName);
        return i;
    }

    /**
     * @param request:
     * @param DataType:
     * @return int
     * @author SunnyDeer
     * @description删除sqlData
     * @date 2023/3/20 14:36
     */
    @DeleteMapping("/sqldata/{DataType}")
    public int deleteSqlData(@PathVariable String DataType,HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);
        int i = sqlGenService.deletesqlData(UserId, DataType);
        return i;
    }

    /**
     * @param request:
     * @param TableName:
     * @return int
     * @author SunnyDeer
     * @description将私有tableData变为公有
     * @date 2023/3/20 14:35
     */
    @GetMapping("/getSql/tableData/public/{TableName}")
    public int PublishTableData(@PathVariable String TableName,HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);

        if (Strings.isNotEmpty(UserId) && Strings.isNotEmpty(TableName)) {
            return sqlGenService.publishTabelData(UserId, TableName);
        } else {
            return 0;
        }
    }

    /**
     * @param request:
     * @param DataType:
     * @return int
     * @author SunnyDeer
     * @description将私有sqlData变为公有
     * @date 2023/3/20 14:34
     */
    @GetMapping("/getSql/sqlData/public/{DataType}")
    public int PublishSqlData(@PathVariable String DataType,HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);
        if (Strings.isNotEmpty(UserId) && Strings.isNotEmpty(DataType)) {
            return sqlGenService.publishSqlData(UserId, DataType);
        } else {
            return 0;
        }
    }

    /**
     * @param request:
     * @param TableName:
     * @return int
     * @author SunnyDeer
     * @description将公共tableData变为私有
     * @date 2023/3/20 14:30
     */
    @GetMapping("/getSql/tableData/private/{TableName}")
    public int PrivateTableData(@PathVariable String TableName,HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);
        if (Strings.isNotEmpty(UserId) && Strings.isNotEmpty(TableName)) {
            return sqlGenService.privateTabelData(UserId, TableName);
        } else {
            return 0;
        }
    }

    /**
     * @param request:
     * @param DataType:
     * @return int
     * @author SunnyDeer
     * @description将公共sqlData变为私有
     * @date 2023/3/20 14:33
     */
    @GetMapping("/getSql/sqlData/private/{DataType}")
    public int PrivateSqlData(@PathVariable String DataType,HttpServletRequest request) {
        String UserId = sqlGenService.getIdByToken(request);
        if (Strings.isNotEmpty(UserId) && Strings.isNotEmpty(DataType)) {
            return sqlGenService.privateSqlData(UserId, DataType);
        } else {
            return 0;
        }
    }

/**
 * @param request:
  * @return List<DataTypeVo>
 * @author SunnyDeer
 * @description 返回全部datatype
 * @date 2023/4/6 20:13
 */
    @GetMapping("/getSql/getDataType")
    public List<DataTypeVo> getDataType(HttpServletRequest request){
        String UserId = sqlGenService.getIdByToken(request);
        return sqlGenService.getDataType(UserId);
    }

    /**
     * @param null:
     * @return null
     * @author SunnyDeer
     * @description
     * @date 2023/4/29 17:08
     */
    @GetMapping("/getSql/checkExist/{type}/{name}")
    public ResponseResult checkExist(HttpServletRequest request, @PathVariable String type, @PathVariable String name){
        return sqlGenService.checkExist(type, name);
    }
}
