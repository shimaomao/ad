package com.planx.advertise.page;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.planx.advertise.model.Region;
import com.planx.advertise.service.RegionCategoryService;
import com.planx.advertise.service.RegionService;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.vo.RegionCategoryTreeNode;

@Controller
@RequestMapping(path = "/category")
public class CategoryPage extends BasePage {

	@Autowired
	private RegionService regionService;

	@Autowired
	private RegionCategoryService regionCategoryService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String tree() {
		return "category/category";
	}

	@RequestMapping(value = { "/byname/{uniqueRegion}" }, method = RequestMethod.GET)
	public String treeByRegion(@PathVariable(required = false) String uniqueRegion, Map<String, Object> model) {
		Region region = regionService.findByUniqueName(uniqueRegion);
		if (region == null) {
			throw new ResourceNotFoundException("region not found");
		}
		List<RegionCategoryTreeNode> regionCategoryTreeNodes = regionCategoryService.getTreeByRegionId(region.getId());
		model.put("regionCategoryTreeNodes", regionCategoryTreeNodes);
		model.put("region", region);

		model.put("title", region.getName() + " - " + DEFAULT_TITLE);
		model.put("description", region.getName() + " - " + DEFAULT_DESCRIPTION);
		model.put("keywords", region.getName() + ", " + DEFAULT_KEYWORDS);
		
        String canonical = "https://" + region.getUniqueName() + "." + websiteDomain;
        model.put("canonical", canonical);

		return "category/categoryByRegion";
	}

}
