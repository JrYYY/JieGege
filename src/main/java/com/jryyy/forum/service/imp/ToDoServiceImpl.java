package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.ToDoMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.ToDoService;
import org.springframework.stereotype.Service;

/**
 * 目标服务实现
 *
 * @author OU
 */
@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoMapper toDoMapper;

    public ToDoServiceImpl(ToDoMapper toDoMapper) {
        this.toDoMapper = toDoMapper;
    }


    @Override
    public Response addTarget(Integer userId, String target) throws Exception {
        toDoMapper.insertToDo(userId, target);
        return new Response<>("添加目标成功");
    }

    @Override
    public Response editTarget(Integer userId, Integer id, String target) throws Exception {
        if (toDoMapper.updateToDo(userId, id, target) == 0) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        } else {
            return new Response<>("修改目标成功");
        }
    }

    @Override
    public Response deleteTarget(Integer userId, Integer id) throws Exception {
        if (toDoMapper.deleteToDo(userId, id) == 0) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        } else {
            return new Response<>("删除目标成功");
        }
    }

    @Override
    public Response findTargetByUserId(Integer userId) throws Exception {
        return new Response<>(toDoMapper.findTargetByUserId(userId));
    }
}
