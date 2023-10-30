package com.xyt.xoj.judge;


import com.xyt.xoj.model.entity.QuestionSubmit;


public interface JudgeService {

    /**
     * 判题服务
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
