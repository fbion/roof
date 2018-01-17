package org.roof.web.menu.service.api;

import java.util.List;

import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuVo;

public interface IMenuService {

	public Menu load(Long id);

	public abstract Menu selectByModule(List<Long> list);

	public abstract List<MenuVo> read(Long parentId);

	/**
	 * 创建一个菜单
	 * 
	 * @return
	 */
	public abstract Menu create(Long parentId, Menu menu);

	/**
	 * 删除一个菜单
	 * 
	 * @param id
	 */
	public abstract void delete(Long id);

	public abstract Long childrenCount(Long parentId);

	void update(Menu menu);

}
