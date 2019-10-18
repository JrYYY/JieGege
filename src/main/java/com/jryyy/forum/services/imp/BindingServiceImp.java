package com.jryyy.forum.services.imp;

import com.jryyy.forum.constant.RoleCode;
import com.jryyy.forum.dao.BindingMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.BadCredentialsException;
import com.jryyy.forum.models.Binding;
import com.jryyy.forum.models.Response;
import com.jryyy.forum.models.response.BindingResponse;
import com.jryyy.forum.services.BindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.util.List;

//import org.springframework.security.authentication.BadCredentialsException;

@Service("BindingService")
public class BindingServiceImp implements BindingService {
    @Autowired
    BindingMapper bindingMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Response queryAllAssociatedUsers(int userId) throws Exception {
        try {
            List<BindingResponse> bindings = bindingMapper.selectBindingUserInfo(userId);
            return new Response<List<BindingResponse>>(bindings);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务器错误");
        }
    }

    @Override
    public Response addAssociatedUsers(int userId, String email) throws Exception {
        Integer boundId = userMapper.findIdByName(email);
        String role = userMapper.findIdByRole(userId);
        if (boundId == null)
            throw new EntityNotFoundException("该用户不存在");
        if (!RoleCode.PARENT.equals(role))
            throw new AccessDeniedException("权限不足");
        if (RoleCode.CHILD.equals(role))
            throw new BadCredentialsException("无法关联该用户");
        if (bindingMapper.findIdByBinding(userId, boundId) != null)
            throw new BadCredentialsException("已绑定,该用户");
        try {
            bindingMapper.insertBinding(Binding.builder().
                    userId(userId).boundId(boundId).
                    build());
            return new Response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("服务器错误");
        }
    }

    @Override
    public Response deleteAssociatedUsers(int userId, int id) throws Exception {
        try {
            bindingMapper.deleteBinding(userId, id);
            return new Response();
        } catch (Exception e) {
            throw new RuntimeException("服务器错误");
        }
    }

    @Override
    public Response confirmBinding(int boundId, int id) throws Exception {
        Integer userId = bindingMapper.confirmUser(boundId, id);
        if (userId == null)
            throw new AccessDeniedException("没权限访问");
        try {
            bindingMapper.updateBindingStatus(boundId, id);
            return new Response();
        } catch (Exception e) {
            throw new RuntimeException("确认失败");
        }
    }

    @Override
    public Response refuseBind(int boundId, int id) throws Exception {
        Integer userId = bindingMapper.confirmUser(boundId, id);
        if (userId == null)
            throw new AccessDeniedException("没权限访问");
        try {
            bindingMapper.deleteBindingBoundId(boundId, id);
            return new Response();
        } catch (Exception e) {
            throw new RuntimeException("服务器错误");
        }
    }
}
