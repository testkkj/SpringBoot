package com.example.board.board.mapper;

import java.util.List;

import com.example.board.board.dto.BoardDto;
import com.example.board.board.dto.BoardFileDto;

import org.apache.ibatis.annotations.Mapper;

/**
 * BoardMapper
 */
@Mapper
public interface BoardMapper {

    List<BoardDto> selectBoardList() throws Exception;

    void insertBoard(BoardDto boardDto) throws Exception;

    void updateHitCount(int boardIdx) throws Exception;

    BoardDto selectBoardDetail(int boardIdx) throws Exception;

    void updateBoard(BoardDto boardDto) throws Exception;

    void deleteBoard(int boardIdx) throws Exception;

    void insertBoardFileList(List<BoardFileDto> list) throws Exception;
}