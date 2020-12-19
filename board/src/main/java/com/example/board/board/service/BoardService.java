package com.example.board.board.service;

import java.util.List;

import com.example.board.board.dto.BoardDto;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * BoardService
 */
public interface BoardService {

    List<BoardDto> selectBoardList() throws Exception;

    void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    BoardDto selectBoardDetail(int boardIdx) throws Exception;

    void updateBoard(BoardDto boardDto) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;
}