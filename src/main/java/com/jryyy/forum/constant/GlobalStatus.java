package com.jryyy.forum.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 异常返回类型
 * @author JrYYY
 */
@Getter
@ToString
@AllArgsConstructor
public enum GlobalStatus {
    /* 全局 */
    tokenCanNotBeEmpty(0, "token不能为空"),
    invalidToken(1, "无效token"),
    insufficientPermissions(2, "权限不足"),
    accountHasBeenFrozen(3, "账号已被冻结"),
    serverError(4, "服务器错误"),
    mailDeliveryFailed(5, "邮件放送失败"),
    alreadyLoggedInElsewhere(22, "已在其他端点登入"),
    /* 登入 */
    wrongPassword(6, "密码错误"),
    userDoesNotExist(7, "用户不存在"),
    unableToCreateToken(8, "无法创建令牌"),
    /* 注册 */
    userAlreadyExists(9, "用户已存在"),
    verificationCodeError(10, "验证码错误"),
    failedToApplyForVerificationCode(11, "申请验证码失败"),
    notApplyingForVerificationCode(12, "未申请验证码"),
    /* 图片 */
    imageSaveFailed(13, "图片保存失败"),
    /* 签到 */
    alreadySignedIn(14, "已签到,签到失败！"),
    /* 绑定 */
    unableToBind(15, "无法关联,该用户"),
    alreadyBound(16, "已绑定,无法绑定"),
    /* 关注 */
    alreadyConcerned(17, "已关注,无法关注"),
    noAttention(18, "没有关注"),

    noSuchPage(19, "查询失败,页数异常"),
    noResourcesFound(20, "没有找到资源"),
    noContent(21, "请填写上传内容");


    private int code;

    private String msg;
}
