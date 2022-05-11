package an.common.http.ext.dingtalk;

import an.common.http.HttpRequest;
import an.common.http.MediaTypeEnum;
import an.common.http.exception.HttpException;
import an.common.http.ext.dingtalk.core.RobotResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * https://open.dingtalk.com/document/group/custom-robot-access
 *
 * @author andanyang
 * @since 2022/5/11 11:43
 */
public class DingRobot extends HttpRequest {

    private String accessToken;
    //private String accessToken;

    public DingRobot(String accessToken) {
        super();
        this.accessToken = accessToken;
    }

    public DingRobot() {
        super();
    }


    @Override
    public MediaTypeEnum getMediaTypeEnum() {
        return MediaTypeEnum.JSON;
    }

    @Override
    public void init() {
        super.init();
        basePath = "https://oapi.dingtalk.com/robot/send?access_token=";
    }


    public void send(AbstractRobotRequest request) {

        //response {"errcode":0,"errmsg":"ok"}
        RobotResponse response = post(StringUtils.defaultIfBlank(request.getAccessToken(), this.accessToken), request, RobotResponse.class);
        if (response == null || response.getErrcode() != 0) {
            throw new HttpException(response.getErrmsg(), response.getErrcode());
        }
    }
}
