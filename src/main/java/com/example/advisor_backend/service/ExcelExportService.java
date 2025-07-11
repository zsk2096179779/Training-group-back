package com.example.advisor_backend.service;

import com.alibaba.excel.EasyExcel;
import com.example.advisor_backend.model.dto.FactorTreeExcelDto;
import com.example.advisor_backend.model.entity.FactorTreeNode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelExportService {

    /**
     * Exports a factor tree to an Excel file and writes it to the HTTP response.
     * @param response The HttpServletResponse to write the file to.
     * @param treeData The root nodes of the tree to export.
     * @param treeType The type of the tree, used for the filename.
     * @throws IOException If an I/O error occurs.
     */
    public void exportFactorTree(HttpServletResponse response, List<FactorTreeNode> treeData, String treeType) throws IOException {
        List<FactorTreeExcelDto> flatData = new ArrayList<>();
        for (FactorTreeNode rootNode : treeData) {
            flattenNode(rootNode, "", flatData);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String fileName = URLEncoder.encode(treeType + "-因子树", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), FactorTreeExcelDto.class)
                 .sheet("因子树")
                 .doWrite(flatData);
    }

    /**
     * Recursively flattens a tree node into a DTO list and populates a map for path lookup.
     */
    private void flattenNode(FactorTreeNode node, String parentPath, List<FactorTreeExcelDto> flatList) {
        FactorTreeExcelDto dto = new FactorTreeExcelDto();
        dto.setName(node.getName());
        dto.setTreeType(node.getTreeType());
        dto.setSortOrder(node.getSortOrder());
        dto.setTreeName(node.getTreeName());
        String currentPath = parentPath.isEmpty() ? node.getName() : parentPath + "/" + node.getName();
        dto.setPath(currentPath);
        flatList.add(dto);
        if (node.getChildren() != null) {
            for (FactorTreeNode child : node.getChildren()) {
                flattenNode(child, currentPath, flatList);
            }
        }
    }
} 