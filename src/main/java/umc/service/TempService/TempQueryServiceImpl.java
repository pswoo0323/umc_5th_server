package umc.service.TempService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.base.Code;
import umc.base.exception.handler.TempHandler;
import umc.service.TempService.TempQueryService;

@Service
@RequiredArgsConstructor//초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해줌.
                       //새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다
public class TempQueryServiceImpl implements TempQueryService {

    @Override
    public void CheckFlag(Integer flag){
        if (flag == 1)
            throw new TempHandler(Code.TEMP_EXCEPTION);
    }
}
