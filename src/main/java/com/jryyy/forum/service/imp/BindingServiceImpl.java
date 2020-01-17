package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.utils.security.UserRoleCode;
import com.jryyy.forum.dao.BindingMapper;
import com.jryyy.forum.dao.UserMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Binding;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.response.BindingResponse;
import com.jryyy.forum.service.BindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @see com.jryyy.forum.service.BindingService
 */
@Service("BindingService")
public class BindingServiceImpl implements BindingService {
    @Autowired
    BindingMapper bindingMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Response queryAllAssociatedUsers(int userId) throws Exception {
        try {
            List<BindingResponse> bindings = bindingMapper.selectBindingUserInfo(userId);
            return new Response<>(bindings);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response addAssociatedUsers(int userId, String email) throws Exception {
        Integer boundId = userMapper.findIdByName(email);
        if (boundId == null)
            throw new GlobalException(GlobalStatus.userDoesNotExist);
        String role1 = userMapper.findIdByRole(userId);
        String role2 = userMapper.findIdByRole(boundId);
        if (!UserRoleCode.PARENT.equals(role1))
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        if (!UserRoleCode.CHILD.equals(role2))
            throw new GlobalException(GlobalStatus.unableToBind);
        if (bindingMapper.findIdByBinding(userId, boundId) != null)
            throw new GlobalException(GlobalStatus.alreadyBound);
        try {
            bindingMapper.insertBinding(Binding.builder().
                    userId(userId).boundId(boundId).
                    build());
            return new Response();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response deleteAssociatedUsers(int userId, int id) throws Exception {
        try {
            bindingMapper.deleteBinding(userId, id);
            return new Response();
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response confirmBinding(int boundId, int id) throws Exception {
        Integer userId = bindingMapper.confirmUser(boundId, id);
        if (userId == null)
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        try {
            bindingMapper.updateBindingStatus(boundId, id);
            return new Response();
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response refuseBind(int boundId, int id) throws Exception {
        Integer userId = bindingMapper.confirmUser(boundId, id);
        if (userId == null)
            throw new GlobalException(GlobalStatus.insufficientPermissions);
        try {
            bindingMapper.deleteBindingBoundId(boundId, id);
            return new Response();
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }
}
