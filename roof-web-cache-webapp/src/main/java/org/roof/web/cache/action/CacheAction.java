package org.roof.web.cache.action;

import org.roof.spring.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author liuxin
 *
 */
@Controller
@RequestMapping("cacheAction")
public class CacheAction {

	private CacheManager cacheManager;

	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("cacheNames", cacheManager.getCacheNames());
		return "/roof-web/web/cache/cache_list.jsp";
	}

	@RequestMapping("/delete")
	public @ResponseBody Result delete(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.clear();
		return new Result("清除成功！");
	}

	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
