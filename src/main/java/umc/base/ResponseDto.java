package umc.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"issSuccess", "code", "message", "result"})
public class ResponseDto<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public static <T> ResponseDto<T> onSuccess(T data){
        return new ResponseDto<>(true, "요청에 성공하였습니다.", "1000", data);
    }

    public static <T> ResponseDto<T> onFailure(Code code, T data){
        return new ResponseDto<>(false, code.getCode(), code.getMessage(), data);
    }
}
