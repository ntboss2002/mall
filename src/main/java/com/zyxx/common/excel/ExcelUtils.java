package com.zyxx.common.excel;

import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 导出excel工具类
 *
 * @author zxy
 */
public class ExcelUtils {

    /**
     * 导出excel
     *
     * @param title      文件标题
     * @param clazz      实体类型
     * @param exportList 导出数据
     * @param <T>
     * @return
     */
    public static <T> ModelAndView export(String title, Class<T> clazz, List<T> exportList) {
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, title);
        mv.addObject(NormalExcelConstants.CLASS, clazz);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams(title, title));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        return mv;
    }

}
