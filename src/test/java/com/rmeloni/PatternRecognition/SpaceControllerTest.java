package com.rmeloni.PatternRecognition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.presentation.SpaceController;
import com.rmeloni.PatternRecognition.service.SpaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SpaceController.class)
public class SpaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SpaceService spaceService; // TODO rename

    @Test
    public void Should_SaveNewPoint_When_InputIsCorrect() throws Exception {
        Point p = new Point(3, 4);
        Mockito.when(spaceService.addPoint(Mockito.any(Point.class))).thenReturn(p);
        String requestBody = "{ \"x\": 3, \"y\": 4 }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.x").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.y").value(4))
                .andReturn();
    }

    @Test
    void Should_ReturnOk_When_InputIsNumber() throws Exception {
        String requestBody = "{ \"x\": 3, \"y\": 4 }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void Should_ReturnOk_When_InputIsCorrectString() throws Exception {
        String requestBody = "{ \"x\": 3, \"y\": \"4\" }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void Should_ReturnBadRequest_When_InputIsNull() throws Exception {
        String requestBody = "{ \"x\": 3 }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print());

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void Should_ReturnBadRequest_When_InputIsWrongString() throws Exception {
        String requestBody = "{ \"x\": 3, \"y\": \"4a\" }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print());

        resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}