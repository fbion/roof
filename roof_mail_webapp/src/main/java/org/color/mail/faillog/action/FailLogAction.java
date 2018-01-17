package org.color.mail.faillog.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.color.mail.faillog.entity.FailLog;
import org.color.mail.faillog.service.api.IFailLogService;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mail/faillogAction")
public class FailLogAction {
	private IFailLogService failLogService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/faillog/failLog/failLog_index.jsp";
	}

	@RequestMapping("/list")
	public String list(FailLog failLog, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = failLogService.page(page, failLog);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/faillog/failLog/failLog_list.jsp";
	}
	
	
	@RequestMapping("/create_page")
	public String create_page(Model model) {
		FailLog failLog = new FailLog();
		model.addAttribute("failLog", failLog);
		this.loadCommon(model);
		return "/faillog/failLog/failLog_create.jsp";
	}
	
	@RequestMapping("/update_page")
	public String update_page(FailLog failLog, Model model) {
		failLog = failLogService.load(failLog);
		model.addAttribute("failLog", failLog);
		this.loadCommon(model);
		return "/faillog/failLog/failLog_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(FailLog failLog, Model model) {
		failLog = failLogService.load(failLog);
		model.addAttribute("failLog", failLog);
		this.loadCommon(model);
		return "/faillog/failLog/failLog_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(FailLog failLog) {
		if (failLog != null) {
			failLogService.save(failLog);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(FailLog failLog) {
		if (failLog != null) {
			failLogService.updateIgnoreNull(failLog);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Result delete(FailLog failLog) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		failLogService.delete(failLog);
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void setFailLogService(
			@Qualifier("failLogService") IFailLogService failLogService) {
		this.failLogService = failLogService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
