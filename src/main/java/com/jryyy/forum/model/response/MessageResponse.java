package com.jryyy.forum.model.response;

import com.jryyy.forum.model.Message;
import com.jryyy.forum.model.MessageRecord;
import com.jryyy.forum.model.request.UserInfoRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private MessageRecord message;

    private UserInfoResponse userInfo;

    private Integer number;
}
