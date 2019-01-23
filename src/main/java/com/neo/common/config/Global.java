package com.neo.common.config;

/**
 * @Auther: gerf
 * @Date: 2018/12/6 17:14
 * @Description: 全局配置类
 */
public class Global {

    /**
     * 显示/隐藏
     */
    public static final String SHOW = "1";
    public static final String HIDE = "0";

    /**
     * 是/否
     */
    public static final String YES = "1";
    public static final String NO = "0";

    /**
     * 对/错
     */
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * 删除标记（0：正常；1：删除；2：禁用；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_STOP = "2";

    /**
     * 初始密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 默认每页列表数
     */
    public static final int DEFAULT_PAGESIZE = 10;

    public static final String LOGIN_SESSION_KEY = "login_user";


    /**
     *
     */
    public static final String SUCCESS = "操作成功";
    public static final String ERROR = "操作失败";
    public static final String PARAM_ERROR = "参数错误";

}