import com.os.framework.vo.transceriver.RtuEquipment;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: framework-base
 * @description:
 * @author: wangbo
 * @create: 2019-02-27 13:03
 **/
public class Test {
    public static void main(String[] args) throws Exception{
        List<RtuEquipment> list = new ArrayList();
        for(int x = 0;x < 10;x ++){
            RtuEquipment equipment = new RtuEquipment();
            equipment.setRtuid("" + x);
            list.add(equipment);
        }
        MessagePack msgPack = new MessagePack();
        byte[] data =  msgPack.write(list);
        System.out.println(data.length);
        {
            List l = msgPack.read(data, Templates.tList(msgPack.lookup(RtuEquipment.class)));
            System.out.println(l);
        }
    }
}
