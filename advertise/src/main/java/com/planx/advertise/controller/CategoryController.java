package com.planx.advertise.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.planx.advertise.dto.CategoryDTO;
import com.planx.advertise.model.Category;
import com.planx.advertise.response.RestStatus;
import com.planx.advertise.response.RestResponseBody;
import com.planx.advertise.service.CategoryService;
import com.planx.advertise.system.exception.ResourceNotFoundException;
import com.planx.advertise.vo.CategoryTreeNode;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> create(@Valid CategoryDTO categoryDTO) {
		Category result = categoryService.create(categoryDTO);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> delete(@PathVariable String id) {
		categoryService.softDelete(id);
		return new RestResponseBody<>(RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> update(@PathVariable String id, @Valid CategoryDTO categoryDTO) {
		Category result = categoryService.update(id, categoryDTO);
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> findById(@PathVariable String id) {
		Category result = categoryService.findById(id);
		if (null == result) {
			throw new ResourceNotFoundException("category not found");
		}
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@PreAuthorize(value = "hasRole('admin')")
	public RestResponseBody<?> tree() {
		List<CategoryTreeNode> result = categoryService.getTree();
		return new RestResponseBody<>(result, RestStatus.SUCCESS);
	}

}
