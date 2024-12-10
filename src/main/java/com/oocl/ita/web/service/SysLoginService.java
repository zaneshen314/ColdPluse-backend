package com.oocl.ita.web.service;


import com.oocl.ita.web.core.email.EmailTemplate;
import com.oocl.ita.web.core.exception.EmailExistException;
import com.oocl.ita.web.core.exception.InvalidEmailFormatException;
import com.oocl.ita.web.core.exception.InvalidVerificationCodeException;
import com.oocl.ita.web.core.exception.VerificationCodeMismatchException;
import com.oocl.ita.web.core.redis.RedisCache;
import com.oocl.ita.web.domain.bo.RegisterBody;
import com.oocl.ita.web.domain.bo.SendEmailBody;
import com.oocl.ita.web.domain.po.User;
import com.oocl.ita.web.core.security.context.AuthenticationContextHolder;
import com.oocl.ita.web.core.security.domain.LoginUser;
import com.oocl.ita.web.core.security.service.TokenService;
import com.oocl.ita.web.common.utils.SecurityUtils;
import com.oocl.ita.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import static com.oocl.ita.web.common.constant.EmailConstants.*;

import static com.oocl.ita.web.core.email.EmailUtil.*;

/**
 * 登录校验方法
 * 

 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository sysUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;


    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password)
    {
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Login failed");
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 获取登录用户信息
     */
    public User getLoginUser() {
        Integer userId = SecurityUtils.getUserId();
        return sysUserRepository.getById(userId);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123456");
        System.out.println(encode);
    }
    public void verificationCode(String email,String code){
        // 从 Redis 获取存储的验证码
        String storedCode = redisCache.getCacheObject(email);
        // 校验验证码
        if (storedCode == null) {
            throw new InvalidVerificationCodeException("The verification code has expired or is invalid. Please obtain the verification code again");
        }

        // 比较用户输入的验证码与 Redis 中存储的验证码
        if (!storedCode.equals(code)) {
            throw new VerificationCodeMismatchException("Verification code does not match, please re-enter");
        }
    }
    public void register(RegisterBody registerBody) {
        String email = registerBody.getEmail();
        // 验证邮箱格式是否正确
        if(!isValidEmail(email)) {
            throw new InvalidEmailFormatException("The email format is incorrect, please re-enter");
        }
        // 校验邮箱是否存在
        List<User> userList = sysUserRepository.findByEmail(email);
        if (CollectionUtils.isEmpty(userList)) {
//            验证code
            verificationCode(email,registerBody.getCode());
            User entity = registerBody.toEntity();
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            sysUserRepository.save(entity);
            // 注册成功后，删除 Redis 中的验证码，防止再次使用
            redisCache.deleteObject(registerBody.getEmail());
        } else {
            throw new EmailExistException();
        }
    }

    public void sendRegisterEmail(SendEmailBody sendEmailBody) {
        String email = sendEmailBody.getEmail();
        if(!isValidEmail(email)){
            throw new InvalidEmailFormatException("The email format is incorrect, please re-enter");
        }
        String verificationCode = generateVerificationCode();
        Map<String, String> params = new HashMap<>();
        params.put(VERIFICATION_CODE, verificationCode);
        //发送邮件
        sendEmailWithTemplate(EmailTemplate.REGISTER,email,params);
        // 将验证码存入 Redis 中，设置过期时间为 10 分钟
        redisCache.setCacheObject(email, verificationCode, 10, TimeUnit.MINUTES);  // 设置 10 分钟过期时间

    }

}
