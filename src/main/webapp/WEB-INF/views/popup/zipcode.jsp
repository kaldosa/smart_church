<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/church-layout.css" />" />
<title>Insert title here</title>
</head>
<body>
<div class="layerPopup billArsA billFindZip"> 
<h1>우편번호 찾기</h1> 

<div class="content"> 
    <p>지역의 읍/면/동의 이름을 공백없이 입력하신 후 <br /><span class="point">검색</span> 버튼을 클릭하세요. </p> 
    <p class="infotext">예) '서울특별시 강남구 역삼동' 인 경우 ‘역삼' 만 입력</p>
    <form id="form_zipcode">
    <div class="inputAdd"> 
        <input type="text" id="findAddr" class="text default" /> 
        <span style="padding-left:3px;"><input type="image" src="<c:url value="/resources/images/church/common/btn_search2.gif"/>" alt="검색" /></span>
    </div>
    </form>
    <div class="btnClose"><a href="javascript:close();"><img src="<c:url value="/resources/images/church/common/btn_close.gif"/>" alt="닫기버튼" width="13" height="13"/></a></div> 

<div id="list_zipcode"></div>
</div> 
    
<!-- //content -->
</div>

<c:set var="path" value="${pageContext.request.contextPath}/ajax/zipcode/" />
<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-1.8.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery/form/jquery.form.js"/>"></script>
<script type="text/javascript">

    $('#form_zipcode').submit(function(e) {
        e.preventDefault();
        
        $('#list_zipcode').empty();
        
        var findAddr = $.trim($('#findAddr').val());
        
        if(!findAddr) {
            return false;
        }

        /* 
         * utf-8 페이지에서 ajax + get 으로 전송시, IE 는 euc-kr 로 값을 전송해버린다.
         * 이를 해결하기 위해, encodeURIComponent 로 강제로 utf-8 로 인코딩 시킴 
         */
        var ajxUrl = '${path}' + encodeURIComponent(findAddr);
        
        $.ajax({
            url: ajxUrl,
            type: "GET",
            dataType: "json",
            success: function(data) {
                $('<p class="comment_sub">아래 해당하는 주소를 선택해 주세요.</p>').appendTo('#list_zipcode');
                var items = [];
                $.each(data, function(i, data) {
                    items.push('<li class="result_item"><a href="#"><span>' 
                            + data.zipcode
                            + ' '
                            + data.sido 
                            + ' ' 
                            + data.gugun 
                            + ' ' 
                            + data.dong 
                            + ' ' 
                            + data.ri 
                            + ' ' 
                            + data.building
                            + '</span>'
                            + ' '
                            + data.bunji
                            + '</a></li>');
                  });
                $('<ul/>', {
                    'class': 'result_zipcode',
                    html: items.join('')
                  }).appendTo('#list_zipcode');
                
                window.resizeTo(420, 500);
            }
            , error: function(xhr, status, error) {
                alert(error);
                return false;
            }
        });
    });
    
    $('.result_item').live('click', function() {
        /* var zipcode = $(this).find('span').attr('class'); */
        var adderss = $(this).find('span').text().trim();
/*         var code = zipcode.split("-");
        $(opener.document).find('#zipCd1').val(code[0]);
        $(opener.document).find('#zipCd2').val(code[1]); */
        $(opener.document).find('#addr1').val(adderss);
        /* $(opener.document).find('#addr2').show(); */
        self.close();
    });
</script>
</body>
</html>