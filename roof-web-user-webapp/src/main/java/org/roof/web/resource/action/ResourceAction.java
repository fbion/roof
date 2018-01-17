package org.roof.web.resource.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.roof.spring.Result;
import org.roof.web.resource.entity.Privilege;
import org.roof.web.resource.entity.Resource;
import org.roof.web.resource.entity.ResourceVo;
import org.roof.web.resource.service.api.IResourceService;
import org.roof.web.resource.service.api.IResourcesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("resourceAction")
public class ResourceAction {
	private IResourceService resourceService;
	private IResourcesUtils resourcesUtils;

	/**
	 * 资源管理首页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/roof-web/web/resources/resource_index.jsp";
	}

	/**
	 * 根据父节点ID加载资源列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Long parentId, Model model) {
		Resource resource = resourceService.load(parentId == null ? 1L : parentId);
		List<Resource> resources = resourceService.findModuleByParent(parentId);
		model.addAttribute("resources", resources);
		model.addAttribute("resource", resource);
		return "/roof-web/web/resources/resource_list.jsp";
	}

	/**
	 * 资源详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(Long id, Model model) {
		Privilege resource = resourceService.load(id);
		if (resource.getParent() != null && resource.getParent().getId() != null) {
			resource.setParent(resourceService.load(resource.getParent().getId()));
		}
		model.addAttribute("resource", resource);
		if ("Privilege".equalsIgnoreCase(resource.getDtype())) {
			return "/roof-web/web/resources/resource_resource_detail.jsp";
		}
		if ("Module".equalsIgnoreCase(resource.getDtype())) {
			return "/roof-web/web/resources/resource_module_detail.jsp";
		}
		if ("QueryResource".equalsIgnoreCase(resource.getDtype())) {
			return "/roof-web/web/resources/resource_query_detail.jsp";
		}
		if ("QueryFilterResource".equalsIgnoreCase(resource.getDtype())) {
			return "/roof-web/web/resources/resource_queryfilter_detail.jsp";
		}
		return "/roof-web/web/resources/resource_resource_detail.jsp";
	}

	/**
	 * 创建一个新的资源
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	public @ResponseBody Result create(Long parentId, String type) {
		resourceService.create(parentId, type);
		return new Result(Result.SUCCESS, "新增成功!");
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(Long id) {
		resourceService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping("/updateModule")
	public @ResponseBody Result updateModule(Integer initBasic, Privilege module) {
		if (module != null && module.getId() != null) {
			resourceService.updateIgnoreNull(module);
			module = (Privilege) resourceService.load(module.getId());
			if (module.getLeaf() != null && initBasic != null && module.getLeaf() && initBasic == 1) {
				resourcesUtils.initBasicOperation(module);
				module.setLeaf(false);
				resourceService.updateIgnoreNull(module);
			}
		}
		return new Result("保存成功!");
	}

	@RequestMapping("/updatePrivilege")
	public @ResponseBody Result updatePrivilege(Privilege privilege) {
		if (privilege != null) {
			resourceService.updateIgnoreNull(privilege);
		}
		return new Result("保存成功!");
	}

	/**
	 * 根据父节点ID加载资源列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/read")
	public @ResponseBody List<ResourceVo> read(Long id, Long roleId, HttpServletRequest request, Model model) {
		String basePath = request.getContextPath();
		if (roleId == null || roleId.longValue() == 0) {
			return resourceService.read(id, basePath);
		} else {
			return resourceService.readByRole(id, roleId, basePath);
		}
	}

	public IResourceService getResourceService() {
		return resourceService;
	}

	public IResourcesUtils getResourcesUtils() {
		return resourcesUtils;
	}

	@Autowired(required = true)
	public void setResourceService(@Qualifier("resourceService") IResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Autowired(required = true)
	public void setResourcesUtils(@Qualifier("resourcesUtils") IResourcesUtils resourcesUtils) {
		this.resourcesUtils = resourcesUtils;
	}

}
