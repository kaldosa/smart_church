package com.laonsys.smartchurch.domain;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = StatusCodeSerializer.class)
public enum StatusCode {
    OK(0, "OK"),
    
    // 입력 데이터 바인딩 에러 1XX
    DATA_BINDING_ERROR(100, "입력 정보가 잘못되었습니다."),
    NOT_ALLOWED_MEDIATYPE_FILESIZE(101, "허용되지 않는 미디어타입 또는 파일크기입니다."),
    
    // 사용자 관련 상태 코드 2XX
    JOIN_INVALID_USER(200, "이미 가입승인이 되었거나 입력하신 정보와 일치하는 사용자가 없습니다."),
    JOIN_EXPIRED_MAIL(201, "인증 메일의 유효기간이 만료되었습니다."),
    USER_NOT_FOUND(202, "입력하신 정보와 일치하는 사용자가 없습니다. \n정확한 정보로 확인 후 다시 입력 부탁드립니다."),
    USER_PERMISSION_DENIED(203, "권한이 없습니다."),
    JOIN_INVALID_CODE(204, "인증코드가 유효하지 않습니다."),
    JOIN_ALREADY_USED(205, "이미 사용중인 이메일입니다."),
    JOIN_FAILED(206, "회원 가입에 실패하였습니다."),
    NO_CONTENTS(300, "컨텐츠가 없습니다."),
    FAILED_CREATE_CONTENTS(301, "컨덴츠를 저장중 오류가 발생하였습니다."),
    FAILED_DELETE_CONTENTS(302, "컨덴츠 삭제중 오류가 발생하였습니다."),
    INVALID_SERVICE_ID(400, "서비스 ID가 유효하지 않습니다."),
    NOT_MEMBER(401, "교회 멤버가 아닙니다.");
    
    private final int value;

    private final String description;
    
    private String another;
    
    private StatusCode(int value, String description) {
        this.value = value;
        this.description = description;
    }
    
    /**
     * 상태코드값 반환
     */
    public int value() {
        return this.value;
    }

    /**
     * 상태코드에 대한 설명을 반환한다.
     * 만약 setAnother를 통해 사용자가 설정한 내용이 없을 경우, 기본 값을 반환한다.
     */
    public String getDescription() {
        String returnValue = null;
        
        if(another != null) {
            returnValue = another;
            another = null;
        } else {
            returnValue = description;
        }
        return returnValue;
    }

    /**
     * 상태코드에 대한 설명을 변경
     * 
     * 사용자가 설정한 내용은 1회성이다.
     * @param another 사용자 지정 설명
     * @return this
     */
    public StatusCode setAnother(String another) {
        this.another = another;
        return this;
    }
    
    /**
     * 코드값과 코드값에 해당하는 enum 이름을 반환
     * 
     * "100 DATA_BINDING_ERROR" 형식의 문자열
     */
    @Override
    public String toString() {
        return Integer.toString(value) + " " + this.name();
    }
}
