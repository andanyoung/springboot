package andanyoung.demo.canal;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author andanyang
 * @since 2022/5/20 15:12
 */
public class CanalClient {
    public static void main(String[] args) throws InvalidProtocolBufferException {

        //1.获取 canal 连接对象
        CanalConnector canalConnector =
                CanalConnectors.newSingleConnector(new
                        InetSocketAddress("192.168.1.13", 11111), "example", "", "");

        int batchSize = 100;

        //2.获取连接
        canalConnector.connect();
        //3.指定要监控的数据库
        canalConnector.subscribe("ces_bi.*");

        while (true) {

            //4.获取 Message
            Message message = canalConnector.getWithoutAck(batchSize);
            long batchId = message.getId();
            List<CanalEntry.Entry> entries = message.getEntries();
            if (batchId == -1 || entries.size() <= 0) {
                System.out.println("没有数据，休息一会");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                printEntry(message.getEntries());

                canalConnector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        }
    }

    /**
     * 打印数据
     *
     * @param entries
     */
    private static void printEntry(List<CanalEntry.Entry> entries) throws InvalidProtocolBufferException {

        for (CanalEntry.Entry entry : entries) {
            // 获取表名
            String tableName = entry.getHeader().getTableName();
            //  Entry 类型
            CanalEntry.EntryType entryType = entry.getEntryType();
            //  判断 entryType 是否为 ROWDATA
            if (CanalEntry.EntryType.ROWDATA.equals(entryType)) {
                //  序列化数据
                ByteString storeValue = entry.getStoreValue();
                //  反序列化
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(storeValue);
                // 获取事件类型
                CanalEntry.EventType eventType = rowChange.getEventType();
                // 获取具体的数据
                List<CanalEntry.RowData> rowDatasList =
                        rowChange.getRowDatasList();
                // 遍历并打印数据
                for (CanalEntry.RowData rowData : rowDatasList) {
                    List<CanalEntry.Column> beforeColumnsList =
                            rowData.getBeforeColumnsList();
                    JSONObject beforeData = new JSONObject();
                    for (CanalEntry.Column column :
                            beforeColumnsList) {
                        beforeData.put(column.getName(), column.getValue());
                    }

                    JSONObject afterData = new JSONObject();
                    List<CanalEntry.Column> afterColumnsList =
                            rowData.getAfterColumnsList();
                    for (CanalEntry.Column column :
                            afterColumnsList) {
                        afterData.put(column.getName(),
                                column.getValue());
                    }
                    System.out.println("TableName:" + tableName
                            +
                            ",EventType:" + eventType +
                            ",After:" + beforeData +
                            ",After:" + afterData);
                }
            }
        }
    }
}
