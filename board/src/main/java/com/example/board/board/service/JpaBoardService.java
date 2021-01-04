package com.example.board.board.service;

import java.util.List;

import com.example.board.board.entity.BoardEntity;
import com.example.board.board.entity.BoardFileEntity;

import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * BoardService
 */
public interface JpaBoardService {

    List<BoardEntity> selectBoardList() throws Exception;

    void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    BoardEntity selectBoardDetail(int boardIdx) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;

    BoardFileEntity selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}