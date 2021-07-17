package com.example.coin.serviceImpl;

import com.example.coin.Converter.UserConverter;
import com.example.coin.data.UserMapper;
import com.example.coin.po.Code;
import com.example.coin.po.User;
import com.example.coin.service.UserService;
import com.example.coin.vo.CodeVO;
import com.example.coin.vo.LoginVO;
import com.example.coin.vo.ResponseVO;
import com.example.coin.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static javax.mail.Transport.send;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    public User getUserInfo(int id) {
        return userMapper.getUserInfoById(id);
    }

    public User getUserInfo(String email) {
        return userMapper.getUserInfo(email);
    }

    public ResponseVO sendCode(String email) {
        if (email==null){
            return ResponseVO.failure("source is null");
        }
        Code code=new Code();
        try {
            StringBuilder newCode= new StringBuilder();
            String all="0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for (int i=0;i<6;i++){
                int num=(int)(Math.random()*82);
                newCode.append(all.charAt(num));
            }
            code.setCode(newCode.toString());
            //System.out.println("1");
            Properties props = new Properties();                // 参数配置
            props.setProperty("mail.transport.protocol", "smtp");   // 协议
            props.setProperty("mail.smtp.host", "smtp.qq.com");   // SMTP服务器地址
            props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
			props.setProperty("mail.smtp.port", "465");  
			props.setProperty("mail.debug","true");  
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.port", "465");
            Session session = Session.getInstance(props);        // 创建会话对象
            session.setDebug(true);
            MimeMessage message = new MimeMessage(session);     // 创建邮件对象
            // From
            message.setFrom(new InternetAddress("462211353@qq.com", "OwO", "UTF-8"));
            // To
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email, "OwO 新用户", "UTF-8"));
            // 主题
            message.setSubject("OwO 注册验证码", "UTF-8");
            // 正文
            message.setContent("\n\n" +
                    "            <p>欢迎来到 OwO</p>\n" +
                    "            <p>您的网站账户注册验证码是：</p>\n" +
                    "        <span style=\"font-size: 24px; color: red\">" + newCode + "</span>", "text/html;charset=UTF-8");
            message.setSentDate(new Date());
            Transport transport = session.getTransport();
            transport.connect("462211353@qq.com", "ogvxvcgzwucccagd");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            return ResponseVO.failure("failure");
        }
        return ResponseVO.success(code);
    }

    public ResponseVO addAccount(UserVO userVO) {
        if (userVO==null){
            return ResponseVO.failure("source is null");
        }
        User user = UserConverter.INSTANCE.v2p(userVO);
        try {
            User test = getUserInfo(user.getEmail());
            if (test != null) {
                return ResponseVO.failure("该邮箱已被使用！");
            }
            userMapper.addUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.failure("register failure");
        }
        return ResponseVO.success(user);
    }

    public ResponseVO modifyName(UserVO userVO){
        if (userVO==null){
            return ResponseVO.failure("source is null");
        }
        User user = UserConverter.INSTANCE.v2p(userVO);
        try {
            User preUser = getUserInfo(userVO.getEmail());
            if (preUser.getUsername().equals(userVO.getUsername())){
                return ResponseVO.failure("same name");
            }else {
                userMapper.update(user);
                user = getUserInfo(userVO.getEmail());
                return ResponseVO.success(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.failure("modify failure");
        }
    }

    public ResponseVO modifyPassword(UserVO userVO){
        if (userVO==null){
            return ResponseVO.failure("source is null");
        }
        User user = UserConverter.INSTANCE.v2p(userVO);
        try {
            User preUser = getUserInfo(userVO.getEmail());
            if (preUser.getPassword().equals(userVO.getPassword())){
                return ResponseVO.failure("same password");
            }else {
                userMapper.update(user);
                user = getUserInfo(userVO.getEmail());
                return ResponseVO.success(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.failure("modify failure");
        }
    }

    public ResponseVO verifyPwd(LoginVO loginVO) {
        if (loginVO==null){
            return ResponseVO.failure("source is null");
        }
        try {
            User user = getUserInfo(loginVO.getEmail());
            if (!user.getPassword().equals(loginVO.getPassword())){
                return ResponseVO.failure("login failure");
            }else {
                return ResponseVO.success(user);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.failure("login failure");
        }
    }
}
