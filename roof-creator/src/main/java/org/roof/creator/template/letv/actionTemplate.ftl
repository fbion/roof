package ${packagePath}.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import ${packagePath}.entity.${alias?cap_first};
import ${packagePath}.entity.${alias?cap_first}Vo;
import ${packagePath}.service.api.I${alias?cap_first}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

<#assign key = primaryKey[0] />
@Controller
@RequestMapping("letv/gcr/${actionName}Action")
public class ${alias?cap_first}Action {
	private I${alias?cap_first}Service ${alias}Service;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/letv/gcr/${alias}/${alias}_index.jsp";
	}

	@RequestMapping("/list")
	public String list(${alias?cap_first} ${alias}, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = ${alias}Service.page(page, ${alias});
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/letv/gcr/${alias}/${alias}_list.jsp";
	}
	
	
	@RequestMapping("/create_page")
	public String create_page(Model model) {
		${alias?cap_first} ${alias} = new ${alias?cap_first}();
		model.addAttribute("${alias}", ${alias});
		this.loadCommon(model);
		return "/letv/gcr/${alias}/${alias}_create.jsp";
	}
	
	@RequestMapping("/update_page")
	public String update_page(${alias?cap_first} ${alias}, Model model) {
		${alias} = ${alias}Service.load(${alias});
		model.addAttribute("${alias}", ${alias});
		this.loadCommon(model);
		return "/letv/gcr/${alias}/${alias}_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(${alias?cap_first} ${alias}, Model model) {
		${alias} = ${alias}Service.load(${alias});
		model.addAttribute("${alias}", ${alias});
		this.loadCommon(model);
		return "/letv/gcr/${alias}/${alias}_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(${alias?cap_first} ${alias}) {
		if (${alias} != null) {
			${alias}Service.save(${alias});
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(${alias?cap_first} ${alias}) {
		if (${alias} != null) {
			${alias}Service.updateIgnoreNull(${alias});
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Result delete(${alias?cap_first} ${alias}) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		${alias}Service.delete(${alias});
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void set${alias?cap_first}Service(
			@Qualifier("${alias}Service") I${alias?cap_first}Service ${alias}Service) {
		this.${alias}Service = ${alias}Service;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
