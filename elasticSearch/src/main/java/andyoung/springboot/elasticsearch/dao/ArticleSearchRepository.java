package andyoung.springboot.elasticsearch.dao;

import andyoung.springboot.elasticsearch.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, String> {
  public Page<Article> findByTitleOrContentLike(String keyword, String content, Pageable pageable);
}
