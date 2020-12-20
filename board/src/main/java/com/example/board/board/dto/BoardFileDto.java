package com.example.board.board.dto;

import lombok.Data;

/**
 * BoardFileDto
 */
@Data
public class BoardFileDto {
    private int idx;
    private int boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
}