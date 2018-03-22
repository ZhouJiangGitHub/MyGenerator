package cn.edu.nju.software.controller;

import cn.edu.nju.software.entity.rely.ResponseData;
import cn.edu.nju.software.entity.UserGoldBill;
import cn.edu.nju.software.service.UserGoldBillService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
/**
* @author zj 
*/
@Api(value = "Manage", description = "管理用户金币账单的相关接口")
@Controller
@RequestMapping("/manage/userGoldBill")
public class UserGoldBillController {
    private static final Logger logger = LoggerFactory.getLogger(UserGoldBillController.class);

    @Autowired
    private UserGoldBillService userGoldBillService;

    @ApiOperation(value = "新增用户金币账单", notes = "")
    @RequestMapping(value = "addUserGoldBill", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<UserGoldBill> addUserGoldBill(
                    
                                @ApiParam("") @RequestParam(value="userId",required = false) Integer userId ,                                        
                                @ApiParam("余额") @RequestParam(value="amount",required = false) Integer amount ,                                        
                                @ApiParam("账单类型") @RequestParam(value="type",required = false) String type ,                                        
                                @ApiParam("") @RequestParam(value="description",required = false) String description ,                                        
                                @ApiParam("相关id") @RequestParam(value="relativeId",required = false) Integer relativeId ,                                        
                                @ApiParam("相关用户id") @RequestParam(value="relativeUserId",required = false) Integer relativeUserId                                         
                                                
                                            ){
        ResponseData<UserGoldBill> responseData=new ResponseData<>();
        UserGoldBill userGoldBill=new UserGoldBill();
                                        userGoldBill.setUserId(userId);
                                                            userGoldBill.setAmount(amount);
                                                            userGoldBill.setType(type);
                                                            userGoldBill.setDescription(description);
                                                            userGoldBill.setRelativeId(relativeId);
                                                            userGoldBill.setRelativeUserId(relativeUserId);
                                                            userGoldBill.setCreateTime(new Date());
                                                            userGoldBill.setUpdateTime(new Date());
                                            int res=userGoldBillService.addUserGoldBill(userGoldBill);
        if (res==0){
        responseData.jsonFill(2,"新增失败}",null);
        return responseData;
        }
        responseData.jsonFill(1,null,userGoldBill);
        return responseData;
    }

    @ApiOperation(value = "更新用户金币账单", notes = "")
    @RequestMapping(value = "updateUserGoldBill", method = {RequestMethod.POST})
    @ResponseBody
    public ResponseData<Boolean> updateUserGoldBill(
        @ApiParam("") @RequestParam("id") Integer id,
                    
                                @ApiParam("") @RequestParam(value="userId",required = false) Integer userId ,                                        
                                @ApiParam("余额") @RequestParam(value="amount",required = false) Integer amount ,                                        
                                @ApiParam("账单类型") @RequestParam(value="type",required = false) String type ,                                        
                                @ApiParam("") @RequestParam(value="description",required = false) String description ,                                        
                                @ApiParam("相关id") @RequestParam(value="relativeId",required = false) Integer relativeId ,                                        
                                @ApiParam("相关用户id") @RequestParam(value="relativeUserId",required = false) Integer relativeUserId                                         
                                                
                                                    ){
        ResponseData<Boolean> responseData=new ResponseData<>();
        UserGoldBill userGoldBill=new UserGoldBill();

                userGoldBill.setId(id);

                                        userGoldBill.setUserId(userId);
                                                            userGoldBill.setAmount(amount);
                                                            userGoldBill.setType(type);
                                                            userGoldBill.setDescription(description);
                                                            userGoldBill.setRelativeId(relativeId);
                                                            userGoldBill.setRelativeUserId(relativeUserId);
                                                            userGoldBill.setCreateTime(new Date());
                                                            userGoldBill.setUpdateTime(new Date());
                                            int res=userGoldBillService.updateUserGoldBill(userGoldBill);
        if (res==0){
            responseData.jsonFill(2,"更新失败",false);
            return responseData;
        }
        responseData.jsonFill(1,null,true);
        return responseData;
    }

    
    @ApiOperation(value = "通过ID获取用户金币账单", notes = "")
    @RequestMapping(value = "getUserGoldBillById", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<UserGoldBill> getUserGoldBillById(@ApiParam("") @RequestParam("id") Integer id){
    ResponseData<UserGoldBill> responseData=new ResponseData<>();
    UserGoldBill userGoldBill=userGoldBillService.getUserGoldBillById(id);
    if (userGoldBill==null){
    responseData.jsonFill(2,"无效的id",null);
    return responseData;
    }
    responseData.jsonFill(1,null,userGoldBill);
    return responseData;
    }

    @ApiOperation(value = "获取所有的用户金币账单", notes = "不提供page和pageSize就不分页")
    @RequestMapping(value = "getAllUserGoldBill", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseData<List<UserGoldBill>> getAllUserGoldBill(
        @ApiParam("page") @RequestParam(value = "page",required = false) Integer page,
        @ApiParam("pageSize") @RequestParam(value = "pageSize",required = false) Integer pageSize){
    ResponseData<List<UserGoldBill>> responseData=new ResponseData<>();
    List<UserGoldBill> list=userGoldBillService.getAllUserGoldBill(page,pageSize);
    responseData.jsonFill(1,null,list);
    responseData.setCount(list.size());
    return responseData;
    }

    @ApiOperation(value = "删除", notes = "")
    @RequestMapping(value = "deleteUserGoldBill/{id}", method = {RequestMethod.DELETE})
    @ResponseBody
    public ResponseData<Boolean> deleteUserGoldBill(@ApiParam("ID") @PathVariable Integer id){
        ResponseData<Boolean> responseData=new ResponseData<>();
        int res=userGoldBillService.deleteUserGoldBill(id);
        if (res==0){
            responseData.jsonFill(2,"删除失败",false);
            return responseData;
        }
        responseData.jsonFill(1,"删除成功",true);
        return responseData;
    }
}
