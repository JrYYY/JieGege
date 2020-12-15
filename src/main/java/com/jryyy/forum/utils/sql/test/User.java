package com.jryyy.forum.utils.sql.test;

import com.jryyy.forum.utils.sql.bind.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
@Join({"user_info b on user.ID = b.userId",
        "(select sex,count(*) number from user " +
                " join user_info on user.id = user_info.userId group by sex )" +
                " c on c.sex = b.sex "})
public class User {

    @Column
    private String emailName;

    @Column
    private String password;

    @Column
    private String nickname;

    @Order(isDesc = true)
    @Column("b.username")
    private String username;

    @Column
    private String number;

    @Column("b.sex")
    @Condition("b.sex = #{data.sex}")
    private String sex;
}
