package com.jryyy.forum.constant;

/**
 *
 * @author OU
 */
public class RedisKey {

    public static final String ONLINE_USER_LIST_KEY = "Online_user_list";

    public static final String USER_OFFLINE_RECORD_KEY = "User_offline_record";

    public static final String SINGLE_CHAT_MESSAGE_KEY = "single_chat_message";

    public static final String GROUP_CHAT_MESSAGE_KEY = "group_chat_message";

    public static String userKey(Integer key){ return String.format("key:%d",key); }

    public static String userKey(String key){ return String.format("key:%s",key); }

    public static String userTokenKey(Integer key){
        return String.format("%d_token_key",key);
    }

    public static String userTokenKey(String key){
        return String.format("%s_token_key",key);
    }

    public static String modifyPasswordCodeKey(String key){
        return String.format("%s_password_key",key);
    }

    public static String lockKey(String key){
        return String.format("%s_lock_key",key);
    }

    public static String lockKey(Integer key){
        return String.format("%d_lock_key",key);
    }

    public static String registrationCodeKey(String key){return String.format("%s_registration_key",key); }

    public static String userMessageKey(Integer toKey,Integer fromKey){return String.format("%d:%d",toKey,fromKey);}

    public static String groupMessageKey(Integer groupId){return String.format("Group:%d",groupId);}
}
