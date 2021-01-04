package com.example.board.board.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.example.board.board.dto.BoardDto;
import com.example.board.board.dto.BoardFileDto;
import com.example.board.board.entity.BoardEntity;
import com.example.board.board.entity.BoardFileEntity;
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
public class JpaBoardServiceImpl implements JpaBoardService {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JpaBoardRepository jpaBoardRepository;

    @Autowired
    FileUtils fileutils;

    @Override
    public List<BoardEntity> selectBoardList() throws Exception {
        return jpaBoardRepository.selectBoardList();
    }

    @Override
    public void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest multipartHttpServletRequest)
            throws Exception {
        boardEntity.setCreatorId("admin");
        List<BoardFileEntity> list = fileutils.parseFileInfo(multipartHttpServletRequest);
        if (CollectionUtils.isEmpty(list) == false) {
            boardEntity.setFileList(list);
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
    public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
        Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
        if (optional.isPresent()) {
            BoardEntity boardEntity = optional.get();
            boardEntity.setHitCnt(boardEntity.getHitCnt() + 1);
            jpaBoardRepository.save(boardEntity);

            return boardEntity;
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        jpaBoardRepository.deleteById(boardIdx);
    }

    @Override
    public BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception {
        BoardFileEntity boardFileEntity = jpaBoardRepository.findBoardFile(boardIdx, idx);
        return boardFileEntity;
    }
}