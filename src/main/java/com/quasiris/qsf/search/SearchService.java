package com.quasiris.qsf.search;

import com.quasiris.qsf.pipeline.*;
import com.quasiris.qsf.pipeline.filter.DebugFilter;
import com.quasiris.qsf.pipeline.filter.elastic.ElasticFilterBuilder;
import com.quasiris.qsf.pipeline.filter.qsql.QSQLRequestFilter;
import com.quasiris.qsf.response.SearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("SearchService")
public class SearchService {

    @Value("${app.elasticBaseUrl}")
    private String baseUrl;


    public SearchResponse search(HttpServletRequest request) throws PipelineContainerException, PipelineContainerDebugException {
        String type = "search";
        Pipeline pipeline = PipelineBuilder.create().
                pipeline(type).
                timeout(4000L).
                filter(new DebugFilter()).
                filter(new QSQLRequestFilter()).
                filter(ElasticFilterBuilder.create().
                        baseUrl(baseUrl).
                        profile("classpath://config/elastic/search.json").
                        resultSetId(type).
                        mapField("gtin_name", "title").
                        mapField("gtin_name", "gtin").
                        mapField("brand", "brand").
                        mapField("gtin_image", "image").
                        mapField("brand_image", "brand_image").
                        mapFilter("brand", "brand").
                        build()).
                build();

        PipelineContainer pipelineContainer = PipelineExecuter.create().
                pipeline(pipeline).
                httpRequest(request).
                execute();

        SearchResponse searchResponse = SearchResponse.create(pipelineContainer, type);
        return searchResponse;
    }
}
