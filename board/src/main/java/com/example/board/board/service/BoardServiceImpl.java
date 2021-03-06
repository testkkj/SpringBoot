package com.example.board.board.service;

import java.util.Iterator;
import java.util.List;

import com.example.board.board.dto.BoardDto;
import com.example.board.board.dto.BoardFileDto;
import com.example.board.board.mapper.BoardMapper;
import com.example.board.common.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * BoardServiceImpl
 */
@Service
public class BoardServiceImpl implements BoardService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileUtils fileutils;

    @Override
    public List<BoardDto> selectBoardList() throws Exception {
        return boardMapper.selectBoardList();
    }

    @Override
    public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardMapper.insertBoard(boardDto);
        List<BoardFileDto> list = fileutils.parseFileInfo(boardDto.getBoardIdx(), multipartHttpServletRequest);
        if (CollectionUtils.isEmpty(list) == false) {
            boardMapper.insertBoardFileList(list);
        }
        // 활성화하면 업로드된 파일 정보가 로그에 출력됨
        // if (ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
        // Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        // String name;
        // while (iterator.hasNext()) {
        // name = iterator.next();
        // log.debug("file tag name : "+name);
        // List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
        // for (MultipartFile multipartFile : list) {
        // log.debug("start file information");
        // log.debug("file name : "+multipartFile.getOriginalFilename());
        // log.debug("file size : "+multipartFile.getSize());
        // log.debug("file content type : "+multipartFile.getContentType());
        // log.debug("end file information.\n");
        // }
        // }
        // }
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception {
        BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
        boardDto.setFileList(fileList);

        boardMapper.updateHitCount(boardIdx);

        return boardDto;
    }

    @Override
    public void updateBoard(BoardDto boardDto) throws Exception {
        boardMapper.updateBoard(boardDto);
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        boardMapper.deleteBoard(boardIdx);
    }

    @Override
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }
}