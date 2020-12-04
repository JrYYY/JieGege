package com.jryyy.forum.utils.sql.test;

import com.jryyy.forum.utils.sql.bind.Column;
import com.jryyy.forum.utils.sql.bind.Condition;
import com.jryyy.forum.utils.sql.bind.Join;
import com.jryyy.forum.utils.sql.bind.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
@Join("user_info b on user.ID = b.userId")
public class User {

    @Column
    private String emailName;

    @Column
    private String password;

    @Column
    private String nickname;

    @Condition
    private String username;
}
