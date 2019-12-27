<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<!--[if IE 6]>
<script type="text/javascript" src="/js/jquery.pngSupport.js"></script>
<script>DD_belatedPNG.fix('.png_bg');</script>
<![endif]-->
</head>
<body>
<!-- 회원가입 -->
<s:bind path="user.*">

<div id="contents">
<div class="title-image"><img src="<c:url value="/resources/images/main/visual_img.png" />" /></div>
<div class="contents-wrap join">

<h1>회원가입</h1>

<div class="join-wrap">
<c:url var="joinUrl" value="/joinUser" />
<form:form id="create-form" name="create-form" method="post" action="${joinUrl}" modelAttribute="user">

<c:if test="${not empty status.errorMessages}">
<div class="alert alert-error alert-block"><a class="close" data-dismiss="alert">×</a><h4 class="alert-heading">Error!</h4>
<div class="error-container">
<ul>
<form:errors path="email" element="li" cssClass="error"/>
<form:errors path="name" element="li" cssClass="error"/>
<form:errors path="password" element="li" cssClass="error"/>
<form:errors path="mobilePhone" element="li" cssClass="error"/>
</ul>
</div>
</div>
</c:if>
    
<!-- 이용약관 -->
<h3 class="agreement">이용약관</h3>
<div class="agreement">
<h4>'SMART CHURCH' 이용약관</h4>

<h5>제1장 총 칙</h5>

<span class="tit">제 1 조 (목적)</span>

이 약관은 '라온시스'(이하 "회사"라 한다)가 운영하는 ‘smartchurch.com’(이하 "사이트"라 한다)에서 제공하는 인터넷 관련 서비스(이하 "서비스"라 한다)를 이용함에 있어 사이트와 이용자의 권리, 의무 및 책임 사항을 규정함을 목적으로 합니다.

<span class="tit">제 2 조 (용어의 정의)</span>
<ol class="doc_list">
<li>이 약관에서 사용하는 용어의 정의는 다음과 같습니다.
<ol class="doc_list_nostyle">
    <li>① '회원'이라 함은 이 약관에 동의하고 서비스를 이용하는 이용자를 말합니다.</li>
    <li>② '이용계약'이라 함은 이 약관을 포함하여 서비스 이용과 관련하여 회사와 회원 간에 체결하는 모든 계약을 말합니다.</li>
    <li>③ '이용자ID'라 함은 회원의 식별 및 서비스 이용을 위하여 회원의 신청에 따라 회사가 회원 별로 부여하는 고유한 문자와 숫자의 조합을 말합니다.</li>
    <li>④ '비밀번호'라 함은 이용자ID로 식별되는 회원의 본인 여부를 검증하기 위하여 회원이 설정하여 회사에 등록한 고유의 문자와 숫자의 조합을 말합니다.</li>
    <li>⑤ '탈퇴'라 함은 회사 또는 회원이 이용계약을 해약하는 것을 말합니다.</li>
</ol>
</li>
<li>이 약관에서 사용하는 용어 중 제1항에서 정하지 아니한 것은 관계 법령 및 서비스별 안내에서 정하는 바에 따르며, 그 외에는 일반 관례에 따릅니다.</li>
</ol>
    
<span class="tit">제 3 조 (이용약관의 효력 및 변경)</span>
<ol class="doc_list">
    <li>이 약관은 ‘사이트‘를 통해 온라인으로 공시하고 회원의 동의와 회사의 승낙으로 효력을 발생하며, 합리적인 사유가 발생할 경우 회사는 관련 법령에 위배되지 않는 범위 안에서 개정할 수 있습니다. 개정된 약관은 정당한 절차에 따라 ‘사이트‘을 통해 공지함으로써 효력을 발휘합니다.</li>
    <li>회원은 정기적으로 ‘사이트‘를 방문하여 약관의 변경사항을 확인하여야 합니다. 변경된 약관에 대한 정보를 알지 못해 발생하는 회원의 피해는 회사에서 책임지지 않습니다.</li>
    <li>회원은 변경된 약관에 동의하지 않을 경우 회원 탈퇴(해지)를 요청할 수 있습니다.</li>
</ol>

<span class="tit">제 4 조 (약관 외 준칙)</span>
회사는 필요한 경우 서비스 내의 개별항목에 대하여 개별약관 또는 운영원칙(이하 '서비스별 안내'라 합니다)을 정할 수 있으며, 이 약관과 서비스별 안내의 내용이 상충되는 경우에는 서비스별 안내의 내용을 우선하여 적용합니다.
        
<h5>제2장 이용계약 체결</h5>

<span class="tit">제 5 조 (이용 계약의 성립)</span>
<ol class="doc_list">
    <li>이용계약은 이용자의 이용계약 내용에 대한 동의와 이용신청에 대하여 회사의 이용승낙으로 성립합니다.</li>
    <li>이용계약에 대한 동의는 이용신청 당시 신청서 상의 '동의함' 버튼을 누름으로써 의사표시를 합니다.</li>
</ol>

<span class="tit">제 6 조 (서비스 이용 신청)</span>
<ol class="doc_list">
    <li>회원으로 가입하여 서비스를 이용하고자 하는 이용자는 회사에서 요청하는 제반 정보(이용자ID, 비밀번호, 이름, 연락처, 이메일 등)를 제공하여야 합니다.</li>
    <li>모든 회원은 반드시 회원 본인의 정보를 제공하여야만 서비스를 이용할 수 있으며, 타인의 정보를 도용하거나 허위의 정보를 등록하는 등 본인의 진정한 정보를 등록하지 않은 회원은 서비스 이용과 관련하여 아무런 권리를 주장할 수 없으며, 관계 법령에 따라 처벌 받을 수 있습니다.</li>
    <li>회원가입은 반드시 본인의 진정한 정보를 통하여만 가입할 수 있으며 회사는 회원이 등록한 정보에 대하여 확인조치를 할 수 있습니다. 회원은 회사의 확인조치에 대하여 적극 협력하여야 하며, 만일 이를 준수하지 아니할 경우 회사는 회원이 등록한 정보가 부정한 것으로 처리할 수 있습니다.</li>
