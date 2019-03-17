package com.planx.advertise.page;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.planx.advertise.service.RegionService;
import com.planx.advertise.vo.RegionTreeNode;

@Controller
@RequestMapping(path = "/")
public class IndexPage extends BasePage {

	@Autowired
	private RegionService regionService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String tree(Map<String, Object> model) {
		List<RegionTreeNode> regionTreeNodes = regionService.getTree();
		model.put("regionTreeNodes", regionTreeNodes);
		return "region/region";
	}
	
}
