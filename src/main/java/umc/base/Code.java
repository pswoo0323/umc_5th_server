package umc.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.base.exception.GeneralException;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum Code {

    // 성공
    OK(HttpStatus.OK, "2000", "OK"),

    // Common Error
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON001","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON002","권한이 잘못되었습니다"),
    _METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON004", "금지된 요청입니다."),

    // Member Error
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4002", "닉네임은 필수 입니다."),

    // Article Error
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 없습니다."),

    // FoodCategory Error
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "FOODCATEGORY4001", "음식 카테고리가 없습니다"),

    // Review Error
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW4001", "리뷰가 없습니다."),

    // Store Error
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "STORE4001", "가게가 없습니다"),

    // Region Error
    REGION_NOT_FOUND(HttpStatus.NOT_FOUND, "REGION4001", "지역이 없습니다"),

    // Mission Error
    MISSION_DEADLINE_FORMAT_NOT_MATCH(HttpStatus.BAD_REQUEST, "MISSION4001", "미션 마감일 형식이 일치하지 않습니다"),
    MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION4002", "미션이 없습니다"),

    // MemberMission Error
    MEMBER_MISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "MISSION4002", "해당하는 사용자가 진행중인 없습니다"),

    // For test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "이거는 테스트");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessage(Throwable e){
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank)) // filter()로 넘오온 함수형 인자의 리턴값이 false인 경우 빈 Optional 객체를 반환함.
                .orElse(this.getMessage());
    }

    public static Code valueOf(HttpStatus httpStatus){
        if(httpStatus == null){
            throw new GeneralException("HttpStatus is null.");
        }

        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatus() == httpStatus)
                .findFirst()
                .orElseGet(() -> {
                    if (httpStatus.is4xxClientError()) {
                        return Code._BAD_REQUEST;
                    } else if (httpStatus.is5xxServerError()) {
                        return Code._INTERNAL_SERVER_ERROR;
                    } else {
                        return Code.OK;
                    }
                });
    }
}
