package cn.duckflew.service;

import cn.duckflew.dao.ArticleMapper;
import cn.duckflew.po.Article;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article>
{
}
