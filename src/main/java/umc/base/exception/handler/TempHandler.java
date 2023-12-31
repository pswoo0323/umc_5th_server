package umc.base.exception.handler;

import umc.base.Code;
import umc.base.exception.GeneralException;

public class TempHandler extends GeneralException {
    public TempHandler(Code errorCode){
        super(errorCode);
    }
}
