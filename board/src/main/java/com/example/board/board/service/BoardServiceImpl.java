package com.example.board.board.service;

import java.util.List;

import com.example.board.board.dto.BoardDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BoardServiceImpl
 */
@Service
public class BoardServiceImpl {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }
}