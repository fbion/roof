package org.roof.web.role.service.api;

import java.io.Serializable;
import java.util.List;

import org.roof.roof.dataaccess.api.Page;
import org.roof.web.role.entity.BaseRole;
import org.roof.web.role.entity.Role;

public interface IRoleService {

	public abstract void update(Role role, String allSrc, String selSrc);

	public abstract void changeSrc(BaseRole baseRole, String allsrc, String selsrc);

	public Page page(Page page, Role role);

	public Serializable save(Role role);

	public Role load(Long id);

	public void delete(Role role);

	public List<Role> loadAll();

}