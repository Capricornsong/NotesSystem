package cn.edu.bnuz.notes.interfaces;

import cn.edu.bnuz.notes.ntwpojo.UserInfo;

public interface ITokenController {
    //获取token
    int GetToken(String username, String password);

//    Register(String email,String username,String password);

    /**
     * 验证邮箱是否已注册
     * @param email
     * @return
     *      10002 已注册
     *      200   未注册
     */
    int EmailCheck(String email);


    /**
     * 验证用户名是否已存在
     * @param username
     * @return
     *      10001 已存在
     *      200   可使用
     */
    int UsernameCheck(String username);

    int SendEmailCode(String email);


    /**
     * 注册新用户
     * @param username
     * @param email
     * @param checkcode
     * @param password
     * @return
     *      200    成功
     *      100085 验证码错误
     */
    int Register(String username,String email,String checkcode,String password);

    UserInfo GetUserInfo();


}
