package com.example.board.board.dto;

import lombok.Data;

/**
 * BoardDto
 */
@Data
public class BoardDto {

    private int boardIdx;
    private String title;
    private String contents;
    private int hitCnt;
    private String creatorId;
    private String createdDatetime;
    private String updatedId;
    private String updatedDatetime;
}