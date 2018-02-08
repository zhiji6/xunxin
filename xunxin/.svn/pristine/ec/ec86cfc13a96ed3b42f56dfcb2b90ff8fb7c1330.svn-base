package xunxin.core;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xunxin.service.MenuService;
import com.xunxin.vo.admin.Menu;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDAOImplTest {
	
	@Autowired
	private Menu menu;
	
	@Autowired
	private MenuService menuService;
	
	@Test
	public void getAllMenu() {
		List<Menu> all = menuService.getAll();
		for(Menu m : all) {
			System.out.println(m.toString());
		}
	}
	
	
}
