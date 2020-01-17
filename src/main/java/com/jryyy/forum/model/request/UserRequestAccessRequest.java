package com.jryyy.forum.model.request;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.RedisKey;
import com.jryyy.forum.utils.security.UserRoleCode;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author JrYYY
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestAccessRequest {

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "Email不能为空")
    private String name;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度不符合标准")
    private String pwd;

    @NotBlank(message = "权限不能为空")
    private String role;

    public User toUser() {
        return new User(name, pwd, role, null,
                0, null);
    }

    /**
     * 判断用户是否已存在
     *
     * @param userMapper {@link UserMapper}
     */
    public void verifyUserRegistered(UserMapper userMapper) throws Exception {
        if (userMapper.findLoginByName(this.name) != null)
            throw new GlobalException(GlobalStatus.userAlreadyExists);
    }

    /**
     * 请求访问权限检测
     */
    public void requestAccessPermissionDetection() throws Exception {
        if (!this.role.equals(UserRoleCode.CHILD) && !this.role.equals(UserRoleCode.PARENT))
            throw new GlobalException(GlobalStatus.insufficientPermissions);
    }

    public void verifyVerificationCode(RedisTemplate redisTemplate) throws Exception {
        String code = (String) redisTemplate.opsForValue().get(RedisKey.registrationCodeKey(this.name));
        if (code == null) {
            throw new GlobalException(GlobalStatus.notApplyingForVerificationCode);
        } else if (!code.equals(this.code)) {
            throw new GlobalException(GlobalStatus.verificationCodeError);
        }
    }

}