</ol>

<span class="tit">제 7 조 (개인정보의 보호 및 사용)</span>

<ol class="doc_list">
<li>회사는 관계 법령이 정하는 바에 따라 회원의 개인정보를 보호하기 위해 노력합니다. 개인정보의 보호 및 사용에 대해서는 관련 법령 및 회사의 개인정보 보호정책이 적용됩니다. 단, 회사의 공식 사이트 이외의 링크된 사이트에서는 회사의 개인정보 보호정책이 적용되지 않습니다. 또한, 회원은 비밀번호 등이 타인에게 노출되지 않도록 철저히 관리해야 하며 회사는 회원의 귀책사유로 인해 노출된 정보에 대해서 책임을 지지 않습니다.</li> 
<li>회사는 다음과 같은 경우에 법이 허용하는 범위 내에서 회원의 개인정보를 제3자에게 제공할 수 있습니다. 
<ol class="doc_list_nostyle">
    <li>① 수사기관이나 기타 정부기관으로부터 정보제공을 요청 받은 경우</li>
    <li>② 회원의 법령 또는 약관의 위반을 포함하여 부정행위 확인 등의 정보보호 업무를 위해 필요한 경우</li>
    <li>③ 기타 법률에 의해 요구되는 경우</li>
</ol>
</li>
</ol>

<span class="tit">제 8 조 (이용 신청의 승낙과 제한)</span>
<ol class="doc_list">
<li>회사는 제5조, 제6조의 규정에 의한 이용신청에 대하여 업무 수행상 또는 기술상 지장이 없는 경우에 원칙적으로 접수순서에 따라 서비스 이용을 승낙합니다.</li>
<li>회사는 아래사항에 해당하는 경우에 대해서 승낙을 보류할 수 있습니다.
    <ol class="doc_list_nostyle">
        <li>① 본인의 진정한 정보를 제공하지 아니한 이용신청의 경우</li>
        <li>② 법령 위반 또는 사회의 안녕과 질서, 미풍양속을 저해할 목적으로 신청한 경우</li>
        <li>③ 부정한 용도로 본 서비스를 이용하고자 하는 경우</li>
        <li>④ 영리를 추구할 목적으로 본 서비스를 이용하고자 하는 경우</li>
        <li>⑤ 서비스와 경쟁관계에 있는 이용자가 신청하는 경우</li>
        <li>⑥ 법령 또는 약관을 위반하여 이용계약이 해지된 적이 있는 이용자가 신청하는 경우</li>
        <li>⑦ 기타 규정한 제반 사항을 위반하며 신청하는 경우</li>
    </ol>
</li>
<li>회사는 서비스 이용신청이 다음 각 호에 해당하는 경우에는 그 신청에 대하여 승낙 제한사유가 해소될 때까지 승낙을 유보할 수 있습니다.
    <ol class="doc_list_nostyle">
        <li>① 회사가 설비의 여유가 없는 경우</li>
        <li>② 회사의 기술상 지장이 있는 경우</li>
        <li>③ 기타 회사의 귀책사유로 이용승낙이 곤란한 경우</li>
    </ol>
</li>
<li>회사는 이용신청고객이 관계 법령에서 규정하는 미성년자일 경우에 서비스별 안내에서 정하는 바에 따라 승낙을 보류할 수 있습니다.</li>
<li>회사는 회원 가입 절차 완료 이후 제2항 각 호에 따른 사유가 발견된 경우 이용 승낙을 철회할 수 있습니다.</li>
</ol>

<span class="tit">제 9 조 (이용자 ID 부여 및 변경 등)</span>
<ol class="doc_list">
<li>회사는 회원에 대하여 약관에 정하는 바에 따라 이용자 ID를 부여합니다.</li>
<li>이용자ID는 원칙적으로 변경이 불가하며 부득이한 사유로 인하여 변경 하고자 하는 경우에는 해당 ID를 해지하고 재가입해야 합니다.</li>
<li>‘사이트‘의 이용자ID는 회원 본인의 동의 하에 회사 또는 자회사가 운영하는 사이트의 회원ID와 연결될 수 있습니다.</li>
<li>이용자ID는 다음 각 호에 해당하는 경우에는 회원의 요청 또는 회사의 직권으로 변경 또는 이용을 정지할 수 있습니다.
    <ol class="doc_list_nostyle">
        <li>① 이용자ID가 전화번호 또는 주민등록번호 등으로 등록되어 사생활 침해가 우려되는 경우</li>
        <li>② 타인에게 혐오감을 주거나 미풍양속에 어긋나는 경우</li>
        <li>③ 회사, 회사의 서비스 또는 서비스 운영자 등의 명칭과 동일하거나 오인 등의 우려가 있는 경우</li>
        <li>④ 기타 합리적인 사유가 있는 경우</li>
    </ol>
</li>
<li>이용자ID 및 비밀번호의 관리책임은 회원에게 있습니다. 이를 소홀이 관리하여 발생하는 서비스 이용상의 손해 또는 제3자에 의한 부정이용 등에 대한 책임은 회원에게 있으며 회사는 그에 대한 책임을 지지 않습니다.</li>
<li>기타회원 개인정보 관리 및 변경 등에 관한 사항은 서비스별 안내에 정하는 바에 의합니다.</li>
</ol>

