package com.example.board.board.service;

import java.util.List;

import com.example.board.board.dto.BoardDto;

/**
 * BoardService
 */
public interface BoardService {

    List<BoardDto> selectBoardList() throws Exception;

    void insertBoard(BoardDto boardDto) throws Exception;

    BoardDto selectBoardDetail(int boardIdx) throws Exception;
}