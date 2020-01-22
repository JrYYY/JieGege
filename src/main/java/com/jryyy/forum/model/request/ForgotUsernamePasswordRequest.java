package com.jryyy.forum.model.request;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.constant.KayAndUrl;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 修改密码响应类
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotUsernamePasswordRequest {

    /**
     * The email.
     */
    @NotBlank(message = "email不能为空")
    @Email(message = "邮箱格式错误")
    private String name;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String code;

    /**
     * 修改的密码
     */
    @NotBlank(message = "密码不能为空")
    private String pwd;

    /**
     * 判断用户是否不存在
     *
     * @param userMapper {@link UserMapper}
     */
    public ForgotUsernamePasswordRequest userDoesNotExist(UserMapper userMapper) throws Exception {
        if (userMapper.findLoginByName(this.name) == null) {
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
        return this;
    }

    /**
     * 验证 验证码
     */
    public void verifyVerificationCode(RedisTemplate template) throws GlobalException {
        String code = (String) template.opsForValue().get(KayAndUrl.modifyPasswordCodeKey(this.name));

        if(code == null){
            throw new GlobalException(GlobalStatus.verificationCodeIsInvalid);
        }

        if(!code.equals(this.code)){
            throw new GlobalException(GlobalStatus.verificationCodeError);
        }
    }
}
