package com.zyxx.common.excel;

import com.zyxx.sys.entity.SysDictDetail;
import com.zyxx.sys.mapper.SysDictDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecgframework.dict.service.AutoPoiDictServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：AutoPoi Excel注解支持字典参数设置
 * 举例： @Excel(name = "性别", width = 15, dicCode = "sex")
 * 1、导出的时候会根据字典配置，把值1,2翻译成：男、女;
 * 2、导入的时候，会把男、女翻译成1,2存进数据库;
 *
 * @Author zxy
 */
@Slf4j
@Service
public class AutoPoiDictService implements AutoPoiDictServiceI {

    @Autowired
    private SysDictDetailMapper sysDictDetailMapper;

    /**
     * 通过字典翻译字典文本
     *
     * @Author zxy
     */
    @Override
    public String[] queryDict(String dicTable, String dicCode, String dicText) {
        List<String> dictReplaces = new ArrayList<>();
        List<SysDictDetail> dictList = sysDictDetailMapper.queryDictItemsByCode(dicCode);
        for (SysDictDetail t : dictList) {
            if (t != null) {
                dictReplaces.add(t.getName() + "_" + t.getCode());
            }
        }
        if (dictReplaces != null && dictReplaces.size() != 0) {
            return dictReplaces.toArray(new String[dictReplaces.size()]);
        }
        return null;
    }
}
