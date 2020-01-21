package com.jryyy.forum.service.imp;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.MessageMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.MessageRecord;
import com.jryyy.forum.model.Response;
import com.jryyy.forum.model.response.MessageResponse;
import com.jryyy.forum.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author OU
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageMapper messageMapper){
        this.messageMapper = messageMapper;
    }

    @Override
    public Response sendMessage(Message message) throws Exception {
        try {
            messageMapper.insertMessage(message);
            return new Response();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response readMessage(Integer to, Integer from) throws Exception {
        try {
            List<MessageRecord> messages = messageMapper.findMessageByFromIdAndToId(from,to);
            List<MessageRecord> messageList = messageMapper.findMessageByFromIdAndToId(to,from);
            messages.addAll(messageList);
            messages.sort(Comparator.comparing(MessageRecord::getDate));
            messageMapper.updateMessageStatus(from,to);
            return new Response<>(messages);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }

    @Override
    public Response uncheckedMessages(Integer userId) throws Exception {
        try {
            List<Integer> uncheckedUserIdList = messageMapper.findFromByTo(userId);
            List<MessageResponse> responseList = new ArrayList<>();
            uncheckedUserIdList.forEach(from -> responseList.add(MessageResponse.builder().message(messageMapper.findMessageByDate(from,userId))
                    .number(messageMapper.findNumberByFromIdAndToId(from,userId)).build()));
            return new Response<>(responseList);
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(GlobalStatus.serverError);
        }
    }
}
