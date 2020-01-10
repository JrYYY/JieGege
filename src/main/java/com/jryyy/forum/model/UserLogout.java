package com.jryyy.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLogout {

    private Integer userId;

    private LocalDateTime localDateTime;

}
