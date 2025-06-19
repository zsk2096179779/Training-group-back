package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.entity.FactorTreeNode;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.service.FactorTreeService;
import com.example.advisor_backend.model.dto.MoveNodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tree")
@CrossOrigin
public class FactorTreeController {

    @Autowired
    private FactorTreeService factorTreeService;

    @GetMapping
    public ApiResponse<List<FactorTreeNode>> getAll() {
        return ApiResponse.ok(factorTreeService.getAllNodes());
    }

    @GetMapping("/children/{parentId}")
    public ApiResponse<List<FactorTreeNode>> getChildren(@PathVariable Long parentId) {
        return ApiResponse.ok(factorTreeService.getChildren(parentId));
    }

    @PostMapping
    public ApiResponse<String> createNode(@RequestBody FactorTreeNode node) {
        return factorTreeService.addNode(node) ? ApiResponse.ok("添加成功") : ApiResponse.error(500, "添加失败");
    }

    @PutMapping
    public ApiResponse<String> updateNode(@RequestBody FactorTreeNode node) {
        return factorTreeService.updateNode(node) ? ApiResponse.ok("更新成功") : ApiResponse.error(500, "更新失败");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteNode(@PathVariable Long id) {
        return factorTreeService.deleteNode(id) ? ApiResponse.ok("删除成功") : ApiResponse.error(500, "删除失败");
    }

    @PostMapping("/move")
    public ApiResponse<String> moveNode(@RequestBody MoveNodeRequest request) {
        factorTreeService.moveNode(request.getId(), request.getNewParentId(), request.getNewSortOrder());
        return ApiResponse.ok("移动成功");
    }
}
