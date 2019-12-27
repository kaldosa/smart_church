package com.laonsys.springmvc.extensions.bind.support;

import java.lang.annotation.Annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.laonsys.springmvc.extensions.bind.annotation.WebQuery;
import com.laonsys.springmvc.extensions.model.Paginate;
import com.laonsys.springmvc.extensions.model.QueryParam;

public class WebQueryArgumentResolver implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        Annotation[] annotations = methodParameter.getParameterAnnotations();
        
        if(methodParameter.getParameterType().equals(QueryParam.class))
        {
            for(Annotation annotation : annotations)
            {
                if(WebQuery.class.isInstance(annotation))
                {
                    QueryParam queryParam = new QueryParam();
                    String criteria = webRequest.getParameter("criteria");
                    String keyword = webRequest.getParameter("keyword");
                    String q_p = webRequest.getParameter("page");
                    String q_ipp = webRequest.getParameter("ipp");
                    String q_ppb = webRequest.getParameter("ppb");
                    
                    if(criteria != null) {
                        queryParam.setCriteria(criteria);
                    }
                    if(keyword != null) {
                        queryParam.setKeyword(keyword);
                    }
                    int page = (q_p != null && !"".equals(q_p)) ? Integer.parseInt(q_p) : 1;
                    int itemPerPage = (q_ipp != null && !"".equals(q_ipp)) ? Integer.parseInt(q_ipp) : 10;
                    int pagePerBlock = (q_ppb != null && !"".equals(q_ppb)) ? Integer.parseInt(q_ppb) : 10;
                    
                    Paginate paginate = new Paginate(page, itemPerPage, pagePerBlock);
                    queryParam.setPaginate(paginate);
                    
                    return (QueryParam)queryParam;
                }
            }
        }
        
        return WebArgumentResolver.UNRESOLVED;
    }

}
