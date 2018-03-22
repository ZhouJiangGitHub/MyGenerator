package cn.edu.nju.software.dao;

import cn.edu.nju.software.entity.UserGoldBill;
import cn.edu.nju.software.entity.UserGoldBillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserGoldBillMapper {
    long countByExample(UserGoldBillExample example);

    int deleteByExample(UserGoldBillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserGoldBill record);

    int insertSelective(UserGoldBill record);

    List<UserGoldBill> selectByExample(UserGoldBillExample example);

    UserGoldBill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserGoldBill record, @Param("example") UserGoldBillExample example);

    int updateByExample(@Param("record") UserGoldBill record, @Param("example") UserGoldBillExample example);

    int updateByPrimaryKeySelective(UserGoldBill record);

    int updateByPrimaryKey(UserGoldBill record);
}