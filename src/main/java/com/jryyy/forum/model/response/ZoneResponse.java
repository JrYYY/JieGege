package com.jryyy.forum.model.response;


import com.jryyy.forum.dao.ZoneMapper;
import com.jryyy.forum.model.IdentifiableEntity;
import com.jryyy.forum.model.Zone;
import com.jryyy.forum.model.ZoneImg;
import com.jryyy.forum.model.ZonePraise;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author JrYYY
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ZoneResponse   {

    /** 标识*/
    private Integer id;

    /** 内容 */
    private String msg;

    /** 创建时间 */
    private LocalDateTime createDate;

    /** 类型 */
    private Integer msgType;

    /** 赞同数 */
    private Integer favorite;

    /** 评论数 */
    private Integer comment;

    /**
     * 是否已评论
     */
    private Boolean isFavorite;

    /**
     *用户信息
     */
    private UserInfoResponse userInfo;

    /**
     * 图片
     */
    private List<ZoneImg> zoneImgList;

    /**
     * 认可的用户
     */
    private List<UserInfoResponse> favoriteUser;


    public void setZone(Zone zone){
        this.id = zone.getId();
        this.msg = zone.getMsg();
        this.createDate = zone.getCreateDate();
        this.msgType = zone.getMsgType();
        this.favorite = zone.getFavorite();
    }

    /**
     * 添加
     * @param zoneMapper {@link ZoneMapper}
     * @param fileUrl 访问文件路径
     */
    public void addZoneImage(ZoneMapper zoneMapper, String fileUrl) {
            this.zoneImgList = zoneMapper.findAllZoneImgByZoneId(getId());
            this.zoneImgList.forEach(zoneImg -> zoneImg.setImgUrl(fileUrl + zoneImg.getImgUrl()));
    }

}

