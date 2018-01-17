package org.roof.web.menu.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.roof.dataaccess.api.AbstractDao;
import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.web.menu.dao.api.IMenuDao;
import org.roof.web.menu.entity.Menu;
import org.roof.web.menu.entity.MenuType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MenuDao extends AbstractDao implements IMenuDao {

	public Menu load(Long id) {
		return (Menu) daoSupport.reload(new Menu(id));
	}

	@Override
	public List<Menu> findMenuByParent(Long parentId, MenuType menuType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentId", parentId);
		param.put("menuType", menuType);
		if (parentId == null) {
			param.put("lvl", 0);
		}
		@SuppressWarnings("unchecked")
		List<Menu> menus = (List<Menu>) daoSupport.selectForList("org.roof.web.menu.dao.findMenuByParent", param);
		return menus;
	}

	@Override
	public Long childrenCount(Long parentId) {
		Long result = (Long) daoSupport.selectForObject("org.roof.web.menu.dao.childrenCount", parentId);
		return result;
	}

	@Override
	public Menu selectByModule(List<Long> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modules", list);
		Menu m = new Menu();
		@SuppressWarnings("unchecked")
		List<Menu> menus = (List<Menu>) daoSupport.findByMappedQuery("org.roof.web.menu.dao.selectByModule", map);
		if (menus.size() > 0) {
			m = menus.get(0);
		}
		return m;
	}

	@Autowired
	public void setDaoSupport(@Qualifier("roofDaoSupport") IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

}
