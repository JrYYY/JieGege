package com.jryyy.forum.model;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *在线用户仓库，存储在线用户
 * @author OU
 */
@Component
public class ParticipantRepository {
    /**
     * 在线用户map，键：用户名称，值：用户对象
     */
    private Map<Integer, User> activeSessions = new ConcurrentHashMap<Integer, User>();

    public Map<Integer, User> getActiveSessions() {
        return activeSessions;
    }

    public void setActiveSessions(Map<Integer, User> activeSessions) {
        this.activeSessions = activeSessions;
    }

    public void add(Integer id, User user){
        activeSessions.put(id, user);

    }

    public User remove(Integer id){
        return activeSessions.remove(id);
    }

    public boolean containsUserName(Integer id){
        return activeSessions.containsKey(id);
    }
}
