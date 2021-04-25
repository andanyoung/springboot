package andyoung.springboot.elasticsearch.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/** */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document(indexName = "article")
public class Article implements Serializable {

  private static final long serialVersionUID = 6056284831555903114L;
  @Id private String id;

  /** 标题 */
  @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String title;

  /** 作者 */
  @Field(type = FieldType.Keyword)
  private String author;

  /** 摘要 */
  @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
  private String summary;

  /** 内容 */
  @Field(analyzer = "ik_max_word", searchAnalyzer = "ik_max_word", store = true)
  private String content;

  /** 审核状态 */
  private int state;

  /** 浏览量 */
  private int pv;

  @Field(enabled = false)
  private String url;

  @Field(format = DateFormat.date_time, type = FieldType.Date)
  private Date publishDate;
}
