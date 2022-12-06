package com.zyxx.business.vo;

import com.zyxx.business.entity.FeedBackInfo;
import lombok.Data;

/**
 * @ClassName FeedBackInfoVO
 * @Description
 * @Author zxy
 * @Date 2020-09-29 15:52:52
 **/
@Data
public class FeedBackInfoVO extends FeedBackInfo {

    private String createUserName;

    private String typeName;
}
