package texst;

import com.alibaba.fastjson.JSON;
import com.zengqiang.future.common.Const;
import com.zengqiang.future.form.ItemForm;
import com.zengqiang.future.form.PostItemForm;
import com.zengqiang.future.pojo.Item;
import com.zengqiang.future.service.IPostService;
import com.zengqiang.future.service.impl.PostServiceImpl;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        createPostItemForm();
    }

    public static void com(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,String> consumer=new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("my-topic"));

            ConsumerRecords<String, String> records = consumer.poll(100);
        System.out.println("########"+records.isEmpty());
            for (ConsumerRecord<String, String> record : records){
                System.out.println("********");
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

            }

    }

    private static void createPostItemForm(){
        PostItemForm form=new PostItemForm();
        form.setContent("测试");
        form.setIsEnabledComment(0);
        form.setType((int)Const.Post.ITEM);
        form.setUserId(1);
        form.setId(1);
        List<ItemForm> items=new ArrayList<>();
        for(int i=0;i<2;i++){
            ItemForm itemForm=new ItemForm();
            itemForm.setItemDescribe("ceshi");
            itemForm.setNumber(3);
            itemForm.setPrice(new BigDecimal("3.53"));
            itemForm.setTitle("测试");
            itemForm.setId(1);
            items.add(itemForm);
        }
        form.setItems(items);
        System.out.println(JSON.toJSONString(form));
    }
}
