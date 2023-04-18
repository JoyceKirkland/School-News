package com.gm.sn.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "article_audit_info")
@ToString
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class AuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private int nid;

    private int uid;//修改文章状态的操作用户id

//    private String userName_zh;//操作用户真名-删除-添加删除记录时用

//    private String newsTitle;//操作文章标题-添加删除记录时用

    private String operate_audit;//执行的操作

    private String audit_mind;//审核意见

    @CreatedDate
    @Column(name = "operate_date")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") //返回时间类型
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //接收时间类型
    private Date operate_date;
}
