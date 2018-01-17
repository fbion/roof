package org.roof.web.dictionary.action;

import java.util.List;

import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.entity.DictionaryVo;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("dictionaryAction")
public class DictionaryAction {

	private IDictionaryService dictionaryService;

	@RequestMapping("/index")
	public String index() {
		return "/roof-web/web/dictionary/dictionary_index.jsp";
	}

	@RequestMapping("/read")
	public @ResponseBody List<DictionaryVo> read(String type) {
		return dictionaryService.read(type);
	}

	@RequestMapping("/readVal")
	public @ResponseBody List<DictionaryVo> readVal(String type, String val) {
		return dictionaryService.readVal(type, val);
	}

	@RequestMapping("/detail")
	public String detail(Long id, Model model) {
		Dictionary dictionary = (Dictionary) dictionaryService.load(new Dictionary(id));
		model.addAttribute("dictionary", dictionary);
		return "/roof-web/web/dictionary/dictionary_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(Long parentId, Dictionary dictionary) {
		if (dictionary != null) {
			dictionaryService.create(parentId, dictionary);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/create_page")
	public String create_page(Long parentId, Model model) {
		if (parentId == null || parentId.longValue() == 0L) {
			parentId = 1L;
		}
		Dictionary dictionary = (Dictionary) dictionaryService.load(new Dictionary(parentId));
		model.addAttribute("dictionary", dictionary);
		return "/roof-web/web/dictionary/dictionary_create_page.jsp";
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(Long id) {
		dictionaryService.delete(id);
		return new Result("删除成功!");
	}

	@RequestMapping("/update")
	public @ResponseBody Result update(Dictionary dictionary) {
		if (dictionary != null) {
			dictionaryService.updateIgnoreNull(dictionary);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}

	@RequestMapping("/query")
	public @ResponseBody List<Dictionary> query(String type, String val, String text) {
		return dictionaryService.query(type, val, text, null);
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

}
