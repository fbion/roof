package org.color.mail.mailuser.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.color.mail.mailuser.entity.MailUser;
import org.color.mail.mailuser.service.api.IMailUserService;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * ${cancel} 取消订阅表达式
 * </p>
 * <p>
 * ${subscribe} 订阅表达式
 * </p>
 * 
 * @author liuxin
 *
 */
@Controller
@RequestMapping("mail/mailuserAction")
public class MailUserAction {
	private IMailUserService mailUserService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model) {
		List<Dictionary> dicList = dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/mail/mailUser/mailUser_index.jsp";
	}

	@RequestMapping("/list")
	public String list(MailUser mailUser, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = mailUserService.page(page, mailUser);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/mail/mailUser/mailUser_list.jsp";
	}

	@RequestMapping("/create_page")
	public String create_page(Model model) {
		MailUser mailUser = new MailUser();
		model.addAttribute("mailUser", mailUser);
		this.loadCommon(model);
		return "/mail/mailUser/mailUser_create.jsp";
	}

	@RequestMapping("/update_page")
	public String update_page(MailUser mailUser, Model model) {
		mailUser = mailUserService.load(mailUser);
		model.addAttribute("mailUser", mailUser);
		this.loadCommon(model);
		return "/mail/mailUser/mailUser_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(MailUser mailUser, Model model) {
		mailUser = mailUserService.load(mailUser);
		model.addAttribute("mailUser", mailUser);
		this.loadCommon(model);
		return "/mail/mailUser/mailUser_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(MailUser mailUser) {
		if (mailUser != null) {
			mailUserService.save(mailUser);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(MailUser mailUser) {
		if (mailUser != null) {
			mailUserService.updateIgnoreNull(mailUser);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(MailUser mailUser) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		mailUserService.delete(mailUser);
		return new Result("删除成功!");
	}

	/**
	 * 退订页面
	 * 
	 * @param mailMD5
	 * @param model
	 * @return
	 */
	@RequestMapping("/cancel_page/{mailMD5}")
	public String cancel_page(@PathVariable("mailMD5") String mailMD5, Model model) {
		model.addAttribute("mailMD5", mailMD5);
		return "/mail/mailUser/mailUser_cancel.jsp";
	}

	/**
	 * 退订
	 * 
	 * @param mailMD5
	 * @return
	 */
	@RequestMapping("/cancel/{mailMD5}")
	public @ResponseBody Result cancel(@PathVariable("mailMD5") String mailMD5) {
		mailUserService.cancel(mailMD5);
		return new Result("退订成功");
	}

	/**
	 * 订阅页面
	 * 
	 * @param mailUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/subscribe_page/{email}")
	public String subscribe_page(@PathVariable String email, Model model) {
		model.addAttribute("email", email);
		return "/mail/mailUser/mailUser_subscribe.jsp";
	}

	/**
	 * 订阅
	 * 
	 * @param email
	 * @return
	 */
	@RequestMapping("/subscribe/{email}")
	public @ResponseBody Result subscribe(@PathVariable String email) {
		return mailUserService.subscribe(email);
	}

	@RequestMapping("/import_page")
	public String import_page() {
		return "/mail/mailUser/mailUser_import.jsp";
	}

	@RequestMapping("/importByXls")
	public @ResponseBody Result importByXls(@RequestParam("file") MultipartFile file) {
		try (InputStream in = file.getInputStream()) {
			mailUserService.importByXls(in);
		} catch (IOException e) {
			return new Result(e);
		}
		return new Result("导入成功");
	}

	@Autowired(required = true)
	public void setMailUserService(@Qualifier("mailUserService") IMailUserService mailUserService) {
		this.mailUserService = mailUserService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
