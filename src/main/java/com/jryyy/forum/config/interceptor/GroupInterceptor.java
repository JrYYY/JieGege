package com.jryyy.forum.config.interceptor;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.GroupMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.utils.group.GroupPermissions;
import com.jryyy.forum.utils.group.GroupRoleCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author OU
 */
@Slf4j
public class GroupInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private GroupMapper groupMapper;

    private static final String USER_ID_REGEX = "\\d+$";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        try {
            if (method.isAnnotationPresent(GroupPermissions.class)) {
                GroupPermissions groupPermissions = method.getAnnotation(GroupPermissions.class);
                Integer groupId = Integer.parseInt(request.getParameter("groupId"));
                log.info(groupId+"");
                int userId = 0;
                Matcher matcher = Pattern.compile(USER_ID_REGEX).matcher(request.getRequestURI());
                if (matcher.find()) {
                    userId = Integer.parseInt(matcher.group());
                }

                String role = groupPermissions.permission();
                String memberRole = groupMapper.findMemberByIdAndGroupId(userId, groupId);

                if (memberRole == null) {
                    throw new GlobalException(GlobalStatus.unauthorizedAccess);
                }

                if (!role.equals(GroupRoleCode.ALL) && !role.equals(memberRole)) {
                    throw new GlobalException(GlobalStatus.insufficientPermissions);
                }

                for (String r : groupPermissions.notAllowed()) {
                    if (r.equals(memberRole)) {
                        throw new GlobalException(GlobalStatus.insufficientPermissions);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return true;
    }
}
