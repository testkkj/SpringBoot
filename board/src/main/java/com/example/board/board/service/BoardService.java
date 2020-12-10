package com.example.board.board.service;

import java.util.List;

import com.example.board.board.dto.BoardDto;

/**
 * BoardService
 */
public interface BoardService {

    List<BoardDto> selectBoardList() throws Exception;
}