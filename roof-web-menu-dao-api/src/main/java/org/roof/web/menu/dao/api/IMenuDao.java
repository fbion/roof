package org.roof.web.menu.dao.api;

import java.util.List;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuType;

public interface IMenuDao extends IDaoSupport {

	public List<Menu> findMenuByParent(Long parentId, MenuType menuType);

	public Long childrenCount(Long parentId);

	public Menu selectByModule(List<Long> list);

	public Menu load(Long id);

}