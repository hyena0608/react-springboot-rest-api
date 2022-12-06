package com.devcourse.blind.domain.category.repository;

import com.devcourse.blind.domain.category.domain.CategoryTitle;
import com.devcourse.blind.domain.category.sql.CategorySql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

import static com.devcourse.blind.domain.category.CategoryParamColumnMapper.CATEGORY_TITLE;

@Slf4j
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        saveAllCategoryTypes();
    }

    @Override
    public void saveAllCategoryTypes() {
        toCategoryTitlesParamSource();
        try {
            jdbcTemplate.batchUpdate(
                    CategorySql.INSERT_CATEGORIES.getSql(),
                    toCategoryTitlesParamSource());
        } catch (DataAccessException e) {
            log.error("Fail - {}", e.getMessage());
        }
    }

    private Map<String, String>[] toCategoryTitlesParamSource() {
        CategoryTitle[] categoryTitles = CategoryTitle.values();
        int titleCounts = categoryTitles.length;
        Map<String, String>[] paramSources = new HashMap[titleCounts];
        for (int index = 0; index < titleCounts; index++) {
            paramSources[index] = new HashMap<>();
            paramSources[index].put(CATEGORY_TITLE.getParam(), categoryTitles[index].toString());
        }
        return paramSources;
    }
}
