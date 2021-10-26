package com.koscom.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void hello주소로요청이오면_hello가_리턴된다() throws  Exception{
        String expectResult = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectResult));
    }

    @Test
    void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                .param("name",name)
                .param("amount", String.valueOf(amount))) // string으로 받아야 한다.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));
                /*
                    달러는 json의 시작을 의미한다. $.name은 name의 field를 의미한다.
                */
    }

    @Test
    void amount가없으면_응답코드가400이_된다() throws Exception {
        String name = "hello";

        mvc.perform(get("/hello/dto")
                        .param("name",name))
                .andExpect(status().isBadRequest());
    }
}
