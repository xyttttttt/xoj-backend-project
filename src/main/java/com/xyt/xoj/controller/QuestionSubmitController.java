package com.xyt.xoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyt.xoj.common.BaseResponse;
import com.xyt.xoj.common.ErrorCode;
import com.xyt.xoj.common.ResultUtils;
import com.xyt.xoj.exception.BusinessException;
import com.xyt.xoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xyt.xoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xyt.xoj.model.entity.QuestionSubmit;
import com.xyt.xoj.model.entity.User;

import com.xyt.xoj.model.vo.QuestionSubmitVO;
import com.xyt.xoj.service.QuestionSubmitService;
import com.xyt.xoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
//@RequestMapping("/question_submit")
@Slf4j
//@Deprecated
public class QuestionSubmitController {

}
