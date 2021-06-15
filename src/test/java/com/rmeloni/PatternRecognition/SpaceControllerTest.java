package com.rmeloni.PatternRecognition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmeloni.PatternRecognition.domain.Point;
import com.rmeloni.PatternRecognition.domain.Segment;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SpaceController.class)
public class SpaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SpaceService spaceService;

    /* POST point */
    @Test
    void POSTPoint_Should_SaveNewPoint_When_InputIsCorrect() throws Exception {
        Point p = new Point(3, 4);
        Mockito.when(spaceService.addPoint(Mockito.any(Point.class))).thenReturn(p);
        String requestBody = "{ \"x\": 3, \"y\": 4 }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.x").value(3))
                .andExpect(jsonPath("$.y").value(4))
                .andReturn();
    }

    @Test
    void POSTPoint_Should_ReturnOk_When_InputIsNumber() throws Exception {
        String requestBody = "{ \"x\": 3, \"y\": 4 }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    void POSTPoint_Should_ReturnOk_When_InputIsCorrectString() throws Exception {
        String requestBody = "{ \"x\": 3, \"y\": \"4\" }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    void POSTPoint_Should_ReturnOk_When_InputIsCorrectStringWithExponentialNotation() throws Exception {
        String requestBody = "{ \"x\": 3, \"y\": \"1e10\" }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    void POSTPoint_Should_ReturnBadRequest_When_JSONIsIncomplete() throws Exception {
        String requestBody = "{ \"x\": 3 }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void POSTPoint_Should_ReturnBadRequest_When_JSONIsInvalid() throws Exception {
        String requestBody = "{ \"x\": 3, }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void POSTPoint_Should_ReturnBadRequest_When_InputIsWrongString() throws Exception {
        String requestBody = "{ \"x\": 3, \"y\": \"4a\" }";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void POSTPoint_Should_ReturnBadRequest_When_InputIsEmptyBody() throws Exception {
        String requestBody = "";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/point")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    /* GET lines */
    @Test
    void GETLines_Should_ReturnListOfSegments_When_InputIsCorrect() throws Exception {
        Segment s1 = new Segment() {{
            setSlope(3.);
            setvAxisIntercept(2.);
            setPoints(new HashSet<>() {{
                add(new Point(0, 2));
                add(new Point(1, 5));
                add(new Point(-1.5, -2.5));
                add(new Point(-2, -4));
            }});
        }};

        Segment s2 = new Segment() {{
            setSlope(Double.POSITIVE_INFINITY);
            setvAxisIntercept(Double.NaN);
            setPoints(new HashSet<>() {{
                add(new Point(4.5, -1));
                add(new Point(4.5, 0));
                add(new Point(4.5, 1));
            }});
        }};

        Segment s3 = new Segment() {{
            setSlope(0);
            setvAxisIntercept(14);
            setPoints(new HashSet<>() {{
                add(new Point(-1, 14));
                add(new Point(0, 14));
                add(new Point(1, 14));
            }});
        }};

        List<Segment> segmentListOfThreePoints = new ArrayList<>() {{
            add(s1);
            add(s2);
            add(s3);
        }};

        List<Segment> segmentListOfFourPoints = new ArrayList<>() {{
            add(s1);
        }};

        Mockito.when(spaceService.getListOfSegmentsWithAtLeastNCollinearPoints(3)).thenReturn(segmentListOfThreePoints);
        String request = "/lines/3";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        Mockito.when(spaceService.getListOfSegmentsWithAtLeastNCollinearPoints(4)).thenReturn(segmentListOfFourPoints);
        request = "/lines/4";

        requestBuilder = MockMvcRequestBuilders.get(request);

        resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].slope", is(3.)))
                .andExpect(jsonPath("$[0].vAxisIntercept", is(2.)))
                .andExpect(jsonPath("$[0].hAxisIntercept", is(0.)))
                .andExpect(jsonPath("$[0].points", hasSize(4)))
                .andExpect(jsonPath("$[0].points[0].x", isA(double.class)))
                .andExpect(jsonPath("$[0].points[0].y", isA(double.class)));
    }

    @Test
    void GETLines_Should_ReturnOk_When_ParamIsExactly2() throws Exception {
        String request = "/lines/2";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    void GETLines_Should_ReturnBadRequest_When_ParamIsPositiveLessThanTwo() throws Exception {
        String request = "/lines/1";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void GETLines_Should_ReturnBadRequest_When_ParamIsZero() throws Exception {
        String request = "/lines/0";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void GETLines_Should_ReturnBadRequest_When_ParamIsNegative() throws Exception {
        String request = "/lines/-2";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void GETLines_Should_ReturnBadRequest_When_ParamIsDouble() throws Exception {
        String request = "/lines/2.3";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isBadRequest());
    }

    /* DELETE */
    @Test
    void DELETE_Should_ReturnOK_When_Called() throws Exception {
        String request = "/delete";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    void DELETE_Should_ReturnEmptyList_When_Called() throws Exception {
        String request = "/delete";
        Mockito.doNothing().when(spaceService).clearSpace();
        Mockito.when(spaceService.getPoints()).thenReturn(new HashSet<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(jsonPath("$", hasSize(0)));
    }

    /* GET space */

    @Test
    void GETSpace_Should_ReturnOK_When_Called() throws Exception {
        String request = "/space";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk());
    }

    @Test
    void GETSpace_Should_ReturnAllPoints_When_Called() throws Exception {
        String request = "/space";
        Set<Point> points = new HashSet<>() {{
            add(new Point(3, 4));
            add(new Point(5, 2));
        }};
        Mockito.when(spaceService.getPoints()).thenReturn(points);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(request);

        ResultActions resultActions = mockMvc.perform(requestBuilder)
                .andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].x", is(3.)))
                .andExpect(jsonPath("$[0].y", is(4.)))
                .andExpect(jsonPath("$[1].x", is(5.)))
                .andExpect(jsonPath("$[1].y", is(2.)));
    }
}