<h5>제 3 장 계약 당사자의 의무</h5>

<span class="tit">제 10 조 (회사의 의무)</span>
<ol class="doc_list">
    <li>회사는 회원이 희망한 서비스 제공 개시일에 특별한 사정이 없는 한 서비스를 이용할 수 있도록 하여야 합니다.</li>
    <li>회사는 계속적이고 안정적인 서비스의 제공을 위하여 설비에 장애가 생기거나 멸실된 때에는 부득이한 사유가 없는 한 지체 없이 이를 수리 또는 복구합니다.</li>
    <li>회사는 개인정보 보호를 위해 보안시스템을 구축하며 개인정보 보호정책을 공시하고 준수합니다.</li>
    <li>회사는 회원으로부터 제기되는 의견이나 불만이 정당하다고 객관적으로 인정될 경우에는 적절한 절차를 거쳐 즉시 처리하여야 합니다. 다만, 즉시 처리가 곤란한 경우는 회원에게 그 사유와 처리일정을 통보하여야 합니다.</li>
</ol>

<span class="tit">제 11 조 (회원의 의무)</span>
<ol class="doc_list">
    <li>회원은 회원가입 신청 또는 회원정보 변경 시 모든 사항을 사실에 근거하여 본인의 진정한 정보로 작성하여야 하며, 허위 또는 타인의 정보를 등록할 경우 이와 관련된 모든 권리를 주장할 수 없습니다.</li>
    <li>회원은 약관에서 규정하는 사항과 기타 회사가 정한 제반 규정, 공지사항 등 회사가 공지하는 사항 및 관계 법령을 준수하여야 하며, 기타 회사의 업무에 방해가 되는 행위, 회사의 명예를 손상시키는 행위, 타인에게 피해를 주는 행위를 해서는 안됩니다.</li>
    <li>회원은 주소, 연락처, 전자우편 주소 등 이용계약사항이 변경된 경우에 해당 절차를 거쳐 이를 회사에 즉시 알려야 합니다.</li>
    <li>회원은 회사의 사전 승낙 없이 서비스를 이용하여 영업활동을 할 수 없으며, 그 영업활동의 결과에 대해 회사는 책임을 지지 않습니다. 또한 회원은 이와 같은 영업활동으로 회사가 손해를 입은 경우, 회원은 회사에 대해 손해배상의무를 지며, 회사는 해당 회원에 대해 서비스 이용제한 및 적법한 절차를 거쳐 손해배상 등을 청구할 수 있습니다.</li>
    <li>회원은 회사의 명시적 동의가 없는 한 서비스의 이용권한, 기타 이용계약상의 지위를 타인에게 양도, 증여할 수 없으며 이를 담보로 제공할 수 없습니다.</li>
    <li>회원은 회사 및 제 3자의 지적 재산권을 포함한 제반 권리를 침해하거나 제18조 각 호에 해당하는 행위를 해서는 안됩니다.</li>
</ol>
        
<h5>제 4 장 서비스의 이용</h5>

<span class="tit">제 12 조 (서비스 이용 시간)</span>
<ol class="doc_list">
    <li>서비스 이용은 회사의 업무상 또는 기술상 특별한 지장이 없는 한 연중무휴, 1일 24시간 운영을 원칙으로 합니다. 단, 회사는 시스템 정기점검, 증설 및 교체를 위해 회사가 정한 날이나 시간에 서비스를 일시 중단할 수 있으며, 예정되어 있는 작업으로 인한 서비스 일시 중단은 사이트를 통해 사전에 공지합니다.</li>
    <li>회사는 긴급한 시스템 점검, 증설 및 교체, 설비의 장애, 서비스 이용의 폭주, 국가비상사태, 정전 등 부득이한 사유가 발생한 경우 사전 예고 없이 일시적으로 서비스의 전부 또는 일부를 중단할 수 있습니다.</li>
    <li>회사는 서비스 개편 등 서비스 운영 상 필요한 경우 회원에게 사전 예고 후 서비스의 전부 또는 일부의 제공을 중단할 수 있습니다.</li>
</ol>

<span class="tit">제 13 조 (회원의 게시물 등)</span>
<ol class="doc_list">
    <li>게시물이라 함은 회원이 서비스를 이용하면서 게시한 글, 사진, 영상물(동영상, UCC) 및 각종 파일과 링크 등을 말합니다.</li>
    <li>회원이 서비스에 등록하는 게시물 등으로 인하여 본인 또는 타인에게 손해나 기타 문제가 발생하는 경우 회원은 이에 대한 책임을 지게 되며, 회사는 특별한 사정이 없는 한 이에 대하여 책임을 지지 않습니다.</li>
    <li>회사는 다음 각 호에 해당하는 게시물 등을 회원의 사전 동의 없이 임시게시 중단, 수정, 삭제, 이동 또는 등록 거부 등의 관련 조치를 취할 수 있습니다.
        <ol class="doc_list_nostyle">
            <li>① 다른 회원 또는 제 3자에게 심한 모욕을 주거나 명예를 손상시키는 내용인 경우</li>
            <li>② 공공질서 및 미풍양속에 위반되는 내용을 유포하거나 링크시키는 경우</li>
            <li>③ 불법복제 또는 해킹을 조장하는 내용인 경우</li>
            <li>④ 불법복제 또는 해킹을 조장하는 내용인 경우</li>
            <li>⑤ 영리를 목적으로 하는 광고일 경우</li>
            <li>⑥ 범죄와 결부된다고 객관적으로 인정되는 내용일 경우</li>
            <li>⑦ 다른 이용자 또는 제 3자의 저작권 등 기타 권리를 침해하는 내용인 경우</li>
            <li>⑧ 사적인 정치적 판단이나 종교적 견해의 내용으로 회사가 서비스 성격에 부합하지 않는다고 판단하는 경우</li>
            <li>⑨ 회사에서 규정한 게시물 원칙에 어긋나거나, 게시판 성격에 부합하지 않는 경우</li>
            <li>⑩ 기타 관계법령에 위배된다고 판단되는 경우</li>
        </ol>
    </li>
    <li>회사는 게시물 등에 대하여 제3자로부터 명예훼손, 지적재산권 등의 권리 침해를 이유로 게시중단 요청을 받은 경우 이를 임시로 게시중단(전송중단)할 수 있으며, 게시중단 요청자와 게시물 등록자 간에 소송, 합의 기타 이에 준하는 관련기관의 결정 등이 이루어져 회사에 접수된 경우 이에 따릅니다.</li>
    <li>해당 게시물 등에 대해 임시게시 중단이 된 경우, 게시물을 등록한 회원은 재게시(전송재개)를 회사에 요청할 수 있으며, 게시 중단일로부터 3개월 내에 재게시를 요청하지 아니한 경우 회사는 이를 삭제할 수 있습니다.</li>
