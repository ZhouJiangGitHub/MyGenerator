package cn.edu.nju.software.entity.rely;

/**
 * @author zj
 */
public enum GoldBillItemType {

    PUBLISH_ORIGINAL_STORY("发布原创故事"), GET_BADGE("获得徽章");

    private final String name;
    private GoldBillItemType(String name){
        this.name=name;
    }

}
