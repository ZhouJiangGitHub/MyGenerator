package cn.edu.nju.software.service.impl;

import cn.edu.nju.software.entity.UserGoldBill;
import cn.edu.nju.software.entity.UserGoldBillExample;
import cn.edu.nju.software.dao.UserGoldBillMapper;
import cn.edu.nju.software.service.UserGoldBillService;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
* @author zj 
*/
@Service
public class UserGoldBillServiceImpl implements UserGoldBillService {
    @Autowired
    private UserGoldBillMapper userGoldBillMapper;

    @Override
    public int addUserGoldBill(UserGoldBill userGoldBill){
        return userGoldBillMapper.insert(userGoldBill);
    }

    @Override
    public int updateUserGoldBill(UserGoldBill userGoldBill){
        return userGoldBillMapper.updateByPrimaryKeySelective(userGoldBill);
    }

    @Override
    public UserGoldBill getUserGoldBillById(Integer id){
        return userGoldBillMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserGoldBill> getAllUserGoldBill(Integer page,Integer pageSize){
        if (page==null||pageSize==null){
            UserGoldBillExample userGoldBillExample=new UserGoldBillExample();
            return userGoldBillMapper.selectByExample(userGoldBillExample);
        }
        int offset = page*pageSize;
        int limit = pageSize;
        //return userGoldBillMapper.selectAllUserGoldBillByPage(offset,limit);
        return new ArrayList<UserGoldBill>();
    }

    @Override
    public int deleteUserGoldBill(Integer id){
        return userGoldBillMapper.deleteByPrimaryKey(id);
    }
}