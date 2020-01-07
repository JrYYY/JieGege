package com.jryyy.forum.model.response;

import com.jryyy.forum.constant.GlobalStatus;
import com.jryyy.forum.dao.UserZoneMapper;
import com.jryyy.forum.exception.GlobalException;
import com.jryyy.forum.model.IdentifiableEntity;
import com.jryyy.forum.model.ZoneImg;
import com.jryyy.forum.model.ZonePraise;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 *
 * @author JrYYY
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ZoneResponse extends IdentifiableEntity {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 内容
     */
    private String msg;

    /**
     * 创建时间
     */
    private Date date;

    /**
     *  类型
     */
    private int msgType;

    /**
     *  认可
     */
    private int praise;

    /**
     * 图片
     */
    private List<ZoneImg> zoneImgList;


    /**
     * 认可的用户
     */
    private List<ZonePraise> praiseUsers;


    /**
     * 添加
     * @param zoneMapper {@link UserZoneMapper}
     * @param fileUrl 访问文件路径
     * @throws GlobalException 抛出信息{@link GlobalStatus}
     */
    public void addZoneImage(UserZoneMapper zoneMapper, String fileUrl) throws GlobalException {
        try {
            this.zoneImgList = zoneMapper.findAllZoneImgByZoneId(super.getId());
            this.zoneImgList.forEach(zoneImg -> zoneImg.setImgUrl(fileUrl + zoneImg.getImgUrl()));
        } catch (Exception e) {
            throw new GlobalException(GlobalStatus.serverError);
        }
    }



}

