package com.gm.sn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Article entity.
 */
@Data
@Entity
@Table(name = "news_article")
@ToString
@DynamicUpdate
@JsonIgnoreProperties({"handler","hibernateLazyInitializer","articleisCheck"})
public class JotterArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(message = "id 不能为 null")
    private int id;

    /**
     * Article title.
     */
    @NotEmpty(message = "文章标题不能为空")
    private String articleTitle;

    /**
     * Article content after render to html.
     */
    private String articleContentHtml;

    /**
     * Article content in markdown syntax.
     */
    private String articleContentMd;

    /**
     * Article abstract.
     */
    private String articleAbstract;

    /**
     * Article cover's url.
     */
    private String articleCover;

    private String articleVideo;

    /**
     * Article release date.
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") //返回时间类型
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") //接收时间类型
    private Date articleDate;

    @JoinColumn(name="article_views")
    private int articleViews;

//    @JoinColumn(name="article_auditMind")
    private String articleAuditMind;

    private String articleRate;

    private String articleAuthor;

    private boolean articleisTop;

    @ManyToOne
    @JoinColumn(name="cid")
    private Category category;

    @ManyToOne
    @JoinColumn(name="articleis_check")
    private Audit_status audit_status;

}