</ol>

<span class="tit">제 14 조 (게시물에 대한 저작권)</span>
<ol class="doc_list">
    <li>회사가 작성한 게시물 또는 저작물에 대한 저작권 기타 지적재산권은 회사에 귀속합니다.</li>
    <li>회원이 서비스 내에 게시한 게시물의 저작권은 게시한 회원에게 귀속됩니다. 단, 회사는 서비스의 운영, 전시, 전송, 배포, 홍보의 목적 및 기타 회사가 자체 개발하거나 다른 회사와의 협력계약 등을 통해 회원들에게 제공하는 일체의 서비스에 회원의 별도 허락 없이 무상으로 저작권법에 규정하는 공정한 관행에 합치되게 합리적인 범위 내에서 다음과 같이 회원이 등록한 게시물을 사용할 수 있습니다.
        <ol class="doc_list_nostyle">
            <li>① 서비스 내에서 회원 게시물의 복제, 수정, 개조, 전시, 전송, 배포 및 저작물성을 해치지 않는 범위 내에서의 편집 저작물 작성</li>
            <li>② 미디어, 통신사 등 서비스 제휴 파트너에게 회원의 게시물 내용을 제공, 전시 혹은 홍보하게 하는 것. 단, 이 경우 회사는 별도의 동의 없이 회원의 이용자ID 외에 회원의 개인정보를 제공하지 않습니다.</li>
        </ol>
    </li>
    <li>회사는 전항 이외의 방법으로 회원의 게시물을 이용하고자 하는 경우, 전화, 팩스, 전자우편 등의 방법을 통해 사전에 회원의 동의를 얻어야 합니다.</li>
    <li>회원이 이용계약 해지를 한 경우 본인 계정에 기록된 게시물(ex. 메일 등) 일체는 삭제됩니다. 단, 타인에 의해 보관, 담기 등으로 재게시 되거나 복제된 게시물과 타인의 게시물과 결합되어 제공되는 게시물, 공용 게시판에 등록된 게시물 등은 그러하지 않습니다.</li>
    <li>회사는 서비스의 운영, 전시, 전송배포, 홍보를 위하여 회원의 별도 허락 없이 무상으로 저작권법에 규정하는 공정한 관행에 합치되게 합리적인 범위 내에서 회원이 등록한 게시물을 사이트가 제공하는 일체의 서비스를 이용하는 사용자들간의 게시물을 복제, 전시, 전송, 배포할 수 있습니다. 단, 인용, 홍보 등을 위하여 필요한 경우에는 최소한의 편집, 수정을 할 수 있습니다.</li>
</ol>

<span class="tit">제 15 조 (정보의 제공)</span>
<ol class="doc_list">
    <li>회사는 회원에게 서비스 이용에 필요가 있다고 인정되는 각종 정보에 대해서 전자우편이나 서신, 우편, SMS, 전화 등의 방법으로 회원에게 제공할 수 있습니다.</li>
    <li>회사는 서비스 개선 및 회원 대상의 서비스 소개 등의 목적으로 회원의 동의 하에 관련 법령에 따라 추가적인 개인 정보를 수집할 수 있습니다.</li>
</ol>

<span class="tit">제 16 조 (광고게재 및 광고주와의 거래)</span>
<ol class="doc_list">
    <li>회사가 회원에게 서비스를 제공할 수 있는 서비스 투자기반의 일부는 광고게재를 통한 수익으로부터 나옵니다. 회원은 서비스 이용 시 노출되는 광고게재에 대해 동의합니다.</li>
    <li>회사는 서비스상에 게재되어 있거나 서비스를 통한 광고주의 판촉활동에 회원이 참여하거나 교신 또는 거래를 함으로써 발생하는 손실과 손해에 대해 책임을 지지 않습니다.</li> 
</ol>

<h5>제 5 장 계약 해지 및 이용 제한</h5>

<span class="tit">제 17 조 (계약 변경 및 해지)</span>
<ol class="doc_list">
    <li>회원이 이용계약을 해지하고자 하는 때에는 회원 본인이 서비스 내의 [회원관리] 메뉴를 이용해 회원탈퇴를 해야 합니다.</li>
    <li>회사는 이용계약을 해지하는 경우 사이트 개인정보보호정책에 따라 회원 등록을 말소합니다. 이 경우 회원에게 이를 통지하며, 회사가 직권으로 이용계약을 해지하고자 하는 경우에는 말소 전에 회원에게 소명의 기회를 부여합니다.</li> 
