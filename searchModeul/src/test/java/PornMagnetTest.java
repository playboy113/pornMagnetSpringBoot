import com.zhang.entity.magnet_model;
import com.zhang.mapper.magnet_modelMapper;
import com.zhang.service.porndoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PornMagnetTest {
    @Autowired
    private magnet_modelMapper  magnet_modelMapper;
    @Test
    public List<magnet_model> selectAll() {
        List<magnet_model> magnetLists = magnet_modelMapper.selectAll();

        return magnetLists;
    }




}
