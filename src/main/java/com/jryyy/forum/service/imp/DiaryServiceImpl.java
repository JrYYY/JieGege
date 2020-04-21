package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.DiaryMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.service.DiaryService;
import org.springframework.stereotype.Service;

/**
 * @author OU
 */
@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryMapper diaryMapper;

    public DiaryServiceImpl(DiaryMapper diaryMapper) {
        this.diaryMapper = diaryMapper;
    }

    @Override
    public Response addDiary(Integer userId, String content) throws Exception {
        diaryMapper.insertDiary(userId, content);
        return new Response<>("添加日记成功");
    }

    @Override
    public Response editDiary(Integer userId, Integer id, String content) throws Exception {
        if (diaryMapper.updateDiary(userId, id, content) == 0) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        } else {
            return new Response<>("修改日记成功");
        }
    }

    @Override
    public Response deleteDiary(Integer userId, Integer id) throws Exception {
        if (diaryMapper.deleteDiary(userId, id) == 0) {
            throw new GlobalException(GlobalStatus.unauthorizedAccess);
        } else {
            return new Response<>("删除日记成功");
        }
    }

    @Override
    public Response findDiaryByUserId(Integer userId) throws Exception {
        return new Response<>(diaryMapper.findDiaryByUserId(userId));
    }
}
