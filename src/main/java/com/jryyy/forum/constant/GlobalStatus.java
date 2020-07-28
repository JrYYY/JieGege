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
    tokenCanNotBeEmpty( "token不能为空"),
    invalidToken("无效token"),
    insufficientPermissions( "权限不足"),
    accountHasBeenFrozen( "账号已被冻结"),
    serverError( "服务器错误"),
    mailDeliveryFailed("邮件放送失败"),
    alreadyLoggedInElsewhere( "已在其他端点登入"),
    unauthorizedAccess("非法访问"),

    wrongPassword("密码错误"),
    userNotLogin("用户未登入"),
    userDoesNotExist( "用户不存在"),
    unableToCreateToken( "无法创建令牌"),
    userAlreadyExists( "用户已存在"),
    verificationCodeError( "验证码错误"),
    failedToApplyForVerificationCode( "申请验证码失败"),
    notApplyingForVerificationCode( "未申请验证码"),
    verificationCodeIsInvalid("请申请验证码"),
    /* 图片 */
    imageSaveFailed( "图片保存失败"),
    fileDoesNotExist("文件不存在"),
    fileTypeIsIncorrect("文件类型不正确"),
    /* 签到 */
    alreadySignedIn( "已签到,签到失败！"),
    /* 绑定 */
    unableToBind( "无法关联,该用户"),
    alreadyBound("已绑定,无法绑定"),
    /* 关注 */
    alreadyConcerned("已关注,无法关注"),
    noAttention( "没有关注"),

    noSuchPage("查询失败,页数异常"),
    noResourcesFound( "没有找到资源"),
    noContent("请填写上传内容"),

    messageSendingFailed("消息发送失败"),
    userHasJoinedTheGroup("该用户已加入"),
    userIsNotInAGroup("该用户不在群组"),

    thesaurusIsNotApplied("未申请词库"),
    insufficientCredit("积分不足");



    private String msg;
}