</ol>
            
<span class="tit">제 18 조 (서비스 이용제한)</span>

회사는 회원이 서비스 이용내용에 있어서 본 약관 제 11조 내용을 위반하거나, 다음 각 호에 해당하는 경우 서비스 이용 제한, 초기화, 이용계약 해지 및 기타 해당 조치를 할 수 있습니다.

<ol class="doc_sub_list">
    <li>회원정보에 부정한 내용을 등록하거나 타인의 이용자ID, 비밀번호 기타 개인정보를 도용하는 행위 또는 이용자ID를 타인과 거래하거나 제공하는 행위</li>
    <li>공공질서 및 미풍양속에 위반되는 저속, 음란한 내용 또는 타인의 명예나 프라이버시를 침해할 수 있는 내용의 정보, 문장, 도형, 음향, 동영상을 전송, 게시, 전자우편 또는 기타의 방법으로 타인에게 유포하는 행위</li>
    <li>다른 이용자를 희롱 또는 위협하거나, 특정 이용자에게 지속적으로 고통 또는 불편을 주는 행위</li>
    <li>회사로부터 특별한 권리를 부여 받지 않고 회사의 클라이언트 프로그램을 변경하거나, 회사의 서버를 해킹하거나, 웹사이트 또는 게시된 정보의 일부분 또는 전체를 임의로 변경하는 행위</li>
    <li>서비스를 통해 얻은 정보를 회사의 사전 승낙 없이 서비스 이용 외의 목적으로 복제하거나, 이를 출판 및 방송 등에 사용하거나, 제 3자에게 제공하는 행위</li>
    <li>회사의 운영진, 직원 또는 관계자를 사칭하거나 고의로 서비스를 방해하는 등 정상적인 서비스 운영에 방해가 될 경우</li>
    <li>정보통신 윤리위원회 등 관련 공공기관의 시정 요구가 있는 경우</li>
    <li>3개월 이상 서비스를 이용한 적이 없는 경우</li>
    <li>약관을 포함하여 회사가 정한 제반 규정을 위반하거나 범죄와 결부된다고 객관적으로 판단되는 등 제반 법령을 위반하는 행위</li>
</ol>
<h5>제 6 장 손해배상 및 기타사항</h5>
<span class="tit">제 19 조 (손해배상)</span>
<ol class="doc_list">
    <li>회사와 이용자는 서비스 이용과 관련하여 고의 또는 과실로 상대방에게 손해를 끼친 경우에는 이를 배상하여야 한다. 단,</li>
    <li>회사는 무료로 제공하는 서비스의 이용과 관련하여 개인정보보호정책에서 정하는 내용에 위반하지 않는 한 어떠한 손해도 책임을 지지 않습니다.</li>
    <li>"사이트"는 서비스가 무료로 운영되는 동안에는 서비스 이용과 관련하여 "사이트"의 고의, 과실에 의한 것이 아닌 한 회원에게 발생한 어떠한 손해에 관해서도 책임을 지지 않습니다.</li>
</ol>

<span class="tit">제 20 조 (면책조항)</span>
<ol class="doc_list">
    <li>회사는 천재지변, 전쟁, 기간통신사업자의 서비스 중지 및 기타 이에 준하는 불가항력으로 인하여 서비스를 제공할 수 없는 경우에는 서비스 제공에 대한 책임이 면제됩니다.</li>
    <li>회사는 서비스용 설비의 보수, 교체, 정기점검, 공사 등 부득이한 사유로 발생한 손해에 대한 책임이 면제됩니다.</li>
    <li>회사는 회원의 컴퓨터 오류에 의해 손해가 발생한 경우, 또는 회원이 신상정보 및 전자우편 주소를 부실하게 기재하여 손해가 발생한 경우 책임을 지지 않습니다.</li>
    <li>회사는 회원이 서비스를 이용하여 기대하는 수익을 얻지 못하거나 상실한 것에 대하여 책임을 지지 않으며, 서비스를 이용하면서 얻은 자료로 인한 손해에 대하여 책임을 지지 않습니다.</li>
    <li>회사는 회원이 서비스에 게재한 각종 정보, 자료, 사실의 신뢰도, 정확성 등 내용에 대하여 책임을 지지 않으며, 회원 상호간 및 회원과 제 3자 상호 간에 서비스를 매개로 발생한 분쟁에 대해 개입할 의무가 없고, 이로 인한 손해를 배상할 책임도 없습니다.</li>
    <li>회사는 회원의 게시물을 등록 전에 사전심사 하거나 상시적으로 게시물의 내용을 확인 또는 검토하여야 할 의무가 없으며, 그 결과에 대한 책임을 지지 아니합니다.</li>
</ol>

<span class="tit">제 21 조 (통지)</span>
<ol class="doc_list">
    <li>회사가 회원에 대하여 통지를 하는 경우 회원이 회사에 등록한 전자우편 주소로 할 수 있습니다.</li>
    <li>회사는 불특정다수 회원에게 통지를 해야 할 경우 공지 게시판을 통해 7일 이상 게시함으로써 개별 통지에 갈음할 수 있습니다.</li>
</ol>

<span class="tit">제 22 조 (재판권 및 준거법)</span>
<ol class="doc_list">
    <li>이 약관에 명시되지 않은 사항은 전기통신사업법 등 대한민국의 관계법령과 상관습에 따릅니다.</li>
    <li>회사의 정액 서비스 회원 및 기타 유료 서비스 이용 회원의 경우 당해 서비스와 관련하여서는 회사가 별도로 정한 약관 및 정책에 따릅니다.</li>
    <li>서비스 이용으로 발생한 분쟁에 대해 소송이 제기되는 경우 대한민국 대구지방법원을 관할 법원으로 합니다.</li>
