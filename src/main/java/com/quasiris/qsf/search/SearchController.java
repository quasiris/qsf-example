package com.quasiris.qsf.search;


import com.quasiris.qsf.pipeline.PipelineContainerDebugException;
import com.quasiris.qsf.pipeline.PipelineContainerException;
import com.quasiris.qsf.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/qsf-example")
public class SearchController {


    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<SearchResponse> search(HttpServletRequest httpServletRequest) throws PipelineContainerException, PipelineContainerDebugException {

        SearchResponse searchResponse = searchService.search(httpServletRequest);
        ResponseEntity<SearchResponse> response = ResponseEntity.ok(searchResponse);
        return response;
    }
}
