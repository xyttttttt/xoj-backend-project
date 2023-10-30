package com.xyt.xoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyt.xoj.model.dto.question.QuestionQueryRequest;
import com.xyt.xoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xyt.xoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xyt.xoj.model.entity.Question;
import com.xyt.xoj.model.entity.QuestionSubmit;
import com.xyt.xoj.model.entity.User;
import com.xyt.xoj.model.vo.QuestionSubmitVO;
import com.xyt.xoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 16048
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2023-10-10 21:03:42
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 点赞
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

/*    *//**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     *//*
    int doQuestionSubmitInner(long userId, long postId);*/

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