</ol>

<span class="tit">&lt;부칙&gt;</span>
        
본 약관은 2012년 3월 1일부터 적용됩니다.<br />
</div>
            
<div class="agreement_check"><input id="termsNConditions" name="termsNConditions" type="checkbox" value="yes" class="input_check" /> <label for="termsNConditions">위의 <strong>'이용약관'에 동의</strong> 합니다.</label></div>
<!--// 이용약관 -->


<!-- 개인정보 취급방침 -->
<h3 class="agreement_info">개인정보 취급방침</h3>
<div class="agreement_info">
    <p class="doc_comment">
        라온시스(주)('www.smartchurch.com' 이하 'SMART CHURCH')은(는) 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리방침을 두고 있습니다.
    </p>
    <p class="doc_comment">
        라온시스(주) ('SMART CHURCH') 은(는) 회사는 개인정보처리방침을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.                   
    </p>
    <p class="doc_comment_none">
        본 방침은부터 2012년 1월 1일부터 시행됩니다.                    
    </p>
    <ol class="doc_list">
        <li class="topmargin">개인정보의 처리 목적  라온시스(주)('www.smartchurch.com' 이하 'SMART CHURCH')은(는) 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으며 이용 목적이 변경될 시에는 사전동의를 구할 예정입니다.
            <ul class="doc_list_nostyle">
                <li>&lt;홈페이지 회원가입 및 관리&gt;
                    <ul class="doc_sub_list">
                        <li>회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지, 각종 고지·통지 등을 목적으로 개인정보를 처리합니다.</li>
                    </ul>
                </li>
                <li>&lt;민원사무 처리&gt;
                    <ul class="doc_sub_list">
                        <li>민원인의 신원 확인, 민원사항 확인, 사실조사를 위한 연락·통지, 처리결과 통보 등을 목적으로 개인정보를 처리합니다.</li>
                    </ul>
                </li>
                <li>&lt;재화 또는 서비스 제공&gt;
                    <ul class="doc_sub_list">
                        <li>서비스 제공, 콘텐츠 제공, 맞춤 서비스 제공, 본인인증 등을 목적으로 개인정보를 처리합니다.</li>
                    </ul>
                </li>
                <li>&lt;마케팅 및 광고에의 활용&gt;
                    <ul class="doc_sub_list">
                        <li>신규 서비스(제품) 개발 및 맞춤 서비스 제공, 이벤트 및 광고성 정보 제공 및 참여기회 제공 , 인구통계학적 특성에 따른 서비스 제공 및 광고 게재 , 서비스의 유효성 확인, 접속빈도 파악 또는 회원의 서비스 이용에 대한 통계 등을 목적으로 개인정보를 처리합니다.</li>
                    </ul>
                </li>
            </ul>
        </li>       
        <li class="topmargin">개인정보 파일 현황
            <p>('www.smartchurch.com' 이하 'SMART CHURCH')가 개인정보 보호법 제32조에 따라 등록․공개하는 개인정보파일의 처리목적은 다음과 같습니다.</p>
            <ul class="doc_list_nostyle">
                <li>&lt;개인정보 파일명 : 개인정보처리방침&gt;
                    <ul class="doc_sub_list">
                        <li>개인정보 항목 : 비밀번호 질문과 답, 비밀번호, 로그인ID, 휴대전화번호, 이름, 이메일, 접속 IP 정보, 쿠키, 서비스 이용 기록, 접속 로그</li>
                        <li>수집방법 : 홈페이지</li>
                        <li>보유근거 : 신용정보의 이용 및 보호에 관한 법률</li>
                        <li>보유기간 : 3년</li>
                        <li>관련법령 : 소비자의 불만 또는 분쟁처리에 관한 기록 : 3년, 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년, 대금결제 및 재화 등의 공급에 관한 기록 : 5년, 계약 또는 청약철회 등에 관한 기록 : 5년, 표시/광고에 관한 기록 : 6개월</li>
                    </ul>
                </li>
            </ul>
        </li>
        <li class="topmargin">개인정보의 처리 및 보유 기간
          <ol class="doc_list_nostyle2">
                <li>① 라온시스(주) ('SMART CHURCH')은(는) 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집시에 동의 받은 개인정보 보유,이용기간 내에서 개인정보를 처리,보유합니다.</li>
                <li>② 각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.
                    <ul class="doc_list_nostyle">
                        <li>&lt;홈페이지 회원가입 및 관리&gt;
                            <ul class="doc_sub_list">
                            <li>&lt;홈페이지 회원가입 및 관리&gt;와 관련한 개인정보는 수집.이용에 관한 동의일로부터 &lt;3년&gt;까지 위 이용목적을 위하여 보유.이용됩니다.</li>
                            <li>보유근거 : 신용정보의 이용 및 보호에 관한 법률</li>
                            <li>관련법령 : 신용정보의 수집/처리 및 이용 등에 관한 기록 : 3년</li>
                            <li>보유기간 : 3년</li>
                            <li>예외사유 :</li>
                            </ul>
                        </li>
                    </ul>
                </li>
          </ol>
        </li>
        <li class="topmargin">개인정보의 제3자 제공에 관한 사항
          <ol class="doc_list_nostyle2">
                <li>① 라온시스(주) ('www.smartchurch.com' 이하 'SMART CHURCH')은(는) 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</li>
                <li>② 라온시스(주) ('SMART CHURCH')은(는) 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.</li>
          </ol>
        </li>   
        
        <li class="topmargin">개인정보처리 위탁
          <ol class="doc_list_nostyle2">
                <li>① 라온시스(주) ('SMART CHURCH')은(는) 원활한 개인정보 업무처리를 위하여 다음과 같이 개인정보 처리업무를 위탁하고 있습니다.</li>
                <li>② 라온시스(주) ('www.smartchurch.com' 이하 'SMART CHURCH')은(는) 위탁계약 체결시 개인정보 보호법 제25조에 따라 위탁업무 수행목적 외 개인정보 처리금지, 기술적․관리적 보호조치, 재위탁 제한, 수탁자에 대한 관리․감독, 손해배상 등 책임에 관한 사항을 계약서 등 문서에 명시하고, 수탁자가 개인정보를 안전하게 처리하는지를 감독하고 있습니다.</li>
                <li>③ 위탁업무의 내용이나 수탁자가 변경될 경우에는 지체없이 본 개인정보 처리방침을 통하여 공개하도록 하겠습니다.</li>
          </ol>
        </li>

        <li class="topmargin">정보주체의 권리,의무 및 그 행사방법 이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.
          <ol class="doc_list_nostyle2">
                <li>① 정보주체는 라온시스(주) ('www.smartchurch.com' 이하 'SMART CHURCH')에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.
                    <ul class="doc_sub_list">
                        <li>개인정보 열람요구</li>
                        <li>오류 등이 있을 경우 정정 요구</li>
                        <li>삭제요구</li>
                        <li>처리정지 요구</li>
                    </ul>
                </li>
                <li>② 제1항에 따른 권리 행사는 라온시스(주) ('www.smartchurch.com' 이하 'SMART CHURCH') 에 대해 개인정보 보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 &lt;기관/회사명&gt; ('SMART CHURCH') 은(는) 이에 대해 지체 없이 조치하겠습니다.</li>
                <li>③ 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는  ('SMART CHURCH') 은(는) 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.</li>
                <li>④ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.</li>
          </ol>
        </li>

        <li class="topmargin">처리하는 개인정보의 항목 작성
            <p>라온시스(주) ('www.smartchurch.com' 이하 'SMART CHURCH')은(는) 다음의 개인정보 항목을 처리하고 있습니다.</p>
            <ul class="doc_list_nostyle">
                <li>&lt;홈페이지 회원가입 및 관리&gt;
                    <ul class="doc_sub_list">
                    <li>필수항목 : 개인정보의 처리 목적, 개인정보파일 현황, 개인정보의 처리 및 보유 기간, 개인정보의 제3자 제공에 관한 사항, 개인정보처리의 위탁에 관한 사항, 정보주체의 권리·의무 및 그 행사방법에 관한 사항, 처리하는 개인정보의 항목, 개인정보의 파기에 관한 사항, 개인정보 보호책임자에 관한 사항, 개인정보 처리방침의 변경에 관한 사항, 개인정보의 안전성 확보조치에 관한 사항</li>
                    <li>선택항목 :</li>
                    </ul>
                </li>
            </ul>
        </li>
        <li class="topmargin">개인정보의 파기
            <p>라온시스(주)  ('SMART CHURCH')은(는) 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다.</p>
            <ul class="doc_list_nostyle">
                <li>&lt;파기절차&gt;
                    <ul class="doc_sub_list">
                    <li>이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.</li>
                    </ul>
                </li>
                <li>&lt;파기기한&gt;
                    <ul class="doc_sub_list">
                    <li>이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.</li>
                    </ul>
                </li>
                <li>&lt;파기방법&gt;
                    <ul class="doc_sub_list">
                    <li>전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용합니다.</li>
                    </ul>
                </li>
            </ul>
        </li>
        <li class="topmargin">개인정보의 안전성 확보 조치
            <p>라온시스(주) ('SMART CHURCH')은(는) 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.</p>
            <ul class="doc_list_nostyle">
                <li>&lt;개인정보 취급 직원의 최소화 및 교육&gt;
                    <ul class="doc_sub_list">
                    <li>개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니다.</li>
                    </ul>
                </li>
                <li>&lt;정기적인 자체 감사 실시&gt;
                    <ul class="doc_sub_list">
                    <li>개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.</li>
                    </ul>
                </li>
                <li>&lt;내부관리계획의 수립 및 시행&gt;
                    <ul class="doc_sub_list">
                    <li>개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</li>
                    </ul>
                </li>
                <li>&lt;개인정보에 대한 접근 제한&gt;
                    <ul class="doc_sub_list">
                    <li>개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</li>
                    </ul>
                </li>
                <li>&lt;접속기록의 보관 및 위변조 방지&gt;
                    <ul class="doc_sub_list">
                    <li>개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</li>
                    </ul>
                </li>
                <li>&lt;문서보안을 위한 잠금장치 사용&gt;
                    <ul class="doc_sub_list">
                    <li>개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 안전한 장소에 보관하고 있습니다.</li>
                    </ul>
                </li>
                <li>&lt;비인가자에 대한 출입 통제&gt;
                    <ul class="doc_sub_list">
                    <li>개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.</li>
                    </ul>
                </li>
            </ul>
        </li>

        <li class="topmargin">개인정보 보호책임자 작성
            <ol class="doc_list_nostyle2">
                <li>① 라온시스(주) ('www.smartedupot.kr' 이하 'Smart Edupot') 은(는) 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.
                    <ul class="doc_list_nostyle">
                        <li>&lt;개인정보 보호책임자&gt;
                            <ul class="doc_sub_list">
                                <li>성명 : 이성원</li>
                                <li>직책 : 이성원</li>
                                <li>직급 : 이사</li>
                                <li>연락처 : 070-7525-5775, laonsys@gmail.com, 0505-720-5775</li>
                            </ul>
                        </li>
                        <li>※ 개인정보 보호 담당부서로 연결됩니다.</li>
                        <li>&lt;개인정보 보호 담당부서&gt;
                            <ul class="doc_sub_list">
                                <li>부서명 :</li>
                                <li>담당자 :</li>
                                <li>연락처 :</li>
                            </ul>
                        </li>
                        <li></li>
                    </ul>
                </li>
                <li>② 정보주체께서는 라온시스(주) ('www.smartedupot.kr' 이하 'Smart Edupot') 의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 라온시스(주) ('www.smartedupot.kr' 이하 'Smart Edupot') 은(는) 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.</li>
            </ol>
        </li>

        <li class="topmargin">개인정보 처리방침 변경
            <p>이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.</p>
        </li>
    </ol>               
