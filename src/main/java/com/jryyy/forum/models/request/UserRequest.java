package com.jryyy.forum.models.request;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.UserStatus;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.BadCredentialsException;
import com.jryyy.forum.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//import org.springframework.security.authentication.BadCredentialsException;

/**
 * 用户请求类型
 *
 * @User JrYYY
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {


    @NotBlank(message = "用户名不能为空")
    @Email(message = "请输入正确的邮箱")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度不满足")
    private String pass;

    /**
     * 密码是否正确
     *
     * @param userMapper {@link UserMapper}
     */
    public User verifyUserLogin(UserMapper userMapper) throws Exception {
        User user = userMapper.findLoginByName(this.name);
        if (!user.getPassword().equals(this.pass)) {
            if (user.getLoginFailedAttemptCount() < Constants.MAXIMUM_NUMBER_ATTEMPTS) {
                userMapper.updateLoginFailedAttemptCount(user.getId(), user.getLoginFailedAttemptCount() + 1);
                throw new BadCredentialsException("密码错误,您还有：" +
                        (Constants.MAXIMUM_NUMBER_ATTEMPTS - user.getLoginFailedAttemptCount() - 1) +
                        "尝试机会");
            } else {
                userMapper.updateStatus(user.getId(), UserStatus.LOCKED);
                throw new ArithmeticException("您的账号已被冻结");
            }
        }
        return user;
    }

    /**
     * 判断用户是否不存在
     *
     * @param userMapper {@link UserMapper}
     */
    public void userDoesNotExist(UserMapper userMapper) throws Exception {
        if (userMapper.findLoginByName(this.name) == null)
            throw new BadCredentialsException("用户不存在");
    }


}
