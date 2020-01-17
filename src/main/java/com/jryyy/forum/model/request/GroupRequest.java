package com.jryyy.forum.model.request;

import com.jryyy.forum.model.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 创建群组请求类
 * @author OU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {

    @NotNull(message = "请填写群名称")
    private String name;

    private String slogan;

    private MultipartFile avatar;

    @Size(min = 1,message = "您的群组没有成员,无法组建群组")
    private List<Integer> members;

    public Group toGroup(){
        return Group.builder().name(name).slogan(slogan).build();
    }
}