</div>

<div class="agreement_check"><input id="privateAgree" name="privateAgree" type="checkbox" value="yes" class="input_check" /> <label for="privateAgree">위의 <strong>'개인정보 처리방침'에 동의</strong> 합니다.</label></div>
<!--// 개인정보 취급방침 -->


<h3 class="joinCheck">회원가입</h3>
<div class="joinCheck">
<fieldset class="ssn">
<legend>회원가입</legend>
<dl class="fields">
<dt>이메일</dt>
<dd><form:input id="email" path="email" maxlength="255" cssClass="input_text input_w150" /> <button type="button" class="btn btn-small">메일인증</button>
</dd>
<dt>이름</dt>
<dd><form:input id="name" path="name" maxlength="12" cssClass="input_text input_w150"/></dd>
<dt>비밀번호</dt>
<dd><form:password id="join-password" path="password" maxlength="12" cssClass="input_text input_w150"/></dd>
<dt>비밀번호 확인</dt>
<dd><input type="password" id="comfirm" name="comfirm" maxlength="12" class="input_text input_w150"/></dd>
<dt>연락처</dt>
<dd>
<s:eval var="mobile" expression="T(com.laonsys.springmvc.extensions.utils.ViewStaticValue).getMobile()" />
<select id="phone0" title="휴대폰" name="phone0" class="input_select input_w65">
<option value="">선택</option>
<c:forEach var="item" items="${mobile}">
<option value="${item}">${item}</option>
</c:forEach>
</select>
<span class="bar">-</span>
<input type="text" id="phone1" name="phone1" maxlength="4" class="input_text input_w55" />
<span class="bar">-</span>
<input type="text" id="phone2" name="phone2" maxlength="4" class="input_text input_w55" />
</dd>
</dl>
<form:hidden id="mobile-phone" path="mobilePhone" />
</fieldset>
<ul class="field_desc">
<li>Smart Church에서는 깨끗하고 안전한 인터넷 서비스 이용을 위해 본인확인을 받고 있습니다.</li>
<li>입력하신 개인정보는 회원님의 동의 없이 제3자에게 제공되지 않으며, 개인정보취급방침에 따라 보호되고 있습니다. </li>
</ul>
</div>

