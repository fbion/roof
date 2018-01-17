package org.color.mail.mail.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.color.mail.mail.entity.Mail;
import org.color.mail.mail.service.api.IMailService;
import org.roof.commons.RoofDateUtils;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("mail/mailAction")
public class MailAction {
	private IMailService mailService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model) {
		List<Dictionary> dicList = dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/mail/mail/mail_index.jsp";
	}

	@RequestMapping("/list")
	public String list(Mail mail, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = mailService.page(page, mail);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/mail/mail/mail_list.jsp";
	}

	@RequestMapping("/create_page")
	public String create_page(Model model) {
		Mail mail = new Mail();
		model.addAttribute("mail", mail);
		this.loadCommon(model);
		return "/mail/mail/mail_create.jsp";
	}

	@RequestMapping("/update_page")
	public String update_page(Mail mail, Model model) {
		mail = mailService.load(mail);
		model.addAttribute("mail", mail);
		this.loadCommon(model);
		return "/mail/mail/mail_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(Mail mail, Model model) {
		mail = mailService.load(mail);
		model.addAttribute("mail", mail);
		this.loadCommon(model);
		return "/mail/mail/mail_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Mail mail) {
		if (mail != null) {
			mailService.save(mail);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(Mail mail) {
		if (mail != null) {
			mailService.updateIgnoreNull(mail);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(Mail mail) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		mailService.delete(mail);
		return new Result("删除成功!");
	}

	@RequestMapping("/batch_send")
	public @ResponseBody Result batch_send(Mail mail) {
		mailService.sendBatchTask(mail.getId(), new Date());
		return new Result("任务启动成功");
	}

	@RequestMapping("/batch_send_page")
	public String batch_send_page(Mail mail, Model model) {
		mail = mailService.load(mail);
		model.addAttribute("mail", mail);
		this.loadCommon(model);
		return "/mail/mail/batch_send.jsp";
	}

	@RequestMapping("/schedule_send")
	public @ResponseBody Result schedule_send(Mail mail, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date date) {
		if (date == null) {
			date = new Date();
		}
		mailService.sendBatchSchedule(mail.getId(), date);
		return new Result("任务启动成功");
	}

	@RequestMapping("/schedule_send_page")
	public String schedule_send_page(Mail mail, Model model) {
		mail = mailService.load(mail);
		model.addAttribute("mail", mail);
		this.loadCommon(model);
		return "/mail/mail/schedule_send.jsp";
	}

	@RequestMapping("/test_send")
	public @ResponseBody Result test_send(Mail mail, String to, Model model) {
		return mailService.testSend(to, mail.getId());
	}

	@RequestMapping("/test_send_page")
	public String test_send_page(Mail mail, Model model) {
		mail = mailService.load(mail);
		model.addAttribute("mail", mail);
		this.loadCommon(model);
		return "/mail/mail/test_send.jsp";
	}

	@Autowired(required = true)
	public void setMailService(@Qualifier("mailService") IMailService mailService) {
		this.mailService = mailService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}
