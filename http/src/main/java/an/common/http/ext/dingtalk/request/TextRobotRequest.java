package an.common.http.ext.dingtalk.request;

import an.common.http.ext.dingtalk.AbstractRobotRequest;
import an.common.http.ext.dingtalk.core.MsgType;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author andanyang
 * @since 2022/5/11 11:56
 */
@Data
public class TextRobotRequest extends AbstractRobotRequest {
    private Map<String, String> text;

    @Override
    public MsgType getMsgType() {
        return MsgType.TEXT;
    }

    public void setContent(String content) {
        if (text == null) {
            text = new HashMap<>(2);
        }
        text.put("content", content);
    }
}
