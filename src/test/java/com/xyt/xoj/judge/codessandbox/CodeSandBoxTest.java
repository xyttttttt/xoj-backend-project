package com.xyt.xoj.judge.codessandbox;

import cn.hutool.core.lang.UUID;
import com.xyt.xoj.judge.codessandbox.impl.ExampleCodeSandBoxImpl;
import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeRequest;
import com.xyt.xoj.judge.codessandbox.model.ExecuteCodeResponse;
import com.xyt.xoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CodeSandBoxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
            CodeSandBox codeSandBox = new ExampleCodeSandBoxImpl();
            String code = "\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"test..........\");\n" +
                    "    }";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2","3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
            Assertions.assertNotNull(executeCodeResponse);

    }

    @Test
    void executeCodeByValue() {
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
        String code = "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"test..........\");\n" +
                "    }";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2","3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeByProxy() {
        CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);

        codeSandBox = new CodeSandboxProxy(codeSandBox);

        String code = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"结果是：\" + (a + b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2","3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            String type = sc.next();
            CodeSandBox codeSandBox = CodeSandboxFactory.newInstance(type);
            String code = "\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"test..........\");\n" +
                    "    }";
            String language = QuestionSubmitLanguageEnum.JAVA.getValue();
            List<String> inputList = Arrays.asList("1 2","3 4");
            ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                    .code(code)
                    .language(language)
                    .inputList(inputList)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        }
    }



}