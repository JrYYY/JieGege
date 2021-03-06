package com.jryyy.forum.model.request;

import com.jryyy.forum.constant.Constants;
import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户请求类型
 *
 * @author JrYYY
 */
@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度不合格")
    private String pwd;

    /**
     * 密码是否正确
     *
     * @param userMapper {@link UserMapper}
     */
    public User verifyUserLogin(UserMapper userMapper) throws Exception {
        User user = userMapper.findUserByName(this.name);
        if (!user.getPassword().equals(this.pwd)) {
            if (user.getLoginFailedAttemptCount() < Constants.MAXIMUM_NUMBER_ATTEMPTS) {
                userMapper.updateLoginFailedAttemptCount(user.getId(), user.getLoginFailedAttemptCount() + 1);
                throw new GlobalException(GlobalStatus.wrongPassword);
            } else {
                throw new GlobalException(GlobalStatus.accountHasBeenFrozen);
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
        log.info(this.name);
        if (userMapper.findUserByName(this.name.trim()) == null) {
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        }
    }


}
