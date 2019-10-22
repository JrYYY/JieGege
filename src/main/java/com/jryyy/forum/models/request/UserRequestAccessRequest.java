package com.jryyy.forum.models.request;

import com.jryyy.forum.constant.RoleCode;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.BadCredentialsException;
import com.jryyy.forum.exception.PreconditionFailedException;
import com.jryyy.forum.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestAccessRequest {
    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "Email不能为空")
    private String name;

    /* 验证码 */
    @NotBlank(message = "验证码不能为空")
    private String code;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度不符合标准")
    private String pass;

    @NotBlank(message = "权限不能为空")
    private String role;

    public User toUser() {
        return new User(null,
                name, pass, role, null,
                0, null);
    }

    /**
     * 判断用户是否已存在
     *
     * @param userMapper {@link UserMapper}
     */
    public void verifyUserRegistered(UserMapper userMapper) throws Exception {
        if (userMapper.findLoginByName(this.name) != null)
            throw new BadCredentialsException("用户已存在");
    }

    /**
     * 请求访问权限检测
     */
    public void requestAccessPermissionDetection() throws Exception {
        if (!this.role.equals(RoleCode.CHILD) && !this.role.equals(RoleCode.PARENT))
            throw new BadCredentialsException("异常访问");
    }

    public void verifyVerificationCode(RedisTemplate redisTemplate) throws Exception {
        String code = (String) redisTemplate.opsForValue().get(this.name);
        if (code == null)
            throw new PreconditionFailedException("尚未请求验证码");
        else if (!code.equals(this.code))
            throw new IllegalArgumentException("验证码错误");
    }

}
