package com.jryyy.forum.constant;

/**
 *
 * @author OU
 */
public class RedisKey {

    public static final String SINGLE_CHAT_MESSAGE = "single_chat_message";

    public static final String GROUP_CHAT_MESSAGE = "group_chat_message";


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

    public static String  userMessageKey(Integer toKey,Integer fromKey){return String.format("%d:%d",toKey,fromKey);}

    public static String groupMessageKey(Integer groupId){return String.format("Group:%d",groupId);}
}
