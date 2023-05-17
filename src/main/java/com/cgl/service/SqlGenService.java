package com.cgl.service;

import com.cgl.model.ResponseResult;
import com.cgl.model.dto.AddWordsDTO;
import com.cgl.model.dto.SqlTable;
import com.cgl.model.dto.UserCols;
import com.cgl.model.po.DataType;
import com.cgl.model.po.SqlData;
import com.cgl.model.po.TableData;
import com.cgl.model.vo.DataTypeVo;
import com.cgl.model.vo.SqlRes;
import com.cgl.model.vo.WordsVO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:sql生成服务接口
 * @author: SunnyDeer
 * @time: 2023/3/8
 */
public interface SqlGenService {
    public SqlRes sqlGenerate(SqlTable sqlTable,String userId);
    public List<WordsVO> ShowAllPublicWords();
    public List<WordsVO> ShowAllPersonalWords(String userid);
    public int AddNewWords(AddWordsDTO addWordsDTO,String userid);
    public int WriteTableHistory(TableData tableData);
    public List<TableData> getTableData();
    public List<TableData> getPrivateTableData(String userid);

    public int deleteTableData(String UserId,String TableName);
    public int publishTabelData(String UserId, String TableName);
    public int privateTabelData(String UserId, String TableName);


    public int publishSqlData(String UserId, String DataType);
    public int deletesqlData(String UserId, String DataType);
    public int privateSqlData(String UserId, String DataType);

    public int AddNewCol(UserCols userCols);
    public List<UserCols> getAllPublicCols();
    public List<UserCols> getPersonalCols(String userId);
    public int publishCols(String userId,int colId);
    public int privateCols(String userId,int colId);
    public int deleteCols(String userId,int colId);
    public String getIdByToken(HttpServletRequest request);


    public List<DataTypeVo> getDataType(String userId);

    public int getTableDataNumByDate(String date,String userid);
    public int getDataTypeNumById(String userId);
    public int getColsNumById(String userId);
    public ResponseResult checkExist(String type, String name);

}