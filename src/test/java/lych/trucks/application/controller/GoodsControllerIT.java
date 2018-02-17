package lych.trucks.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lych.trucks.application.dto.request.GoodsRequest;
import lych.trucks.domain.model.Goods;
import lych.trucks.domain.model.Order;
import lych.trucks.domain.repository.GoodsRepository;
import lych.trucks.domain.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GoodsControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrderRepository orderRepository;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private GoodsRequest request;

    private Integer goodsId;

    private Integer orderId;

    private static final String NAME = "name";

    private static final String TYPE = "type";

    private static final Double WEIGHT = 12.0;

    private static final Double VOLUME = 11.0;

    private static final Integer CUSTOMER_ID = 1;

    private static final Integer INCORRECT_ORDER_ID = 22;

    private static final Integer INCORRECT_GOODS_ID = 212;

    private static final String INCORRECT_GOODS_TYPE = "wrong";

    private static final String INCORRECT_GOODS_NAME = "wrongNAME";

    @Before
    public void setUp() {

        orderRepository.deleteAll();
        goodsRepository.deleteAll();

        final Order order = orderRepository.save(new Order());

        orderId = order.getOrderId();

        final Goods goods = new Goods();

        goods.setOrder(order);
        goods.setName(NAME);
        goods.setGoodsType(TYPE);

        goodsId = goodsRepository.save(goods).getGoodsId();

        request = new GoodsRequest(NAME, WEIGHT, VOLUME, TYPE);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void create() throws Exception {

        final String content = "new";

        request.setName(content);

        mockMvc.perform(request(POST, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/" + orderId + "/goods")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(content)));
    }

    @Test
    public void update() throws Exception {

        final String content = "update";

        request.setName(content);
        request.setGoodsId(goodsId);

        mockMvc.perform(request(PUT, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goodsId", is(goodsId)))
                .andExpect(jsonPath("$.name", is(content)));
    }

    @Test
    public void delete() throws Exception {

        mockMvc.perform(request(DELETE, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods/" + goodsId)
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(goodsRepository.exists(goodsId), is(false));
    }

    @Test
    public void fetchAll() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/" + orderId + "/goods")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].goodsId", is(goodsId)))
                .andExpect(jsonPath("$.[0].name", is(NAME)))
                .andExpect(jsonPath("$.[0].goodsType", is(TYPE)));
    }

    @Test
    public void fetchAll_call_incorrectOrderId_expect_IllegalArgument() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/" + INCORRECT_ORDER_ID
                + "/goods")
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Goods not found."
                        + " Goods in Order with this Id: '22' not exists.")))
                .andExpect(jsonPath("$.errorId", notNullValue()));
    }

    @Test
    public void fetch() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods/" + goodsId)
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.goodsId", is(goodsId)))
                .andExpect(jsonPath("$.name", is(NAME)))
                .andExpect(jsonPath("$.goodsType", is(TYPE)));
    }

    @Test
    public void fetch_call_incorrectId_expect_IllegalArgument() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods/" + INCORRECT_GOODS_ID)
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Goods not found."
                        + " Goods with this Id: '212' not exists.")))
                .andExpect(jsonPath("$.errorId", notNullValue()));
    }

    @Test
    public void fetchByType() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods/type/" + TYPE)
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].goodsId", is(goodsId)))
                .andExpect(jsonPath("$.[0].goodsType", is(TYPE)));
    }

    @Test
    public void fetchByType_call_incorrectType_expect_illegalArgument() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods/type/" + INCORRECT_GOODS_TYPE)
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Goods not found."
                        + " Goods with this type: 'wrong' not exists.")))
                .andExpect(jsonPath("$.errorId", notNullValue()));
    }

    @Test
    public void fetchByName() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods/name/" + NAME)
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].goodsId", is(goodsId)))
                .andExpect(jsonPath("$.[0].name", is(NAME)));
    }

    @Test
    public void fetchByName_call_incorrectName_expect_IllegalArgument() throws Exception {

        mockMvc.perform(request(GET, "/cargo/v1/customers/" + CUSTOMER_ID + "/orders/"
                + orderId + "/goods/name/" + INCORRECT_GOODS_NAME)
                .accept(APPLICATION_JSON_UTF8_VALUE)
                .contentType(APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Goods not found."
                        + " Goods with this name: 'wrongNAME' not exists.")))
                .andExpect(jsonPath("$.errorId", notNullValue()));
    }
}
