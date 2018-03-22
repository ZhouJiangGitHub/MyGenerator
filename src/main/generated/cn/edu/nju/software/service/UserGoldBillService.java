package cn.edu.nju.software.service;

import cn.edu.nju.software.entity.UserGoldBill;
import java.util.List;

/**
* @author zj 
*/
public interface UserGoldBillService {

    int addUserGoldBill(UserGoldBill userGoldBill);

    int updateUserGoldBill(UserGoldBill userGoldBill);

    UserGoldBill getUserGoldBillById(Integer id);

    List<UserGoldBill> getAllUserGoldBill(Integer page,Integer pageSize);

    int deleteUserGoldBill(Integer id);
}