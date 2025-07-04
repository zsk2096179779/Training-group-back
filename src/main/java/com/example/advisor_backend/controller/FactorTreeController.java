package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.entity.FactorTreeNode;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.model.entity.UpdateOrderRequest;
import com.example.advisor_backend.service.FactorTreeService;
import com.example.advisor_backend.model.dto.MoveNodeRequest;
import com.example.advisor_backend.service.ExcelExportService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.advisor_backend.model.dto.TreeWithNodesDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/factor-tree")
@CrossOrigin
public class FactorTreeController {
    @Autowired
    private FactorTreeService factorTreeService;

    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/export")
    public void exportTree(@RequestParam("treeType") String treeType, HttpServletResponse response) {
        try {
            List<FactorTreeNode> tree = factorTreeService.getTreeByType(treeType);
            excelExportService.exportFactorTree(response, tree, treeType);
        } catch (Exception e) {
            // Handle exceptions, e.g., log the error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // Optionally write an error message to the response
        }
    }
    @PostMapping("/updateOrder")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateOrder(@RequestBody UpdateOrderRequest req) {
        factorTreeService.updateOrder(req.getId(), req.getNewParentId(), req.getNewSortOrder(), req.getNewTreeName());
        return ApiResponse.ok();
    }

    @GetMapping("/list")
    public ApiResponse<List<FactorTreeNode>> getTree(@RequestParam String treeType) {
        List<FactorTreeNode> tree = factorTreeService.getTreeByType(treeType);
        return ApiResponse.ok(tree);
    }
    @GetMapping
    public ApiResponse<List<FactorTreeNode>> getAll() {
        return ApiResponse.ok(factorTreeService.getAllNodes());
    }

    @GetMapping("/children/{parentId}")
    public ApiResponse<List<FactorTreeNode>> getChildren(@PathVariable Long parentId) {
        return ApiResponse.ok(factorTreeService.getChildren(parentId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> createNode(@RequestBody FactorTreeNode node) {
        return factorTreeService.addNode(node) ? ApiResponse.ok("添加成功") : ApiResponse.error(500, "添加失败");
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> updateNode(@RequestBody FactorTreeNode node) {
        return factorTreeService.updateNode(node) ? ApiResponse.ok("更新成功") : ApiResponse.error(500, "更新失败");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteNode(@PathVariable Long id) {
        return factorTreeService.deleteNode(id) ? ApiResponse.ok("删除成功") : ApiResponse.error(500, "删除失败");
    }

    @PostMapping("/move")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> moveNode(@RequestBody MoveNodeRequest request) {
        factorTreeService.moveNode(request.getId(), request.getNewParentId(), request.getNewSortOrder());
        return ApiResponse.ok("移动成功");
    }

    @PostMapping("/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> importTree(@RequestParam("file") MultipartFile file) {
        try {
            factorTreeService.importFromExcel(file);
            return ApiResponse.ok("导入成功");
        } catch (Exception e) {
            return ApiResponse.error(500, "导入失败: " + e.getMessage());
        }
    }

    @PostMapping("/rename")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> renameTreeName(@RequestParam String treeType,
                                              @RequestParam String treeName,
                                              @RequestParam String newTreeName) {
        factorTreeService.renameTreeName(treeType, treeName, newTreeName);
        return ApiResponse.ok("重命名成功");
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteTree(@RequestParam String treeType,
                                          @RequestParam String treeName) {
        factorTreeService.deleteTree(treeType, treeName);
        return ApiResponse.ok("删除成功");
    }

    @GetMapping("/listByType")
    public ApiResponse<Map<String, List<FactorTreeNode>>> listByType(@RequestParam String treeType) {
        List<FactorTreeNode> allNodes = factorTreeService.getAllNodesByType(treeType);
        Map<String, List<FactorTreeNode>> grouped = allNodes.stream()
            .collect(java.util.stream.Collectors.groupingBy(FactorTreeNode::getTreeName));
        return ApiResponse.ok(grouped);
    }

    @GetMapping("/listWithTreeName")
    public ApiResponse<List<TreeWithNodesDto>> listWithTreeName(@RequestParam(required = false) String treeType) {
        if (treeType == null || treeType.trim().isEmpty()) {
            return ApiResponse.ok(java.util.Collections.emptyList());
        }
        List<FactorTreeNode> allNodes = factorTreeService.getAllNodesByType(treeType);
        Map<String, List<FactorTreeNode>> grouped = allNodes.stream()
            .collect(java.util.stream.Collectors.groupingBy(FactorTreeNode::getTreeName));
        List<TreeWithNodesDto> result = grouped.entrySet().stream()
            .map(e -> {
                TreeWithNodesDto dto = new TreeWithNodesDto();
                dto.setTreeName(e.getKey());
                dto.setNodes(e.getValue());
                return dto;
            })
            .collect(java.util.stream.Collectors.toList());
        return ApiResponse.ok(result);
    }
}
