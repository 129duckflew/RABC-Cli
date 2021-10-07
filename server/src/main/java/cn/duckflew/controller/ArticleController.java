package cn.duckflew.controller;

import cn.duckflew.config.annotation.RequiresPermissions;
import cn.duckflew.po.Article;
import cn.duckflew.service.ArticleService;
import cn.duckflew.util.CommonUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController
{

    @Autowired
    ArticleService articleService;

    @RequiresPermissions(value = {"article:list"})
    @GetMapping("/")
    public JSONObject listArticle()
    {
        List<Article> articles = articleService.list();
        return CommonUtils.successJson(articles);
    }

    @RequiresPermissions({"article:add"})
    @PostMapping("/")
    public JSONObject addArticle(@RequestBody JSONObject jsonObject)
    {
        CommonUtils.hasAllRequired(jsonObject,"content");
        Article article = new Article();
        article.setContent((String) jsonObject.get("content"));
        article.setCreateTime(LocalDateTime.now());
        article.setDeleteStatus("1");
        articleService.save(article);
        return CommonUtils.successJson(article);
    }
    @RequiresPermissions({"article:update"})
    @PutMapping("/")
    public JSONObject updateArticle(@RequestBody JSONObject jsonObject)
    {
        CommonUtils.hasAllRequired(jsonObject,"content,id,deleteStatus");
        Article article = new Article();
        article.setId((Integer) jsonObject.get("id"));
        article.setContent((String) jsonObject.get("content"));
        article.setUpdateTime(LocalDateTime.now());
        article.setDeleteStatus((String) jsonObject.get("deleteStatus"));
        articleService.updateById(article);
        return CommonUtils.successJson(article);
    }
    @RequiresPermissions({"article:delete"})
    @DeleteMapping("/{id}")
    public JSONObject deleteArticle(@PathVariable Integer id)
    {
        articleService.removeById(id);
        return CommonUtils.successJson(new JSONObject());
    }
}
