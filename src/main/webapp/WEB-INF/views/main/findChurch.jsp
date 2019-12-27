<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link media="screen" rel="stylesheet" href="<c:url value='/resources/js/jquery/bootstrap/bootstrap.form.css' />" />
<link media="screen" rel="stylesheet" href="<c:url value='/resources/css/application.css' />" />
<title></title>
</head>
<body>
<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>

<div class="contents-wrap  bg-white">

<div class="page-title"><img src="<c:url value="/resources/images/main/img_application_title.gif"/>" /></div>

  <div class="content_layout">
      <div class="container service-wrap">
      <ul id="myTab" class="nav nav-tabs">
          <li class="active"><a data-toggle="tab" href="#find-church">교회검색</a></li>
      </ul>
      
      <div id="myTabContent" class="tab-content">
          <div id="find-church" class="find-form-wrap tab-pane active"><c:url var="findUrl" value="/application-service/find"/>
              <form id="find-church-form" class="form-inline" action="${findUrl}" method="get">
              <select class="input-medium" name="criteria">
                  <option value="name">교회명 (기본)</option>
                  <option value="pastor">담임목사</option>
                  <option value="address">교회주소</option>
              </select>
              <input type="text" class="input-medium" placeholder="검색어" name="keyword">
              <button type="submit" class="btn">교회 검색</button>
              </form>
              
              <div id="result-list"></div>
          </div>
      </div>
      </div>
  </div>
</div>

</div>
<script type="text/javascript">
$(function () {
	$.validator.setDefaults({onkeyup:false,onclick:false,onfocusout:false});

    $('#myTab a:first').tab('show');
    
    $('#find-church-form').validate({
    	submitHandler: function(form) {
    		/*
    		 $(form).ajaxSubmit(); 으로 ajax 호출을 했을 때,
    		 첫번째 호출은 ajax 호출이 되지만, 두번째 호출시에는 ajax 호출이 되지 않는다.
    		*/
    		
    		var url = '${findUrl}'; /*$('#search-church-form').attr('action');*/
    		$.ajax({
    			cache: false,
    			type: 'GET',
    			url: url,
    			data: $('#find-church-form').serialize(),
    			success: function(html) {
    				$('#result-list').children().remove();
    				$('#result-list').append(html);
    			}
    		});
    	},
        showErrors:function(errorMap, errorList){
            if(!$.isEmptyObject(errorList)){
                alert(errorList[0].message);
            }
        },
        rules: {
            'keyword': {
                required: true,
                kor: true
            }
        },
        messages: {
            'keyword': {
                required: '검색어를 입력하세요.'
            }
        }
    });
});
</script>
</body>
</html>