<div class="btn_area">
<button type="submit" class="btn btn-primary btn-large">가입</button> &nbsp;<a href="<c:url value="/"/>" class="btn btn-large">취소</a>
</div>
<input type="hidden" id="confirmEmail" name="confirmEmail"/>
</form:form>

</div>
</div>
</div>
</s:bind>

<div id="dialog-modal" title="가입 확인"></div>
<div id="validation-dialog"><div class="validate-container"><ul></ul></div></div>

<script type="text/javascript">
$(function() {
   
    var mobile = $('#mobile-phone');
    
    $('#create-form').validate({
        submitHandler: function(form) {
        	mobile.val($('#phone0').val() + "-" + $('#phone1').val() + "-" + $('#phone2').val());
            form.submit();
        },    	
        errorLabelContainer: ".validate-container ul",
        wrapper: "li",
        errorPlacement: function(error, element) {
            error.appendTo(".validate-container ul");
        },
        invalidHandler: function() {
            $("#validation-dialog").dialog('open');
        },
        onfocusout: false,
        onkeyup: false,
        onclick: false,
        rules: {
            'termsNConditions': {
                required: true
            },
            'privateAgree': {
            	required: true
            },
            'email': {
            	required: true,
                email: true
            },
            'name': {
                required: true
            },
            'password': {
                required: true,
                passwordRex: true
            },
            'comfirm': {
                required: true,
                equalTo: '#join-password'
            },
            'phone0': {
                required: true
            },
            'phone1': {
                required: true,
                number: true,
                rangelength: [3, 4]
            },
            'phone2': {
                required: true,
                number: true,
                rangelength: [4, 4]
            },
        },
        messages: {
            'termsNConditions': {
                required: "이용약관 동의에 체크해주세요."
            },
            'privateAgree': {
                required: "개인정보 처리방침 동의에 체크해주세요."
            },
            'email': {
                required: "이메일을 입력하세요."
            },
            'name': {
                required: "이름을 입력하세요."
            },
            'password': {
                required: "비밀번호를 입력하세요."
            },
            'comfirm': {
                required: "비밀번호 확인하세요.",
                equalTo: "비밀번호가 일치하지 않습니다."
            },
            'phone0': {
                required: "통신사를 선택해 주세요."
            },
            'phone1': {
                required: "연락처를 입력하세요."
            },
            'phone2': {
                required: "연락처를 입력하세요.",
                rangelength: "4자리 숫자만 입력하세요"
            }
        }
    });
    
    $( "#dialog:ui-dialog" ).dialog( "destroy" );
    
    $("#validation-dialog").dialog({
        autoOpen:false,
        modal:true,
        show: 'fade',
        hide: 'fade',
        resizable: false,
        close: function(event, ui) {
            $('.validate-container ul').children().remove();
        }
    });
});
</script>
</body>
</html>